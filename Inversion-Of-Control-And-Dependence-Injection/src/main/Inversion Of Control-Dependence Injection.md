**Inversion of Control (IoC)** và **Dependency Injection (DI)** là hai khái niệm cốt lõi trong Spring Framework giúp quản lý và tổ chức mã nguồn hiệu quả. Dưới đây là giải thích chi tiết về hai khái niệm này và cách chúng hoạt động trong Spring Framework:

### Inversion of Control (IoC)

**Inversion of Control** (IoC) là một nguyên tắc thiết kế phần mềm mà trong đó trách nhiệm quản lý vòng đời và các phụ thuộc của các đối tượng trong ứng dụng được chuyển từ đối tượng chính sang một cơ chế quản lý bên ngoài. IoC giúp giảm sự phụ thuộc giữa các lớp và làm cho mã nguồn trở nên linh hoạt hơn.

#### Trong Spring Framework:

- **IoC Container**: Spring Framework cung cấp một IoC Container (hay còn gọi là Application Context) để quản lý các đối tượng của ứng dụng. Container này chịu trách nhiệm tạo, cấu hình và quản lý vòng đời của các bean (đối tượng) trong ứng dụng.
- **Bean Definition**: Trong Spring, một bean là một đối tượng được quản lý bởi IoC Container. Các định nghĩa về bean thường được cấu hình trong các file cấu hình XML, hoặc thông qua các chú thích (annotations) hoặc các lớp cấu hình Java.

### Dependency Injection (DI)

**Dependency Injection** (DI) là một kỹ thuật trong IoC mà trong đó các phụ thuộc của một đối tượng được cung cấp từ bên ngoài thay vì được đối tượng tự tạo ra. DI giúp giảm sự phụ thuộc chặt chẽ giữa các lớp và tăng khả năng tái sử dụng và kiểm thử mã nguồn.

#### Các loại Dependency Injection:

1. **Constructor Injection**:
    - Phụ thuộc được cung cấp thông qua constructor của lớp.
    - Cung cấp một cách rõ ràng để yêu cầu phụ thuộc và đảm bảo rằng các phụ thuộc không thể thay đổi sau khi đối tượng được tạo ra.

   ```java
   @Component
   public class MyService {
       private final MyRepository myRepository;

       @Autowired
       public MyService(MyRepository myRepository) {
           this.myRepository = myRepository;
       }
   }
   ```

2. **Setter Injection**:
    - Phụ thuộc được cung cấp thông qua các phương thức setter.
    - Cho phép thay đổi phụ thuộc sau khi đối tượng đã được tạo ra, giúp dễ dàng thay đổi cấu hình.

   ```java
   @Component
   public class MyService {
       private MyRepository myRepository;

       @Autowired
       public void setMyRepository(MyRepository myRepository) {
           this.myRepository = myRepository;
       }
   }
   ```

3. **Field Injection**:
    - Phụ thuộc được tiêm trực tiếp vào các trường dữ liệu (fields) của lớp.
    - Cách này đơn giản nhưng có thể làm giảm khả năng kiểm tra đơn vị (unit testing) và khó theo dõi hơn so với constructor injection.

   ```java
   @Component
   public class MyService {
       @Autowired
       private MyRepository myRepository;
   }
   ```

#### Cấu hình Dependency Injection trong Spring:

1. **Sử dụng Annotation**:
    - Spring cung cấp các annotation như `@Autowired`, `@Inject`, `@Resource` để tự động tiêm các phụ thuộc.

   ```java
   @Component
   public class MyService {
       @Autowired
       private MyRepository myRepository;
   }
   ```

2. **Sử dụng XML Configuration**:
    - Trong các phiên bản cũ của Spring, bạn có thể cấu hình các bean và phụ thuộc trong một file cấu hình XML.

   ```xml
   <bean id="myService" class="com.example.MyService">
       <property name="myRepository" ref="myRepository"/>
   </bean>
   <bean id="myRepository" class="com.example.MyRepository"/>
   ```

3. **Sử dụng Java Configuration**:
    - Spring cung cấp một cách để cấu hình các bean thông qua các lớp cấu hình Java sử dụng `@Configuration` và `@Bean`.

   ```java
   @Configuration
   public class AppConfig {
       @Bean
       public MyService myService() {
           return new MyService(myRepository());
       }

       @Bean
       public MyRepository myRepository() {
           return new MyRepository();
       }
   }
   ```

### Lợi ích của IoC và DI trong Spring

- **Giảm sự phụ thuộc**: Giúp giảm sự phụ thuộc chặt chẽ giữa các lớp, làm cho mã nguồn dễ bảo trì hơn.
- **Tăng khả năng tái sử dụng**: Các lớp có thể dễ dàng thay đổi phụ thuộc mà không cần phải sửa đổi mã nguồn bên trong lớp đó.
- **Cải thiện khả năng kiểm thử**: Bạn có thể dễ dàng thay thế các phụ thuộc với các mock objects trong quá trình kiểm thử.
- **Quản lý vòng đời bean**: Spring IoC Container quản lý vòng đời và trạng thái của các bean, giúp bạn tập trung vào logic ứng dụng hơn là quản lý đối tượng.

### Kết luận

**IoC** và **DI** là những khái niệm cốt lõi trong Spring Framework, giúp làm giảm sự phụ thuộc giữa các lớp, quản lý vòng đời của các bean, và cải thiện khả năng kiểm thử mã nguồn. Spring Framework sử dụng IoC Container để thực hiện DI, cung cấp một cách linh hoạt và mạnh mẽ để cấu hình và quản lý các đối tượng trong ứng dụng Java.