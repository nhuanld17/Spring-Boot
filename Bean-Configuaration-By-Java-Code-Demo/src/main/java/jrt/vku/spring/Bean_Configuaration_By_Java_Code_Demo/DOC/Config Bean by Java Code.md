### Cấu hình Bean bằng Java Code trong Spring Boot

Trong Spring, có hai cách chính để cấu hình Bean:
1. **Cấu hình bằng XML** (ít được sử dụng trong các ứng dụng hiện đại).
2. **Cấu hình bằng Java code (Java-based configuration)**.

Trong Spring Boot, **cấu hình bằng Java code** là phương pháp phổ biến nhất và được khuyến khích, giúp tạo ra các Bean thông qua các lớp Java mà không cần XML.

### Bean là gì?

Trong Spring, **Bean** là các đối tượng do Spring container quản lý. Các Bean này có thể là bất kỳ thành phần nào trong ứng dụng của bạn, chẳng hạn như **services**, **repositories**, hoặc **controllers**. Spring tạo, quản lý, và tiêm phụ thuộc (dependency injection) vào các Bean đó thông qua container.

### Cấu hình Bean bằng Java Code

Khi sử dụng **Java-based configuration**, chúng ta không cần sử dụng file XML để cấu hình Spring container. Thay vào đó, chúng ta sử dụng các lớp Java và annotation như `@Configuration`, `@Bean`, hoặc `@Component`.

### Cấu hình Bean bằng `@Configuration` và `@Bean`

Annotation `@Configuration` dùng để đánh dấu một class là một **class cấu hình**, nơi bạn định nghĩa các Bean.

Annotation `@Bean` được sử dụng để đánh dấu một phương thức trả về một đối tượng Bean. Mỗi lần phương thức này được gọi, một Bean mới hoặc đã tồn tại trong container sẽ được trả về.

#### Ví dụ đơn giản về cấu hình Bean

Giả sử bạn có một ứng dụng cần sử dụng một đối tượng `Car`.

```java
public class Car {
    private String brand;

    public Car(String brand) {
        this.brand = brand;
    }

    public void drive() {
        System.out.println("Driving a " + brand + " car");
    }
}
```

Bây giờ, bạn có thể cấu hình Bean cho `Car` trong Spring bằng cách sử dụng Java code.

#### Tạo lớp cấu hình

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Định nghĩa Bean cho Car
    @Bean
    public Car car() {
        return new Car("Toyota");
    }
}
```

- Annotation `@Configuration` được sử dụng để đánh dấu class `AppConfig` là một class chứa cấu hình cho Spring container.
- Annotation `@Bean` được sử dụng để đánh dấu phương thức `car()` là một Bean.
- Mỗi lần Spring container cần một Bean `Car`, nó sẽ gọi phương thức `car()` và trả về đối tượng `Car` với giá trị `brand` là "Toyota".

#### Sử dụng Bean

Bạn có thể sử dụng Bean `Car` trong ứng dụng Spring của mình bằng cách sử dụng dependency injection.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Driver {

    private Car car;

    // Tiêm Bean Car bằng @Autowired
    @Autowired
    public Driver(Car car) {
        this.car = car;
    }

    public void drive() {
        car.drive();
    }
}
```

### Main Application

Spring Boot tự động phát hiện các lớp cấu hình và các Bean được khai báo thông qua annotation `@SpringBootApplication`. Bạn chỉ cần sử dụng `ApplicationContext` để lấy các Bean.

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringApp {

    public static void main(String[] args) {
        // Khởi chạy Spring Boot và lấy ApplicationContext
        ApplicationContext context = SpringApplication.run(SpringApp.class, args);

        // Lấy Bean Driver từ ApplicationContext
        Driver driver = context.getBean(Driver.class);

        // Sử dụng Bean
        driver.drive();
    }
}
```

### Các Annotation Liên Quan

- **@Configuration**: Đánh dấu một class là class cấu hình, nơi các Bean được định nghĩa.
- **@Bean**: Đánh dấu một phương thức trả về một đối tượng Bean.
- **@Autowired**: Tiêm một Bean vào đối tượng hiện tại thông qua constructor, setter, hoặc field injection.

### Cấu hình Bean với tham số

Bạn có thể định nghĩa các Bean phụ thuộc vào nhau. Ví dụ, nếu một `Car` cần một `Engine` làm tham số:

```java
public class Engine {
    private String type;

    public Engine(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
```

Trong lớp cấu hình, bạn có thể cấu hình Bean `Engine` và truyền nó cho Bean `Car`.

```java
@Configuration
public class AppConfig {

    @Bean
    public Engine engine() {
        return new Engine("V8");
    }

    @Bean
    public Car car() {
        return new Car(engine());  // Sử dụng Bean Engine làm tham số cho Car
    }
}
```

### Kết luận

- **Cấu hình Bean bằng Java code** cho phép bạn quản lý các Bean một cách linh hoạt và dễ dàng.
- Bạn sử dụng `@Configuration` để tạo lớp cấu hình và `@Bean` để định nghĩa các Bean.
- Việc sử dụng **Java-based configuration** giúp ứng dụng của bạn tránh khỏi sự phức tạp khi phải sử dụng file cấu hình XML, tạo ra cấu trúc rõ ràng và dễ bảo trì hơn.

### Ví dụ đầy đủ:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Engine engine() {
        return new Engine("V8");
    }

    @Bean
    public Car car() {
        return new Car(engine());
    }
}

public class Engine {
    private String type;

    public Engine(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

public class Car {
    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        System.out.println("Driving with " + engine.getType() + " engine");
    }
}
```

Trong ví dụ này:
- Bean `Engine` được tạo và truyền làm tham số cho `Car`.
- Bạn có thể thấy cách Spring Boot quản lý Bean và tiêm phụ thuộc vào một cách tự động.