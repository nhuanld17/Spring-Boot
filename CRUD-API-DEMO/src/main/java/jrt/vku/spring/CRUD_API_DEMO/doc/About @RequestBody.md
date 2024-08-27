`@RequestBody` trong Spring là một annotation dùng để đánh dấu rằng một đối tượng trong tham số của phương thức trong controller sẽ được lấy từ body của HTTP request. Spring sẽ tự động chuyển đổi dữ liệu trong body của request thành đối tượng Java tương ứng.

### Giải thích dễ hiểu:
Khi bạn xây dựng một API trong Spring, thường thì bạn cần lấy dữ liệu mà người dùng gửi lên thông qua một request (ví dụ: dữ liệu JSON). `@RequestBody` giúp bạn lấy dữ liệu đó và chuyển đổi nó thành một đối tượng Java để dễ dàng xử lý trong code.

### Ví dụ cụ thể:
Giả sử bạn có một API để tạo một sinh viên mới. Người dùng sẽ gửi dữ liệu của sinh viên qua một POST request với JSON dữ liệu. Bạn có thể sử dụng `@RequestBody` để tự động chuyển đổi JSON đó thành một đối tượng `Student`.

```java
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student) {
        // Logic để lưu sinh viên vào cơ sở dữ liệu
        return student; // trả về đối tượng sinh viên vừa tạo
    }
}
```

Giả sử người dùng gửi một yêu cầu POST với nội dung sau:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "johndoe@example.com"
}
```

Spring sẽ tự động chuyển đổi JSON này thành một đối tượng `Student` với các thuộc tính `firstName`, `lastName`, và `email` được gán giá trị tương ứng.

### Lợi ích:
- Giúp code trở nên ngắn gọn và dễ đọc hơn vì không cần phải tự viết code để xử lý và chuyển đổi dữ liệu từ JSON sang đối tượng Java.
- Giảm thiểu lỗi do việc chuyển đổi thủ công.

Hy vọng với cách giải thích này, bạn đã hiểu rõ hơn về `@RequestBody` trong Spring!