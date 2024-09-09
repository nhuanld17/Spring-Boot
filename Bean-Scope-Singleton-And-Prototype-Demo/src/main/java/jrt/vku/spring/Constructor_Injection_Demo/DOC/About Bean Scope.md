### Phạm vi của Bean (Bean Scope) trong Spring Boot

Trong Spring, **Bean Scope** quy định cách thức và thời gian mà các **bean** được khởi tạo, quản lý và sử dụng trong ứng dụng. Mỗi bean trong Spring có một phạm vi (scope) xác định vòng đời và cách thức nó được cung cấp cho các thành phần khác trong ứng dụng.

### 1. **Các loại phạm vi Bean trong Spring**

Spring hỗ trợ nhiều loại phạm vi cho các bean, trong đó phổ biến nhất là hai phạm vi:

- **Singleton** (mặc định)
- **Prototype**

Ngoài ra còn có các phạm vi khác ít phổ biến hơn trong ứng dụng web như:
- **Request**
- **Session**
- **Application**

#### a. **Singleton Scope (Mặc định)**

- **Singleton** là phạm vi mặc định của Spring. Khi một bean được khai báo với phạm vi `singleton`, Spring sẽ tạo một instance duy nhất của bean đó cho toàn bộ ứng dụng. Mỗi khi bạn yêu cầu bean này, Spring sẽ cung cấp cùng một instance cho tất cả các yêu cầu.

Ví dụ:

```java
@Component
public class SingletonService {
    public SingletonService() {
        System.out.println("SingletonService được khởi tạo");
    }
    
    public void serve() {
        System.out.println("SingletonService phục vụ");
    }
}
```

Trong ví dụ này, dù gọi bao nhiêu lần, Spring sẽ luôn trả về cùng một instance của `SingletonService`:

```java
@RestController
public class MyController {
    
    @Autowired
    private SingletonService singletonService1;
    
    @Autowired
    private SingletonService singletonService2;
    
    @GetMapping("/test-singleton")
    public String testSingleton() {
        // Cả hai biến sẽ trỏ đến cùng một đối tượng
        if (singletonService1 == singletonService2) {
            return "Cả hai là cùng một instance";
        } else {
            return "Không phải là cùng một instance";
        }
    }
}
```

Kết quả sẽ là: "Cả hai là cùng một instance", bởi vì `SingletonService` có phạm vi singleton.

#### b. **Prototype Scope**

- **Prototype** khác với `singleton` ở chỗ: mỗi lần bạn yêu cầu một bean với phạm vi `prototype`, Spring sẽ tạo ra một instance mới. Điều này có nghĩa là mỗi lần sử dụng, bạn sẽ nhận được một đối tượng khác nhau.

Ví dụ:

```java
@Component
@Scope("prototype")
public class PrototypeService {
    public PrototypeService() {
        System.out.println("PrototypeService được khởi tạo");
    }
    
    public void serve() {
        System.out.println("PrototypeService phục vụ");
    }
}
```

Trong controller:

```java
@RestController
public class MyController {

    @Autowired
    private PrototypeService prototypeService1;
    
    @Autowired
    private PrototypeService prototypeService2;
    
    @GetMapping("/test-prototype")
    public String testPrototype() {
        // Hai biến sẽ trỏ đến hai đối tượng khác nhau
        if (prototypeService1 == prototypeService2) {
            return "Cả hai là cùng một instance";
        } else {
            return "Không phải là cùng một instance";
        }
    }
}
```

Kết quả sẽ là: "Không phải là cùng một instance", bởi vì `PrototypeService` có phạm vi `prototype`, và mỗi lần Spring sẽ tạo một đối tượng mới.

#### c. **Request Scope**

- **Request** là phạm vi chỉ áp dụng cho các ứng dụng web. Mỗi bean có phạm vi `request` sẽ được khởi tạo một lần cho mỗi yêu cầu HTTP. Mỗi yêu cầu web mới sẽ nhận được một instance bean mới.

