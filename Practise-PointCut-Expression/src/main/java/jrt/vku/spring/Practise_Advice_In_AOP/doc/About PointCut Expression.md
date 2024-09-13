### Giới thiệu về **Pointcut Expression** trong Spring AOP

Trong Spring AOP, **Pointcut Expression** (biểu thức pointcut) được sử dụng để xác định **vị trí** (point) trong chương trình mà **advice** (lời khuyên) sẽ được áp dụng. Các pointcut này giúp xác định chính xác những phương thức nào sẽ bị ảnh hưởng bởi các aspect. Pointcut là một phần rất quan trọng của AOP, vì nó cho phép bạn kiểm soát chi tiết các nơi mà các logic phụ (cross-cutting concerns) như logging, transaction management, hay security sẽ được áp dụng.

### Các khái niệm cơ bản

- **Join Point**: Một điểm cụ thể trong luồng thực thi của chương trình, chẳng hạn như lời gọi phương thức hoặc xử lý ngoại lệ.
- **Advice**: Đoạn mã được thực thi tại một điểm cụ thể (trước, sau, hoặc quanh Join Point) nhờ vào AOP.
- **Pointcut**: Một biểu thức giúp xác định vị trí nào trong chương trình sẽ áp dụng **advice**.

### Các thành phần của Pointcut Expression

Biểu thức Pointcut trong Spring AOP thường có dạng sau:

```java
execution(modifiers-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)
```

- **modifiers-pattern**: Tùy chọn, mô tả các modifier của phương thức (public, private, protected,...).
- **return-type-pattern**: Xác định kiểu trả về của phương thức (`*` là đại diện cho tất cả các kiểu).
- **declaring-type-pattern**: Tùy chọn, xác định class hoặc package chứa phương thức.
- **method-name-pattern**: Tên của phương thức hoặc ký tự đại diện (`*` để khớp với mọi phương thức).
- **param-pattern**: Mô tả các tham số của phương thức (có thể dùng dấu `..` để đại diện cho bất kỳ số lượng tham số nào).
- **throws-pattern**: Tùy chọn, mô tả các ngoại lệ mà phương thức có thể ném ra.

### Các biểu thức thông dụng của Pointcut

1. **`execution`**: Thường dùng nhất, nó giúp áp dụng advice cho các phương thức dựa trên chữ ký của chúng.
    - **Ví dụ**: `execution(public * com.example.service.*.*(..))`
        - Áp dụng cho tất cả các phương thức public trong package `com.example.service` với bất kỳ tên phương thức và bất kỳ tham số nào.

2. **`within`**: Dùng để chỉ ra tất cả các phương thức trong một class hoặc package.
    - **Ví dụ**: `within(com.example.service.*)`
        - Áp dụng cho tất cả các phương thức trong các class thuộc package `com.example.service`.

3. **`this`** và **`target`**: Áp dụng cho các đối tượng. `this` dùng với proxy của đối tượng, còn `target` là đối tượng thật.
    - **Ví dụ**: `this(com.example.service.MyService)`
        - Áp dụng advice cho tất cả các phương thức của đối tượng là proxy của `MyService`.

4. **`args`**: Dùng để chỉ ra các phương thức có tham số cụ thể.
    - **Ví dụ**: `args(java.lang.String)`
        - Áp dụng cho tất cả các phương thức có tham số là kiểu `String`.

5. **`@annotation`**: Áp dụng advice cho các phương thức có một annotation cụ thể.
    - **Ví dụ**: `@annotation(org.springframework.transaction.annotation.Transactional)`
        - Áp dụng cho tất cả các phương thức có annotation `@Transactional`.

### Ví dụ cụ thể và dễ hiểu

Hãy xem xét ví dụ dưới đây, sử dụng **Pointcut Expression** để áp dụng advice cho các phương thức trong `CalculaService`:

```java
@Aspect
@Component
public class LoggingAspect {

    // Before Advice cho tất cả các phương thức add trong CalculaService với 2 tham số bất kỳ
    @Before("execution(public double jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(double, double))")
    public void beforeAdding(JoinPoint joinPoint) {
        System.out.println("Before adding: " + joinPoint.getSignature());
    }
    
    // After Advice cho tất cả các phương thức public trong CalculaService
    @After("execution(public * jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        System.out.println("After method: " + joinPoint.getSignature());
    }
}
```

### Giải thích ví dụ:

1. **Biểu thức `execution(public double jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(double, double))`**:
    - `public double`: Chỉ áp dụng cho phương thức có kiểu trả về `double` và có modifier là `public`.
    - `jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(double, double)`: Áp dụng chính xác cho phương thức `add` trong class `CalculaService` với hai tham số kiểu `double`.

2. **Biểu thức `execution(public * jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.*(..))`**:
    - `public *`: Áp dụng cho tất cả các phương thức public với bất kỳ kiểu trả về nào (`*`).
    - `CalculaService.*(..)`: Áp dụng cho tất cả các phương thức trong class `CalculaService` với bất kỳ tên phương thức và tham số nào.

### Kết quả:

Khi chạy chương trình với `CalculaService`, ví dụ khi gọi phương thức `add(5, 10)`:

```
Before adding: double jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(double, double)
After method: double jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(double, double)
Adding result: 15.0
```

- Advice `@Before` được thực thi **trước khi** phương thức `add` chạy.
- Advice `@After` được thực thi **sau khi** phương thức `add` hoàn thành.

### Kết luận:

- **Pointcut Expression** là một cách mạnh mẽ để xác định các phương thức trong ứng dụng mà bạn muốn áp dụng advice.
- Bạn có thể tùy chỉnh rất nhiều bằng cách kết hợp các biểu thức khác nhau như `execution`, `within`, `args`, `@annotation`,... để phù hợp với yêu cầu của mình.
- Spring AOP giúp thêm các chức năng như logging, quản lý giao dịch mà không làm thay đổi mã chính của các phương thức.

Nếu cần thêm chi tiết về các loại pointcut khác, mình sẵn sàng giúp đỡ thêm!