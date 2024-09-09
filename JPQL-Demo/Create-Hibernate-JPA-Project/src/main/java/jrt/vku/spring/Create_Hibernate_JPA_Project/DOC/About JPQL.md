### JPQL trong Java Spring Boot

**JPQL** (Java Persistence Query Language) là một ngôn ngữ truy vấn hướng đối tượng dành cho JPA (Java Persistence API). JPQL cho phép bạn truy vấn và thao tác với dữ liệu trong cơ sở dữ liệu theo mô hình hướng đối tượng, thay vì truy vấn trực tiếp trên các bảng như trong SQL. JPQL làm việc với các thực thể (entities) chứ không làm việc trực tiếp với các bảng trong cơ sở dữ liệu.

### Đặc điểm chính của JPQL
- JPQL hoạt động trên các đối tượng **entity** (như `Employee`, `Customer`, `Order`) thay vì các bảng cơ sở dữ liệu.
- Cú pháp của JPQL tương tự như SQL, nhưng thay vì làm việc với bảng và cột, nó làm việc với các class và thuộc tính của class.
- JPQL sử dụng cú pháp như `SELECT`, `FROM`, `WHERE`, `JOIN`, `ORDER BY` tương tự SQL, nhưng tất cả đều áp dụng cho các đối tượng Java.

### Ví dụ về JPQL

Giả sử bạn có hai thực thể `Student` và `Course` với mối quan hệ **nhiều-nhiều** (Many-to-Many):

#### Thực thể `Student`

```java
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    // Constructor, getter, setter
}
```

#### Thực thể `Course`

```java
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    // Constructor, getter, setter
}
```

### Sử dụng JPQL để truy vấn

Giả sử bạn muốn truy vấn tất cả các sinh viên đăng ký một khóa học nhất định. Bạn có thể viết JPQL trong Spring như sau:

```java
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Student> findStudentsByCourseTitle(String courseTitle) {
        // JPQL Query để lấy danh sách sinh viên theo tên khóa học
        String jpql = "SELECT s FROM Student s JOIN s.courses c WHERE c.title = :courseTitle";
        TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
        query.setParameter("courseTitle", courseTitle);
        
        return query.getResultList();
    }
}
```

#### Giải thích:
- **`SELECT s FROM Student s JOIN s.courses c WHERE c.title = :courseTitle`**:
    - `SELECT s`: Chọn đối tượng `Student` (không phải các cột như SQL mà là các đối tượng).
    - `FROM Student s`: Truy vấn từ thực thể `Student`.
    - `JOIN s.courses c`: Kết hợp (`JOIN`) giữa thực thể `Student` và các `Course` mà sinh viên đó đã đăng ký.
    - `WHERE c.title = :courseTitle`: Điều kiện lọc theo tiêu đề của khóa học (`Course`).

- **`query.setParameter("courseTitle", courseTitle)`**: Thiết lập giá trị cho tham số `courseTitle` trong câu truy vấn.

- **`query.getResultList()`**: Lấy danh sách kết quả từ câu truy vấn.

### Sử dụng JPQL trong Spring Data JPA

Trong **Spring Data JPA**, bạn có thể định nghĩa JPQL ngay trong phương thức của repository mà không cần phải viết câu lệnh JPQL dài dòng trong code. Bạn chỉ cần sử dụng annotation `@Query` để xác định truy vấn.

#### Ví dụ với Spring Data JPA:

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.title = :courseTitle")
    List<Student> findStudentsByCourseTitle(@Param("courseTitle") String courseTitle);
}
```

#### Giải thích:
- **`@Query`**: Được sử dụng để định nghĩa JPQL truy vấn ngay trong interface repository.
- **`@Param("courseTitle")`**: Được dùng để gắn tham số `courseTitle` với tham số của câu JPQL.
- Spring Data JPA sẽ tự động tạo ra câu truy vấn JPQL và thực thi nó mà không cần bạn phải viết nhiều code như trước.

### Các thành phần chính của JPQL

1. **SELECT**: Để chọn dữ liệu.
    - Ví dụ: `SELECT s FROM Student s` - Chọn tất cả sinh viên.

2. **FROM**: Để chỉ định thực thể sẽ truy vấn.
    - Ví dụ: `FROM Student s` - Truy vấn từ thực thể `Student`.

3. **WHERE**: Để lọc dữ liệu theo điều kiện.
    - Ví dụ: `WHERE s.name = 'John'` - Lọc sinh viên theo tên.

4. **JOIN**: Để kết hợp các thực thể có quan hệ với nhau.
    - Ví dụ: `JOIN s.courses c` - Kết hợp sinh viên với các khóa học mà họ đăng ký.

5. **ORDER BY**: Để sắp xếp kết quả truy vấn.
    - Ví dụ: `ORDER BY s.name` - Sắp xếp sinh viên theo tên.

6. **GROUP BY** và **HAVING**: Dùng để nhóm dữ liệu và áp dụng các điều kiện nhóm.
    - Ví dụ: `SELECT c.title, COUNT(s) FROM Course c JOIN c.students s GROUP BY c.title HAVING COUNT(s) > 10` - Đếm số sinh viên đăng ký từng khóa học và chỉ hiển thị những khóa có hơn 10 sinh viên.

### Ví dụ tổng quan với nhiều chức năng của JPQL

```java
public List<Student> findTopCoursesWithMostStudents(int minStudentCount) {
    String jpql = "SELECT c.title, COUNT(s) FROM Course c JOIN c.students s " +
                  "GROUP BY c.title HAVING COUNT(s) > :minStudentCount ORDER BY COUNT(s) DESC";
    TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
    query.setParameter("minStudentCount", minStudentCount);
    
    return query.getResultList();
}
```

### Kết luận

- **JPQL** cho phép bạn truy vấn cơ sở dữ liệu theo hướng đối tượng, giúp các truy vấn dễ đọc và dễ bảo trì hơn khi làm việc với JPA.
- Bạn có thể sử dụng JPQL trực tiếp trong các repository hoặc thông qua annotation `@Query` trong **Spring Data JPA**.
- Với JPQL, bạn có thể thực hiện các truy vấn phức tạp như `JOIN`, `GROUP BY`, và `ORDER BY` để thao tác với dữ liệu trong ứng dụng của mình một cách hiệu quả hơn.