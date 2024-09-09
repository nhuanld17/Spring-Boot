### REST Controller trong Spring Boot

**REST Controller** trong Spring Boot là một thành phần được sử dụng để xây dựng các API theo kiến trúc RESTful. RESTful API (Representational State Transfer) là một kiểu kiến trúc phổ biến để xây dựng các dịch vụ web, nơi các tài nguyên (resources) có thể được truy cập và thao tác bằng các phương thức HTTP như `GET`, `POST`, `PUT`, `DELETE`.

Trong Spring Boot, chúng ta sử dụng **`@RestController`** để đánh dấu một lớp Java là một REST Controller. Controller này sẽ xử lý các yêu cầu HTTP và trả về dữ liệu, thường dưới dạng JSON hoặc XML.

### Đặc điểm chính của REST Controller trong Spring Boot
1. **`@RestController`** là một phiên bản đặc biệt của **`@Controller`**, tự động thêm **`@ResponseBody`** vào tất cả các phương thức, tức là kết quả của phương thức sẽ được trả về dưới dạng JSON hoặc XML thay vì chuyển hướng đến một view.
2. Sử dụng các annotation như **`@GetMapping`**, **`@PostMapping`**, **`@PutMapping`**, và **`@DeleteMapping`** để ánh xạ các yêu cầu HTTP tương ứng.
3. Được sử dụng để xây dựng API RESTful cho các ứng dụng backend, giao tiếp giữa client-server.

### Cách sử dụng REST Controller

#### Ví dụ về REST Controller cơ bản

Trong ví dụ sau, chúng ta sẽ tạo một REST Controller để quản lý các đối tượng `Student`. Controller này sẽ có các phương thức để thực hiện các thao tác như lấy danh sách sinh viên, thêm mới, cập nhật và xóa sinh viên.

#### 1. Định nghĩa REST Controller

```java
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Giả lập cơ sở dữ liệu với danh sách sinh viên
    private List<Student> students = new ArrayList<>();

    // Lấy danh sách sinh viên
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // Lấy thông tin sinh viên theo ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm mới một sinh viên
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    // Cập nhật thông tin sinh viên
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(updatedStudent.getName());
                student.setAge(updatedStudent.getAge());
                return student;
            }
        }
        return null;
    }

    // Xóa sinh viên theo ID
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        students.removeIf(student -> student.getId() == id);
        return "Student with ID " + id + " has been deleted";
    }
}
```

#### 2. Định nghĩa class `Student`

```java
public class Student {
    private int id;
    private String name;
    private int age;

    public Student() {}

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

### Giải thích chi tiết:

1. **`@RestController`**: Annotation này xác định rằng lớp `StudentController` sẽ là một REST controller, và tất cả các phương thức bên trong sẽ trả về dữ liệu (thường là JSON).

2. **`@RequestMapping("/students")`**: Tất cả các URL trong lớp này sẽ bắt đầu với `/students`. Ví dụ:
    - Lấy danh sách sinh viên: `GET /students`
    - Lấy thông tin sinh viên theo ID: `GET /students/{id}`

3. **`@GetMapping`**: Được sử dụng để ánh xạ các yêu cầu HTTP GET. Trong ví dụ này:
    - **`getAllStudents()`** sẽ trả về danh sách tất cả sinh viên.
    - **`getStudentById(int id)`** sẽ trả về thông tin sinh viên theo `id`.

4. **`@PostMapping`**: Được sử dụng để ánh xạ các yêu cầu HTTP POST. **`addStudent(Student student)`** sẽ thêm mới một sinh viên vào danh sách và trả về sinh viên vừa thêm.

5. **`@PutMapping("/{id}")`**: Được sử dụng cho các yêu cầu HTTP PUT (cập nhật). **`updateStudent(int id, Student updatedStudent)`** sẽ cập nhật thông tin sinh viên dựa trên `id`.

6. **`@DeleteMapping("/{id}")`**: Được sử dụng cho các yêu cầu HTTP DELETE. **`deleteStudent(int id)`** sẽ xóa sinh viên theo `id` và trả về thông báo.

7. **`@PathVariable`**: Sử dụng để ánh xạ giá trị từ URL vào tham số của phương thức. Ví dụ: `@PathVariable int id` sẽ lấy giá trị `id` từ đường dẫn `/students/{id}`.

8. **`@RequestBody`**: Sử dụng để ánh xạ dữ liệu JSON từ body của yêu cầu HTTP vào đối tượng Java. Trong ví dụ này, nó được sử dụng trong phương thức `addStudent` và `updateStudent` để nhận dữ liệu sinh viên từ body của request.

### Ví dụ yêu cầu HTTP

- **GET /students**
    - Trả về danh sách tất cả sinh viên dưới dạng JSON.

- **POST /students**
    - Body request:
      ```json
      {
        "id": 1,
        "name": "John Doe",
        "age": 20
      }
      ```
    - Thêm sinh viên mới và trả về sinh viên vừa thêm.

- **PUT /students/1**
    - Body request:
      ```json
      {
        "name": "John Smith",
        "age": 21
      }
      ```
    - Cập nhật thông tin sinh viên có `id = 1` và trả về sinh viên đã cập nhật.

- **DELETE /students/1**
    - Xóa sinh viên có `id = 1` và trả về thông báo đã xóa.

### Kết luận:

- **`@RestController`** là một công cụ mạnh mẽ trong Spring Boot để xây dựng các RESTful API dễ dàng và hiệu quả.
- Các phương thức như **`@GetMapping`**, **`@PostMapping`**, **`@PutMapping`**, và **`@DeleteMapping`** giúp ánh xạ các phương thức HTTP với các phương thức xử lý trong controller.
- Việc sử dụng **`@PathVariable`** và **`@RequestBody`** giúp dễ dàng truy xuất và ánh xạ dữ liệu từ yêu cầu HTTP vào các đối tượng Java.

REST Controller là phần quan trọng trong Spring Boot để xây dựng các API backend hiện đại, cho phép client-server giao tiếp dễ dàng thông qua HTTP.