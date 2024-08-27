`@DeleteMapping` là một annotation trong Spring Framework, được sử dụng để ánh xạ các yêu cầu HTTP DELETE tới một phương thức xử lý cụ thể trong controller. Annotation này thường được sử dụng trong các ứng dụng RESTful để xóa một tài nguyên.

### Cách sử dụng `@DeleteMapping`

#### 1. Sử dụng cơ bản

```java
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @DeleteMapping("/sinhvien/{id}")
    public String deleteSinhVien(@PathVariable int id) {
        // Logic để xóa sinh viên có id tương ứng
        // Ví dụ: xóa sinh viên trong cơ sở dữ liệu theo id
        return "Đã xóa sinh viên với ID: " + id;
    }
}
```

Trong ví dụ trên:
- URL `/sinhvien/{id}` sẽ được ánh xạ tới phương thức `deleteSinhVien()`.
- `{id}` là một biến đường dẫn (path variable) đại diện cho ID của sinh viên cần xóa.
- Phương thức `deleteSinhVien()` sẽ nhận ID từ URL, sau đó thực hiện thao tác xóa sinh viên tương ứng khỏi cơ sở dữ liệu.

#### 2. Sử dụng với tham số yêu cầu (Request Parameters)

```java
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @DeleteMapping("/sinhvien")
    public String deleteSinhVienByName(@RequestParam String name) {
        // Logic để xóa sinh viên dựa trên tên
        // Ví dụ: xóa sinh viên trong cơ sở dữ liệu theo tên
        return "Đã xóa sinh viên với tên: " + name;
    }
}
```

Trong ví dụ này, thay vì sử dụng biến đường dẫn, chúng ta sử dụng tham số yêu cầu để xác định sinh viên cần xóa.

### Lợi ích của `@DeleteMapping`

- **Rõ ràng và ngắn gọn**: `@DeleteMapping` làm cho mã nguồn dễ đọc hơn so với việc sử dụng `@RequestMapping(method = RequestMethod.DELETE)`.
- **Tuân thủ nguyên tắc RESTful**: Phù hợp với HTTP DELETE khi bạn muốn xóa một tài nguyên, giúp tuân thủ các nguyên tắc thiết kế RESTful.
- **Đơn giản hóa việc xử lý yêu cầu xóa**: Annotation này giúp bạn dễ dàng tạo các phương thức xử lý yêu cầu xóa, làm cho mã nguồn gọn gàng và dễ bảo trì.

### Khi nào sử dụng `@DeleteMapping`

- **Xóa một tài nguyên cụ thể**: Khi bạn muốn xóa một tài nguyên (ví dụ, xóa một bản ghi trong cơ sở dữ liệu).
- **Thực hiện thao tác xóa trong các ứng dụng RESTful**: Khi xây dựng API RESTful, `@DeleteMapping` được sử dụng để ánh xạ các yêu cầu HTTP DELETE.

### So sánh với các annotation khác

- **`@GetMapping`**: Dùng để lấy thông tin tài nguyên.
- **`@PostMapping`**: Dùng để tạo mới tài nguyên.
- **`@PutMapping`**: Dùng để cập nhật toàn bộ tài nguyên.
- **`@PatchMapping`**: Dùng để cập nhật một phần tài nguyên.

---

Nếu bạn có thêm câu hỏi về `@DeleteMapping` hoặc các annotation khác trong Spring, mình sẽ rất sẵn lòng giúp bạn hiểu rõ hơn!