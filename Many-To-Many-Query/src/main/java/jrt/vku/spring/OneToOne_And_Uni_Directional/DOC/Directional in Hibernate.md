Trong Java Spring và Hibernate, khái niệm **"Directional"** (tính hướng) liên quan đến cách các mối quan hệ giữa các thực thể (entities) được ánh xạ và quản lý trong cơ sở dữ liệu. Một mối quan hệ giữa các thực thể có thể là:

1. **Unidirectional** (một chiều)
2. **Bidirectional** (hai chiều)

### 1. **Unidirectional (Một chiều)**

Trong mối quan hệ **một chiều**, chỉ có một thực thể biết về mối quan hệ này. Điều đó có nghĩa là một thực thể duy nhất có tham chiếu đến thực thể kia, nhưng thực thể kia không có tham chiếu ngược lại.

#### Ví dụ:

Giả sử có hai thực thể `User` và `Address`. Một **User** có một **Address**, nhưng `Address` không biết về `User`.

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
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

- Trong trường hợp này, `User` biết về `Address` thông qua trường `address`, nhưng `Address` không có bất kỳ tham chiếu nào đến `User`.

### 2. **Bidirectional (Hai chiều)**

Trong mối quan hệ **hai chiều**, cả hai thực thể đều biết về nhau. Điều này có nghĩa là cả hai thực thể đều chứa tham chiếu đến thực thể còn lại.

#### Ví dụ:

Giả sử có hai thực thể **Student** và **Classroom**. Một **Classroom** có nhiều **Student**, và mỗi **Student** thuộc về một **Classroom**.

```java
@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Student> students;
}

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

- Ở đây, `Classroom` biết về danh sách các `Student` thông qua thuộc tính `students`, và `Student` cũng biết về `Classroom` thông qua thuộc tính `classroom`.

### **Sự khác nhau giữa Unidirectional và Bidirectional:**

- **Unidirectional (Một chiều):**
    - Mối quan hệ chỉ được quản lý ở một phía.
    - Chỉ có một thực thể giữ thông tin về mối quan hệ.

- **Bidirectional (Hai chiều):**
    - Mối quan hệ được quản lý từ cả hai phía.
    - Cả hai thực thể đều tham chiếu lẫn nhau.
    - Trong Hibernate, khi mối quan hệ là hai chiều, một trong các thực thể phải được đánh dấu là "sở hữu" (owner) của mối quan hệ, và thực thể kia được xem như là "bị chiếu xạ" (inverse). Thực thể "sở hữu" sẽ chịu trách nhiệm lưu trữ khóa ngoại.

### **Owner (Sở hữu) và Inverse (Bị chiếu xạ) trong Bidirectional:**

- Trong một mối quan hệ **Bidirectional**, một thực thể sẽ là **owner** (thực thể chịu trách nhiệm quản lý khóa ngoại trong cơ sở dữ liệu), và thực thể còn lại sẽ là **inverse** (bị chiếu xạ).

#### Ví dụ về **OneToMany** Bidirectional:

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

- Trong ví dụ này:
    - `Teacher` là **inverse** side (bên bị chiếu xạ) với thuộc tính `mappedBy`.
    - `Classroom` là **owner** (bên sở hữu) của mối quan hệ và lưu trữ khóa ngoại `teacher_id`.

### **Tóm lại:**
- **Unidirectional**: Mối quan hệ chỉ được ánh xạ một chiều.
- **Bidirectional**: Mối quan hệ được ánh xạ hai chiều, và phải có một thực thể là **owner** của mối quan hệ.