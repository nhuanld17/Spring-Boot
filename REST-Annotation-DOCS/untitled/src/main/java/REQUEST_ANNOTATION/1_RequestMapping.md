`@RequestMapping` là một annotation trong Spring Framework được sử dụng để ánh xạ các yêu cầu HTTP (HTTP request) tới các phương thức của controller. Annotation này có thể được áp dụng ở cả mức lớp và mức phương thức trong controller, cho phép bạn định nghĩa URL, phương thức HTTP, kiểu dữ liệu trả về, v.v.

### Cách hoạt động của `@RequestMapping`

- **Mức lớp**: Khi sử dụng `@RequestMapping` ở mức lớp, nó xác định URL cơ bản cho tất cả các phương thức trong lớp đó.
- **Mức phương thức**: Khi sử dụng ở mức phương thức, nó xác định ánh xạ cụ thể cho phương thức đó.

### Ví dụ sử dụng `@RequestMapping`

#### 1. Ánh xạ cơ bản:

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sinhvien")
public class SinhVienController {

    @RequestMapping("/all")
    public List<SinhVien> getAllSinhVien() {
        // Logic để lấy danh sách sinh viên
        return new ArrayList<>();
    }
}
```
Ở đây, URL `/api/sinhvien/all` sẽ ánh xạ tới phương thức `getAllSinhVien()`.

#### 2. Sử dụng với phương thức HTTP cụ thể:

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sinhvien")
public class SinhVienController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SinhVien getSinhVienById(@PathVariable int id) {
        // Logic để lấy sinh viên theo ID
        return new SinhVien(id, "Nguyen Van", "A", "a@example.com");
    }
}
```
Ở đây, URL `/api/sinhvien/{id}` với phương thức GET sẽ ánh xạ tới phương thức `getSinhVienById()`.

#### 3. Sử dụng với các phương thức HTTP khác nhau:

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sinhvien")
public class SinhVienController {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public SinhVien createSinhVien(@RequestBody SinhVien sinhVien) {
        // Logic để tạo sinh viên mới
        return sinhVien;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public SinhVien updateSinhVien(@PathVariable int id, @RequestBody SinhVien sinhVien) {
        // Logic để cập nhật sinh viên
        return sinhVien;
    }
}
```
Ở đây, phương thức `createSinhVien` sẽ xử lý các yêu cầu POST tới `/api/sinhvien/create`, và `updateSinhVien` sẽ xử lý các yêu cầu PUT tới `/api/sinhvien/update/{id}`.

### Thuộc tính của `@RequestMapping`

- **`value`** hoặc **`path`**: Xác định URL hoặc đường dẫn mà phương thức sẽ ánh xạ tới.
- **`method`**: Xác định phương thức HTTP (GET, POST, PUT, DELETE, v.v.) mà yêu cầu phải có để ánh xạ tới phương thức.
- **`params`**: Xác định các tham số yêu cầu HTTP cần có.
- **`headers`**: Xác định các tiêu đề yêu cầu HTTP cần có.
- **`produces`**: Xác định kiểu dữ liệu mà phương thức có thể trả về (ví dụ: `application/json`).
- **`consumes`**: Xác định kiểu dữ liệu mà phương thức có thể xử lý (ví dụ: `application/json`).

### Sử dụng các Annotation chuyên biệt hơn

Ngoài `@RequestMapping`, Spring còn cung cấp các annotation chuyên biệt hơn như:

- **`@GetMapping`**: Tương đương với `@RequestMapping(method = RequestMethod.GET)`
- **`@PostMapping`**: Tương đương với `@RequestMapping(method = RequestMethod.POST)`
- **`@PutMapping`**: Tương đương với `@RequestMapping(method = RequestMethod.PUT)`
- **`@DeleteMapping`**: Tương đương với `@RequestMapping(method = RequestMethod.DELETE)`

### Lợi ích của `@RequestMapping`

- **Tùy chỉnh ánh xạ URL**: `@RequestMapping` cho phép bạn tùy chỉnh cách mà các yêu cầu HTTP được xử lý trong ứng dụng của bạn.
- **Hỗ trợ nhiều phương thức HTTP**: Bạn có thể dễ dàng xử lý các loại yêu cầu HTTP khác nhau (GET, POST, PUT, DELETE, v.v.) chỉ với một annotation.
- **Định nghĩa linh hoạt**: Có thể sử dụng kết hợp với các thuộc tính khác để tạo ra các ánh xạ phức tạp và tùy chỉnh cao.

`@RequestMapping` là một công cụ mạnh mẽ trong Spring MVC, giúp bạn dễ dàng xây dựng các ứng dụng web RESTful với các ánh xạ URL và phương thức HTTP rõ ràng và hiệu quả.