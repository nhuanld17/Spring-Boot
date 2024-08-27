**Password Storage** trong Spring Security là một cơ chế giúp lưu trữ mật khẩu người dùng một cách an toàn trong cơ sở dữ liệu. Đây là một bước rất quan trọng để bảo vệ thông tin nhạy cảm và đảm bảo rằng mật khẩu của người dùng không thể dễ dàng bị lấy cắp hoặc khai thác nếu hệ thống bị xâm nhập.

### 1. Khái Niệm Về Password Storage

Trong Spring Security, mật khẩu không bao giờ được lưu trữ dưới dạng văn bản thuần túy (plain text). Thay vào đó, chúng được **mã hóa** (hashed) trước khi lưu trữ vào cơ sở dữ liệu. Mã hóa mật khẩu là quá trình chuyển đổi mật khẩu thành một chuỗi ký tự không thể đảo ngược bằng các thuật toán mã hóa. Khi người dùng đăng nhập, mật khẩu họ nhập vào sẽ được mã hóa và so sánh với mật khẩu đã được mã hóa trong cơ sở dữ liệu.

### 2. Các Phương Pháp Mã Hóa Mật Khẩu

Spring Security hỗ trợ nhiều cách thức mã hóa mật khẩu khác nhau, bao gồm:

- **BCrypt**: Đây là một trong những phương pháp mã hóa phổ biến và bảo mật nhất hiện nay, được thiết kế để chậm lại theo thời gian, giúp chống lại các cuộc tấn công brute force.
- **PBKDF2**: Đây là một phương pháp mã hóa mật khẩu dựa trên HMAC (Hash-based Message Authentication Code).
- **SCrypt**: Một phương pháp mã hóa mật khẩu khác với mục đích bảo mật cao hơn.
- **SHA-256**: Sử dụng hàm băm để mã hóa mật khẩu.

### 3. Cách Sử Dụng BCrypt Password Encoder trong Spring Security

BCrypt là một lựa chọn phổ biến và an toàn để mã hóa mật khẩu trong Spring Security. Dưới đây là cách cấu hình và sử dụng BCrypt trong ứng dụng Spring:

#### Bước 1: Cấu Hình BCryptPasswordEncoder

Đầu tiên, bạn cần cấu hình `BCryptPasswordEncoder` như một bean trong Spring Security.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

#### Bước 2: Mã Hóa Mật Khẩu Trước Khi Lưu Trữ

Khi tạo mới hoặc cập nhật mật khẩu của người dùng, bạn cần mã hóa mật khẩu trước khi lưu nó vào cơ sở dữ liệu:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // Lưu user vào cơ sở dữ liệu
    }
}
```

#### Bước 3: Xác Thực Mật Khẩu

Khi người dùng đăng nhập, mật khẩu họ nhập vào sẽ được mã hóa và so sánh với mật khẩu đã mã hóa trong cơ sở dữ liệu. Spring Security sẽ tự động thực hiện việc này nếu bạn cấu hình đúng:

```java
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(), 
            new ArrayList<>()
        );
    }
}
```

### 4. Lợi Ích của BCrypt

- **Bảo Mật Cao**: BCrypt được thiết kế để chống lại các cuộc tấn công brute force.
- **Salt**: BCrypt tự động sinh ra một giá trị **salt** cho mỗi lần mã hóa, giúp tăng cường bảo mật bằng cách làm cho mỗi mật khẩu mã hóa là duy nhất ngay cả khi người dùng nhập cùng một mật khẩu.
- **Dễ Sử Dụng**: Spring Security tích hợp sẵn và dễ dàng sử dụng BCrypt thông qua `BCryptPasswordEncoder`.

### 5. Tổng Kết

**Password Storage** trong Spring Security đảm bảo rằng mật khẩu người dùng được mã hóa và lưu trữ một cách an toàn. Việc sử dụng các thuật toán mã hóa như BCrypt giúp bảo vệ hệ thống trước các cuộc tấn công vào mật khẩu. Với Spring Security, việc mã hóa và xác thực mật khẩu trở nên dễ dàng và hiệu quả, giúp tăng cường bảo mật cho ứng dụng của bạn.