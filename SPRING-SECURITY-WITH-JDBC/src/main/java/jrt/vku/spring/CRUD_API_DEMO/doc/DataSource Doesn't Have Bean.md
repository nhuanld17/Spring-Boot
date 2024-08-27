Trong Spring, các thành phần như `DataSource` thường được cấu hình và quản lý bởi Spring Framework một cách tự động, đặc biệt khi bạn sử dụng các starter packages của Spring Boot như `spring-boot-starter-data-jpa`.

### Giải thích:

1. **`DataSource` trong Spring Boot:**
    - Spring Boot có cơ chế tự động cấu hình (`Auto-Configuration`) rất mạnh mẽ. Khi bạn thêm các dependencies như `spring-boot-starter-data-jpa` vào dự án, Spring Boot sẽ tự động cấu hình `DataSource` dựa trên các thông tin được cung cấp trong file `application.properties` hoặc `application.yml`.
    - Ví dụ: Nếu bạn cấu hình các thuộc tính như sau trong `application.properties`:

      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/student_ms
      spring.datasource.username=root
      spring.datasource.password=nhuan.142857
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      ```

      Spring Boot sẽ tự động tạo ra một `DataSource` bean dựa trên các thuộc tính này.

2. **Tại sao `@Autowired` hoạt động:**
    - `@Autowired` được sử dụng để tiêm phụ thuộc (`dependency injection`) vào phương thức hoặc constructor trong Spring. Khi Spring phát hiện `@Autowired` trên một phương thức, nó sẽ cố gắng tìm kiếm một bean phù hợp trong Spring Context để tiêm vào.
    - Trong trường hợp của bạn, Spring Boot đã tự động tạo và quản lý một `DataSource` bean dựa trên cấu hình của bạn trong `application.properties`. Do đó, khi bạn sử dụng `@Autowired` trên phương thức `jdbcUserDetailsManager`, Spring sẽ tự động tiêm `DataSource` đã được cấu hình vào phương thức này.

3. **Cấu hình `JdbcUserDetailsManager`:**
    - `JdbcUserDetailsManager` là một class trong Spring Security được sử dụng để quản lý người dùng thông qua cơ sở dữ liệu. Nó cần một `DataSource` để kết nối và tương tác với cơ sở dữ liệu. Khi bạn tiêm `DataSource` vào `JdbcUserDetailsManager`, nó sẽ sử dụng `DataSource` này để thực hiện các thao tác với bảng `users` và `authorities` trong cơ sở dữ liệu của bạn.

### Tóm tắt:
- Spring Boot tự động cấu hình `DataSource` nếu bạn cung cấp các thuộc tính cần thiết trong file cấu hình của mình.
- Bạn không cần phải tự định nghĩa `@Bean` cho `DataSource` trong hầu hết các trường hợp khi sử dụng Spring Boot, vì nó đã được tự động quản lý.
- `@Autowired` hoạt động vì Spring Boot đã cung cấp và quản lý một bean `DataSource` mà bạn có thể sử dụng trong ứng dụng của mình.

Vì vậy, ngay cả khi bạn không thấy rõ ràng rằng `DataSource` được khai báo trong code, nó vẫn được tạo ra và quản lý bởi Spring Boot dựa trên cấu hình tự động.