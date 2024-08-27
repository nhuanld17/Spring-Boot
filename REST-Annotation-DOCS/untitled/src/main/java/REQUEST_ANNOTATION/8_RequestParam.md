`@RequestParam` là một annotation trong Spring Framework, được sử dụng để ánh xạ các tham số từ yêu cầu HTTP vào các tham số của phương thức trong controller. Nó thường được sử dụng khi bạn muốn lấy dữ liệu từ query parameters của URL trong một yêu cầu GET hoặc POST.

### Cách sử dụng `@RequestParam`

#### 1. Ví dụ cơ bản

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @GetMapping("/sinhvien")
    public String getSinhVienByName(@RequestParam(name = "ten") String name) {
        return "Tên sinh viên: " + name;
    }
}
```

Trong ví dụ trên:
- Phương thức `getSinhVienByName()` nhận một tham số `name` từ query parameter `ten` trong URL.
- Khi bạn gửi yêu cầu tới URL `http://localhost:8080/sinhvien?ten=John`, phương thức này sẽ trả về "Tên sinh viên: John".

### 2. Sử dụng `@RequestParam` với giá trị mặc định

Bạn có thể cung cấp một giá trị mặc định nếu query parameter không được truyền vào yêu cầu:

```java
@GetMapping("/sinhvien")
public String getSinhVienByName(@RequestParam(name = "ten", defaultValue = "Unknown") String name) {
    return "Tên sinh viên: " + name;
}
```

Nếu URL `http://localhost:8080/sinhvien` được gọi mà không có query parameter `ten`, phương thức sẽ trả về "Tên sinh viên: Unknown".

### 3. `@RequestParam` với các kiểu dữ liệu khác

`@RequestParam` có thể được sử dụng với các kiểu dữ liệu khác, như `int`, `boolean`, hoặc `List<String>`:

```java
@GetMapping("/sinhvien")
public String getSinhVienByDetails(
        @RequestParam(name = "id") int id,
        @RequestParam(name = "active", defaultValue = "true") boolean isActive) {
    return "ID sinh viên: " + id + ", Trạng thái: " + (isActive ? "Active" : "Inactive");
}
```

### Trường hợp sử dụng thực tế

Giả sử bạn có một ứng dụng quản lý sinh viên, nơi người dùng có thể tìm kiếm sinh viên theo tên và lọc theo lớp học:

```java
@GetMapping("/sinhvien/search")
public List<SinhVien> searchSinhVien(
        @RequestParam(name = "ten") String name,
        @RequestParam(name = "lop", required = false) String className) {
    if (className != null) {
        return sinhVienService.findByNameAndClass(name, className);
    } else {
        return sinhVienService.findByName(name);
    }
}
```

Trong ví dụ này:
- URL `http://localhost:8080/sinhvien/search?ten=John&lop=10A` sẽ tìm kiếm sinh viên tên John trong lớp 10A.
- Nếu `lop` không được cung cấp, phương thức sẽ tìm kiếm sinh viên chỉ theo tên.

### Giải thích trường hợp thực tế

Trong các ứng dụng web, người dùng thường nhập thông tin vào các form, và các giá trị từ form này sẽ được gửi lên server dưới dạng query parameters. `@RequestParam` cho phép bạn dễ dàng ánh xạ những giá trị này vào các phương thức của controller, từ đó xử lý và trả về kết quả tương ứng.

Ví dụ, trong một trang tìm kiếm sản phẩm, người dùng có thể nhập từ khóa tìm kiếm và chọn các bộ lọc khác nhau (như giá cả, danh mục). Các giá trị này sẽ được truyền lên server thông qua query parameters, và `@RequestParam` sẽ giúp bạn xử lý chúng trong các phương thức của controller.