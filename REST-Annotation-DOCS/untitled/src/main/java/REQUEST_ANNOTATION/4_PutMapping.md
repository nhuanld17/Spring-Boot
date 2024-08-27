`@PutMapping` là một annotation trong Spring Framework, được sử dụng để ánh xạ các yêu cầu HTTP PUT tới một phương thức xử lý cụ thể trong controller. Annotation này thường được sử dụng trong kiến trúc RESTful để cập nhật một tài nguyên hiện có.

### Cách sử dụng `@PutMapping`

#### 1. Sử dụng cơ bản

```java
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @PutMapping("/sinhvien/{id}")
    public SinhVien updateSinhVien(@PathVariable int id, @RequestBody SinhVien sinhVien) {
        // Logic để cập nhật sinh viên có id tương ứng
        // Ví dụ: tìm sinh viên trong database theo id và cập nhật thông tin của họ
        sinhVien.setId(id);
        // Cập nhật sinh viên trong cơ sở dữ liệu
        return sinhVien;
    }
}
```

Trong ví dụ trên:
- URL `/sinhvien/{id}` sẽ được ánh xạ tới phương thức `updateSinhVien()`.
- `{id}` là một biến đường dẫn (path variable) đại diện cho ID của sinh viên cần cập nhật.
- Phương thức `updateSinhVien()` sẽ nhận ID từ URL và thông tin cập nhật từ phần thân yêu cầu (dùng `@RequestBody`), sau đó thực hiện cập nhật sinh viên tương ứng trong cơ sở dữ liệu.

#### 2. Sử dụng với tham số yêu cầu (Request Parameters)

```java
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @PutMapping("/sinhvien/{id}")
    public SinhVien updateSinhVien(@PathVariable int id, @RequestParam String hoDem, @RequestParam String ten, @RequestParam String email) {
        // Logic để cập nhật sinh viên
        SinhVien sinhVien = new SinhVien(hoDem, ten, email);
        sinhVien.setId(id);
        // Cập nhật sinh viên trong cơ sở dữ liệu
        return sinhVien;
    }
}
```

Ở đây, các tham số cần cập nhật được truyền qua các tham số yêu cầu (request parameters) thay vì sử dụng `@RequestBody`.

### Lợi ích của `@PutMapping`

- **Rõ ràng và dễ hiểu**: Giống như các annotation khác trong nhóm `@RequestMapping`, `@PutMapping` giúp mã nguồn rõ ràng và ngắn gọn hơn so với `@RequestMapping(method = RequestMethod.PUT)`.
- **Tuân thủ nguyên tắc RESTful**: `@PutMapping` được sử dụng khi bạn muốn thực hiện thao tác cập nhật toàn bộ một tài nguyên, phù hợp với HTTP PUT trong REST.
- **Dễ dàng quản lý các thao tác cập nhật**: Phương thức này thường được sử dụng để cập nhật thông tin tài nguyên hiện có, như chỉnh sửa thông tin của một bản ghi trong cơ sở dữ liệu.

### Khi nào sử dụng `@PutMapping`

- **Cập nhật tài nguyên hiện có**: Khi bạn muốn thay đổi thông tin của một tài nguyên cụ thể (ví dụ, cập nhật hồ sơ người dùng).
- **Thực hiện cập nhật toàn bộ tài nguyên**: HTTP PUT theo nguyên tắc nên được sử dụng để thay thế hoàn toàn một tài nguyên, không chỉ là cập nhật một phần.

### So sánh với các annotation khác

- **`@GetMapping`**: Dùng để lấy thông tin tài nguyên.
- **`@PostMapping`**: Dùng để tạo mới tài nguyên.
- **`@DeleteMapping`**: Dùng để xóa tài nguyên.
- **`@PatchMapping`**: Dùng để cập nhật một phần của tài nguyên, thay vì toàn bộ như `@PutMapping`.

---

Nếu bạn có thêm câu hỏi về `@PutMapping` hoặc các annotation khác, mình sẽ rất vui lòng hỗ trợ!