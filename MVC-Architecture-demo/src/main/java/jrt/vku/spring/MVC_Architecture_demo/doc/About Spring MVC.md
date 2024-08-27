### Spring MVC là gì?

**Spring MVC** (Model-View-Controller) là một module của Spring Framework, cung cấp một cách tiếp cận mạnh mẽ và linh hoạt để xây dựng các ứng dụng web dựa trên kiến trúc MVC. Nó tách biệt các thành phần logic, dữ liệu, và giao diện người dùng trong một ứng dụng web, giúp quản lý dễ dàng hơn và tăng tính khả dụng của ứng dụng.

### Các thành phần chính trong Spring MVC

1. **Model**:
    - Chứa dữ liệu của ứng dụng và quy định cách dữ liệu này được xử lý và lưu trữ.
    - Trong Spring MVC, Model được đại diện bởi các lớp Java đơn giản (Plain Old Java Objects - POJOs). Nó có thể là một lớp riêng biệt hoặc dữ liệu được lưu trữ trong cơ sở dữ liệu.

2. **View**:
    - Là phần giao diện người dùng, nơi dữ liệu từ Model được hiển thị. View thường được tạo bằng các template engines như Thymeleaf, JSP, hoặc các framework JavaScript như Angular hoặc React.
    - View không chứa logic xử lý, mà chỉ tập trung vào việc hiển thị dữ liệu.

3. **Controller**:
    - Đóng vai trò trung gian giữa Model và View. Controller nhận yêu cầu từ người dùng (thông qua các request HTTP), xử lý yêu cầu đó bằng cách tương tác với Model, sau đó trả về một View tương ứng để hiển thị kết quả cho người dùng.

### Cách hoạt động của Spring MVC

1. Người dùng gửi một yêu cầu (request) HTTP đến ứng dụng web.
2. DispatcherServlet (là một front controller) nhận yêu cầu và chuyển tiếp nó đến Controller phù hợp.
3. Controller xử lý yêu cầu bằng cách gọi các phương thức cần thiết trong Model để truy xuất hoặc lưu trữ dữ liệu.
4. Controller trả về một Model chứa dữ liệu cần thiết cho View, cùng với tên của View cần sử dụng.
5. View sẽ lấy dữ liệu từ Model và hiển thị nó cho người dùng.
6. Kết quả cuối cùng được gửi lại cho người dùng thông qua HTTP response.

### Ví dụ Spring MVC đơn giản

Giả sử bạn muốn tạo một trang web hiển thị một thông điệp chào mừng đơn giản.

#### 1. Cấu hình Spring MVC:

```java
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.demo")
public class WebConfig implements WebMvcConfigurer {
    // Cấu hình Spring MVC ở đây
}
```

#### 2. Tạo Controller:

```java
@Controller
public class HomeController {

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với Spring MVC!");
        return "welcome";  // tên của View (welcome.html)
    }
}
```

#### 3. Tạo View (Thymeleaf Template):

`welcome.html` trong thư mục `src/main/resources/templates/`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Welcome</title>
</head>
<body>
    <h1 th:text="${message}"></h1>
</body>
</html>
```

#### 4. Kết quả

- Khi người dùng truy cập `http://localhost:8080/welcome`, Spring MVC sẽ:
    - Gọi Controller `HomeController` để xử lý yêu cầu.
    - Controller sẽ thêm thông điệp "Chào mừng bạn đến với Spring MVC!" vào Model và trả về tên View là `welcome`.
    - Thymeleaf sẽ render View `welcome.html` với dữ liệu từ Model và hiển thị kết quả cuối cùng cho người dùng.

### Kết luận

Spring MVC giúp bạn xây dựng các ứng dụng web một cách rõ ràng và có cấu trúc tốt. Bằng cách tách biệt logic xử lý, dữ liệu và giao diện, Spring MVC giúp mã nguồn dễ bảo trì, mở rộng và kiểm thử.