### Vòng đời của Bean trong Spring Boot

Vòng đời của Bean trong Spring Boot mô tả quá trình một **Bean** (đối tượng do Spring quản lý) được tạo ra, khởi tạo, sử dụng, và cuối cùng bị hủy khi không còn cần thiết. Quá trình này bao gồm các bước từ việc khởi tạo đối tượng cho đến khi Spring container giải phóng nó. Hiểu rõ vòng đời của bean giúp bạn có thể can thiệp vào quá trình khởi tạo và hủy bỏ các bean một cách hiệu quả hơn.

### Các bước trong vòng đời của một Bean

1. **Tạo Bean**: Spring sẽ tạo một instance (đối tượng) của bean khi cần thiết. Đối tượng được tạo ra thông qua constructor hoặc một phương thức factory.

2. **Gọi phương thức `@Autowired` hoặc `@Inject`**: Sau khi tạo bean, Spring sẽ tiêm các phụ thuộc (dependencies) vào bean thông qua các phương thức được đánh dấu bởi `@Autowired` hoặc `@Inject`.

3. **Gọi các phương thức cấu hình (init methods)**: Sau khi tiêm các phụ thuộc, nếu có các phương thức được đánh dấu bằng `@PostConstruct` hoặc cấu hình phương thức khởi tạo trong file cấu hình bean, Spring sẽ gọi chúng để thực hiện các bước khởi tạo cuối cùng.

4. **Bean sẵn sàng sử dụng**: Sau khi được khởi tạo hoàn tất, bean sẵn sàng để sử dụng trong ứng dụng.

5. **Gọi phương thức hủy (destroy methods)**: Khi bean không còn cần thiết nữa (trong trường hợp ứng dụng kết thúc hoặc đối tượng không còn được sử dụng), Spring sẽ gọi các phương thức hủy để giải phóng tài nguyên, ví dụ như đóng kết nối cơ sở dữ liệu hoặc tắt các dịch vụ bên ngoài.

### Các giai đoạn chi tiết của vòng đời Bean

#### 1. **Bean Instantiation (Tạo Bean)**

Spring container sẽ tạo một instance của bean bằng cách gọi constructor của bean hoặc thông qua phương thức factory (nếu được cấu hình).

Ví dụ:

```java
@Component
public class MyBean {
    public MyBean() {
        System.out.println("Bean MyBean được tạo");
    }
}
```

#### 2. **Dependency Injection (Tiêm phụ thuộc)**

Sau khi bean được tạo, Spring sẽ thực hiện **tiêm phụ thuộc** bằng cách cung cấp các đối tượng cần thiết cho bean thông qua các annotation như `@Autowired`, `@Inject`, hoặc thông qua setter method.

```java
@Component
public class MyBean {
    private MyDependency dependency;
    
    @Autowired
    public void setDependency(MyDependency dependency) {
        this.dependency = dependency;
    }
}
```

#### 3. **Initializing Bean (Khởi tạo Bean)**

Sau khi các phụ thuộc đã được tiêm, Spring sẽ gọi các phương thức khởi tạo để thực hiện bất kỳ thiết lập bổ sung nào. Bạn có thể dùng:
- **Phương thức `@PostConstruct`** để đánh dấu các phương thức cần thực thi ngay sau khi bean được khởi tạo.
- **Implement `InitializingBean`** hoặc cấu hình phương thức init bằng `@Bean(initMethod = "init")`.

Ví dụ với `@PostConstruct`:

```java
@Component
public class MyBean {
    
    @PostConstruct
    public void init() {
        System.out.println("Bean MyBean đã được khởi tạo");
    }
}
```

Ví dụ với `InitializingBean` interface:

```java
@Component
public class MyBean implements InitializingBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MyBean đã được khởi tạo");
    }
}
```

#### 4. **Bean Sẵn sàng để Sử dụng**

Sau khi đã được khởi tạo hoàn chỉnh và các phụ thuộc đã được tiêm, bean sẽ sẵn sàng để sử dụng trong ứng dụng.

```java
@Component
public class MyService {
    
    @Autowired
    private MyBean myBean;
    
    public void performAction() {
        myBean.doSomething();
    }
}
```

#### 5. **Destroying Bean (Hủy Bean)**

Khi bean không còn cần thiết, Spring sẽ gọi các phương thức hủy để giải phóng tài nguyên. Bạn có thể sử dụng:
- **Phương thức `@PreDestroy`** để đánh dấu phương thức sẽ được gọi trước khi bean bị hủy.
- **Implement `DisposableBean`** hoặc cấu hình phương thức destroy trong `@Bean(destroyMethod = "destroy")`.

Ví dụ với `@PreDestroy`:

```java
@Component
public class MyBean {
    
    @PreDestroy
    public void destroy() {
        System.out.println("Bean MyBean đang bị hủy");
    }
}
```

Ví dụ với `DisposableBean` interface:

```java
@Component
public class MyBean implements DisposableBean {
    
    @Override
    public void destroy() throws Exception {
        System.out.println("MyBean đang bị hủy");
    }
}
```

### Tổng kết quá trình vòng đời Bean

- **Tạo Bean**: Bean được tạo bằng cách gọi constructor hoặc phương thức factory.
- **Tiêm phụ thuộc**: Các phụ thuộc được tiêm vào bean bằng cách sử dụng `@Autowired`, `@Inject`, hoặc setter methods.
- **Khởi tạo Bean**: Các phương thức khởi tạo như `@PostConstruct` hoặc `afterPropertiesSet()` được gọi để hoàn thành quá trình khởi tạo.
- **Bean sẵn sàng để sử dụng**: Sau khi khởi tạo, bean có thể được sử dụng trong ứng dụng.
- **Hủy Bean**: Khi không còn cần thiết, các phương thức hủy như `@PreDestroy` hoặc `destroy()` được gọi để giải phóng tài nguyên.

### Ví dụ đầy đủ

```java
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class MyBean implements InitializingBean, DisposableBean {

    public MyBean() {
        System.out.println("MyBean được tạo");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Phương thức @PostConstruct được gọi");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Phương thức afterPropertiesSet() được gọi");
    }

    public void performTask() {
        System.out.println("MyBean đang thực hiện nhiệm vụ");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Phương thức @PreDestroy được gọi");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Phương thức destroy() được gọi");
    }
}
```

Trong ví dụ này:
- Bean được khởi tạo thông qua constructor.
- Sau đó, `@PostConstruct` và `afterPropertiesSet()` được gọi để khởi tạo bean.
- Khi không còn cần thiết, `@PreDestroy` và `destroy()` sẽ được gọi để hủy bean.

### Kết luận

Vòng đời của một bean trong Spring Boot bao gồm nhiều bước từ khi tạo, khởi tạo, đến khi hủy. Hiểu rõ quá trình này giúp bạn kiểm soát tốt hơn cách bean được quản lý trong ứng dụng, đặc biệt trong các ứng dụng phức tạp đòi hỏi kiểm soát tài nguyên và quản lý vòng đời các đối tượng.