Ví dụ:

```java
@Component
@Scope("request")
public class RequestService {
    public RequestService() {
        System.out.println("RequestService được khởi tạo");
    }
}
```

Trong ứng dụng web, mỗi lần có yêu cầu HTTP đến, Spring sẽ tạo một instance mới cho `RequestService`.

#### d. **Session Scope**

- **Session** cũng là một phạm vi dành cho các ứng dụng web. Bean có phạm vi `session` sẽ được khởi tạo một lần cho mỗi session của người dùng (client). Trong suốt thời gian phiên làm việc của người dùng (session), Spring sẽ cung cấp cùng một instance của bean đó.

Ví dụ:

```java
@Component
@Scope("session")
public class SessionService {
    public SessionService() {
        System.out.println("SessionService được khởi tạo");
    }
}
```

Trong trường hợp này, mỗi session người dùng sẽ chỉ có một `SessionService` riêng biệt, và nó sẽ tồn tại trong suốt thời gian phiên làm việc.

#### e. **Application Scope**

- **Application** scope là phạm vi tồn tại suốt vòng đời của một `ServletContext`. Điều này có nghĩa là bean sẽ được tạo ra một lần duy nhất cho toàn bộ ứng dụng web, tương tự như singleton, nhưng nó gắn liền với vòng đời của ứng dụng web.

Ví dụ:

```java
@Component
@Scope("application")
public class ApplicationService {
    public ApplicationService() {
        System.out.println("ApplicationService được khởi tạo");
    }
}
```

`ApplicationService` sẽ được tạo một lần duy nhất khi ứng dụng web được khởi chạy và sẽ tồn tại suốt vòng đời của ứng dụng.

### 2. **Tùy chỉnh phạm vi Bean**

Bạn có thể tùy chỉnh phạm vi của một bean bằng cách sử dụng annotation `@Scope` cùng với loại phạm vi mong muốn. Ví dụ:

```java
@Component
@Scope("prototype")  // hoặc "singleton", "request", "session", "application"
public class MyBean {
    // ...
}
```

Hoặc bạn có thể sử dụng trong file cấu hình:

```java
@Bean
@Scope("prototype")
public MyBean myBean() {
    return new MyBean();
}
```

### 3. **Khi nào sử dụng từng loại Bean Scope?**

- **Singleton**: Sử dụng cho những bean không có trạng thái và có thể tái sử dụng trong toàn bộ ứng dụng.
- **Prototype**: Sử dụng khi cần tạo ra một đối tượng mới mỗi lần sử dụng, chẳng hạn như các dịch vụ có trạng thái.
- **Request**: Dành cho các bean chỉ tồn tại trong phạm vi một yêu cầu HTTP, thường sử dụng trong các ứng dụng web.
- **Session**: Sử dụng cho các bean liên quan đến dữ liệu người dùng trong suốt phiên làm việc.
- **Application**: Phù hợp cho các bean dùng chung trên toàn bộ ứng dụng web và chỉ cần một instance duy nhất.

### 4. **Ví dụ tổng hợp**

```java
@Component
@Scope("singleton")
public class SingletonService {
    // Singleton Bean
}

@Component
@Scope("prototype")
public class PrototypeService {
    // Prototype Bean
}

@Component
@Scope("request")
public class RequestService {
    // Bean chỉ tồn tại trong một request HTTP
}

@Component
@Scope("session")
public class SessionService {
    // Bean chỉ tồn tại trong một session của người dùng
}

@Component
@Scope("application")
public class ApplicationService {
    // Bean tồn tại trong suốt vòng đời của ứng dụng
}
```

Tóm lại, **Bean Scope** là một khái niệm quan trọng trong Spring giúp xác định cách thức quản lý các bean trong ứng dụng, từ việc tạo, quản lý đến việc hủy bỏ. Hiểu rõ về các phạm vi bean sẽ giúp tối ưu hóa hiệu suất và đáp ứng các yêu cầu cụ thể của ứng dụng.