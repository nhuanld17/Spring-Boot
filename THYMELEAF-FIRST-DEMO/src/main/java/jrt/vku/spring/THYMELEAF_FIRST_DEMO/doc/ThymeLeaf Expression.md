### Thymeleaf Expression là gì?

**Thymeleaf Expression** là các biểu thức được sử dụng trong Thymeleaf để truy cập và thao tác với dữ liệu trong mô hình (model) của ứng dụng Spring MVC. Biểu thức này giúp hiển thị dữ liệu, điều kiện, lặp qua các phần tử, và thực hiện các phép toán đơn giản trực tiếp trong các tệp template HTML.

### Các loại biểu thức trong Thymeleaf

Thymeleaf hỗ trợ nhiều loại biểu thức khác nhau để đáp ứng các nhu cầu khác nhau của ứng dụng:

1. **Variable Expression (Biểu thức biến)**: `${...}`
2. **Selection Variable Expression (Biểu thức lựa chọn biến)**: `*{...}`
3. **Message Expression (Biểu thức thông điệp)**: `#{...}`
4. **Link URL Expression (Biểu thức URL liên kết)**: `@{...}`
5. **Fragment Expression (Biểu thức đoạn mã)**: `~{...}`
6. **Literal Substitution (Thay thế ký tự)**: `|...|`
7. **Text Operations (Toán tử văn bản)**: `th:text`, `th:utext`
8. **Boolean Operations (Toán tử boolean)**: `th:if`, `th:unless`
9. **Iteration (Lặp)**: `th:each`

### Giải thích chi tiết từng loại biểu thức

#### 1. **Variable Expression (Biểu thức biến): `${...}`**

Biểu thức này được sử dụng để truy cập các thuộc tính trong mô hình (model). Ví dụ:

**Controller**:
```java
@GetMapping("/greeting")
public String greeting(Model model) {
    model.addAttribute("name", "John");
    return "greeting";
}
```

**Template `greeting.html`**:
```html
<p>Hello, <span th:text="${name}">Guest</span>!</p>
```

- **Giải thích**: Biểu thức `${name}` sẽ lấy giá trị của biến `name` từ mô hình, trong ví dụ này là `"John"`, và hiển thị nó trên giao diện.

#### 2. **Selection Variable Expression (Biểu thức lựa chọn biến): `*{...}`**

Biểu thức này thường được sử dụng trong các phần tử lặp lại hoặc khi làm việc với các thuộc tính đối tượng con của một đối tượng cha.

**Ví dụ**:
```html
<div th:object="${user}">
    <p>Name: <span th:text="*{name}">John Doe</span></p>
    <p>Email: <span th:text="*{email}">john.doe@example.com</span></p>
</div>
```

- **Giải thích**: Biểu thức `*{name}` và `*{email}` sẽ lấy giá trị từ đối tượng `user` đang được gán cho th:object.

#### 3. **Message Expression (Biểu thức thông điệp): `#{...}`**

Biểu thức này được sử dụng để truy cập các thông điệp quốc tế hóa từ các tệp `messages.properties`.

**Ví dụ**:

Trong tệp `messages.properties`:
```properties
greeting=Hello, {0}!
```

Trong template:
```html
<p th:text="#{greeting(${name})}">Hello, Guest!</p>
```

- **Giải thích**: Biểu thức `#{greeting(${name})}` sẽ lấy thông điệp từ tệp `messages.properties` và thay thế `{0}` bằng giá trị của `${name}`.

#### 4. **Link URL Expression (Biểu thức URL liên kết): `@{...}`**

Biểu thức này được sử dụng để tạo các URL động, giúp liên kết đến các tài nguyên hoặc điều hướng giữa các trang.

**Ví dụ**:
```html
<a th:href="@{/login}">Login</a>
```

- **Giải thích**: Biểu thức `@{/login}` sẽ tạo ra một URL động cho trang đăng nhập.

#### 5. **Fragment Expression (Biểu thức đoạn mã): `~{...}`**

Biểu thức này cho phép tái sử dụng các đoạn mã (fragments) từ các tệp template khác.

**Ví dụ**:
```html
<div th:insert="~{fragments/header :: header}"></div>
```

- **Giải thích**: Biểu thức `~{fragments/header :: header}` sẽ chèn đoạn mã `header` từ tệp `header.html` vào vị trí này.

#### 6. **Literal Substitution (Thay thế ký tự): `|...|`**

Biểu thức này cho phép thay thế chuỗi ký tự và giá trị biến vào một chuỗi định dạng.

**Ví dụ**:
```html
<p th:text="|Hello, ${name}!|">Hello, Guest!</p>
```

- **Giải thích**: Biểu thức `|Hello, ${name}!|` sẽ thay thế `${name}` bằng giá trị của nó, tạo ra chuỗi `"Hello, John!"`.

#### 7. **Text Operations (Toán tử văn bản): `th:text`, `th:utext`**

- `th:text`: Hiển thị văn bản thông thường.
- `th:utext`: Hiển thị văn bản với các thẻ HTML không bị escape.

**Ví dụ**:
```html
<p th:text="${description}">This is a description.</p>
```

- **Giải thích**: `th:text` sẽ thay thế nội dung của thẻ `<p>` bằng giá trị của biến `description`.

#### 8. **Boolean Operations (Toán tử boolean): `th:if`, `th:unless`**

Sử dụng để kiểm tra các điều kiện logic.

**Ví dụ**:
```html
<p th:if="${user != null}">Welcome, <span th:text="${user.username}">User</span>!</p>
```

- **Giải thích**: Nếu `user` không phải là `null`, đoạn mã này sẽ hiển thị câu chào với tên người dùng.

#### 9. **Iteration (Lặp): `th:each`**

Sử dụng để lặp qua một danh sách các đối tượng.

**Ví dụ**:
```html
<ul>
    <li th:each="item : ${items}" th:text="${item.name}">Item Name</li>
</ul>
```

- **Giải thích**: Biểu thức `th:each` sẽ lặp qua danh sách `items` và hiển thị tên của mỗi phần tử.

### Kết luận

Thymeleaf Expression là một phần quan trọng giúp bạn dễ dàng thao tác với dữ liệu trong mô hình và hiển thị chúng trên giao diện người dùng. Hiểu rõ các loại biểu thức này sẽ giúp bạn tận dụng tối đa sức mạnh của Thymeleaf trong việc xây dựng các trang web động và tương tác. Các ví dụ trên đã minh họa cách sử dụng cụ thể từng loại biểu thức, giúp bạn dễ dàng áp dụng vào các dự án thực tế.