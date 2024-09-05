Lớp `UserServiceImpl` là lớp triển khai của interface `UserService` và thực hiện các phương thức để quản lý người dùng trong ứng dụng Spring Security. Dưới đây là giải thích chi tiết về các phần của lớp:

### 1. **Khai báo và khởi tạo**
```java
private UserRepository userRepository;
private RoleRepository roleRepository;

@Autowired
public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
}
```
- **`UserRepository`** và **`RoleRepository`** là các lớp tương tác với cơ sở dữ liệu cho các thực thể **User** và **Role**.
- **`@Autowired`**: Spring tự động tiêm phụ thuộc vào lớp này. Khi lớp này được khởi tạo, các đối tượng `userRepository` và `roleRepository` sẽ được truyền vào từ các Bean được cấu hình.

### 2. **Phương thức `findByUsername(String username)`**
```java
@Override
public User findByUsername(String username) {
    return userRepository.findByUsername(username);
}
```
- Phương thức này tìm và trả về đối tượng **User** dựa trên tên người dùng (**username**). Nó gọi phương thức `findByUsername` của **`UserRepository`** để lấy thông tin từ cơ sở dữ liệu.

### 3. **Phương thức `saveUser(User user)`**
```java
@Override
public void saveUser(User user) {
    userRepository.save(user);
}
```
- Phương thức này lưu đối tượng **User** vào cơ sở dữ liệu thông qua **`userRepository`**. Nó được sử dụng khi cần đăng ký hoặc cập nhật thông tin người dùng.

### 4. **Phương thức `loadUserByUsername(String username)`**
```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
        throw new UsernameNotFoundException("Invalid username or password.");
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
}
```
- Đây là phương thức quan trọng trong Spring Security, được sử dụng để tải thông tin người dùng dựa trên **username** khi người dùng cố gắng đăng nhập.
- **Bước xử lý**:
    - Lấy thông tin **User** từ cơ sở dữ liệu bằng `findByUsername`.
    - Nếu **User** không tồn tại, ném ngoại lệ `UsernameNotFoundException`.
    - Nếu **User** tồn tại, trả về một đối tượng `UserDetails` (cụ thể là `org.springframework.security.core.userdetails.User`), chứa các thông tin như **username**, **password**, và các quyền hạn (roles) của người dùng.

### 5. **Phương thức `getAuthorities(Collection<Role> roles)`**
```java
private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
}
```
- Phương thức này chuyển đổi danh sách các vai trò (**Role**) của người dùng thành danh sách các quyền hạn (**GrantedAuthority**) mà Spring Security sử dụng để kiểm tra quyền truy cập.
- **Vai trò (role)** được ánh xạ thành đối tượng `SimpleGrantedAuthority`, trong đó tên vai trò sẽ được dùng để kiểm soát quyền truy cập.

### 6. **Chú thích `@PostConstruct` và phương thức `insertUser()`**
```java
@PostConstruct
public void insertUser() {
    User user = new User();
    user.setUsername("Nhuan");
    user.setPassword("$2a$12$PVOp62u2uoYHd91YI9JpmuYG8Jeth0rgPtna5nuLZVBUyUWedHgXG");
    user.setEnabled(true);
    
    Role role = new Role();
    role.setName("ROLE_ADMIN");
    
    Collection<Role> roles = new ArrayList<>();
    roles.add(role);
    
    user.setRoles(roles);
    userRepository.save(user);
}
```
- Phương thức này được đánh dấu với **`@PostConstruct`**, nghĩa là nó sẽ được gọi sau khi đối tượng `UserServiceImpl` được khởi tạo.
- **Mục đích**:
    - Thêm dữ liệu mẫu vào cơ sở dữ liệu (một người dùng tên **Nhuan** và một vai trò **ROLE_ADMIN**). Phần này chỉ cần sử dụng một lần để chèn dữ liệu, sau đó có thể comment lại.

### Tóm lại:
- Lớp `UserServiceImpl` cung cấp các chức năng cần thiết để quản lý người dùng trong hệ thống xác thực, bao gồm tìm kiếm người dùng, lưu thông tin người dùng, và xử lý đăng nhập.
- Nó sử dụng **`UserRepository`** và **`RoleRepository`** để tương tác với cơ sở dữ liệu và xử lý vai trò, quyền hạn của người dùng thông qua Spring Security.