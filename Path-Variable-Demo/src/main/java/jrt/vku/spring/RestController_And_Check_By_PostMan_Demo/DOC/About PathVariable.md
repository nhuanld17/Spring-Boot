`@PathVariable` là một annotation trong Spring Framework, được sử dụng để lấy các biến từ URL (đường dẫn) trong các phương thức xử lý của controller. Nó giúp bạn trích xuất các phần của URL và sử dụng chúng như các tham số của phương thức.

### Cách hoạt động của `@PathVariable`

Khi một URL có chứa các đoạn động (dynamic segments), bạn có thể sử dụng `@PathVariable` để lấy giá trị của các đoạn này và sử dụng trong logic xử lý của phương thức.

### Ví dụ cơ bản

Giả sử bạn có một ứng dụng quản lý sinh viên và bạn muốn lấy thông tin sinh viên dựa trên ID được cung cấp trong URL.

#### 1. Định nghĩa route với `@PathVariable`

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @GetMapping("/sinhvien/{id}")
    public SinhVien getSinhVienById(@PathVariable int id) {
        // Giả sử bạn có một phương thức để tìm sinh viên theo ID
        SinhVien sinhVien = findSinhVienById(id);
        return sinhVien;
    }

    // Giả lập phương thức tìm sinh viên theo ID
    private SinhVien findSinhVienById(int id) {
        // Thực hiện logic tìm sinh viên theo ID
        return new SinhVien(id, "Nguyen Van", "A", "email@example.com"); // giả sử tìm thấy
    }
}
```

#### 2. Truy cập endpoint

Khi bạn truy cập vào URL: `http://localhost:8080/sinhvien/123`, giá trị `123` sẽ được gán cho biến `id` trong phương thức `getSinhVienById`. Phương thức này sẽ lấy giá trị `id` và sử dụng nó để tìm sinh viên với ID tương ứng.

### Các tùy chọn của `@PathVariable`

- **Tên biến đường dẫn và biến phương thức**: Tên của biến trong URL có thể khác với tên biến trong phương thức. Khi đó, bạn có thể chỉ định tên biến đường dẫn bằng cách sử dụng cú pháp sau:

  ```java
  @GetMapping("/sinhvien/{studentId}")
  public SinhVien getSinhVienById(@PathVariable("studentId") int id) {
      // id sẽ lấy giá trị từ đoạn động "studentId" trong URL
  }
  ```

- **Nhiều `@PathVariable` trong một phương thức**: Bạn có thể sử dụng nhiều `@PathVariable` để trích xuất nhiều phần của URL.

  ```java
  @GetMapping("/sinhvien/{id}/{email}")
  public SinhVien getSinhVienByIdAndEmail(@PathVariable int id, @PathVariable String email) {
      // Sử dụng cả id và email để tìm sinh viên
  }
  ```

### Trường hợp thực tế

`@PathVariable` thường được sử dụng khi bạn muốn thực hiện các thao tác dựa trên các giá trị trong URL, như truy vấn cơ sở dữ liệu, cập nhật thông tin, hoặc xử lý các yêu cầu cụ thể mà trong đó URL chứa các thông tin động.

Ví dụ, trong một API RESTful, bạn có thể có các route như:

- `GET /sinhvien/{id}`: Lấy thông tin sinh viên theo ID.
- `DELETE /sinhvien/{id}`: Xóa sinh viên theo ID.
- `PUT /sinhvien/{id}`: Cập nhật thông tin sinh viên theo ID.

### Lợi ích của `@PathVariable`

- **URL rõ ràng và thân thiện**: Sử dụng `@PathVariable` giúp tạo ra các URL dễ hiểu và rõ ràng, phù hợp với chuẩn RESTful.
- **Lấy dữ liệu động dễ dàng**: Cho phép bạn dễ dàng trích xuất và sử dụng các phần động trong URL để xử lý logic kinh doanh.

Nếu bạn cần thêm ví dụ hoặc giải thích chi tiết về `@PathVariable`, hãy cho mình biết nhé!