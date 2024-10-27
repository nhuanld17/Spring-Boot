Để giúp bạn hiểu cách hoạt động của `@Cacheable` và `@CacheEvict` trong thực tế, mình sẽ hướng dẫn bạn xây dựng một dự án Spring Boot đơn giản với MySQL. Dự án này sẽ mô phỏng cách lưu trữ dữ liệu của sách (`Book`) và cách cache được sử dụng để tăng hiệu năng truy vấn dữ liệu.

### Bước 1: Tạo Project Spring Boot
1. **Tạo một project Spring Boot mới** với các dependencies sau:
   - Spring Web
   - Spring Data JPA
   - MySQL Driver
   - Spring Cache

2. **Cấu hình MySQL trong `application.properties`**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
   spring.datasource.username=root
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   spring.cache.type=simple
   ```
3. **Thêm annotation @EnableCaching vào Class chính**:
   ```java
   @SpringBootApplication  
   @EnableCaching  
   public class CacheableAndCacheEvitAnnotationApplication {  
     
       public static void main(String[] args) {  
          SpringApplication.run(CacheableAndCacheEvitAnnotationApplication.class, args);  
       }  
     
   }
   ```
### Bước 2: Tạo Entity `Book`
Tạo lớp `Book` đại diện cho bảng `book` trong cơ sở dữ liệu.

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private double price;

    // Constructors, getters, and setters
}
```

### Bước 3: Tạo Repository cho Book
Tạo một interface `BookRepository` để tương tác với cơ sở dữ liệu.

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
```

### Bước 4: Cấu hình Cache trong Service
Bây giờ, hãy tạo `BookService` để quản lý sách và sử dụng `@Cacheable` và `@CacheEvict`.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Cacheable(value = "books", key = "#id")
    public Optional<Book> getBookById(Long id) {
        System.out.println("Fetching book from database with id: " + id);
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(value = "books", key = "#id")
    public void deleteBookById(Long id) {
        System.out.println("Deleting book from database and cache with id: " + id);
        bookRepository.deleteById(id);
    }
}
```

- **`@Cacheable(value = "books", key = "#id")`**: Cache kết quả của phương thức `getBookById`. Khi phương thức được gọi lần đầu tiên với một `id` cụ thể, kết quả sẽ được lưu vào cache với `key = id`. Lần gọi tiếp theo với `id` đó sẽ lấy dữ liệu từ cache thay vì truy vấn cơ sở dữ liệu.

- **`@CacheEvict(value = "books", key = "#id")`**: Khi xóa một `Book`, dữ liệu tương ứng trong cache cũng sẽ bị xóa.

### Bước 5: Tạo Controller để Kiểm Tra
Tạo một `BookController` để thao tác với dữ liệu sách thông qua API.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Optional<Book> getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }
}
```

### Bước 6: Kiểm Tra Cache Hoạt Động Thế Nào
1. **Thêm một cuốn sách mới** bằng cách gọi `POST /books` với dữ liệu JSON như sau:
   ```json
   {
       "title": "Spring Framework in Action",
       "author": "Craig Walls",
       "price": 29.99
   }
   ```
2. **Truy vấn sách** với `GET /books/{id}`, ví dụ `GET /books/1`. Nếu `id = 1` tồn tại, bạn sẽ thấy thông báo trong console:
   ```
   Fetching book from database with id: 1
   ```
   Sách sẽ được lưu vào cache sau lần gọi này.

3. **Truy vấn lại cùng `id` (ví dụ `GET /books/1`)**. Lần này, bạn sẽ không thấy thông báo "Fetching book from database..." nữa, vì kết quả được lấy từ cache.

4. **Xóa sách với `DELETE /books/{id}`**. Sau khi gọi, cache của `id` này sẽ bị xóa. Nếu bạn kiểm tra lại `GET /books/{id}` lần nữa, thông báo sẽ lại xuất hiện trong console, cho thấy dữ liệu được truy xuất từ cơ sở dữ liệu chứ không phải từ cache.

### Giải thích chi tiết cách cache hoạt động:

1. **@Cacheable**: Khi lần đầu bạn gọi `getBookById` với `id` bất kỳ, dữ liệu được lấy từ cơ sở dữ liệu và lưu vào cache với khóa là `id`. Những lần gọi sau với `id` đó, dữ liệu được lấy trực tiếp từ cache để tăng tốc độ truy xuất.

2. **@CacheEvict**: Khi gọi `deleteBookById`, dữ liệu sách sẽ bị xóa khỏi cache nếu có `key` tương ứng, đồng thời dữ liệu sẽ bị xóa trong cơ sở dữ liệu. Điều này đảm bảo dữ liệu trong cache luôn đồng bộ với cơ sở dữ liệu.

### Tóm tắt

Trong thực tế, caching là một kỹ thuật mạnh mẽ để cải thiện hiệu suất của ứng dụng bằng cách giảm số lần truy xuất dữ liệu từ cơ sở dữ liệu. `@Cacheable` và `@CacheEvict` giúp bạn dễ dàng kiểm soát việc lưu và xóa cache để dữ liệu luôn chính xác.