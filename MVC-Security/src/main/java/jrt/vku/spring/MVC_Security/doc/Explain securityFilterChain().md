### Hàm `securityFilterChain`:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        configurer -> configurer
            .requestMatchers("/public/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
            .requestMatchers("/teacher/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")
            .anyRequest().permitAll()
    )
    .formLogin(
        form -> form
            .loginPage("/showLoginPage")
            .loginProcessingUrl("/authenticateTheUser")
            .defaultSuccessUrl("/home", true)
            .permitAll()
    )
    .logout(
        logout -> logout.permitAll()
    )
    .exceptionHandling(
        configurer -> configurer.accessDeniedPage("/showPage403")
    );
    return http.build();
}
```

### Giải thích chi tiết:

1. **@Bean**:
    - Chú thích này cho Spring biết rằng hàm `securityFilterChain` sẽ tạo ra một bean của loại `SecurityFilterChain`, mà Spring sẽ quản lý.

2. **public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception**:
    - Đây là khai báo của hàm. `HttpSecurity` là đối tượng mà bạn sử dụng để cấu hình bảo mật cho ứng dụng. Hàm này có thể ném ra ngoại lệ `Exception`, do đó bạn cần xử lý hoặc khai báo rằng nó có thể ném ngoại lệ.

3. **http.authorizeHttpRequests(...)**:
    - Phần này cấu hình các quy tắc quyền truy cập cho các yêu cầu HTTP.

    - **configurer -> configurer.requestMatchers("/public/**").permitAll()**:
        - Các yêu cầu đến URL bắt đầu bằng `/public/` sẽ được phép truy cập mà không cần xác thực (không cần đăng nhập).

    - **configurer.requestMatchers("/admin/**").hasRole("ADMIN")**:
        - Các yêu cầu đến URL bắt đầu bằng `/admin/` yêu cầu người dùng phải có vai trò `ADMIN`.

    - **configurer.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")**:
        - Các yêu cầu đến URL bắt đầu bằng `/manager/` yêu cầu người dùng có ít nhất một trong các vai trò `ADMIN` hoặc `MANAGER`.

    - **configurer.requestMatchers("/teacher/**").hasAnyRole("ADMIN", "MANAGER", "TEACHER")**:
        - Các yêu cầu đến URL bắt đầu bằng `/teacher/` yêu cầu người dùng có ít nhất một trong các vai trò `ADMIN`, `MANAGER`, hoặc `TEACHER`.

    - **configurer.anyRequest().permitAll()**:
        - Bất kỳ yêu cầu nào không khớp với các mẫu trước đó sẽ được phép truy cập mà không cần xác thực.

4. **.formLogin(...)**:
    - Cấu hình cho đăng nhập.

    - **form -> form.loginPage("/showLoginPage")**:
        - Cung cấp URL của trang đăng nhập tùy chỉnh.

    - **form.loginProcessingUrl("/authenticateTheUser")**:
        - URL mà trình duyệt sẽ gửi thông tin đăng nhập đến (thường là phương thức POST).

    - **form.defaultSuccessUrl("/home", true)**:
        - URL sẽ được chuyển hướng đến sau khi đăng nhập thành công. `true` cho phép chuyển hướng đến trang này ngay cả khi người dùng đã đăng nhập trước đó.

    - **form.permitAll()**:
        - Cho phép tất cả người dùng (kể cả những người chưa đăng nhập) truy cập trang đăng nhập.

5. **.logout(...)**:
    - Cấu hình cho chức năng đăng xuất.

    - **logout -> logout.permitAll()**:
        - Cho phép tất cả người dùng truy cập vào chức năng đăng xuất mà không cần xác thực.

6. **.exceptionHandling(...)**:
    - Cấu hình xử lý lỗi.

    - **configurer -> configurer.accessDeniedPage("/showPage403")**:
        - Cung cấp URL của trang sẽ được hiển thị khi người dùng không có quyền truy cập vào tài nguyên (403 Forbidden).

7. **return http.build()**:
    - Xây dựng và trả về đối tượng `SecurityFilterChain` đã cấu hình từ `HttpSecurity`.

### Tóm tắt:

- Hàm `securityFilterChain` cấu hình bảo mật cho ứng dụng web bằng cách xác định quyền truy cập cho các URL khác nhau.
- Nó cũng cấu hình trang đăng nhập, trang đăng xuất và xử lý lỗi cho ứng dụng.
- Điều này giúp bảo vệ các tài nguyên của ứng dụng và xác thực người dùng.