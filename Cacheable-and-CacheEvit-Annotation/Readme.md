
# Spring Boot Caching Example

## Mô tả
Dự án này là một ví dụ đơn giản về cách sử dụng **Spring Boot**, **MySQL** và **caching** để lưu trữ và truy xuất dữ liệu một cách hiệu quả. Mục tiêu của dự án là minh họa cách hoạt động của các annotation `@Cacheable` và `@CacheEvict` trong Spring, nhằm giúp cải thiện hiệu năng của ứng dụng khi truy xuất dữ liệu từ cơ sở dữ liệu.

## Tính năng
Dự án bao gồm các tính năng chính sau:
- Thêm mới sách vào cơ sở dữ liệu
- Truy xuất thông tin sách theo ID với caching để tăng tốc độ phản hồi
- Xóa sách theo ID và tự động xóa cache tương ứng để đảm bảo dữ liệu trong cache luôn đồng bộ với cơ sở dữ liệu

## Công nghệ sử dụng
- **Spring Boot** - Framework Java cho các ứng dụng web và API REST
- **Spring Data JPA** - Tương tác với cơ sở dữ liệu MySQL thông qua JPA
- **MySQL** - Cơ sở dữ liệu quan hệ để lưu trữ dữ liệu sách
- **Spring Cache** - Tích hợp caching trong Spring Boot

## Cấu trúc dự án
- `Book` - Entity đại diện cho bảng `book` trong cơ sở dữ liệu
- `BookRepository` - Lớp repository để tương tác với cơ sở dữ liệu
- `BookService` - Service chứa các phương thức có sử dụng caching
- `BookController` - REST API để thao tác với dữ liệu sách

1**Chạy ứng dụng**:
   ```bash
   mvn spring-boot:run
   ```

2**Kiểm tra API**:
    - **Thêm mới sách**: `POST /books`
      ```json
      {
          "title": "Spring Framework in Action",
          "author": "Craig Walls",
          "price": 29.99
      }
      ```
    - **Truy xuất sách theo ID**: `GET /books/{id}`
    - **Xóa sách theo ID**: `DELETE /books/{id}`

## Cách hoạt động của caching
- Khi truy vấn sách lần đầu tiên bằng `GET /books/{id}`, dữ liệu sẽ được lưu vào cache.
- Những lần truy vấn sau, dữ liệu sẽ được lấy từ cache thay vì cơ sở dữ liệu, giúp cải thiện hiệu năng.
- Khi xóa sách với `DELETE /books/{id}`, cache của sách đó sẽ bị xóa để đảm bảo dữ liệu trong cache luôn đồng bộ với cơ sở dữ liệu.

## Đóng góp
Nếu bạn muốn đóng góp vào dự án, vui lòng tạo **Pull Request** hoặc mở **Issue** để thảo luận thêm.

## Giấy phép
Dự án này được phân phối theo giấy phép MIT.


## Ghi chú
Nhớ thay `your-username` bằng tên người dùng GitHub của bạn trong đường dẫn clone để chính xác khi bạn đẩy dự án lên GitHub.
