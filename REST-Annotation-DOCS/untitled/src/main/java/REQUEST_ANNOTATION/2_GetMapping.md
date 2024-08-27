`@GetMapping` là một annotation trong Spring Framework, được sử dụng để ánh xạ các yêu cầu HTTP GET tới một phương thức xử lý cụ thể trong controller. Annotation này là một phiên bản chuyên biệt của `@RequestMapping` dành riêng cho các yêu cầu GET, giúp mã nguồn của bạn ngắn gọn và dễ đọc hơn.

### Cách sử dụng `@GetMapping`

#### 1. Sử dụng cơ bản

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @GetMapping("/sinhvien")
    public List<SinhVien> getAllSinhVien() {
        // Logic để lấy danh sách sinh viên
        return new ArrayList<>();
    }
}
```

Ở đây, URL `/sinhvien` sẽ được ánh xạ tới phương thức `getAllSinhVien()`. Khi trình duyệt hoặc công cụ nào đó gửi yêu cầu GET tới URL này, phương thức sẽ được gọi.

#### 2. Sử dụng với biến đường dẫn (Path Variables)

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @GetMapping("/sinhvien/{id}")
    public SinhVien getSinhVienById(@PathVariable int id) {
        // Logic để lấy sinh viên theo ID
        return new SinhVien(id, "Nguyen Van", "A", "a@example.com");
    }
}
```

Ở đây, URL `/sinhvien/{id}` ánh xạ tới phương thức `getSinhVienById()`, và `@PathVariable` được sử dụng để trích xuất giá trị của `{id}` từ URL và truyền vào phương thức.

#### 3. Sử dụng với tham số yêu cầu (Request Parameters)

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @GetMapping("/sinhvien")
    public SinhVien getSinhVienByName(@RequestParam String name) {
        // Logic để lấy sinh viên theo tên
        return new SinhVien(1, "Nguyen Van", name, "a@example.com");
    }
}
```

Trong ví dụ này, URL `/sinhvien?name=Van` sẽ gọi phương thức `getSinhVienByName()`, với tham số `name` được lấy từ yêu cầu HTTP và truyền vào phương thức.

### Lợi ích của `@GetMapping`

- **Ngắn gọn và rõ ràng**: `@GetMapping` giúp mã nguồn của bạn dễ đọc hơn so với việc sử dụng `@RequestMapping(method = RequestMethod.GET)`.
- **Tập trung vào REST**: Việc sử dụng `@GetMapping` giúp xây dựng các API RESTful tuân theo quy ước, nơi mà các yêu cầu GET được sử dụng để truy xuất dữ liệu.
- **Hỗ trợ đầy đủ các tính năng**: `@GetMapping` hỗ trợ đầy đủ các tính năng của `@RequestMapping`, bao gồm ánh xạ URL, sử dụng biến đường dẫn, tham số yêu cầu, tiêu đề HTTP, v.v.

### Sự khác biệt so với `@RequestMapping`

Mặc dù `@GetMapping` là một dạng đặc biệt của `@RequestMapping`, nó chỉ dành riêng cho các yêu cầu GET. Điều này giúp mã của bạn trở nên dễ hiểu và phù hợp với nguyên tắc của RESTful hơn khi bạn muốn chỉ định rõ ràng các loại yêu cầu HTTP.

---

Nếu bạn có thắc mắc nào khác về `@GetMapping` hoặc muốn biết thêm về các annotation khác, cứ tiếp tục nhé!