### Các Mối Quan Hệ trong Hibernate

Hibernate, một framework ORM (Object-Relational Mapping), cho phép ánh xạ các đối tượng Java tới các bảng trong cơ sở dữ liệu quan hệ. Một trong những tính năng mạnh mẽ của Hibernate là khả năng quản lý các mối quan hệ giữa các thực thể. Các mối quan hệ cơ bản trong Hibernate bao gồm:

1. **One-to-One (Một - Một)**
2. **One-to-Many (Một - Nhiều)**
3. **Many-to-One (Nhiều - Một)**
4. **Many-to-Many (Nhiều - Nhiều)**

Dưới đây là chi tiết về từng mối quan hệ kèm theo ví dụ thực tế:

### 1. One-to-One (Một - Một)

#### Giới Thiệu
Mối quan hệ One-to-One đại diện cho mối quan hệ mà mỗi bản ghi trong bảng này liên kết với một bản ghi duy nhất trong bảng kia. Ví dụ, một người có một địa chỉ cụ thể.

#### Cấu Hình Hibernate
- Sử dụng `@OneToOne` để xác định mối quan hệ này.
- Có thể sử dụng `@JoinColumn` để xác định cột khóa ngoại.

#### Ví Dụ

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // Getters and Setters
}

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String street;
    private String city;

    // Getters and Setters
}
```

#### Giải Thích
- **User** có một mối quan hệ One-to-One với **Address**.
- Trường `address_id` trong bảng `users` là khóa ngoại trỏ đến cột `id` trong bảng `addresses`.

### 2. One-to-Many (Một - Nhiều)

#### Giới Thiệu
Mối quan hệ One-to-Many là khi một bản ghi trong bảng này có thể liên kết với nhiều bản ghi trong bảng khác. Ví dụ, một tác giả có thể viết nhiều cuốn sách.

#### Cấu Hình Hibernate
- Sử dụng `@OneToMany` ở phía cha và `@ManyToOne` ở phía con.
- Có thể sử dụng `mappedBy` để chỉ định phía chủ sở hữu của mối quan hệ.

#### Ví Dụ

```java
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    // Getters and Setters
}

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // Getters and Setters
}
```

#### Giải Thích
- **Author** có một danh sách **Book** liên kết với nó.
- Trong **Book**, cột `author_id` là khóa ngoại trỏ đến cột `id` trong bảng `authors`.

### 3. Many-to-One (Nhiều - Một)

#### Giới Thiệu
Mối quan hệ Many-to-One là nghịch đảo của One-to-Many, nơi nhiều bản ghi trong bảng này có thể liên kết với một bản ghi duy nhất trong bảng khác. Ví dụ, nhiều sinh viên thuộc về một lớp học.

#### Cấu Hình Hibernate
- Sử dụng `@ManyToOne` ở phía con và `@OneToMany` ở phía cha.

#### Ví Dụ

```java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassRoom classRoom;

    // Getters and Setters
}

@Entity
@Table(name = "class_rooms")
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
    private List<Student> students;

    // Getters and Setters
}
```

#### Giải Thích
- **Student** có một mối quan hệ Many-to-One với **ClassRoom**.
- Trong **Student**, cột `class_id` là khóa ngoại trỏ đến cột `id` trong bảng `class_rooms`.

### 4. Many-to-Many (Nhiều - Nhiều)

#### Giới Thiệu
Mối quan hệ Many-to-Many là khi một bản ghi trong bảng này có thể liên kết với nhiều bản ghi trong bảng khác và ngược lại. Ví dụ, nhiều sinh viên có thể tham gia nhiều khóa học, và nhiều khóa học có thể có nhiều sinh viên.

#### Cấu Hình Hibernate
- Sử dụng `@ManyToMany` ở cả hai phía.
- Sử dụng `@JoinTable` để xác định bảng trung gian lưu trữ khóa ngoại.

#### Ví Dụ

```java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_courses",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    // Getters and Setters
}

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    // Getters and Setters
}
```

#### Giải Thích
- **Student** có một mối quan hệ Many-to-Many với **Course**.
- Một bảng trung gian `student_courses` lưu trữ khóa ngoại từ cả `students` và `courses`.

### Kết Luận

Các mối quan hệ trong Hibernate giúp ánh xạ các quan hệ giữa các thực thể trong cơ sở dữ liệu với các lớp Java. Hiểu rõ cách cấu hình và sử dụng chúng là rất quan trọng để xây dựng các ứng dụng sử dụng Hibernate một cách hiệu quả.

Việc lựa chọn và cấu hình đúng mối quan hệ sẽ đảm bảo rằng các hoạt động CRUD (Create, Read, Update, Delete) hoạt động đúng đắn và tối ưu.