Trong Java Spring và Hibernate, các mối quan hệ giữa các thực thể (entities) được thể hiện bằng các chú thích (annotations) để quản lý cách các bảng trong cơ sở dữ liệu liên kết với nhau. Dưới đây là các loại quan hệ chính trong Java Spring Hibernate:

### 1. **One-to-One (1-1)**

Mối quan hệ **một-một** là khi một bản ghi trong bảng này tương ứng với duy nhất một bản ghi trong bảng khác.

- **Annotation** sử dụng: `@OneToOne`
- **Ví dụ**: Một người dùng có một địa chỉ.

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
}
```

### 2. **One-to-Many (1-N)**

Mối quan hệ **một-nhiều** là khi một bản ghi trong bảng này có thể liên kết với nhiều bản ghi trong bảng khác.

- **Annotation** sử dụng: `@OneToMany`
- **Ví dụ**: Một giáo viên dạy nhiều lớp học.

```java
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Classroom> classrooms;
}

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
```

### 3. **Many-to-One (N-1)**

Mối quan hệ **nhiều-một** là khi nhiều bản ghi trong bảng này liên kết với một bản ghi trong bảng khác. Đây thực ra là quan hệ ngược lại của **One-to-Many**.

- **Annotation** sử dụng: `@ManyToOne`
- **Ví dụ**: Nhiều sinh viên thuộc về một lớp học.

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
```

### 4. **Many-to-Many (N-N)**

Mối quan hệ **nhiều-nhiều** là khi nhiều bản ghi trong bảng này có thể liên kết với nhiều bản ghi trong bảng khác.

- **Annotation** sử dụng: `@ManyToMany`
- **Ví dụ**: Một sinh viên có thể đăng ký nhiều môn học và một môn học có nhiều sinh viên.

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    @JoinTable(
      name = "student_course", 
      joinColumns = @JoinColumn(name = "student_id"), 
      inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
```

### **Tóm lại:**
- **One-to-One**: `@OneToOne`
- **One-to-Many**: `@OneToMany`, `@ManyToOne`
- **Many-to-Many**: `@ManyToMany`

Các chú thích này được sử dụng để định nghĩa cách các thực thể tương tác và liên kết với nhau, giúp Hibernate ánh xạ chính xác quan hệ giữa các bảng trong cơ sở dữ liệu.