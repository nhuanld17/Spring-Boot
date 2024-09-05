Class `RegisterController` trong đoạn code này là một controller Spring MVC chịu trách nhiệm xử lý yêu cầu liên quan đến đăng ký tài khoản người dùng. Nó chứa các phương thức để hiển thị và xử lý form đăng ký.

### Cấu trúc tổng quan:
- Sử dụng `@Controller` để định nghĩa lớp này là một Spring MVC Controller.
- Sử dụng `@RequestMapping("/register")` để tất cả các yêu cầu trong controller này sẽ được định tuyến với đường dẫn `/register`.

### Giải thích chi tiết các thành phần:

1. **Các dependency:**
    - `UserService userService`: Dịch vụ để xử lý logic nghiệp vụ liên quan đến người dùng.
    - `RoleRepository roleRepository`: Repository để truy xuất dữ liệu liên quan đến vai trò của người dùng trong hệ thống.

   Hai dependency này được inject thông qua constructor bằng `@Autowired`.

2. **Phương thức `showRegistrationForm`:**
    - **Mô tả:**
        - Phương thức này xử lý yêu cầu GET đến `/register/showRegisterForm`.
        - Nó trả về trang view tên là `register/form`, trang này chứa form đăng ký.
        - Một đối tượng `RegisterUser` được thêm vào model để dữ liệu từ form có thể được bind vào.

   ```java
   @GetMapping("/showRegisterForm")
   public String showRegistrationForm(Model model) {
       RegisterUser registerUser = new RegisterUser();
       model.addAttribute("registerUser", registerUser);
       return "register/form";
   }
   ```

3. **Phương thức `initBinder`:**
    - **Mô tả:**
        - Phương thức này được gọi trước khi dữ liệu form được bind vào đối tượng.
        - Nó sử dụng `StringTrimmerEditor` để xóa khoảng trắng thừa từ các chuỗi được nhập vào form.
        - `@InitBinder` giúp cấu hình bind dữ liệu từ request vào đối tượng.

   ```java
   @InitBinder
   public void initBinder(WebDataBinder binder) {
       StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
       binder.registerCustomEditor(String.class, stringTrimmerEditor);
   }
   ```

4. **Phương thức `process`:**
    - **Mô tả:**
        - Phương thức này xử lý yêu cầu POST từ form đăng ký tại `/register/process`.
        - Sử dụng `@Valid` để xác thực đối tượng `RegisterUser` dựa trên các ràng buộc được khai báo.
        - Nếu có lỗi xác thực (`bindingResult.hasErrors()`), nó sẽ trả lại trang đăng ký với thông báo lỗi.
        - Nếu username đã tồn tại, người dùng sẽ được yêu cầu nhập lại thông tin với thông báo lỗi.
        - Nếu đăng ký hợp lệ, đối tượng `User` mới sẽ được tạo với thông tin từ form, mã hóa mật khẩu bằng `BCryptPasswordEncoder`, và gán vai trò mặc định là `ROLE_USER`.
        - Người dùng mới sẽ được lưu vào cơ sở dữ liệu thông qua phương thức `saveUser` của `UserService`.
        - Sau khi đăng ký thành công, đối tượng `User` mới sẽ được lưu vào session và trả về trang xác nhận (`confirmation`).

   ```java
   @PostMapping("/process")
   public String process(@Valid @ModelAttribute("registerUser") RegisterUser registerUser,
                         BindingResult bindingResult,
                         Model model,
                         HttpSession session) {
       String username = registerUser.getUsername();

       // Form validation
       if (bindingResult.hasErrors()) {
           return "register/form";
       }

       // Kiểm tra sự tồn tại của user
       User user = userService.findByUsername(username);
       if (user != null) {
           model.addAttribute("registerUser", new RegisterUser());
           model.addAttribute("my_error", "Tài khoản đã tồn tại");
           return "register/form";
       }

       // Nếu chưa tồn tại thì lưu lại
       User newUser = new User();
       newUser.setUsername(registerUser.getUsername());
       newUser.setPassword(new BCryptPasswordEncoder().encode(registerUser.getPassword()));
       newUser.setFirstname(registerUser.getFirstName());
       newUser.setLastname(registerUser.getLastName());
       newUser.setEmail(registerUser.getEmail());

       // Gán vai trò mặc định
       Role defaultRole = roleRepository.findByName("ROLE_USER");
       Collection<Role> roles = new ArrayList<>();
       roles.add(defaultRole);
       newUser.setRoles(roles);

       userService.saveUser(newUser);

       // Thông báo thành công
       session.setAttribute("my_user", newUser);
       return "register/confirmation";
   }
   ```

### Tóm tắt:
- **Mục tiêu của Controller này** là quản lý việc đăng ký tài khoản người dùng, bao gồm hiển thị form và xử lý dữ liệu từ form, kiểm tra sự tồn tại của username, mã hóa mật khẩu, và gán vai trò mặc định cho người dùng mới.
- Phương thức `process` chịu trách nhiệm xử lý logic đăng ký: xác thực form, kiểm tra người dùng, mã hóa mật khẩu, lưu vào database, và phản hồi lại người dùng.