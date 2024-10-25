## **Ngôn ngữ Truy vấn REST với Spring Data JPA Specifications**
**1. Tổng quan**  
Trong hướng dẫn này, chúng ta sẽ xây dựng một API REST tìm kiếm/lọc bằng cách sử dụng Spring Data JPA và Specifications.

Chúng ta đã bắt đầu tìm hiểu về một ngôn ngữ truy vấn trong bài viết đầu tiên của loạt bài này với một giải pháp dựa trên JPA Criteria.  
Vậy tại sao lại cần một ngôn ngữ truy vấn? Bởi vì việc tìm kiếm/lọc tài nguyên theo các trường đơn giản là không đủ đối với các API quá phức tạp. Một ngôn ngữ truy vấn linh hoạt hơn và cho phép chúng ta lọc đúng những tài nguyên mà chúng ta cần.

**2. Thực thể User (User Entity)**  
Đầu tiên, hãy bắt đầu với một thực thể User đơn giản cho API tìm kiếm của chúng ta:
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    private int age;
    
    // standard getters and setters
}
```

**3. Lọc bằng Specification**  
Giờ hãy đi thẳng vào phần thú vị nhất của vấn đề: truy vấn với các Spring Data JPA Specifications tùy chỉnh.  
Chúng ta sẽ tạo một `UserSpecification` triển khai giao diện `Specification` và truyền vào điều kiện của riêng chúng ta để xây dựng truy vấn thực tế:
```java
public class UserSpecification implements Specification<User> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
      (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
 
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
```
Như chúng ta có thể thấy, chúng ta tạo một `Specification` dựa trên một số điều kiện đơn giản mà chúng ta biểu diễn trong lớp `SearchCriteria` sau:
```java
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
```
Việc triển khai `SearchCriteria` giữ một biểu diễn cơ bản của điều kiện, và dựa trên điều kiện này, chúng ta sẽ xây dựng truy vấn:
- `key`: tên trường, ví dụ, `firstName`, `age`, v.v.
- `operation`: toán tử, ví dụ, bằng, nhỏ hơn, v.v.
- `value`: giá trị của trường, ví dụ, `john`, `25`, v.v.

Tất nhiên, cách triển khai này còn đơn giản và có thể được cải thiện. Tuy nhiên, nó là một nền tảng vững chắc cho các thao tác mạnh mẽ và linh hoạt mà chúng ta cần.

**4. Repository User**  
Tiếp theo, hãy xem qua `UserRepository`.  
Chúng ta chỉ cần mở rộng `JpaSpecificationExecutor` để sử dụng các API của Specification mới:
```java
public interface UserRepository 
  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {}
```

**5. Kiểm tra các Truy vấn Tìm kiếm**  
Giờ hãy kiểm tra API tìm kiếm mới.  
Đầu tiên, hãy tạo một số người dùng để chuẩn bị sẵn cho các bài kiểm tra:
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class })
@Transactional
@TransactionConfiguration
public class JPASpecificationIntegrationTest {

    @Autowired
    private UserRepository repository;

    private User userJohn;
    private User userTom;

    @Before
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        repository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        repository.save(userTom);
    }
}
```

Tiếp theo, hãy xem cách tìm những người dùng có họ cho trước:
```java
@Test
public void givenLast_whenGettingListOfUsers_thenCorrect() {
    UserSpecification spec = 
      new UserSpecification(new SearchCriteria("lastName", ":", "doe"));
    
    List<User> results = repository.findAll(spec);

    assertThat(userJohn, isIn(results));
    assertThat(userTom, isIn(results));
}
```

Giờ chúng ta sẽ tìm người dùng với cả tên và họ:
```java
@Test
public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
    UserSpecification spec1 = 
      new UserSpecification(new SearchCriteria("firstName", ":", "john"));
    UserSpecification spec2 = 
      new UserSpecification(new SearchCriteria("lastName", ":", "doe"));
    
    List<User> results = repository.findAll(Specification.where(spec1).and(spec2));

    assertThat(userJohn, isIn(results));
    assertThat(userTom, not(isIn(results)));
}
```
Lưu ý: Chúng ta đã sử dụng `where` và `and` để kết hợp các Specifications.

Tiếp theo, hãy tìm người dùng với họ cho trước và tuổi tối thiểu:
```java
@Test
public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
    UserSpecification spec1 = 
      new UserSpecification(new SearchCriteria("age", ">", "25"));
    UserSpecification spec2 = 
      new UserSpecification(new SearchCriteria("lastName", ":", "doe"));

    List<User> results = 
      repository.findAll(Specification.where(spec1).and(spec2));

    assertThat(userTom, isIn(results));
    assertThat(userJohn, not(isIn(results)));
}
```

Giờ chúng ta sẽ xem cách tìm một người dùng không tồn tại:
```java
@Test
public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
    UserSpecification spec1 = 
      new UserSpecification(new SearchCriteria("firstName", ":", "Adam"));
    UserSpecification spec2 = 
      new UserSpecification(new SearchCriteria("lastName", ":", "Fox"));

    List<User> results = 
      repository.findAll(Specification.where(spec1).and(spec2));

    assertThat(userJohn, not(isIn(results)));
    assertThat(userTom, not(isIn(results)));  
}
```

Cuối cùng, chúng ta sẽ tìm một người dùng dựa trên một phần của tên:
```java
@Test
public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
    UserSpecification spec = 
      new UserSpecification(new SearchCriteria("firstName", ":", "jo"));
    
    List<User> results = repository.findAll(spec);

    assertThat(userJohn, isIn(results));
    assertThat(userTom, not(isIn(results)));
}
```

6. Kết hợp Specifications  
   Tiếp theo, hãy xem cách kết hợp các **Specifications** tùy chỉnh của chúng ta để sử dụng nhiều ràng buộc và lọc theo nhiều tiêu chí khác nhau.  
   Chúng ta sẽ triển khai một trình xây dựng (**builder**) — **UserSpecificationsBuilder** — để dễ dàng và linh hoạt kết hợp các **Specifications**. Nhưng trước tiên, hãy xem qua đối tượng **SpecSearchCriteria**:

```java
public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public boolean isOrPredicate() {
        return orPredicate;
    }
}
```

```java
public class UserSpecificationsBuilder {
    
    private final List<SpecSearchCriteria> params;

    public UserSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public final UserSpecificationsBuilder with(String key, String operation, Object value, 
      String prefix, String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final UserSpecificationsBuilder with(String orPredicate, String key, String operation, 
      Object value, String prefix, String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // phép toán có thể là phức tạp
                boolean startWithAsterisk = prefix != null && 
                  prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                boolean endWithAsterisk = suffix != null && 
                  suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification build() {
        if (params.size() == 0)
            return null;

        Specification result = new UserSpecification(params.get(0));
     
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
              ? Specification.where(result).or(new UserSpecification(params.get(i))) 
              : Specification.where(result).and(new UserSpecification(params.get(i)));
        }
        
        return result;
    }
}
```

7. **UserController**  
   Cuối cùng, chúng ta hãy sử dụng tính năng tìm kiếm/lọc với **Specifications** mới này và thiết lập REST API bằng cách tạo một **UserController** với một thao tác tìm kiếm đơn giản:

```java
@Controller
public class UserController {

    @Autowired
    private UserRepository repo;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<User> search(@RequestParam(value = "search") String search) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        
        Specification<User> spec = builder.build();
        return repo.findAll(spec);
    }
}
```

Lưu ý rằng để hỗ trợ các hệ thống không dùng tiếng Anh, đối tượng **Pattern** có thể được thay đổi như sau:

```java
Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
```

Dưới đây là một URL thử nghiệm để kiểm tra API:

```
http://localhost:8082/spring-rest-query-language/auth/users?search=lastName:doe,age>25
```

Và đây là phản hồi:

```json
[{
    "id":2,
    "firstName":"tom",
    "lastName":"doe",
    "email":"tom@doe.com",
    "age":26
}]
```

Vì các truy vấn được chia bởi dấu “,” trong ví dụ **Pattern**, nên các thuật ngữ tìm kiếm không thể chứa ký tự này. **Pattern** cũng không khớp với khoảng trắng.  
Nếu chúng ta muốn tìm kiếm các giá trị có chứa dấu phẩy, có thể sử dụng một ký tự phân tách khác như “;”.  
Một lựa chọn khác là thay đổi **Pattern** để tìm kiếm các giá trị nằm giữa dấu ngoặc kép rồi loại bỏ những dấu này khỏi thuật ngữ tìm kiếm:

```java
Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\"([^\"]+)\")");
```

8. **Kết luận**  
   Bài viết này đã đề cập đến một triển khai đơn giản có thể làm nền tảng cho một ngôn ngữ truy vấn REST mạnh mẽ.  
   Chúng ta đã tận dụng tốt **Spring Data Specifications** để giữ cho API tránh khỏi việc phụ thuộc quá nhiều vào domain và có thể xử lý nhiều loại thao tác khác nhau.  
   Triển khai đầy đủ của bài viết này có thể được tìm thấy trong dự án GitHub. Đây là một dự án dựa trên **Maven**, vì vậy nó dễ dàng được import và chạy.