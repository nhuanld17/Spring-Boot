Trong Spring AOP, **advice** là đoạn mã thực thi tại một điểm xác định (pointcut) trong chương trình. Các loại advice khác nhau sẽ được thực thi tại các thời điểm khác nhau trong vòng đời của phương thức. Dưới đây là các loại advice chính trong Spring AOP cùng với giải thích chi tiết và ví dụ dễ hiểu.

### 1. **Before Advice**
- **Giới thiệu:** `@Before` advice được thực thi trước khi phương thức mục tiêu (target method) được gọi.
- **Cách hoạt động:** Đoạn mã này sẽ chạy ngay trước khi logic chính của phương thức bắt đầu.

#### Ví dụ:
```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("Before advice: Bắt đầu phương thức " + joinPoint.getSignature().getName());
    }
}
```

- **Giải thích:** Advice này sẽ ghi log trước khi bất kỳ phương thức nào trong các lớp thuộc package `com.example.service` được gọi.

#### Kết quả khi chạy:
```
Before advice: Bắt đầu phương thức transferMoney
```

### 2. **After (Finally) Advice**
- **Giới thiệu:** `@After` advice được thực thi sau khi phương thức mục tiêu hoàn thành, bất kể phương thức đó có ném ra exception hay không.
- **Cách hoạt động:** Đoạn mã này luôn luôn được thực thi sau khi phương thức kết thúc.

#### Ví dụ:
```java
@Aspect
@Component
public class LoggingAspect {
    @After("execution(* com.example.service.*.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        System.out.println("After advice: Hoàn thành phương thức " + joinPoint.getSignature().getName());
    }
}
```

- **Giải thích:** Dù phương thức có thành công hay thất bại (ném ra exception), advice này vẫn chạy.

#### Kết quả khi chạy:
```
After advice: Hoàn thành phương thức transferMoney
```

### 3. **After Returning Advice**
- **Giới thiệu:** `@AfterReturning` advice chỉ được thực thi khi phương thức kết thúc mà **không có exception**.
- **Cách hoạt động:** Chạy sau khi phương thức hoàn thành thành công và trả về kết quả.

#### Ví dụ:
```java
@Aspect
@Component
public class LoggingAspect {
    @AfterReturning(pointcut = "execution(* com.example.service.AccountService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("AfterReturning advice: Phương thức " + joinPoint.getSignature().getName() + " trả về: " + result);
    }
}
```

- **Giải thích:** Nếu phương thức `AccountService` kết thúc mà không có lỗi và trả về một kết quả, advice này sẽ in ra kết quả đó.

#### Kết quả khi chạy:
```
AfterReturning advice: Phương thức transferMoney trả về: Success
```

### 4. **After Throwing Advice**
- **Giới thiệu:** `@AfterThrowing` advice được thực thi khi phương thức ném ra một exception.
- **Cách hoạt động:** Advice này sẽ chạy khi phương thức gặp lỗi và ném ra một exception.

#### Ví dụ:
```java
@Aspect
@Component
public class LoggingAspect {
    @AfterThrowing(pointcut = "execution(* com.example.service.AccountService.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("AfterThrowing advice: Phương thức " + joinPoint.getSignature().getName() + " ném ra ngoại lệ: " + error);
    }
}
```

- **Giải thích:** Nếu một phương thức trong `AccountService` ném ra một lỗi, advice này sẽ ghi lại thông tin của lỗi đó.

#### Kết quả khi chạy:
```
AfterThrowing advice: Phương thức transferMoney ném ra ngoại lệ: InsufficientFundsException
```

### 5. **Around Advice**
- **Giới thiệu:** `@Around` advice được thực thi **xung quanh** phương thức mục tiêu. Điều này có nghĩa là bạn có thể kiểm soát hoàn toàn việc thực thi phương thức, bao gồm việc quyết định có gọi phương thức hay không, hay sửa đổi giá trị trả về.
- **Cách hoạt động:** `Around` advice có thể chạy trước và sau khi phương thức được gọi, và có thể thao tác hoặc ghi đè giá trị trả về hoặc bắt ngoại lệ.

#### Ví dụ:
```java
@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* com.example.service.AccountService.*(..))")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around advice: Trước khi phương thức " + joinPoint.getSignature().getName() + " được gọi.");
        Object result = joinPoint.proceed();  // Gọi phương thức mục tiêu
        System.out.println("Around advice: Sau khi phương thức " + joinPoint.getSignature().getName() + " hoàn thành.");
        return result;
    }
}
```

- **Giải thích:** `Around` advice chạy cả trước và sau khi phương thức `AccountService` được thực thi. Bằng cách sử dụng `joinPoint.proceed()`, phương thức mục tiêu sẽ được thực hiện.

#### Kết quả khi chạy:
```
Around advice: Trước khi phương thức transferMoney được gọi.
Chuyển 5000.0 từ 12345 đến 67890
Around advice: Sau khi phương thức transferMoney hoàn thành.
```

### **Tóm tắt các loại Advice:**

- **Before Advice (`@Before`)**: Chạy trước khi phương thức được gọi.
- **After Advice (`@After`)**: Chạy sau khi phương thức hoàn thành (cả khi thành công hay thất bại).
- **After Returning Advice (`@AfterReturning`)**: Chạy sau khi phương thức hoàn thành thành công và trả về kết quả.
- **After Throwing Advice (`@AfterThrowing`)**: Chạy khi phương thức ném ra một exception.
- **Around Advice (`@Around`)**: Chạy xung quanh phương thức (trước và sau khi phương thức được thực thi), cho phép bạn kiểm soát hoàn toàn việc thực thi phương thức.

### **Kết luận:**
Mỗi loại advice trong Spring AOP đều có mục đích và cách sử dụng riêng, giúp bạn dễ dàng áp dụng các chức năng cắt ngang như logging, bảo mật, quản lý giao dịch một cách linh hoạt. `Before` và `After` được sử dụng phổ biến cho các nhiệm vụ đơn giản, trong khi `Around` cung cấp sự linh hoạt toàn diện để điều khiển việc thực thi của phương thức.