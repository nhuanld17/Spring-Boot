Trong Hibernate và Spring, cơ chế **Cascade** định nghĩa cách hành vi của các hoạt động trên thực thể (entity) như lưu, cập nhật, hoặc xóa, được tự động truyền từ thực thể này sang các thực thể liên quan. Nói cách khác, **Cascade** cho phép các thao tác CRUD thực hiện trên một thực thể có thể tự động lan sang các thực thể khác có quan hệ với nó.

Dưới đây là các loại Cascade trong Hibernate/Spring:

### 1. **CascadeType.PERSIST**
- Khi bạn **lưu** một thực thể "cha" (parent), các thực thể con liên kết cũng sẽ được lưu tự động.
- Điều này có nghĩa là nếu một đối tượng cha chưa tồn tại trong cơ sở dữ liệu và bạn lưu nó, tất cả các đối tượng con của nó cũng sẽ được lưu vào cơ sở dữ liệu.

#### Ví dụ:
```java
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Student> students = new ArrayList<>();

    // getters, setters
}

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // getters, setters
}
```

```java
// Khi lưu Teacher, các đối tượng Student cũng sẽ tự động được lưu.
Teacher teacher = new Teacher();
Student student1 = new Student();
Student student2 = new Student();

teacher.getStudents().add(student1);
teacher.getStudents().add(student2);

entityManager.persist(teacher); // Cả teacher và students đều sẽ được lưu vào DB.
```

### 2. **CascadeType.MERGE**
- Khi bạn **gộp** một thực thể cha, tất cả các thực thể con liên kết cũng sẽ được gộp lại, tức là đồng bộ các thay đổi từ đối tượng vào cơ sở dữ liệu.
- Điều này có nghĩa là nếu bạn cập nhật một đối tượng cha và thực hiện thao tác `merge`, tất cả các thực thể con cũng sẽ được cập nhật.

#### Ví dụ:
```java
Teacher teacher = entityManager.find(Teacher.class, 1L);
teacher.setName("Updated Teacher");

Student student = teacher.getStudents().get(0);
student.setName("Updated Student");

entityManager.merge(teacher); // Cả teacher và student đều được cập nhật.
```

### 3. **CascadeType.REMOVE**
- Khi bạn **xóa** một thực thể cha, tất cả các thực thể con liên kết cũng sẽ bị xóa theo.
- Điều này có nghĩa là nếu bạn xóa một đối tượng cha khỏi cơ sở dữ liệu, tất cả các đối tượng con của nó cũng sẽ bị xóa tự động.

#### Ví dụ:
```java
Teacher teacher = entityManager.find(Teacher.class, 1L);
entityManager.remove(teacher); // Teacher và tất cả Student liên quan sẽ bị xóa.
```

### 4. **CascadeType.REFRESH**
- Khi bạn **làm mới** một thực thể cha, tất cả các thực thể con liên kết cũng sẽ được làm mới theo.
- Điều này có nghĩa là khi bạn gọi `refresh`, tất cả các thực thể con của thực thể cha sẽ được tải lại từ cơ sở dữ liệu để đồng bộ với trạng thái hiện tại.

#### Ví dụ:
```java
Teacher teacher = entityManager.find(Teacher.class, 1L);

// Thay đổi dữ liệu trực tiếp trên cơ sở dữ liệu, sau đó gọi refresh:
entityManager.refresh(teacher); // Cả teacher và students sẽ được đồng bộ với dữ liệu từ DB.
```

### 5. **CascadeType.DETACH**
- Khi bạn **tách** một thực thể cha ra khỏi ngữ cảnh hiện tại (detaching), tất cả các thực thể con liên kết cũng sẽ bị tách ra khỏi phiên làm việc hiện tại.
- Điều này có nghĩa là cả thực thể cha và thực thể con sẽ không còn được quản lý bởi Hibernate nữa, cho đến khi bạn gắn chúng lại.

#### Ví dụ:
```java
Teacher teacher = entityManager.find(Teacher.class, 1L);
entityManager.detach(teacher); // Teacher và các Student liên quan đều bị tách ra khỏi context quản lý của Hibernate.
```

### 6. **CascadeType.ALL**
- Kết hợp tất cả các loại cascade: `PERSIST`, `MERGE`, `REMOVE`, `REFRESH`, `DETACH`.
- Điều này có nghĩa là mọi thao tác CRUD trên thực thể cha đều sẽ được tự động áp dụng cho các thực thể con liên kết.

#### Ví dụ:
```java
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL) // Áp dụng tất cả các loại Cascade
    private List<Student> students = new ArrayList<>();

    // getters, setters
}
```

### **Tóm tắt từng Cascade:**
- **PERSIST**: Lưu thực thể cha -> Thực thể con cũng sẽ được lưu.
- **MERGE**: Gộp thực thể cha -> Thực thể con cũng sẽ được cập nhật.
- **REMOVE**: Xóa thực thể cha -> Thực thể con cũng sẽ bị xóa.
- **REFRESH**: Làm mới thực thể cha -> Thực thể con cũng sẽ được làm mới.
- **DETACH**: Tách thực thể cha -> Thực thể con cũng sẽ bị tách ra khỏi ngữ cảnh.
- **ALL**: Áp dụng tất cả các loại cascade trên.

### Ví dụ thực tế sử dụng Cascade:

```java
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    // Thêm item vào order
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    // Xóa item khỏi order
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
}

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}

// Thực hiện lưu một Order mới và các OrderItem liên quan
Order order = new Order();
OrderItem item1 = new OrderItem("Laptop", 1);
OrderItem item2 = new OrderItem("Mouse", 2);

order.addItem(item1);
order.addItem(item2);

entityManager.persist(order); // Lưu cả order và order items vào DB.
```

Trong ví dụ trên, khi bạn gọi `persist(order)`, cả `Order` và các `OrderItem` liên quan cũng sẽ được lưu do sử dụng `CascadeType.ALL`.