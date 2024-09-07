Trong Hibernate và Spring, **FetchType** (kiểu truy xuất) xác định cách thức Hibernate tải dữ liệu từ cơ sở dữ liệu khi bạn truy cập vào các thực thể (entity) có quan hệ. FetchType giúp bạn quản lý hiệu quả việc truy xuất dữ liệu bằng cách kiểm soát liệu dữ liệu liên quan có được tải **ngay lập tức** khi thực thể chính được truy xuất hay **khi nào thực sự cần**.

Có hai loại **FetchType** chính trong Hibernate:

1. **FetchType.EAGER**: Tải dữ liệu ngay lập tức (eager fetching).
2. **FetchType.LAZY**: Tải dữ liệu khi cần thiết (lazy fetching).

### 1. **FetchType.EAGER** (Tải ngay lập tức)
- Khi sử dụng `FetchType.EAGER`, các đối tượng liên quan (related entities) sẽ được **tải ngay lập tức** khi bạn truy xuất thực thể chính, tức là dữ liệu liên quan sẽ được lấy từ cơ sở dữ liệu cùng lúc với thực thể chính, ngay cả khi bạn không thực sự cần dùng đến dữ liệu đó ngay lập tức.

#### Ví dụ:
```java
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Mỗi giáo viên có nhiều học sinh, và danh sách học sinh sẽ được tải ngay khi truy cập Teacher
    @OneToMany(fetch = FetchType.EAGER)
    private List<Student> students;

    // Getters, setters
}
```

```java
// Khi bạn truy xuất Teacher, danh sách Student sẽ tự động được tải ngay lập tức.
Teacher teacher = entityManager.find(Teacher.class, 1L);
List<Student> students = teacher.getStudents(); // Students đã được tải sẵn từ DB.
```

#### **Ưu điểm**:
- Dữ liệu liên quan được tải sẵn, thuận tiện khi bạn biết chắc rằng sẽ cần sử dụng chúng.

#### **Nhược điểm**:
- Tốn tài nguyên và giảm hiệu năng nếu dữ liệu liên quan rất lớn hoặc bạn không cần sử dụng tất cả ngay lập tức.

---

### 2. **FetchType.LAZY** (Tải khi cần thiết)
- Với `FetchType.LAZY`, dữ liệu liên quan **sẽ chỉ được tải khi thực sự cần sử dụng**, tức là khi bạn gọi đến các phương thức truy xuất dữ liệu đó. Điều này giúp tiết kiệm tài nguyên và cải thiện hiệu năng, đặc biệt khi dữ liệu liên quan là lớn và không phải lúc nào cũng cần thiết.

#### Ví dụ:
```java
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Danh sách học sinh chỉ được tải khi cần thiết (lúc gọi getStudents)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Student> students;

    // Getters, setters
}
```

```java
// Khi truy xuất Teacher, danh sách Student chưa được tải ngay.
Teacher teacher = entityManager.find(Teacher.class, 1L);

// Lúc này, Student mới được tải khi gọi phương thức getStudents.
List<Student> students = teacher.getStudents();
```

#### **Ưu điểm**:
- Tiết kiệm tài nguyên, chỉ tải dữ liệu khi cần thiết.
- Hiệu năng tốt hơn khi dữ liệu liên quan không phải lúc nào cũng được sử dụng.

#### **Nhược điểm**:
- Có thể gây ra lỗi `LazyInitializationException` nếu truy cập dữ liệu sau khi session đã đóng (khi session bị đóng, dữ liệu chưa được tải sẽ không thể truy xuất).

---

### **Ví dụ thực tế về FetchType**

Giả sử bạn có hai thực thể: `Order` và `OrderItem` với quan hệ một-nhiều (One-to-Many):

```java
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY) // LAZY: Tải các OrderItem khi cần
    private List<OrderItem> items;

    // Getters, setters
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

    // Getters, setters
}
```

#### FetchType.LAZY trong thực tế:
```java
Order order = entityManager.find(Order.class, 1L); // Chỉ tải Order, không tải OrderItem.

// OrderItem chưa được tải vì fetch = LAZY
List<OrderItem> items = order.getItems(); // Bây giờ OrderItem mới được tải từ DB.
```

#### FetchType.EAGER trong thực tế:
Nếu thay đổi quan hệ thành `fetch = FetchType.EAGER`:
```java
@OneToMany(mappedBy = "order", fetch = FetchType.EAGER) // EAGER: Tải OrderItem ngay lập tức
private List<OrderItem> items;
```

```java
Order order = entityManager.find(Order.class, 1L); // Cả Order và OrderItem sẽ được tải cùng lúc.
List<OrderItem> items = order.getItems(); // OrderItem đã được tải từ DB ngay từ đầu.
```

### **So sánh FetchType.LAZY và FetchType.EAGER**:

| Thuộc tính | `FetchType.LAZY` | `FetchType.EAGER` |
|------------|------------------|-------------------|
| **Thời điểm tải dữ liệu** | Khi được yêu cầu (khi gọi các phương thức truy cập) | Ngay lập tức khi tải thực thể chính |
| **Hiệu năng** | Tốt hơn khi không cần truy cập thường xuyên dữ liệu liên quan | Tốn tài nguyên hơn nếu không cần thiết phải tải dữ liệu liên quan |
| **Tài nguyên** | Tiết kiệm tài nguyên hơn | Có thể lãng phí tài nguyên nếu dữ liệu liên quan không cần dùng |
| **Dễ gặp lỗi LazyInitializationException** | Có (nếu session đóng trước khi truy cập dữ liệu liên quan) | Không gặp vấn đề này |

### **Khi nào nên dùng FetchType.LAZY và FetchType.EAGER?**
- **FetchType.LAZY** phù hợp cho các quan hệ chứa nhiều dữ liệu hoặc dữ liệu không phải lúc nào cũng cần thiết. Bạn sẽ chỉ tải dữ liệu khi thực sự cần, điều này giúp giảm tải hệ thống và cải thiện hiệu năng.

- **FetchType.EAGER** phù hợp khi bạn biết chắc chắn rằng dữ liệu liên quan sẽ luôn được sử dụng ngay lập tức cùng với thực thể chính, và không muốn thực hiện nhiều truy vấn.

Tóm lại, lựa chọn giữa **FetchType.LAZY** và **FetchType.EAGER** phụ thuộc vào tình huống thực tế của ứng dụng, nhu cầu về dữ liệu và hiệu năng cần đạt được.