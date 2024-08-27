`@RestControllerAdvice` là một annotation trong Spring Framework, được sử dụng để xử lý các ngoại lệ (exceptions) trong các ứng dụng RESTful. Nó là một biến thể của `@ControllerAdvice` và được sử dụng cùng với `@RestController` để cung cấp cơ chế xử lý ngoại lệ toàn cầu cho tất cả các REST controllers trong ứng dụng.

### Chức năng của `@RestControllerAdvice`

- **Global Exception Handling**: `@RestControllerAdvice` cho phép bạn xử lý ngoại lệ một cách tập trung mà không cần phải viết mã xử lý ngoại lệ trong từng controller.
- **REST-Specific**: Các phương thức trong `@RestControllerAdvice` tự động trả về JSON hoặc các định dạng dữ liệu khác mà bạn định nghĩa, thay vì trả về tên của một view.

### Cách sử dụng `@RestControllerAdvice`

#### 1. Định nghĩa `@RestControllerAdvice`

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>("Resource not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

#### 2. Sử dụng `@RestControllerAdvice` trong thực tế

Giả sử bạn có một ứng dụng RESTful để quản lý sinh viên. Bạn có một `SinhVienController` để xử lý các yêu cầu liên quan đến sinh viên, và trong quá trình xử lý, có thể xảy ra một số lỗi như không tìm thấy sinh viên với ID đã cho (`ResourceNotFoundException`).

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sinhvien")
public class SinhVienController {

    @GetMapping("/{id}")
    public SinhVien getSinhVienById(@PathVariable int id) {
        // Giả sử tìm kiếm sinh viên trong cơ sở dữ liệu
        SinhVien sinhVien = findSinhVienById(id);
        if (sinhVien == null) {
            throw new ResourceNotFoundException("Sinh viên không tồn tại với ID: " + id);
        }
        return sinhVien;
    }

    // Giả lập phương thức tìm kiếm sinh viên trong cơ sở dữ liệu
    private SinhVien findSinhVienById(int id) {
        // Thực hiện logic tìm kiếm trong cơ sở dữ liệu
        return null; // Giả sử không tìm thấy
    }
}
```

Nếu sinh viên không tồn tại, `ResourceNotFoundException` sẽ được ném ra. Thay vì phải xử lý ngoại lệ này trong từng controller, bạn có thể định nghĩa một lớp xử lý ngoại lệ toàn cục như sau:

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>("Không tìm thấy tài nguyên: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Đã xảy ra lỗi: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### Lợi ích của `@RestControllerAdvice`

1. **Centralized Exception Handling**: Bạn có thể quản lý việc xử lý ngoại lệ của toàn bộ ứng dụng RESTful ở một nơi duy nhất.
2. **Code Simplification**: Giảm thiểu việc lặp lại mã xử lý ngoại lệ trong từng controller.
3. **Consistent Error Responses**: Dễ dàng đảm bảo rằng tất cả các phản hồi lỗi từ API đều có cùng một định dạng, giúp phía client dễ dàng xử lý.

### Khi nào nên sử dụng `@RestControllerAdvice`?

- Khi bạn muốn có một cách tiếp cận tập trung và thống nhất để xử lý ngoại lệ trong các ứng dụng RESTful.
- Khi bạn muốn đảm bảo rằng các phản hồi lỗi từ API luôn có định dạng nhất quán.
- Khi bạn cần xử lý các ngoại lệ chung như `ResourceNotFoundException`, `IllegalArgumentException`, hoặc `ValidationException` một cách hiệu quả.

Nếu bạn có bất kỳ câu hỏi nào thêm về `@RestControllerAdvice` hoặc muốn biết thêm chi tiết về cách sử dụng trong các trường hợp cụ thể, mình sẵn sàng hỗ trợ!