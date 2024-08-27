### Truy vấn với các điều kiện mở rộng trong Spring Data JPA

Spring Data JPA cung cấp một cách đơn giản và mạnh mẽ để truy vấn cơ sở dữ liệu bằng cách sử dụng các phương thức trong các repository mà không cần phải viết SQL thủ công. Với Spring Data JPA, bạn có thể tạo các truy vấn phức tạp bằng cách sử dụng các từ khóa truy vấn hoặc sử dụng các kỹ thuật nâng cao như JPQL, Native Query, Specifications, và Query by Example.

### 1. Từ khóa truy vấn trong Spring Data JPA

Spring Data JPA cho phép bạn tạo các truy vấn phức tạp chỉ bằng cách đặt tên phương thức trong Repository interface. Dưới đây là một số từ khóa phổ biến được sử dụng:

- `findBy`, `readBy`, `queryBy`, `getBy` - Truy vấn theo một thuộc tính.
- `And`, `Or` - Kết hợp nhiều điều kiện.
- `Between` - Truy vấn với điều kiện nằm trong khoảng.
- `LessThan`, `GreaterThan`, `LessThanEqual`, `GreaterThanEqual` - Truy vấn với điều kiện lớn hơn/nhỏ hơn.
- `Like` - Truy vấn với điều kiện tương tự (sử dụng ký tự đại diện).
- `In` - Truy vấn với điều kiện nằm trong một tập hợp.
- `OrderBy` - Sắp xếp kết quả.

#### Ví dụ:

Giả sử bạn có một `Student` entity:

```java
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private int age;
    private String email;

    // Constructors, getters, setters
}
```

Và bạn muốn tạo các truy vấn với điều kiện mở rộng:

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Truy vấn theo tên
    List<Student> findByFirstName(String firstName);

    // Truy vấn theo tuổi lớn hơn hoặc bằng
    List<Student> findByAgeGreaterThanEqual(int age);

    // Truy vấn theo tên và tuổi
    List<Student> findByFirstNameAndAge(String firstName, int age);

    // Truy vấn với tên gần đúng (chứa ký tự "an")
    List<Student> findByFirstNameLike(String namePattern);

    // Truy vấn với tuổi nằm trong khoảng
    List<Student> findByAgeBetween(int startAge, int endAge);

    // Truy vấn với điều kiện sắp xếp theo tuổi giảm dần
    List<Student> findByLastNameOrderByAgeDesc(String lastName);
}
```

**Giải thích:**

- **`findByFirstName(String firstName)`**: Truy vấn tất cả `Student` có `firstName` bằng giá trị được truyền vào.
- **`findByAgeGreaterThanEqual(int age)`**: Truy vấn tất cả `Student` có `age` lớn hơn hoặc bằng giá trị `age` được truyền vào.
- **`findByFirstNameAndAge(String firstName, int age)`**: Truy vấn tất cả `Student` có `firstName` và `age` tương ứng với giá trị được truyền vào.
- **`findByFirstNameLike(String namePattern)`**: Truy vấn tất cả `Student` có `firstName` gần đúng với mẫu ký tự được truyền vào (ví dụ: `namePattern = "%an%"`).
- **`findByAgeBetween(int startAge, int endAge)`**: Truy vấn tất cả `Student` có `age` nằm trong khoảng từ `startAge` đến `endAge`.
- **`findByLastNameOrderByAgeDesc(String lastName)`**: Truy vấn tất cả `Student` có `lastName` tương ứng với giá trị được truyền vào và sắp xếp kết quả theo `age` giảm dần.

### 2. Sử dụng JPQL và Native Query

Ngoài việc sử dụng các từ khóa, bạn có thể sử dụng JPQL (Java Persistence Query Language) hoặc Native Query để viết các truy vấn phức tạp hơn.

#### JPQL:

```java
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s WHERE s.age > :age")
    List<Student> findStudentsOlderThan(int age);
}
```

**Giải thích:**

- **JPQL**: Cú pháp giống với SQL, nhưng hoạt động trên các đối tượng Entity thay vì các bảng cơ sở dữ liệu. Trong ví dụ này, chúng ta đang truy vấn tất cả `Student` có `age` lớn hơn giá trị `age` được truyền vào.

#### Native Query:

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT * FROM Student WHERE age > :age", nativeQuery = true)
    List<Student> findStudentsOlderThan(@Param("age") int age);
}
```

**Giải thích:**

- **Native Query**: Truy vấn trực tiếp bằng SQL. Trong ví dụ này, chúng ta đang sử dụng SQL để truy vấn tất cả `Student` có `age` lớn hơn giá trị `age` được truyền vào.

### 3. Sử dụng Specifications

Spring Data JPA cũng hỗ trợ Specifications, cho phép bạn tạo các truy vấn động, linh hoạt hơn bằng cách kết hợp nhiều điều kiện.

```java
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
}

public class StudentSpecification {

    public static Specification<Student> hasFirstName(String firstName) {
        return (root, query, builder) -> builder.equal(root.get("firstName"), firstName);
    }

    public static Specification<Student> hasAgeGreaterThan(int age) {
        return (root, query, builder) -> builder.greaterThan(root.get("age"), age);
    }
}
```

**Sử dụng Specification:**

```java
List<Student> students = studentRepository.findAll(
        Specification.where(StudentSpecification.hasFirstName("John"))
                     .and(StudentSpecification.hasAgeGreaterThan(18))
);
```

**Giải thích:**

- **Specification**: Là một kỹ thuật để xây dựng các truy vấn phức tạp một cách động. Bạn có thể kết hợp các điều kiện với nhau bằng `and`, `or`, `not`, v.v.

### 4. Query by Example (QBE)

Query by Example là một cách tiếp cận khác để thực hiện truy vấn động mà không cần phải viết bất kỳ câu lệnh JPQL hay SQL nào.

```java
import org.springframework.data.domain.Example;

Student studentExample = new Student();
studentExample.setFirstName("John");

Example<Student> example = Example.of(studentExample);

List<Student> students = studentRepository.findAll(example);
```

**Giải thích:**

- **Query by Example**: Cách truy vấn bằng cách tạo một đối tượng mẫu (`example`) với các thuộc tính cần truy vấn. Spring Data JPA sẽ sử dụng các giá trị của đối tượng này để xây dựng truy vấn.

### Tổng kết

- **Spring Data JPA** cung cấp nhiều cách để truy vấn dữ liệu một cách linh hoạt và dễ sử dụng, từ việc sử dụng các từ khóa đơn giản đến các kỹ thuật phức tạp như JPQL, Native Query, Specifications và Query by Example.
- **Các phương thức truy vấn** được định nghĩa trong Repository interface có thể được tạo tự động dựa trên tên phương thức hoặc tùy chỉnh bằng cách sử dụng annotation `@Query`.
- **Specifications và Query by Example** là các kỹ thuật nâng cao cho phép bạn xây dựng các truy vấn động mà không cần viết mã SQL thủ công.

Với các kỹ thuật này, bạn có thể tạo ra các truy vấn phức tạp và mạnh mẽ trong Spring Data JPA một cách hiệu quả.