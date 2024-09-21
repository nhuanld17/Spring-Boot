`Model` trong Spring MVC là một phần quan trọng để truyền dữ liệu từ Controller sang View. Nó giúp bạn chuyển dữ liệu cần thiết đến View (thường là các trang HTML) để hiển thị cho người dùng. `Model` được sử dụng để lưu trữ các thuộc tính và các giá trị tương ứng mà bạn muốn hiển thị trong View.

### 1. **Giới thiệu về `Model`**

`Model` là một giao diện (interface) được sử dụng trong Spring MVC để thêm các dữ liệu vào đối tượng model, mà sau đó sẽ được truyền đến View để hiển thị. Khi controller xử lý một request, nó có thể thêm dữ liệu vào `Model`, sau đó trả về tên View để Spring MVC render trang tương ứng với dữ liệu đã được thêm vào model.

**Các cách sử dụng `Model`:**
- `Model`: Một interface đơn giản cung cấp các phương thức để thêm dữ liệu vào model.
- `ModelMap`: Một triển khai cụ thể của `Model`, hỗ trợ các chức năng tương tự như một `Map`.
- `ModelAndView`: Kết hợp dữ liệu `Model` và tên của `View` trong một đối tượng duy nhất.

### 2. **Cách sử dụng `Model`**

`Model` thường được sử dụng trong các phương thức của Controller, nơi bạn có thể thêm dữ liệu vào model bằng phương thức `addAttribute(String key, Object value)`.

#### Ví dụ cơ bản:

Giả sử bạn có một ứng dụng quản lý danh sách sản phẩm. Bạn muốn hiển thị danh sách sản phẩm lên trang web.

**Bước 1: Định nghĩa `Product` model**

```java
public class Product {
    private String name;
    private double price;

    // Constructor, getters, setters
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
```

**Bước 2: Tạo Controller để truyền dữ liệu đến View**

Trong Controller, ta sử dụng `Model` để thêm dữ liệu vào và trả về tên View cần render.

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String getProducts(Model model) {
        // Tạo danh sách sản phẩm
        List<Product> productList = Arrays.asList(
                new Product("Laptop", 1200.0),
                new Product("Smartphone", 800.0),
                new Product("Tablet", 400.0)
        );

        // Thêm danh sách sản phẩm vào model
        model.addAttribute("products", productList);

        // Trả về tên view (trang products.html sẽ được render)
        return "products";
    }
}
```

**Bước 3: Tạo View (`products.html`) để hiển thị dữ liệu**

Trong file `products.html`, sử dụng Thymeleaf hoặc JSP để truy cập vào dữ liệu đã được truyền từ `Model`.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Product List</title>
</head>
<body>
    <h1>Danh sách sản phẩm</h1>
    <ul>
        <!-- Lặp qua danh sách sản phẩm và hiển thị từng sản phẩm -->
        <li th:each="product : ${products}">
            Tên: <span th:text="${product.name}"></span> - 
            Giá: <span th:text="${product.price}"></span> USD
        </li>
    </ul>
</body>
</html>
```

### 3. **Giải thích chi tiết**

- **Controller (`ProductController`)**: Khi người dùng truy cập đường dẫn `/products`, phương thức `getProducts()` sẽ được gọi.
- **`model.addAttribute("products", productList);`**: Dòng này thêm đối tượng `productList` vào `Model` với khóa là `"products"`.
- **Trả về `return "products";`**: Spring sẽ tìm một file HTML có tên là `products.html` và sử dụng dữ liệu trong model để render trang.
- **View (`products.html`)**: Sử dụng Thymeleaf để lặp qua danh sách sản phẩm và hiển thị tên và giá của từng sản phẩm.

### 4. **Tại sao sử dụng `Model`?**

- **Dễ dàng quản lý dữ liệu**: `Model` giúp truyền dữ liệu từ backend sang frontend một cách trực tiếp và rõ ràng.
- **Tái sử dụng**: Các giá trị được truyền từ `Model` có thể dễ dàng được tái sử dụng trong nhiều thành phần của View.
- **Tính rõ ràng và tách biệt**: Việc thêm dữ liệu vào model giúp giữ cho mã nguồn controller rõ ràng và tách biệt với view.

### 5. **Lợi ích của việc sử dụng `Model`**

- **Tách biệt logic và hiển thị**: Controller xử lý logic, và View chỉ chịu trách nhiệm hiển thị.
- **Dễ bảo trì**: Thay đổi một phần không ảnh hưởng đến phần còn lại.
- **Truyền dữ liệu đơn giản**: Sử dụng `addAttribute()` để thêm bất kỳ đối tượng nào vào View.

Hy vọng giải thích này giúp bạn hiểu rõ hơn về cách sử dụng `Model` trong Spring MVC! Nếu cần thêm thông tin, bạn có thể hỏi thêm nhé!