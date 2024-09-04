Phương thức `securityFilterChain` được định nghĩa trong một lớp cấu hình bảo mật Spring Security. Phương thức này trả về một `SecurityFilterChain` để xác định cách mà các yêu cầu HTTP sẽ được xử lý và bảo vệ trong ứng dụng.

Dưới đây là phần giải thích chi tiết về từng phần của phương thức này:

### 1. `@Bean`
- **Giải thích**: Annotation `@Bean` cho Spring biết rằng phương thức này trả về một bean được quản lý bởi Spring IoC Container. Bean này sẽ được sử dụng để cấu hình bảo mật cho ứng dụng.

### 2. `public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception`
- **Giải thích**: Phương thức này sử dụng đối tượng `HttpSecurity` để cấu hình bảo mật. `HttpSecurity` cho phép bạn cấu hình các chi tiết như xác thực (authentication), cấp quyền (authorization), trang đăng nhập, xử lý ngoại lệ, v.v.

### 3. `http.authorizeHttpRequests(...)`
- **Giải thích**: Đây là phần cốt lõi của cấu hình bảo mật, nơi bạn xác định quyền truy cập cho các yêu cầu HTTP cụ thể.

### 4. Cấu hình các `requestMatchers`
- **Giải thích**:
    - **`requestMatchers("/public/**").permitAll()`**: Mọi người đều có thể truy cập vào các URL bắt đầu bằng `/public/` mà không cần xác thực.
    - **`requestMatchers("/admin/**").hasRole("ADMIN")`**: Chỉ những người dùng có vai trò "ADMIN" mới có thể truy cập vào các URL bắt đầu bằng `/admin/`.
    - **`requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")`**: Người dùng có vai trò "MANAGER" hoặc "ADMIN" mới có thể truy cập vào các URL bắt đầu bằng `/manager/`.
    - **`requestMatchers("/teacher/**").hasAnyRole("TEACHER", "MANAGER", "ADMIN")`**: Người dùng có vai trò "TEACHER", "MANAGER", hoặc "ADMIN" mới có thể truy cập vào các URL bắt đầu bằng `/teacher/`.
    - **`anyRequest().permitAll()`**: Bất kỳ yêu cầu nào khác đều được phép truy cập mà không cần xác thực.

### 5. Cấu hình `formLogin`
- **Giải thích**: Cấu hình này xác định cách xử lý trang đăng nhập cho ứng dụng.
    - **`form.loginPage("/showLoginPage")`**: Chỉ định URL của trang đăng nhập tùy chỉnh.
    - **`form.loginProcessingUrl("/authenticateTheUser")`**: Chỉ định URL mà Spring Security sẽ sử dụng để xử lý thông tin đăng nhập khi người dùng gửi form đăng nhập.
    - **`form.defaultSuccessUrl("/home")`**: Sau khi đăng nhập thành công, người dùng sẽ được chuyển hướng đến trang `/home`.
    - **`permitAll()`**: Cho phép tất cả mọi người truy cập vào trang đăng nhập và quá trình xác thực mà không cần phải đăng nhập trước.

### 6. Cấu hình `logout`
- **Giải thích**: Xác định cấu hình cho việc đăng xuất khỏi ứng dụng.
    - **`logout.permitAll()`**: Cho phép tất cả người dùng (kể cả chưa đăng nhập) truy cập vào chức năng đăng xuất.

### 7. Xử lý ngoại lệ `exceptionHandling`
- **Giải thích**: Cấu hình này xác định cách ứng dụng sẽ xử lý các ngoại lệ liên quan đến việc từ chối quyền truy cập.
    - **`configurer.accessDeniedPage("/showPage403")`**: Khi người dùng bị từ chối truy cập (ví dụ, họ không có đủ quyền để truy cập một trang), họ sẽ được chuyển hướng đến trang `/showPage403`.

### 8. `return http.build();`
- **Giải thích**: Phương thức này xây dựng và trả về một `SecurityFilterChain` dựa trên cấu hình đã xác định.

### Tóm tắt

Phương thức này định nghĩa các quy tắc bảo mật cho các URL cụ thể trong ứng dụng, thiết lập trang đăng nhập, quy trình đăng xuất, và xử lý ngoại lệ liên quan đến từ chối quyền truy cập. Mỗi phần cấu hình đảm bảo rằng chỉ những người dùng có quyền thích hợp mới có thể truy cập vào các tài nguyên nhất định.