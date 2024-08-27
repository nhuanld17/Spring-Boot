Khi sử dụng Thymeleaf để xây dựng ứng dụng web, bạn có thể dễ dàng thêm CSS và JavaScript vào trang HTML của mình. Để làm điều này, bạn cần đảm bảo rằng các file CSS và JavaScript được đặt đúng vị trí và được tham chiếu chính xác trong các template Thymeleaf.

### 1. Đặt file CSS và JS trong thư mục tĩnh
Theo cấu trúc thư mục mặc định của Spring Boot, các tài nguyên tĩnh như CSS, JavaScript, hình ảnh thường được đặt trong thư mục `src/main/resources/static`.

- **CSS:** `src/main/resources/static/css/style.css`
- **JavaScript:** `src/main/resources/static/js/script.js`

### 2. Tham chiếu đến file CSS và JS trong Thymeleaf
Để tham chiếu đến các file CSS và JS trong template Thymeleaf, bạn sử dụng cú pháp `th:href` và `th:src` để đảm bảo các đường dẫn được xử lý động theo đúng context path của ứng dụng.

#### Ví dụ: Cấu trúc một trang HTML sử dụng Thymeleaf, CSS và JS

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>My Thymeleaf Page</title>

    <!-- Thêm CSS -->
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>

    <h1>BẢNG CỬU CHƯƠNG - <span th:text="${number}">0</span></h1>

    <!-- Thêm JS -->
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

### 3. Giải thích chi tiết

- **`th:href="@{/css/style.css}"`**: Thymeleaf sẽ tự động chuyển đổi đường dẫn `/css/style.css` thành đường dẫn tương ứng với context path của ứng dụng. Ví dụ, nếu ứng dụng của bạn chạy trên `http://localhost:8080`, Thymeleaf sẽ render đường dẫn đầy đủ là `http://localhost:8080/css/style.css`.

- **`th:src="@{/js/script.js}"`**: Tương tự như `th:href`, `th:src` đảm bảo đường dẫn đến file JavaScript được xử lý đúng cách.

### 4. File CSS và JS mẫu
#### `style.css` (nằm trong `src/main/resources/static/css/style.css`):
```css
body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 0;
}

h1 {
    color: #333;
    text-align: center;
    margin-top: 50px;
}
```

#### `script.js` (nằm trong `src/main/resources/static/js/script.js`):
```javascript
document.addEventListener("DOMContentLoaded", function() {
    console.log("Page loaded and script executed!");
});
```

### 5. Kết quả
Khi bạn chạy ứng dụng và truy cập vào trang có sử dụng template này, bạn sẽ thấy trang web được hiển thị với CSS đã áp dụng, và JavaScript được thực thi.

Với các bước trên, bạn có thể dễ dàng sử dụng CSS và JavaScript trong các trang Thymeleaf của mình.