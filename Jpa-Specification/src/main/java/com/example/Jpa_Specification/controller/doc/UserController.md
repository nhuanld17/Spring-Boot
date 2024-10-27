Class `UserController` này là một controller của ứng dụng Spring Boot dùng để xử lý các yêu cầu tìm kiếm người dùng (`User`) thông qua tiêu chí động (dynamic criteria) truyền vào từ phía client. Chúng ta sẽ đi vào chi tiết từng phần của class và cung cấp ví dụ cho từng chức năng để dễ hiểu hơn.

---

### Giải Thích Class `UserController`

```java
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

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
}
```

---

#### Các Thành Phần Trong Class `UserController`

1. **`@RestController`**:
   - `@RestController` là annotation của Spring dùng để đánh dấu class này là một RESTful controller. Điều này nghĩa là các phương thức trong class sẽ trả về dữ liệu JSON thay vì một view (giao diện).

2. **`@Autowired private UserRepository userRepository;`**:
   - `@Autowired` giúp inject `UserRepository` vào controller. `UserRepository` là nơi truy xuất dữ liệu người dùng từ cơ sở dữ liệu.

3. **`@GetMapping("/users")`**:
   - Chỉ định rằng phương thức `search` sẽ xử lý các yêu cầu GET đến endpoint `/users`.
   - Phương thức `search` nhận một tham số query `search` (truyền vào qua URL), đại diện cho các tiêu chí tìm kiếm.

---

#### Chi Tiết Về Phương Thức `search`

##### 1. Khởi Tạo `UserSpecificationsBuilder`
```java
UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
```
- `UserSpecificationsBuilder` là một lớp builder (xây dựng) cho phép thêm các tiêu chí tìm kiếm vào một đối tượng `Specification<User>`.

##### 2. Biểu Thức Chính Quy (Regex) Để Phân Tích Tiêu Chí Tìm Kiếm 

Để hiểu rõ phần này, chúng ta sẽ phân tích từng chi tiết trong đoạn mã này:

```java
Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
Matcher matcher = pattern.matcher(search + ",");
```
Biểu thức chính quy (regex) ở đây là `"(\\w+?)(:|<|>)(\\w+?),"` và có ý nghĩa như sau:

- **`\\w+?`**: Tìm một hoặc nhiều ký tự chữ và số, đại diện cho **tên của trường** (như `name`, `age`). Dấu `+?` ở đây là **non-greedy quantifier** (bộ định lượng không tham lam) để đảm bảo tìm phần nhỏ nhất khớp với tiêu chí.
- **`(:|<|>)`**: Một trong các ký tự `:`, `<`, hoặc `>`. Phần này đại diện cho **toán tử** so sánh (`:` cho bằng, `<` cho nhỏ hơn, `>` cho lớn hơn).
- **`\\w+?`**: Một hoặc nhiều ký tự chữ và số, đại diện cho **giá trị của trường** (ví dụ: `John`, `30`).
- **`,`**: Dấu phẩy ở cuối biểu thức là để xác định rằng mỗi tiêu chí sẽ được phân tách bằng dấu phẩy.

Với biểu thức trên, chúng ta có thể xử lý các yêu cầu tìm kiếm dạng `"name:John,age>30"`.

#### Giải Thích `search + ","`

Biến `search` đại diện cho chuỗi truy vấn tìm kiếm từ client. Đoạn mã `search + ","` có nghĩa là sẽ nối thêm một dấu phẩy vào cuối chuỗi `search`. Điều này giúp đảm bảo rằng mỗi tiêu chí tìm kiếm sẽ kết thúc bằng một dấu phẩy, thuận tiện cho biểu thức chính quy xử lý, ngay cả khi chuỗi tìm kiếm ban đầu không có dấu phẩy cuối cùng.

Ví dụ:
- Nếu `search = "name:John,age>30"`, khi thêm dấu phẩy, nó sẽ trở thành `name:John,age>30,`.
- Điều này giúp `Pattern` dễ dàng phân tách mỗi tiêu chí mà không cần xử lý dấu phẩy cuối cùng thủ công.

---

#### Ví Dụ Minh Họa Từng Bước

Giả sử chúng ta có chuỗi tìm kiếm sau từ client:
```java
String search = "name:John,age>30";
Matcher matcher = pattern.matcher(search + ","); // search + "," thành "name:John,age>30,"
```

#### Bước 1: Chuẩn Bị `Pattern` và `Matcher`

- Sau khi thêm dấu phẩy, `search` sẽ trở thành `"name:John,age>30,"`.
- `Pattern` sẽ khớp các cụm tiêu chí nhờ biểu thức chính quy đã xây dựng.

#### Bước 2: Tìm Kiếm Với `matcher.find()`

Sử dụng `matcher.find()` trong vòng lặp, chúng ta có thể tìm lần lượt các cụm tiêu chí:

1. **Lần đầu tiên chạy `matcher.find()`**:
    - `matcher.group(1)` sẽ khớp với `name` (tên của trường).
    - `matcher.group(2)` sẽ khớp với `:` (toán tử so sánh).
    - `matcher.group(3)` sẽ khớp với `John` (giá trị).

   => Kết quả là nhóm `"name:John,"`.

