`@ControllerAdvice` là một annotation trong Spring Framework được sử dụng để cung cấp một cách toàn cục để xử lý các ngoại lệ, chỉnh sửa dữ liệu phản hồi, và thực hiện các tác vụ trước và sau khi các phương thức xử lý yêu cầu được gọi. Đây là một cách tiện lợi để xử lý các trường hợp lỗi và cung cấp phản hồi tùy chỉnh cho toàn bộ ứng dụng.

### **Giải Thích**

1. **Xử Lý Ngoại Lệ Toàn Cục**: Bạn có thể định nghĩa các phương thức để xử lý các loại ngoại lệ cụ thể hoặc chung. Khi một ngoại lệ xảy ra trong bất kỳ `@Controller` nào, các phương thức trong lớp `@ControllerAdvice` sẽ được gọi để xử lý ngoại lệ đó.

2. **Chỉnh Sửa Dữ Liệu Phản Hồi**: `@ControllerAdvice` cho phép bạn thay đổi hoặc làm phong phú thêm dữ liệu phản hồi từ các `@Controller` trước khi trả về cho client.

3. **Thực Hiện Các Tác Vụ Trước và Sau Khi Xử Lý Yêu Cầu**: Bạn có thể sử dụng `@ControllerAdvice` để thực hiện các hành động như ghi log, thay đổi dữ liệu hoặc xử lý các điều kiện trước khi gửi phản hồi.

### **Ví Dụ Thực Tế**

Dưới đây là một ví dụ về cách sử dụng `@ControllerAdvice` để xử lý ngoại lệ toàn cục trong một ứng dụng Spring Boot.

#### **1. Định Nghĩa Lớp Ngoại Lệ**

```java
package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

#### **2. Định Nghĩa Lớp `@ControllerAdvice`**

```java
package com.example.demo.advice;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
```

#### **3. Định Nghĩa Lớp Phản Hồi Lỗi**

```java
package com.example.demo.entity;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

#### **4. Định Nghĩa `@RestController`**

```java
package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExampleController {

    @GetMapping("/resource/{id}")
    public String getResourceById(@PathVariable int id) {
        if (id < 1 || id > 10) { // Giả sử id từ 1 đến 10 là hợp lệ
            throw new ResourceNotFoundException("Resource with id " + id + " not found");
        }
        return "Resource with id " + id;
    }
}
```

### **Giải Thích**

- **`@ControllerAdvice`**: Được gán cho lớp `GlobalExceptionHandler`, cho phép xử lý ngoại lệ toàn cục. Khi một ngoại lệ kiểu `ResourceNotFoundException` được ném ra, phương thức `handleResourceNotFoundException` sẽ được gọi.

- **`@ExceptionHandler`**: Được sử dụng để chỉ định phương thức xử lý các ngoại lệ của kiểu `ResourceNotFoundException`.

- **`ErrorResponse`**: Được sử dụng để định dạng dữ liệu phản hồi lỗi, bao gồm mã trạng thái và thông báo lỗi.

- **`ExampleController`**: Đây là lớp xử lý các yêu cầu API. Nếu `id` không hợp lệ, nó sẽ ném ra `ResourceNotFoundException`, và ngoại lệ này sẽ được xử lý bởi `GlobalExceptionHandler`.

### **Kết Luận**

Sử dụng `@ControllerAdvice` giúp bạn quản lý các lỗi và phản hồi một cách đồng bộ và hiệu quả trong toàn bộ ứng dụng Spring Boot của bạn. Bạn có thể xử lý lỗi, chỉnh sửa dữ liệu phản hồi, và thực hiện các tác vụ bổ sung một cách tập trung và dễ dàng duy trì.