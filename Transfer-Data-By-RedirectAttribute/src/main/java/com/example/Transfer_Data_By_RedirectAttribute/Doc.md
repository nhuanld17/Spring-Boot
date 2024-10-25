`RedirectAttributes` trong Spring MVC là một công cụ hữu ích để truyền dữ liệu từ Controller này sang View khác, đặc biệt là trong trường hợp điều hướng với `redirect:`. Khi bạn thực hiện chuyển hướng từ một Controller sang một URL khác, mô hình (Model) không được chuyển tiếp theo cách thông thường. Đây là lúc `RedirectAttributes` phát huy tác dụng, cho phép bạn truyền dữ liệu qua Redirect mà không cần phụ thuộc vào URL parameters hoặc session attributes.

### Nguyên lý hoạt động của RedirectAttributes

1. **Chuyển hướng (`redirect:`)**: Khi bạn sử dụng `redirect:` trong Spring MVC, trình duyệt sẽ gửi một yêu cầu HTTP mới tới URL đã được chỉ định. Điều này có nghĩa là mọi dữ liệu chỉ có trên request ban đầu sẽ không tồn tại trong request mới.

2. **Truyền dữ liệu**: `RedirectAttributes` được sử dụng để thêm các thuộc tính vào redirect URL hoặc lưu các thuộc tính tạm thời (flash attributes) để có thể truy cập được sau khi chuyển hướng.

3. **Flash Attributes**: Các thuộc tính này được lưu trong session một cách tạm thời và chỉ tồn tại cho đến khi yêu cầu tiếp theo hoàn tất. Sau đó, chúng sẽ bị xóa khỏi session.

4. **Khác biệt với Session**: Khác với việc sử dụng session attributes (dữ liệu sẽ tồn tại cho đến khi bị xoá một cách rõ ràng hoặc session kết thúc), flash attributes chỉ tồn tại trong thời gian rất ngắn, giúp tránh vấn đề về việc dữ liệu dư thừa tồn tại lâu trong session.

### Cách sử dụng `RedirectAttributes`

Để sử dụng `RedirectAttributes`, bạn sẽ thêm dữ liệu vào flash attributes trong Controller và dữ liệu này sẽ có sẵn trong View sau khi chuyển hướng.

### Ví dụ thực tế: Truyền dữ liệu từ Controller sang View bằng `RedirectAttributes`

Dưới đây là một ví dụ chi tiết mô phỏng việc sử dụng `RedirectAttributes` trong Spring Boot:

#### 1. Cấu trúc dự án

- **`HomeController`**: Controller chứa logic xử lý.
- **Templates**:
    - **`home.html`**: Trang hiển thị dữ liệu.
    - **`result.html`**: Trang kết quả sau khi chuyển hướng.

#### 2. Code thực tế

##### `HomeController.java`

```java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Trả về view home.html
    }

    @PostMapping("/submitForm")
    public String submitForm(@RequestParam("name") String name, 
                             RedirectAttributes redirectAttributes) {
        // Thêm dữ liệu vào RedirectAttributes để chuyển tiếp
        redirectAttributes.addFlashAttribute("message", "Hello, " + name + "!");
        return "redirect:/result"; // Chuyển hướng đến /result
    }

    @GetMapping("/result")
    public String result() {
        // Dữ liệu "message" sẽ có sẵn ở đây sau khi chuyển hướng
        return "result"; // Trả về view result.html
    }
}
```

##### `home.html`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
    <h1>Home Page</h1>
    <form action="/submitForm" method="post">
        <label for="name">Enter your name:</label>
        <input type="text" id="name" name="name" required>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
```

##### `result.html`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Result</title>
</head>
<body>
    <h1>Result Page</h1>
    <p th:text="${message}"></p> <!-- Hiển thị thông báo từ flash attribute -->
</body>
</html>
```

### Giải thích

1. **Trang `home.html`**: Người dùng nhập tên và nhấn submit, điều này sẽ gửi yêu cầu `POST` đến `/submitForm`.

2. **Phương thức `submitForm`**: Khi nhận được yêu cầu, phương thức này lấy tên người dùng từ form và thêm thông điệp vào `RedirectAttributes` với `addFlashAttribute`. Sau đó, nó chuyển hướng tới `/result`.

3. **Trang `result.html`**: Phương thức `result` hiển thị dữ liệu flash attribute đã được thêm vào trước đó, cụ thể là thông điệp "Hello, [tên]!".

4. **Flash Attributes**: Các giá trị này không nằm trong URL và chỉ tồn tại trong session cho đến khi trang mới được hiển thị, giúp bảo mật và tránh dữ liệu dư thừa.

### Lợi ích của RedirectAttributes

- **Bảo mật**: Không để lộ dữ liệu trên URL.
- **Dễ dùng**: Tránh quản lý session phức tạp.
- **Tạm thời**: Không giữ dữ liệu quá lâu, tránh lãng phí bộ nhớ.

`RedirectAttributes` là một cách tiện lợi và an toàn để truyền dữ liệu tạm thời từ Controller sang View trong Spring MVC, đặc biệt hữu ích khi cần chuyển hướng.