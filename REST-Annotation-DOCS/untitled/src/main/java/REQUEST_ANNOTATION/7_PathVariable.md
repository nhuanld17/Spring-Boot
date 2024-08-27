`@PathVariable` là một annotation trong Spring Framework dùng để ràng buộc giá trị của một biến đường dẫn (URI template variable) với một tham số trong phương thức xử lý của controller. Điều này cho phép bạn trích xuất các giá trị từ URL và sử dụng chúng trong phương thức xử lý.

### Cách sử dụng `@PathVariable`

#### Ví dụ cơ bản

Giả sử bạn có một ứng dụng quản lý sinh viên, và bạn muốn tạo một API để lấy thông tin của một sinh viên dựa trên ID của họ. Để làm điều này, bạn có thể sử dụng `@PathVariable` để lấy giá trị ID từ URL.

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SinhVienController {

    @GetMapping("/sinhvien/{id}")
    public String getSinhVienById(@PathVariable int id) {
        // Giả sử bạn có logic để lấy sinh viên từ cơ sở dữ liệu bằng ID
        return "Thông tin sinh viên có ID: " + id;
    }
}
```

Trong ví dụ trên:
- `@GetMapping("/sinhvien/{id}")`: Đây là endpoint mà phương thức `getSinhVienById()` sẽ xử lý. `{id}` là một biến đường dẫn, giá trị của nó sẽ được truyền vào phương thức.
- `@PathVariable int id`: Annotation này ràng buộc giá trị của `{id}` từ URL với tham số `id` trong phương thức.

Nếu bạn gửi một yêu cầu GET tới URL `/sinhvien/123`, thì phương thức `getSinhVienById()` sẽ nhận được giá trị `id` là `123`, và nó sẽ trả về chuỗi `"Thông tin sinh viên có ID: 123"`.

### Ví dụ thực tế

Giả sử bạn đang xây dựng một RESTful API cho một cửa hàng sách trực tuyến. Bạn muốn cung cấp một API để lấy thông tin chi tiết về một cuốn sách dựa trên ISBN (International Standard Book Number).

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/books/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        // Giả sử bạn có logic để tìm cuốn sách trong cơ sở dữ liệu bằng ISBN
        Book book = bookService.findBookByIsbn(isbn);
        return book;
    }
}
```

Trong ví dụ này:
- Endpoint `/books/{isbn}` sẽ được xử lý bởi phương thức `getBookByIsbn()`.
- `@PathVariable String isbn`: Annotation này ràng buộc giá trị của `{isbn}` từ URL với tham số `isbn` trong phương thức.

Nếu bạn gửi một yêu cầu GET tới URL `/books/978-3-16-148410-0`, phương thức `getBookByIsbn()` sẽ nhận được giá trị `isbn` là `978-3-16-148410-0` và trả về thông tin chi tiết về cuốn sách có ISBN này.

### Khi nào nên sử dụng `@PathVariable`

- **Khi bạn cần lấy dữ liệu động từ URL**: Ví dụ như ID, mã sản phẩm, tên người dùng, hoặc các thông tin đặc trưng khác.
- **Trong các API RESTful**: Để ánh xạ các tham số URL đến các phương thức trong controller.

### So sánh với `@RequestParam`

- `@PathVariable` lấy dữ liệu từ đường dẫn URL.
- `@RequestParam` lấy dữ liệu từ các tham số query trong URL (sau dấu `?`).

Ví dụ: URL `/books?isbn=978-3-16-148410-0` có thể được xử lý bằng `@RequestParam`, trong khi URL `/books/978-3-16-148410-0` sẽ được xử lý bằng `@PathVariable`.

### Lợi ích của `@PathVariable`

- **Đơn giản và trực quan**: Nó giúp các URL trở nên rõ ràng và dễ hiểu.
- **Thân thiện với REST**: Giúp xây dựng các endpoint RESTful tuân theo quy ước tốt.

Nếu bạn cần thêm bất kỳ ví dụ nào khác hoặc muốn biết thêm chi tiết về `@PathVariable`, mình sẵn lòng hỗ trợ!