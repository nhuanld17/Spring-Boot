Chúng ta sẽ xem xét cách hoạt động của ba class: `UserController`, `UserSpecificationsBuilder`, và `UserSpecification` trong việc thực hiện tìm kiếm người dùng dựa trên các tiêu chí động. Tôi sẽ mô tả chi tiết các bước và cách thức hoạt động của từng class.

### Mô tả tổng quan

Khi một yêu cầu GET được gửi đến `/users` với một tham số tìm kiếm, các class này sẽ phối hợp để tạo ra một truy vấn dựa trên các tiêu chí được chỉ định trong tham số tìm kiếm đó.

### Bước 1: Gửi yêu cầu tìm kiếm

Giả sử bạn gửi một yêu cầu GET đến `/users` với tham số tìm kiếm như sau:

```
/users?search=username:john,age>25,city:NewYork,
```

### Bước 2: UserController nhận yêu cầu

Khi yêu cầu được gửi, phương thức `search` trong `UserController` sẽ được gọi. Đoạn mã của phương thức `search` như sau:

```java
@GetMapping("/users")
public List<User> search(@RequestParam(value = "search") String search) {
    UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
    Matcher matcher = pattern.matcher(search + ",");
    
    while (matcher.find()) {
        builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
    }

    Specification<User> spec = builder.build();
    List<User> users = userRepository.findAll(spec);
    System.out.println("Users found: " + users); // In ra kết quả tìm kiếm
    return users;
}
```

### Chi tiết hoạt động trong UserController

1. **Khởi tạo UserSpecificationsBuilder**:
   ```java
   UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
   ```
    - Tạo một đối tượng mới của `UserSpecificationsBuilder` để xây dựng các tiêu chí tìm kiếm.

2. **Biểu thức chính quy (Regex)**:
   ```java
   Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
   Matcher matcher = pattern.matcher(search + ",");
   ```
    - Sử dụng regex để tìm các cụm từ trong chuỗi tìm kiếm với cấu trúc: `key:operation:value`. Ví dụ: `username:john`, `age>25`, và `city:NewYork`.

3. **Lặp qua các kết quả tìm thấy**:
   ```java
   while (matcher.find()) {
       builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
   }
   ```
    - Mỗi khi một cụm từ được tìm thấy, phương thức `with` của `UserSpecificationsBuilder` sẽ được gọi để thêm tiêu chí vào danh sách các tham số.

### Bước 3: Xây dựng Specification

Sau khi hoàn tất vòng lặp, chúng ta sẽ có một danh sách các tiêu chí tìm kiếm trong `builder`. Bây giờ, chúng ta cần xây dựng một `Specification<User>`:

```java
Specification<User> spec = builder.build();
```

### Chi tiết hoạt động trong UserSpecificationsBuilder

1. **Xây dựng Specification**:
   ```java
   public Specification<User> build() {
       if (params.isEmpty()) {
           return null;
       }
       
       Specification<User> result = new UserSpecification(params.get(0));
       
       for (int i = 1; i < params.size(); i++) {
           result = Specification.where(result).and(new UserSpecification(params.get(i)));
       }
       
       return result;
   }
   ```
    - Nếu không có tham số nào, nó trả về null.
    - Nếu có, nó tạo ra một `UserSpecification` đầu tiên từ tham số đầu tiên trong danh sách.
    - Sau đó, nó lặp qua các tham số còn lại và kết hợp chúng bằng cách sử dụng phương thức `and` của `Specification`.

### Bước 4: Tìm kiếm người dùng trong UserRepository

Sau khi có được `Specification`, phương thức sau đây sẽ được gọi:

```java
List<User> users = userRepository.findAll(spec);
```

### Chi tiết hoạt động trong UserSpecification

Hàm `toPredicate` trong `UserSpecification` sẽ được gọi bởi Spring Data JPA để chuyển đổi tiêu chí tìm kiếm thành một điều kiện SQL:

```java
@Override
public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (criteria.getOperation().equalsIgnoreCase(">")) {
        return builder.greaterThan(
                root.<String>get(criteria.getKey()), (String) criteria.getValue()
        );
    } else if (criteria.getOperation().equalsIgnoreCase("<")) {
        return builder.lessThan(
                root.<String>get(criteria.getKey()), (String) criteria.getValue()
        );
    } else if (criteria.getOperation().equalsIgnoreCase(":")) {
        if (root.get(criteria.getKey()).getJavaType() == String.class) {
            return builder.like(
                    root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%"
            );
        } else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
    }
    
    return null;
}
```

- Dựa trên các phép toán đã được chỉ định (`:`, `>`, `<`), nó sẽ tạo ra các điều kiện SQL tương ứng.
- Ví dụ:
    - Đối với `username:john`, sẽ tạo điều kiện `LIKE`.
    - Đối với `age>25`, sẽ tạo điều kiện `greaterThan`.
    - Đối với `city:NewYork`, sẽ tạo điều kiện `LIKE`.

### Bước 5: Trả về kết quả

Khi tất cả các điều kiện đã được thiết lập và truy vấn đã được thực hiện, danh sách người dùng sẽ được trả về và in ra console:

```java
System.out.println("Users found: " + users); // In ra kết quả tìm kiếm
```

### Ví dụ tổng quan

Giả sử bạn có một cơ sở dữ liệu với các bản ghi người dùng sau:

| ID | Username | Age | City      |
|----|----------|-----|-----------|
| 1  | john     | 30  | NewYork   |
| 2  | jane     | 28  | NewYork   |
| 3  | mike     | 35  | LosAngeles|
| 4  | johnny   | 22  | NewYork   |

Với yêu cầu `/users?search=username:john,age>25,city:NewYork,`, mã của bạn sẽ hoạt động như sau:

1. Tìm các tiêu chí: `username:john`, `age>25`, `city:NewYork`.
2. Xây dựng `Specification` từ các tiêu chí này.
3. Gọi `userRepository.findAll(spec)` với các điều kiện:
    - `username LIKE '%john%'`
    - `age > 25`
    - `city LIKE '%NewYork%'`

Kết quả tìm kiếm sẽ trả về danh sách người dùng thỏa mãn các điều kiện trên, trong trường hợp này có thể chỉ là người dùng với ID `1`:

```json
[
  {
    "id": 1,
    "username": "john",
    "age": 30,
    "city": "NewYork"
  }
]
```

### Kết luận

Chương trình sử dụng kết hợp của các class `UserController`, `UserSpecificationsBuilder`, và `UserSpecification` để tạo ra một hệ thống tìm kiếm động dựa trên các tiêu chí người dùng nhập vào. Từng bước từ việc nhận yêu cầu, phân tích tiêu chí, xây dựng truy vấn, cho đến việc thực thi và trả kết quả, tất cả đều phối hợp một cách chặt chẽ để cung cấp cho người dùng một trải nghiệm tìm kiếm linh hoạt và mạnh mẽ.

Nếu bạn có thêm câu hỏi hoặc cần làm rõ bất kỳ phần nào khác, hãy cho tôi biết!