2. **Lần thứ hai chạy `matcher.find()`**:
    - `matcher.group(1)` sẽ khớp với `age`.
    - `matcher.group(2)` sẽ khớp với `>`.
    - `matcher.group(3)` sẽ khớp với `30`.

   => Kết quả là nhóm `"age>30,"`.

Kết thúc vòng lặp, chúng ta đã tách được hai tiêu chí `"name:John"` và `"age>30"` để truyền vào `UserSpecificationsBuilder`.

---

#### Ví Dụ Từng Bước với `matcher.find()`

Dưới đây là cách `matcher.find()` sẽ xử lý từng tiêu chí trong ví dụ:

1. **Chuẩn bị Pattern và Matcher**:
   ```java
   String search = "name:John,age>30";
   Matcher matcher = pattern.matcher(search + ",");
   ```

2. **Chạy matcher.find()**:
   ```java
   while (matcher.find()) {
       System.out.println("Field: " + matcher.group(1));
       System.out.println("Operator: " + matcher.group(2));
       System.out.println("Value: " + matcher.group(3));
   }
   ```

3. **Kết Quả In Ra**:
    - Lần 1:
      ```
      Field: name
      Operator: :
      Value: John
      ```
    - Lần 2:
      ```
      Field: age
      Operator: >
      Value: 30
      ```

Với cách xử lý này, chúng ta đã tách thành công từng cụm tìm kiếm (`name:John` và `age>30`) để đưa vào xây dựng `Specification`.

#### 3. Lặp Qua Các Tiêu Chí Tìm Kiếm Và Thêm Vào Builder
```java
while (matcher.find()) {
    builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
}
```

- **`matcher.group(1)`**: Đại diện cho tên trường (ví dụ: `name`).
- **`matcher.group(2)`**: Đại diện cho toán tử (`:` hoặc `>`).
- **`matcher.group(3)`**: Đại diện cho giá trị của trường (ví dụ: `John`).

Hàm `builder.with(...)` thêm tiêu chí vào `UserSpecificationsBuilder`, nơi lưu tất cả các tiêu chí tìm kiếm.

#### 4. Xây Dựng `Specification` Và Truy Xuất Người Dùng Theo Tiêu Chí
```java
Specification<User> spec = builder.build();
List<User> users = userRepository.findAll(spec);
System.out.println("Users found: " + users); // In ra kết quả tìm kiếm
return users;
```

- `builder.build()` xây dựng đối tượng `Specification<User>`, đại diện cho tất cả tiêu chí tìm kiếm đã thêm.
- `userRepository.findAll(spec)`: Sử dụng `Specification` để tìm tất cả người dùng thỏa mãn tiêu chí và lưu kết quả vào danh sách `users`.
- `System.out.println(...)`: In kết quả tìm kiếm ra console (hữu ích cho việc kiểm tra kết quả tìm kiếm).

---

### Ví Dụ Sử Dụng

Giả sử chúng ta có các người dùng sau trong cơ sở dữ liệu:

| ID  | Name     | Age |
|-----|----------|-----|
| 1   | John     | 28  |
| 2   | Alice    | 35  |
| 3   | Bob      | 40  |
| 4   | Charlie  | 22  |

Nếu gọi API với các yêu cầu sau, kết quả sẽ là:

1. **Yêu Cầu Tìm Kiếm `name:John`**
   - URL: `/users?search=name:John`
   - **Phân Tích**:
      - `matcher.group(1) = "name"`
      - `matcher.group(2) = ":"`
      - `matcher.group(3) = "John"`
   - Kết quả: `[User{id=1, name='John', age=28}]`

2. **Yêu Cầu Tìm Kiếm `age>30`**
   - URL: `/users?search=age>30`
   - **Phân Tích**:
      - `matcher.group(1) = "age"`
      - `matcher.group(2) = ">"` (lớn hơn)
      - `matcher.group(3) = "30"`
   - Kết quả: `[User{id=2, name='Alice', age=35}, User{id=3, name='Bob', age=40}]`

3. **Yêu Cầu Tìm Kiếm Kết Hợp `name:Bob,age>30`**
   - URL: `/users?search=name:Bob,age>30`
   - **Phân Tích**:
      - Điều kiện 1: `name:Bob`
      - Điều kiện 2: `age>30`
   - Kết quả: `[User{id=3, name='Bob', age=40}]`

---

### Ví Dụ Thực Tế

Giả sử bạn gọi API `/users?search=name:Alice,age<40`. Class `UserController` sẽ:

1. Tách tiêu chí `name:Alice` và `age<40` qua regex.
2. Thêm các tiêu chí này vào `UserSpecificationsBuilder`.
3. Xây dựng `Specification<User>`.
4. Tìm tất cả người dùng thỏa mãn điều kiện `name` là `Alice` và `age` nhỏ hơn `40`.
5. Trả về kết quả dưới dạng JSON cho client, ví dụ: `[User{id=2, name='Alice', age=35}]`.

--- 

### Tóm Lược

Class `UserController` giúp tạo ra endpoint tìm kiếm người dùng linh hoạt bằng cách sử dụng các tiêu chí động. Sử dụng `Specification` và `UserSpecificationsBuilder`, controller này cho phép client tùy ý tìm kiếm dựa trên các thuộc tính của `User`.