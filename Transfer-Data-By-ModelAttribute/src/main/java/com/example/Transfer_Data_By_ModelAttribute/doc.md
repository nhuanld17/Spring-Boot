### Giới thiệu về `@ModelAttribute`

Trong Spring MVC, `@ModelAttribute` là một annotation rất hữu ích giúp bạn truyền dữ liệu giữa Controller và View. Nó có thể được sử dụng để:

1. **Chuyển dữ liệu từ Controller đến View**: Bạn có thể thêm đối tượng vào mô hình, và Spring sẽ tự động truyền nó đến view.
2. **Ràng buộc dữ liệu từ Request đến đối tượng trong Controller**: Khi một form được gửi, Spring có thể tự động gán các giá trị từ request vào một đối tượng Java.

### Nguyên lý hoạt động của `@ModelAttribute`

1. **Khi sử dụng trong phương thức**: Khi một phương thức được đánh dấu với `@ModelAttribute`, Spring sẽ gọi phương thức đó trước khi gọi các phương thức xử lý yêu cầu khác. Kết quả trả về của phương thức này sẽ được thêm vào model với tên mà bạn chỉ định (hoặc tên của kiểu đối tượng nếu không chỉ định).

2. **Khi sử dụng với tham số**: Bạn có thể sử dụng `@ModelAttribute` để ràng buộc dữ liệu từ request vào một đối tượng. Spring sẽ tự động lấy các giá trị từ các trường trong request và gán vào các thuộc tính của đối tượng.

### Ví dụ thực tế

Giả sử bạn đang xây dựng một ứng dụng quản lý sinh viên và bạn muốn truyền dữ liệu sinh viên từ Controller đến View.

#### Bước 1: Tạo đối tượng Sinh viên

```java
public class Student {
    private String name;
    private int age;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

#### Bước 2: Tạo Controller

```java
@Controller
public class StudentController {

    @ModelAttribute("student")
    public Student populateStudent() {
        return new Student(); // Trả về một đối tượng sinh viên mới
    }

    @GetMapping("/student/form")
    public String showForm(Model model) {
        return "studentForm"; // Trả về view để hiển thị form
    }

    @PostMapping("/student/save")
    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        // Lưu sinh viên (chưa có logic lưu trong ví dụ này)
        redirectAttributes.addFlashAttribute("message", "Student saved successfully: " + student.getName());
        return "redirect:/student/form"; // Redirect về form
    }
}
```

#### Bước 3: Tạo View (studentForm.html)

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Form</title>
</head>
<body>
    <h1>Student Form</h1>
    <form action="/student/save" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" th:value="${student.name}"/><br/>
        
        <label for="age">Age:</label>
        <input type="number" id="age" name="age" th:value="${student.age}"/><br/>
        
        <button type="submit">Submit</button>
    </form>

    <div th:if="${message}" th:text="${message}"></div> <!-- Hiển thị thông báo -->
</body>
</html>
```

### Mô phỏng việc truyền dữ liệu từ Controller sang View qua Session

Giả sử bạn muốn lưu thông tin sinh viên vào session trước khi chuyển đến view.

#### Bước 1: Sử dụng HttpSession trong Controller

```java
@Controller
public class StudentController {

    @GetMapping("/student/form")
    public String showForm(HttpSession session, Model model) {
        // Lấy thông tin sinh viên từ session (nếu có)
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            model.addAttribute("student", student);
        }
        return "studentForm"; // Trả về view để hiển thị form
    }

    @PostMapping("/student/save")
    public String saveStudent(@ModelAttribute("student") Student student, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lưu sinh viên vào session
        session.setAttribute("student", student);
        redirectAttributes.addFlashAttribute("message", "Student saved successfully: " + student.getName());
        return "redirect:/student/form"; // Redirect về form
    }
}
```

#### Bước 2: View (không thay đổi)

View vẫn sẽ sử dụng cùng một template `studentForm.html` như ở trên.

### Kết luận

- `@ModelAttribute` là công cụ mạnh mẽ trong Spring MVC giúp bạn truyền dữ liệu giữa Controller và View một cách dễ dàng.
- Bạn có thể sử dụng nó để tạo đối tượng cho view hoặc để ràng buộc dữ liệu từ request vào đối tượng Java.
- Kết hợp với session, bạn có thể lưu trữ dữ liệu tạm thời giữa các request, giúp ứng dụng trở nên linh hoạt và dễ quản lý hơn.

Nếu bạn cần thêm thông tin hay giải thích chi tiết hơn về phần nào, hãy cho tôi biết nhé!