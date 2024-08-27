`@ExceptionHandler` là một annotation trong Spring Framework được sử dụng để xử lý các ngoại lệ (exceptions) trong các lớp controller. Annotation này cho phép bạn định nghĩa một hoặc nhiều phương thức để xử lý các loại ngoại lệ cụ thể hoặc tất cả các ngoại lệ phát sinh trong các phương thức của controller.

### Cách hoạt động của `@ExceptionHandler`

Khi một ngoại lệ xảy ra trong một phương thức của controller, phương thức được gán `@ExceptionHandler` sẽ được gọi để xử lý ngoại lệ đó. Điều này giúp bạn kiểm soát cách phản hồi của ứng dụng khi có lỗi xảy ra, giúp cải thiện trải nghiệm người dùng và cung cấp thông tin lỗi có cấu trúc.

### Cú pháp và Ví dụ

#### 1. Định nghĩa một ngoại lệ tùy chỉnh

```java
public class SinhVienNotFoundException extends RuntimeException {
    public SinhVienNotFoundException(String message) {
        super(message);
    }
}
```

#### 2. Sử dụng `@ExceptionHandler` trong Controller

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @GetMapping("/sinhvien/{id}")
    public SinhVien getSinhVienById(@PathVariable int id) {
        SinhVien sinhVien = findSinhVienById(id);
        if (sinhVien == null) {
            throw new SinhVienNotFoundException("Không tìm thấy sinh viên với ID: " + id);
        }
        return sinhVien;
    }

    // Phương thức xử lý ngoại lệ SinhVienNotFoundException
    @ExceptionHandler(SinhVienNotFoundException.class)
    public ResponseEntity<String> handleSinhVienNotFound(SinhVienNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Giả lập phương thức tìm sinh viên theo ID
    private SinhVien findSinhVienById(int id) {
        // Logic tìm sinh viên theo ID
        return null; // giả sử không tìm thấy
    }
}
```

### Giải thích

- **Ngoại lệ tùy chỉnh**: `SinhVienNotFoundException` là một ngoại lệ tùy chỉnh dùng để thông báo rằng sinh viên với ID cụ thể không được tìm thấy.

- **Xử lý ngoại lệ**: Phương thức `handleSinhVienNotFound()` được chú thích với `@ExceptionHandler(SinhVienNotFoundException.class)` sẽ xử lý các ngoại lệ kiểu `SinhVienNotFoundException`. Khi ngoại lệ này xảy ra, phương thức này sẽ được gọi để xử lý và trả về phản hồi với thông báo lỗi và mã trạng thái HTTP `404 NOT FOUND`.

### Các tính năng của `@ExceptionHandler`

- **Xử lý nhiều ngoại lệ**: Bạn có thể chỉ định nhiều loại ngoại lệ trong cùng một phương thức bằng cách sử dụng một mảng trong annotation:

  ```java
  @ExceptionHandler({ SinhVienNotFoundException.class, SomeOtherException.class })
  public ResponseEntity<String> handleMultipleExceptions(Exception ex) {
      // Xử lý các ngoại lệ khác nhau
  }
  ```

- **Xử lý tất cả ngoại lệ**: Để xử lý tất cả các ngoại lệ không được chỉ định rõ ràng, bạn có thể sử dụng phương thức `Exception` như sau:

  ```java
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllExceptions(Exception ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  ```

- **Trả về các loại phản hồi khác nhau**: Phương thức xử lý ngoại lệ có thể trả về bất kỳ loại phản hồi nào, bao gồm `ResponseEntity`, `ModelAndView`, hoặc đơn giản là một đối tượng với thông báo lỗi.

### Lợi ích của `@ExceptionHandler`

- **Tập trung xử lý lỗi**: Giúp bạn tập trung xử lý lỗi và thông báo lỗi trong một nơi duy nhất.
- **Cải thiện trải nghiệm người dùng**: Cung cấp phản hồi rõ ràng và có cấu trúc cho người dùng khi có lỗi xảy ra.
- **Dễ bảo trì**: Giảm thiểu mã lặp lại và dễ dàng cập nhật cách xử lý lỗi khi cần thiết.

Nếu bạn có thêm câu hỏi hoặc cần giải thích thêm về `@ExceptionHandler`, hãy cho mình biết!