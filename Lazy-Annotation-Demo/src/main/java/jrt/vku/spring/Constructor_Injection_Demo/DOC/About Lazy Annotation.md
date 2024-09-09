### 1. **Giới thiệu về `@Lazy` trong Spring Boot**

Trong Spring, các bean thường được khởi tạo một cách **eagerly** (ngay lập tức) khi ứng dụng khởi động, nghĩa là tất cả các bean được tạo ra ngay lúc ứng dụng bắt đầu hoạt động, bất kể chúng có được sử dụng hay không. Tuy nhiên, trong nhiều trường hợp, bạn có thể không muốn bean được khởi tạo ngay lập tức mà chỉ khởi tạo khi cần thiết. Để xử lý điều này, Spring cung cấp annotation `@Lazy`.

- **`@Lazy` annotation** cho phép trì hoãn việc khởi tạo (lazy initialization) của một bean cho đến khi nó thực sự được sử dụng (được yêu cầu).

    - **Lazy Initialization**: Khi một bean được đánh dấu với `@Lazy`, nó sẽ không được khởi tạo khi ứng dụng bắt đầu, mà chỉ được khởi tạo lần đầu tiên khi có yêu cầu sử dụng nó.

    - **Eager Initialization**: Đây là cơ chế khởi tạo mặc định trong Spring, nơi các bean được tạo ra ngay khi ứng dụng bắt đầu, bất kể có sử dụng hay không.

### 2. **Cách sử dụng `@Lazy`**

Bạn có thể sử dụng `@Lazy` trong hai trường hợp:
- Trên **class** (bean).
- Trên **dependency injection** giữa các bean.

### 3. **Sử dụng `@Lazy` trên một class**

Bạn có thể sử dụng `@Lazy` để chỉ định rằng bean này chỉ được khởi tạo khi có yêu cầu (lần đầu tiên được sử dụng).

**Ví dụ**:

```java
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ExpensiveService {
    
    public ExpensiveService() {
        System.out.println("ExpensiveService: Bean được khởi tạo");
    }

    public void doSomething() {
        System.out.println("ExpensiveService: Thực hiện hành động nào đó...");
    }
}
```

Trong ví dụ này, bean `ExpensiveService` sẽ không được khởi tạo khi ứng dụng khởi động mà chỉ được khởi tạo khi phương thức `doSomething()` được gọi.

### 4. **Sử dụng `@Lazy` với Dependency Injection**

Khi sử dụng `@Lazy` cho các dependency giữa các bean, bạn có thể điều khiển khi nào một dependency được khởi tạo, thay vì nó được khởi tạo ngay khi Spring tạo ra đối tượng chính.

**Ví dụ**:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {

    private ServiceB serviceB;

    // Inject ServiceB with Lazy initialization
    @Autowired
    public ServiceA(@Lazy ServiceB serviceB) {
        this.serviceB = serviceB;
        System.out.println("ServiceA: Đã khởi tạo");
    }

    public void performAction() {
        System.out.println("ServiceA: Gọi phương thức performAction");
        serviceB.doSomething();
    }
}

@Component
public class ServiceB {

    public ServiceB() {
        System.out.println("ServiceB: Bean được khởi tạo");
    }

    public void doSomething() {
        System.out.println("ServiceB: Thực hiện hành động nào đó...");
    }
}
```

Trong ví dụ này:
- `ServiceB` sẽ không được khởi tạo khi `ServiceA` được khởi tạo, mà chỉ khi phương thức `doSomething()` trong `ServiceA` được gọi.
- Điều này là nhờ vào annotation `@Lazy` trong constructor của `ServiceA`.

**Output**:

```
ServiceA: Đã khởi tạo
ServiceA: Gọi phương thức performAction
ServiceB: Bean được khởi tạo
ServiceB: Thực hiện hành động nào đó...
```

Trong output này, bạn có thể thấy rằng `ServiceB` chỉ được khởi tạo khi cần thiết (khi `serviceB.doSomething()` được gọi).

### 5. **Ưu điểm và nhược điểm của `@Lazy`**

#### **Ưu điểm**:
1. **Tối ưu hóa bộ nhớ**: Bean không cần thiết sẽ không được khởi tạo trừ khi có yêu cầu.
2. **Cải thiện thời gian khởi động**: Ứng dụng sẽ khởi động nhanh hơn vì không phải khởi tạo tất cả các bean ngay lập tức.
3. **Phù hợp cho các bean nặng**: Đối với các bean tốn tài nguyên (ví dụ: kết nối cơ sở dữ liệu, tải file lớn), việc trì hoãn khởi tạo có thể giúp ứng dụng hoạt động trơn tru hơn.

#### **Nhược điểm**:
1. **Tăng độ phức tạp**: Khi sử dụng `@Lazy`, bạn cần đảm bảo rằng bean sẽ được khởi tạo đúng lúc, điều này có thể làm tăng độ phức tạp khi debug.
2. **Chậm khi khởi tạo lần đầu**: Bean sẽ cần thêm thời gian để khởi tạo khi được sử dụng lần đầu, có thể dẫn đến độ trễ trong một số trường hợp.
3. **Không phải lúc nào cũng cần thiết**: Đối với các ứng dụng nhỏ hoặc với các bean ít tài nguyên, việc lazy initialization không đem lại nhiều lợi ích.

### 6. **Kết luận**

`@Lazy` trong Spring Boot là một công cụ mạnh mẽ để quản lý việc khởi tạo bean theo nhu cầu. Khi được sử dụng đúng cách, nó giúp tối ưu hóa bộ nhớ và cải thiện hiệu suất khởi động của ứng dụng. Tuy nhiên, nó cũng yêu cầu sự cân nhắc kỹ lưỡng, vì không phải lúc nào việc trì hoãn khởi tạo cũng là cần thiết.