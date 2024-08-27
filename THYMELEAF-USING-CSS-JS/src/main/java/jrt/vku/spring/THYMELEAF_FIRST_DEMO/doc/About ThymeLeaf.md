### Giới thiệu về Thymeleaf

**Thymeleaf** là một Java template engine được thiết kế để xử lý và xuất ra các tài liệu như HTML, XML, JavaScript, CSS, văn bản thuần túy. Thymeleaf đặc biệt được ưa chuộng trong các ứng dụng web vì khả năng kết hợp mạnh mẽ giữa việc hiển thị dữ liệu từ phía server và việc tạo ra giao diện người dùng động.

### Đặc điểm nổi bật của Thymeleaf

1. **Khả năng "Natural Templating"**:
    - Thymeleaf cho phép bạn tạo ra các template HTML mà khi mở trong trình duyệt vẫn hiển thị bình thường ngay cả khi không có dữ liệu từ server. Điều này giúp bạn dễ dàng phát triển và kiểm tra giao diện người dùng mà không cần chạy ứng dụng.

2. **Hỗ trợ đa dạng ngôn ngữ**:
    - Thymeleaf có thể xử lý nhiều loại tài liệu khác nhau như HTML, XML, JavaScript, CSS, và văn bản thuần túy.

3. **Tích hợp chặt chẽ với Spring Framework**:
    - Thymeleaf tích hợp rất tốt với Spring MVC, làm cho việc hiển thị dữ liệu từ mô hình (model) lên giao diện (view) trở nên dễ dàng hơn.

4. **Dễ dàng mở rộng**:
    - Thymeleaf hỗ trợ việc mở rộng thông qua các tùy chỉnh và plugin, giúp bạn tạo ra các giải pháp linh hoạt phù hợp với nhu cầu của ứng dụng.

### Ví dụ thực tế

#### 1. **Cấu hình Thymeleaf trong Spring Boot**
Khi tạo một ứng dụng Spring Boot, Thymeleaf thường được cấu hình sẵn trong `pom.xml` hoặc `build.gradle` của dự án.

**Ví dụ `pom.xml`**:
```xml
<dependencies>
    <!-- Thymeleaf dependency -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
</dependencies>
```

#### 2. **Tạo một view với Thymeleaf**

Giả sử bạn có một controller trong Spring Boot:

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Thymeleaf!");
        return "home";  // Tên của template sẽ được Thymeleaf tìm trong thư mục templates
    }
}
```

**Template `home.html`** (đặt trong thư mục `src/main/resources/templates`):

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Home Page</title>
</head>
<body>
    <h1>Thymeleaf Example</h1>
    <p th:text="${message}">Default message</p>
</body>
</html>
```

- **Giải thích**:
    - `th:text="${message}"`: Đây là cú pháp của Thymeleaf, thay thế giá trị của biến `message` từ mô hình (`model`) vào phần tử `<p>`. Nếu biến `message` không tồn tại, nó sẽ hiển thị `Default message`.

#### 3. **Thêm điều kiện hiển thị**
Thymeleaf cho phép bạn thêm logic điều kiện vào các phần tử HTML.

**Ví dụ**: Hiển thị một thông báo nếu người dùng đăng nhập thành công:

```html
<div th:if="${user != null}">
    <p>Welcome, <span th:text="${user.username}">User</span>!</p>
</div>
<div th:unless="${user != null}">
    <p>Please <a href="/login">login</a>.</p>
</div>
```

- **Giải thích**:
    - `th:if="${user != null}"`: Nếu biến `user` không null, phần tử `<div>` sẽ được hiển thị.
    - `th:unless="${user != null}"`: Nếu biến `user` là null, phần tử `<div>` sẽ hiển thị phần yêu cầu người dùng đăng nhập.

### Kết luận

Thymeleaf là một công cụ mạnh mẽ và linh hoạt giúp phát triển giao diện người dùng động trong các ứng dụng Java. Sự kết hợp giữa tính dễ hiểu, khả năng mở rộng, và tích hợp tốt với Spring Framework khiến Thymeleaf trở thành lựa chọn phổ biến cho nhiều dự án Java web. Hy vọng các ví dụ trên đã giúp bạn có cái nhìn rõ ràng và dễ hiểu về Thymeleaf!