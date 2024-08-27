`@Service` là một annotation trong Spring Framework được sử dụng để đánh dấu một lớp là một "service" (dịch vụ) trong tầng service của ứng dụng. Annotation này giúp Spring biết rằng lớp đó cần được quản lý bởi Spring container, đồng thời hỗ trợ việc phát hiện tự động (component scanning) và tiêm phụ thuộc (dependency injection).

### Giải thích chi tiết

- **Tầng Service trong Spring**: Trong một ứng dụng phân lớp (layered architecture), tầng service chịu trách nhiệm xử lý các logic nghiệp vụ. Các lớp trong tầng này thường gọi tới các lớp DAO (Data Access Object) hoặc Repository để truy cập dữ liệu, thực hiện các thao tác như tính toán, kiểm tra nghiệp vụ, và gọi tới các lớp khác trong hệ thống.

- **Quản lý bean trong Spring**: Khi bạn đánh dấu một lớp bằng `@Service`, Spring sẽ tự động phát hiện và quản lý nó như một bean trong container. Điều này có nghĩa là bạn có thể tiêm (inject) các bean khác vào lớp này và cũng có thể tiêm bean này vào các lớp khác.

- **Tích hợp với các annotation khác**: `@Service` thường được sử dụng cùng với các annotation như `@Autowired` để tiêm phụ thuộc, `@Transactional` để quản lý giao dịch, và các annotation khác trong Spring.

### Ví dụ thực tế

Giả sử bạn đang xây dựng một hệ thống quản lý sinh viên. Bạn cần tạo một service để quản lý thông tin sinh viên, bao gồm các chức năng như thêm sinh viên, tìm sinh viên theo ID, và cập nhật thông tin sinh viên.

#### 1. Lớp Service

```java
package com.example.demo.service;

import com.example.demo.dao.StudentDAO;
import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentDAO studentDAO;

    @Autowired
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    public Student findById(int id) {
        return studentDAO.findById(id);
    }

    public Student save(Student student) {
        return studentDAO.save(student);
    }

    public void deleteById(int id) {
        studentDAO.deleteById(id);
    }
}
```

#### 2. Lớp DAO (Data Access Object)

```java
package com.example.demo.dao;

import com.example.demo.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDAO {

    private List<Student> students = new ArrayList<>();

    public List<Student> findAll() {
        return students;
    }

    public Student findById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Student save(Student student) {
        students.add(student);
        return student;
    }

    public void deleteById(int id) {
        students.removeIf(student -> student.getId() == id);
    }
}
```

#### 3. Lớp Controller

```java
package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable int id) {
        return studentService.findById(id);
    }

    @PostMapping("/")
    public Student addStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentService.deleteById(id);
    }
}
```

### Giải thích

1. **Lớp `StudentService`**: Được đánh dấu với `@Service`, lớp này chứa các logic nghiệp vụ liên quan đến sinh viên. Các phương thức trong lớp này tương tác với lớp `StudentDAO` để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên đối tượng `Student`.

2. **Tiêm phụ thuộc (`@Autowired`)**: Constructor của `StudentService` được tiêm phụ thuộc `StudentDAO` bằng cách sử dụng `@Autowired`. Spring sẽ tự động tìm và tiêm một instance của `StudentDAO` vào `StudentService`.

3. **Lớp `StudentController`**: Lớp này sử dụng `StudentService` để xử lý các yêu cầu từ phía người dùng. Ví dụ, khi người dùng gửi một yêu cầu GET tới `/students/`, `StudentController` sẽ gọi phương thức `findAll` của `StudentService` để lấy danh sách sinh viên và trả về kết quả.

### Kết luận

Annotation `@Service` trong Spring rất quan trọng khi bạn muốn tạo các lớp dịch vụ để quản lý logic nghiệp vụ trong ứng dụng. Nó giúp phân tách rõ ràng các tầng trong ứng dụng và giúp Spring quản lý các bean một cách tự động, hỗ trợ việc tiêm phụ thuộc và làm cho mã nguồn dễ bảo trì và mở rộng hơn.