### `@ControllerAdvice` trong Spring Boot

**`@ControllerAdvice`** là một annotation trong Spring Boot được sử dụng để xử lý **ngoại lệ toàn cục** (global exception handling) và cung cấp lời khuyên (advice) chung cho tất cả các controller trong ứng dụng. Thay vì phải xử lý ngoại lệ trong từng controller riêng lẻ, ta có thể dùng `@ControllerAdvice` để viết một lớp xử lý ngoại lệ chung cho toàn bộ ứng dụng, giúp mã trở nên ngắn gọn và dễ bảo trì hơn.

### Các chức năng chính của `@ControllerAdvice`

1. **Global Exception Handling**: Xử lý tất cả các ngoại lệ phát sinh trong controller ở một nơi duy nhất.
2. **Binding và Validation Advice**: Có thể sử dụng để xử lý các lỗi liên quan đến ràng buộc (binding) và kiểm tra hợp lệ (validation) dữ liệu.
3. **Model Advice**: Cung cấp dữ liệu chung cho các view mà không cần phải khai báo trong từng controller.

### Cách hoạt động

- Khi một ngoại lệ xảy ra trong bất kỳ controller nào, Spring sẽ tìm kiếm các phương thức có đánh dấu annotation **`@ExceptionHandler`** trong lớp được đánh dấu bằng `@ControllerAdvice`. Những phương thức này sẽ được gọi để xử lý ngoại lệ thay vì để controller tự xử lý.

### Ví dụ về `@ControllerAdvice` xử lý ngoại lệ

#### 1. Tạo một lớp `ControllerAdvice` để xử lý ngoại lệ toàn cục

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý ngoại lệ khi không tìm thấy đối tượng (ví dụ: sinh viên không tồn tại)
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
        return new ResponseEntity<>("Student not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Xử lý ngoại lệ tổng quát
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

#### 2. Định nghĩa một ngoại lệ tùy chỉnh `StudentNotFoundException`

```java
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
```

#### 3. Sử dụng ngoại lệ trong Controller

```java
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();

    // Lấy sinh viên theo ID, nếu không tồn tại thì ném ngoại lệ
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found"));
    }
}
```

### Giải thích chi tiết:

1. **`@ControllerAdvice`**: Annotation này giúp khai báo một lớp xử lý ngoại lệ toàn cục. Khi có ngoại lệ xảy ra ở bất kỳ controller nào, các phương thức trong lớp này sẽ được gọi để xử lý ngoại lệ.

2. **`@ExceptionHandler(StudentNotFoundException.class)`**: Annotation này chỉ định rằng phương thức **`handleStudentNotFoundException`** sẽ xử lý ngoại lệ `StudentNotFoundException`. Khi ngoại lệ này bị ném (thrown) ra từ bất kỳ controller nào, phương thức này sẽ được gọi để trả về phản hồi thích hợp.

3. **Phương thức xử lý ngoại lệ tổng quát**:
    - **`@ExceptionHandler(Exception.class)`**: Đây là phương thức xử lý ngoại lệ tổng quát. Nó sẽ xử lý mọi ngoại lệ khác mà không có ngoại lệ tùy chỉnh nào bắt được. Ví dụ: nếu một ngoại lệ bất kỳ như `NullPointerException` xảy ra, phương thức này sẽ được gọi.

4. **Trả về HTTP status code**:
    - Trong ví dụ này, ngoại lệ `StudentNotFoundException` trả về mã trạng thái `404 NOT FOUND` thông qua `ResponseEntity`. Điều này giúp API trả về thông tin lỗi chi tiết cùng với mã trạng thái thích hợp.
    - Ngoại lệ tổng quát sẽ trả về `500 INTERNAL SERVER ERROR` để biểu thị lỗi máy chủ nội bộ.

#### 4. Ví dụ về phản hồi khi xảy ra ngoại lệ

- **Yêu cầu**: `GET /students/1`
- **Trường hợp sinh viên không tồn tại**:
    - Phản hồi trả về:
   ```json
   {
     "status": 404,
     "error": "Student not found: Student with ID 1 not found"
   }
   ```
- **Trường hợp lỗi tổng quát**:
    - Nếu có một lỗi nào khác xảy ra, phương thức xử lý tổng quát sẽ trả về mã `500` kèm theo thông báo lỗi chung.

### Kết luận:

- **`@ControllerAdvice`** là công cụ mạnh mẽ trong Spring Boot, giúp xử lý ngoại lệ một cách tập trung và dễ dàng. Thay vì xử lý từng ngoại lệ trong từng controller, ta có thể sử dụng một lớp duy nhất để xử lý toàn bộ ngoại lệ của ứng dụng.
- Nó giúp mã nguồn trở nên ngắn gọn, dễ quản lý và giúp phản hồi lỗi từ API được chuẩn hóa với các mã trạng thái HTTP thích hợp.
- `@ControllerAdvice` còn có thể được sử dụng để xử lý các vấn đề về kiểm tra dữ liệu (validation), dữ liệu truyền vào không hợp lệ, hoặc các lỗi liên quan đến binding dữ liệu.

