Lớp `UserService` là một **interface** được định nghĩa để cung cấp các dịch vụ liên quan đến **User** trong ứng dụng. Nó mở rộng từ `UserDetailsService`, một interface quan trọng của **Spring Security** để lấy thông tin người dùng trong quá trình xác thực. Dưới đây là giải thích chi tiết:

### 1. **`UserService extends UserDetailsService`**

- **`UserDetailsService`**:
    - Đây là một interface của Spring Security, được sử dụng để tải thông tin người dùng từ cơ sở dữ liệu trong quá trình xác thực.
    - Phương thức chính của nó là `loadUserByUsername(String username)`, được Spring Security gọi khi cần xác thực người dùng.

- **`extends UserDetailsService`**:
    - Bằng cách mở rộng (`extends`), `UserService` kế thừa phương thức `loadUserByUsername()` từ `UserDetailsService`. Điều này bắt buộc lớp triển khai `UserService` phải cung cấp logic để tải thông tin người dùng dựa trên **username**.

### 2. **Phương thức `findByUsername(String username)`**

```java
public User findByUsername(String username);
```

- **Chức năng**:
    - Tìm và trả về đối tượng **User** dựa trên tên người dùng (**username**).
    - Phương thức này thường được dùng để lấy thông tin chi tiết của một người dùng cụ thể khi có username, đặc biệt là trong quá trình xác thực.

- **Mục đích**:
    - Hỗ trợ cho quá trình xác thực và quản lý người dùng bằng cách tìm kiếm trong cơ sở dữ liệu.

### 3. **Phương thức `saveUser(User user)`**

```java
public void saveUser(User user);
```

- **Chức năng**:
    - Lưu một đối tượng **User** vào cơ sở dữ liệu. Điều này bao gồm cả việc tạo mới hoặc cập nhật thông tin của người dùng hiện tại.

- **Mục đích**:
    - Quản lý thông tin người dùng bằng cách thêm người dùng mới hoặc cập nhật thông tin (bao gồm username, mật khẩu, vai trò, v.v.).

### Tóm tắt lại:

- **`UserService`** là một interface mở rộng `UserDetailsService` để cung cấp các dịch vụ liên quan đến người dùng (User).
- **`findByUsername(String username)`**: Dùng để tìm người dùng dựa trên **username**, quan trọng cho quá trình xác thực.
- **`saveUser(User user)`**: Lưu thông tin người dùng vào cơ sở dữ liệu, dùng cho việc đăng ký hoặc cập nhật thông tin người dùng.

Khi triển khai lớp này, bạn sẽ phải hiện thực các phương thức này để tương tác với cơ sở dữ liệu hoặc các lớp liên quan đến người dùng.