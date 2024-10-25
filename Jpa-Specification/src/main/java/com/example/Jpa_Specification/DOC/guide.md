Dựa vào bài viết hướng dẫn sử dụng JPA Specification trong Spring Data, sau đây là hướng dẫn chi tiết giúp bạn xây dựng một API REST tìm kiếm/lọc dựa trên những thông tin đã cung cấp.

### 1. Thiết lập Dự án

Trước hết, hãy tạo một dự án Spring Boot mới. Bạn có thể sử dụng Spring Initializr để tạo dự án với các dependency cần thiết như:

- Spring Web
- Spring Data JPA
- H2 Database (hoặc bất kỳ cơ sở dữ liệu nào bạn muốn sử dụng)

### 2. Tạo Thực thể User

Tạo một thực thể `User` với các thuộc tính cần thiết. Tạo một lớp `User.java` trong gói `model`.

```java
package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private int age;

    // Standard getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

### 3. Tạo lớp `SearchCriteria`

Tạo lớp `SearchCriteria` để lưu trữ thông tin về điều kiện tìm kiếm.

```java
package com.example.demo.specification;

public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    // Getters and Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
```

### 4. Tạo lớp `UserSpecification`

Tạo lớp `UserSpecification` để triển khai logic tìm kiếm dựa trên các điều kiện đã định nghĩa.

```java
package com.example.Jpa_Specification.specification;  
  
import com.example.Jpa_Specification.model.User;  
import jakarta.persistence.criteria.CriteriaBuilder;  
import jakarta.persistence.criteria.CriteriaQuery;  
import jakarta.persistence.criteria.Predicate;  
import jakarta.persistence.criteria.Root;  
import org.springframework.data.jpa.domain.Specification;  
  
public class UserSpecification implements Specification<User> {  
    private SearchCriteria criteria;  
      
    public UserSpecification(SearchCriteria criteria) {  
       this.criteria = criteria;  
    }  
      
    @Override  
  public Predicate toPredicate  
  (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {  
       if (criteria.getOperation().equalsIgnoreCase(">")) {  
          return builder.greaterThan(  
                root.<String>get(criteria.getKey()), (String) criteria.getValue()  
          );  
       } else if (criteria.getOperation().equalsIgnoreCase("<")) {  
          return builder.lessThan(  
                root.<String>get(criteria.getKey()), (String) criteria.getValue()  
          );  
       } else if (criteria.getOperation().equalsIgnoreCase(":")) {  
          if (root.get(criteria.getKey()).getJavaType() == String.class) {  
             return builder.like(  
                   root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%"  
  );  
          } else {  
             return builder.equal(root.get(criteria.getKey()), criteria.getValue());  
          }  
       }  
         
       return null;  
    }  
}
```

### 5. Tạo `UserRepository`

Tạo interface `UserRepository` để mở rộng `JpaSpecificationExecutor`.

```java
package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
```

### 6. Tạo lớp `UserController`

Tạo `UserController` để xử lý các yêu cầu HTTP.

```java
package com.example.Jpa_Specification.controller;

import com.example.Jpa_Specification.model.User;
import com.example.Jpa_Specification.repository.UserRepository;
import com.example.Jpa_Specification.specification.UserSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
@Autowired
private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> search(@RequestParam(value = "search") String search) {
        System.out.println("Search input: " + search);
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search+",");
        String[] searchParams = search.split(",");
        
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<User> spec = builder.build();
        List<User> users = userRepository.findAll(spec);
        System.out.println("Users found: " + users); // In ra kết quả tìm kiếm
        return users;
    }
}
```

### 7. Tạo `UserSpecificationsBuilder`

Tạo lớp `UserSpecificationsBuilder` để giúp xây dựng các Specification.

```java
package com.example.Jpa_Specification.specification;  
  
import com.example.Jpa_Specification.model.User;  
import org.springframework.data.jpa.domain.Specification;  
  
import java.util.ArrayList;  
import java.util.List;  
  
public class UserSpecificationsBuilder {  
    private final List<SearchCriteria> params;  
      
    public UserSpecificationsBuilder() {  
       params = new ArrayList<>();  
    }  
      
    public UserSpecificationsBuilder with(String key, String operation, Object value) {  
       params.add(new SearchCriteria(key, operation, value));  
       return this;  
    }  
      
    public Specification<User> build() {  
       if (params.isEmpty()) {  
          return null;  
       }  
         
       Specification<User> result = new UserSpecification(params.get(0));  
         
       for (int i = 1; i < params.size(); i++) {  
          result = Specification.where(result).and(new UserSpecification(params.get(i)));  
       }  
         
       return result;  
         
    }  
}
```

### 8. Kiểm Tra API

Tạo một số người dùng trong cơ sở dữ liệu trước khi kiểm tra. Bạn có thể tạo một lớp `CommandLineRunner` để thêm một số người dùng mẫu.

```java
package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.save(new User("John", "Doe", "john@doe.com", 22));
        userRepository.save(new User("Tom", "Doe", "tom@doe.com", 26));
    }
}
```

### 9. Chạy Ứng Dụng

Chạy ứng dụng của bạn và thử nghiệm API bằng cách sử dụng URL như sau:

```
http://localhost:8080/users?search=lastName:doe,age>25
```

### 10. Kết Luận

Bây giờ bạn đã thiết lập thành công một API REST tìm kiếm bằng cách sử dụng Spring Data JPA Specifications. Hệ thống này cho phép bạn thực hiện các tìm kiếm linh hoạt dựa trên nhiều điều kiện khác nhau.

Nếu bạn có bất kỳ câu hỏi nào hoặc cần thêm thông tin, hãy cho tôi biết!