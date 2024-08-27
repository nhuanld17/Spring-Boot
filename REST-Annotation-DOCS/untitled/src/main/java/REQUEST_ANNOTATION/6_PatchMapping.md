`@PatchMapping` là một annotation trong Spring Framework, được sử dụng để ánh xạ các yêu cầu HTTP PATCH tới một phương thức xử lý cụ thể trong controller. Annotation này thường được sử dụng trong các ứng dụng RESTful để cập nhật một phần của tài nguyên hiện có thay vì cập nhật toàn bộ tài nguyên như `@PutMapping`.

### Cách sử dụng `@PatchMapping`

#### 1. Sử dụng cơ bản

```java
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @PatchMapping("/sinhvien/{id}")
    public String updatePartialSinhVien(@PathVariable int id, @RequestBody SinhVien sinhVien) {
        // Logic để cập nhật một phần thông tin của sinh viên có id tương ứng
        // Ví dụ: cập nhật thông tin sinh viên trong cơ sở dữ liệu
        return "Đã cập nhật sinh viên với ID: " + id;
    }
}
```

Trong ví dụ trên:
- URL `/sinhvien/{id}` được ánh xạ tới phương thức `updatePartialSinhVien()`.
- `{id}` là một biến đường dẫn đại diện cho ID của sinh viên cần cập nhật.
- Phương thức `updatePartialSinhVien()` nhận một đối tượng `SinhVien` từ phần thân yêu cầu (request body) để thực hiện cập nhật một phần thông tin.

#### 2. Cập nhật một phần tài nguyên

```java
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SinhVienController {

    @PatchMapping("/sinhvien/{id}")
    public String updatePartialSinhVien(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        // Logic để cập nhật một phần thông tin của sinh viên có id tương ứng
        // Ví dụ: duyệt qua map và cập nhật các thuộc tính tương ứng
        return "Đã cập nhật sinh viên với ID: " + id;
    }
}
```

Trong ví dụ này:
- `@RequestBody Map<String, Object> updates` cho phép bạn gửi một phần dữ liệu cần cập nhật, ví dụ như chỉ cập nhật tên hoặc email của sinh viên.
- Phương thức `updatePartialSinhVien()` sẽ duyệt qua map và cập nhật các thuộc tính tương ứng trong cơ sở dữ liệu.

### Khi nào nên sử dụng `@PatchMapping`

- **Cập nhật một phần tài nguyên**: Khi bạn chỉ cần cập nhật một hoặc một vài thuộc tính của tài nguyên mà không muốn gửi toàn bộ tài nguyên.
- **Hiệu quả hơn PUT**: `@PatchMapping` giúp giảm thiểu dữ liệu cần truyền tải qua mạng khi bạn chỉ cập nhật một phần của tài nguyên.

### So sánh với các annotation khác

- **`@GetMapping`**: Dùng để lấy thông tin tài nguyên.
- **`@PostMapping`**: Dùng để tạo mới tài nguyên.
- **`@PutMapping`**: Dùng để cập nhật toàn bộ tài nguyên.
- **`@DeleteMapping`**: Dùng để xóa tài nguyên.

### Lợi ích của `@PatchMapping`

- **Tối ưu hóa yêu cầu cập nhật**: `@PatchMapping` giúp gửi ít dữ liệu hơn trong các yêu cầu cập nhật, làm cho ứng dụng hiệu quả hơn.
- **Linh hoạt hơn**: Nó cung cấp sự linh hoạt trong việc cập nhật tài nguyên, cho phép bạn chỉ cần gửi những phần thay đổi.

Nếu bạn có bất kỳ câu hỏi nào khác hoặc cần giải thích thêm về `@PatchMapping` hoặc các annotation khác trong Spring, mình rất sẵn lòng hỗ trợ!