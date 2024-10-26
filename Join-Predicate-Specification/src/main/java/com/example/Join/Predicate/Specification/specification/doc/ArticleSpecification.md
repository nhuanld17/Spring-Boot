Dưới đây là phân tích chi tiết hơn về class `ArticleSpecification`, cùng với các giải thích về các đoạn mã, chức năng và ví dụ minh họa cụ thể:

```java
package com.nico.store.store.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nico.store.store.domain.Article;
import com.nico.store.store.domain.Brand;
import com.nico.store.store.domain.Category;
import com.nico.store.store.domain.Size;
```

### Phần đầu: Khai báo package và import
- **Khai báo package**: Class này nằm trong package `com.nico.store.store.repository`, cho thấy đây là một phần của ứng dụng quản lý cửa hàng.
- **Import**: Các thư viện JPA và các domain (`Article`, `Brand`, `Category`, `Size`) được import để sử dụng trong class.

### 1. Constructor

```java
private ArticleSpecification() {}
```
- **Chức năng**: Constructor được khai báo là `private` để ngăn không cho người dùng tạo các instance của `ArticleSpecification`. Điều này thường được thực hiện khi class chỉ cần cung cấp các phương thức tĩnh mà không cần khởi tạo đối tượng.
- **Ví dụ**: Không thể tạo một instance như sau:
  ```java
  ArticleSpecification spec = new ArticleSpecification(); // Không hợp lệ
  ```

### 2. Phương thức `filterBy`

```java
public static Specification<Article> filterBy(Integer priceLow, Integer priceHigh, List<String> sizes, 
                                              List<String> categories, List<String> brands, String search) {
```
- **Chức năng**: Phương thức tĩnh này dùng để tạo ra một đối tượng `Specification<Article>` dựa trên các tiêu chí lọc được cung cấp.
- **Tham số**:
    - `priceLow`: Giá thấp nhất để lọc các bài viết (kiểu Integer).
    - `priceHigh`: Giá cao nhất để lọc các bài viết.
    - `sizes`: Danh sách các kích cỡ mà người dùng muốn tìm (kiểu List<String>).
    - `categories`: Danh sách các danh mục mà người dùng muốn lọc.
    - `brands`: Danh sách các thương hiệu mà người dùng quan tâm.
    - `search`: Từ khóa để tìm kiếm trong tiêu đề bài viết.

### 3. Tạo Predicate

```java
return new Specification<Article>() {
    @Override
    public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        query.distinct(true);
```
- **Chức năng**: Tạo một anonymous inner class implements `Specification<Article>`, và override phương thức `toPredicate` để định nghĩa các điều kiện lọc cho truy vấn.
- **`List<Predicate> predicates = new ArrayList<>();`**: Tạo một danh sách `predicates` để chứa các điều kiện lọc.
- **`query.distinct(true);`**: Thiết lập truy vấn để loại bỏ các bản ghi trùng lặp trong kết quả. Điều này có nghĩa là nếu có nhiều bài viết có cùng thuộc tính, chỉ một bản ghi sẽ được trả về.

### 4. Lọc theo Kích cỡ

```java
if (sizes != null && !sizes.isEmpty()) {
    Join<Article, Size> joinSize = root.join("sizes");
    predicates.add(criteriaBuilder.and(joinSize.get("value").in(sizes)));
}
```
#### Dòng 1: `if (sizes != null && !sizes.isEmpty())`
- **Mục đích**: Đảm bảo rằng danh sách `sizes` không phải là `null` và không rỗng trước khi thực hiện lọc theo kích cỡ.
- **Logic**:
    - `sizes != null`: Kiểm tra `sizes` khác `null` để tránh lỗi `NullPointerException`.
    - `!sizes.isEmpty()`: Đảm bảo rằng danh sách có ít nhất một phần tử (các kích cỡ để lọc).

> **Ví dụ**: Nếu `sizes` có giá trị là `["S", "M", "L"]`, câu lệnh `if` sẽ cho phép mã bên trong được thực thi vì `sizes` không `null` và không rỗng.

#### Dòng 2: `Join<Article, Size> joinSize = root.join("sizes");`
- **Mục đích**: Thực hiện "join" giữa bảng `Article` (bài viết) và bảng `Size` (kích cỡ).
- **Giải thích chi tiết**:
    - `root.join("sizes")`: Sử dụng `root` (đại diện cho bảng `Article`) để "join" đến thuộc tính `sizes` của `Article`.
    - `Join<Article, Size>`: Tạo một đối tượng `Join` giữa `Article` và `Size`, cho phép lấy thông tin từ cả hai bảng trong cùng một truy vấn.
    - **Tại sao cần "join"**: Các bài viết (`Article`) có thể chứa nhiều kích cỡ (`Size`), và để lọc các bài viết theo kích cỡ, chúng ta cần truy cập bảng `Size` và so sánh các giá trị trong `sizes`.

> **Ví dụ**: Nếu `sizes` là `["S", "M", "L"]`, đoạn mã sẽ thực hiện "join" với bảng `Size` để lấy các bài viết có kích cỡ là "S", "M" hoặc "L".

#### Dòng 3: `predicates.add(criteriaBuilder.and(joinSize.get("value").in(sizes)));`
- **Mục đích**: Thêm điều kiện lọc vào danh sách `predicates` để chỉ lấy những bài viết có kích cỡ nằm trong danh sách `sizes`.
- **Chi tiết**:
    - `joinSize.get("value")`: Truy cập cột `value` của bảng `Size`, là nơi lưu kích cỡ cụ thể của từng bài viết.
    - `in(sizes)`: Kiểm tra xem giá trị của `joinSize.get("value")` (kích cỡ của bài viết) có nằm trong danh sách `sizes` hay không.
    - `criteriaBuilder.and(...)`: Thêm điều kiện lọc vào danh sách `predicates` dưới dạng một `Predicate` sử dụng phép AND.

> **Ví dụ**: Nếu `sizes` là `["S", "M", "L"]`, câu lệnh này sẽ thêm điều kiện lọc để chỉ lấy những bài viết có kích cỡ "S", "M" hoặc "L".

### Tổng kết

- **Chức năng**: Đoạn mã trên thêm điều kiện lọc để lấy các bài viết (`Article`) có kích cỡ nằm trong danh sách `sizes`. Điều này giúp người dùng có thể lọc các bài viết theo kích cỡ cụ thể mà họ quan tâm.
- **Hiệu quả**: Bằng cách sử dụng `join` và `Predicate`, truy vấn SQL sẽ được xây dựng tự động để tìm kiếm nhanh và chính xác các bài viết có kích cỡ được chỉ định.

### 5. Lọc theo Danh mục
```java
if (categories != null && !categories.isEmpty()) {
    Join<Article, Category> joinSize = root.join("categories");
    predicates.add(criteriaBuilder.and(joinSize.get("name").in(categories)));
}
```
#### Dòng 1: `if (categories != null && !categories.isEmpty())`
- **Mục đích**: Kiểm tra điều kiện danh sách `categories` trước khi thêm vào tiêu chí lọc để đảm bảo không xảy ra lỗi do danh sách `categories` bị rỗng hoặc là `null`.
- **Cách hoạt động**:
    - `categories != null`: Kiểm tra danh sách `categories` khác `null`.
    - `!categories.isEmpty()`: Đảm bảo danh sách `categories` có ít nhất một phần tử, tức là có danh mục cần lọc.

> **Ví dụ**: Nếu `categories` là `["Electronics", "Books"]`, điều kiện `if` sẽ cho phép mã bên trong được thực thi vì danh sách `categories` không rỗng và không `null`.

#### Dòng 2: `Join<Article, Category> joinSize = root.join("categories");`
- **Mục đích**: Thực hiện "join" giữa bảng `Article` và bảng `Category` để có thể truy cập vào các thuộc tính của `Category` liên quan đến `Article`.
- **Chi tiết**:
    - `root.join("categories")`: Sử dụng `root` (đại diện cho bảng `Article`) để "join" với thuộc tính `categories` của bảng `Article`.
    - `Join<Article, Category>`: Tạo một đối tượng `Join` để kết nối bảng `Article` với bảng `Category`, giúp lấy dữ liệu từ cả hai bảng trong cùng một truy vấn.
    - **Ý nghĩa của "join"**: Các bài viết (`Article`) có thể thuộc nhiều danh mục (`Category`). Việc "join" này cho phép truy vấn lấy ra các bài viết có liên kết với các danh mục trong danh sách `categories`.

> **Ví dụ**: Nếu `categories` là `["Electronics", "Books"]`, câu lệnh `join` sẽ kết nối với bảng `Category` và lấy tất cả bài viết thuộc danh mục `Electronics` hoặc `Books`.

#### Dòng 3: `predicates.add(criteriaBuilder.and(joinSize.get("name").in(categories)));`
- **Mục đích**: Thêm điều kiện lọc vào danh sách `predicates` để chỉ lấy những bài viết có danh mục nằm trong danh sách `categories`.
- **Chi tiết**:
    - `joinSize.get("name")`: Truy cập cột `name` của bảng `Category`, là nơi lưu tên danh mục của mỗi bài viết.
    - `in(categories)`: Kiểm tra xem giá trị của `joinSize.get("name")` (tên danh mục của bài viết) có nằm trong danh sách `categories` hay không.
    - `criteriaBuilder.and(...)`: Sử dụng `and` để thêm điều kiện vào danh sách `predicates`, xác định rằng bài viết phải thuộc ít nhất một trong các danh mục trong `categories`.

> **Ví dụ**: Nếu `categories` là `["Electronics", "Books"]`, đoạn mã này sẽ thêm một điều kiện lọc để lấy những bài viết nằm trong các danh mục `Electronics` hoặc `Books`.

- **Chức năng**: Đoạn mã này thêm điều kiện lọc để chỉ lấy các bài viết (`Article`) thuộc một hoặc nhiều danh mục nằm trong danh sách `categories`.
- **Hiệu quả**: Bằng cách dùng `join` và `Predicate`, điều kiện lọc sẽ được tự động chuyển thành truy vấn SQL để chỉ lấy các bài viết có danh mục tương ứng, giúp người dùng có thể tìm kiếm theo danh mục dễ dàng.

### 6. Lọc theo Thương hiệu


Tiếp tục với đoạn mã sau:

```java
if (brands != null && !brands.isEmpty()) {
    Join<Article, Brand> joinSize = root.join("brands");
    predicates.add(criteriaBuilder.and(joinSize.get("name").in(brands)));
}
```
#### Dòng 1: `if (brands != null && !brands.isEmpty())`
- **Mục đích**: Đảm bảo rằng danh sách `brands` không phải là `null` và không trống trước khi áp dụng điều kiện lọc theo thương hiệu.
- **Logic**:
    - `brands != null`: Kiểm tra xem danh sách `brands` có tồn tại (không phải là `null`) để tránh lỗi `NullPointerException`.
    - `!brands.isEmpty()`: Đảm bảo rằng danh sách `brands` chứa ít nhất một phần tử (một hoặc nhiều thương hiệu để lọc).

> **Ví dụ**: Nếu `brands` có giá trị là `["Nike", "Adidas"]`, thì điều kiện `if` sẽ đúng và các dòng lệnh bên trong sẽ được thực thi, vì `brands` khác `null` và không rỗng.

#### Dòng 2: `Join<Article, Brand> joinSize = root.join("brands");`
- **Mục đích**: Thực hiện "join" giữa bảng `Article` và bảng `Brand` để truy cập vào thông tin về thương hiệu của từng bài viết.
- **Giải thích chi tiết**:
    - `root.join("brands")`: Sử dụng đối tượng `root` (đại diện cho bảng `Article`) để thực hiện "join" đến thuộc tính `brands` của `Article`.
    - `Join<Article, Brand>`: Tạo một đối tượng `Join` giữa `Article` và `Brand`, giúp truy xuất dữ liệu từ bảng `Brand` trong cùng một truy vấn với `Article`.
    - **Tại sao cần "join"**: Các bài viết (`Article`) có thể liên kết với nhiều thương hiệu (`Brand`), và việc "join" giúp truy vấn thông tin thương hiệu liên quan để có thể lọc bài viết theo thương hiệu.

> **Ví dụ**: Nếu `brands` là `["Nike", "Adidas"]`, đoạn mã này sẽ "join" với bảng `Brand` để lấy các bài viết có thương hiệu là "Nike" hoặc "Adidas".

#### Dòng 3: `predicates.add(criteriaBuilder.and(joinSize.get("name").in(brands)));`
- **Mục đích**: Thêm điều kiện lọc vào danh sách `predicates` để chỉ chọn những bài viết có thương hiệu nằm trong danh sách `brands`.
- **Chi tiết**:
    - `joinSize.get("name")`: Truy cập vào cột `name` của bảng `Brand`, là nơi lưu tên thương hiệu của từng bài viết.
    - `in(brands)`: Kiểm tra xem giá trị của `joinSize.get("name")` (tên thương hiệu của bài viết) có nằm trong danh sách `brands` hay không.
    - `criteriaBuilder.and(...)`: Tạo điều kiện dưới dạng `Predicate` sử dụng phép AND và thêm nó vào danh sách `predicates`.

> **Ví dụ**: Nếu `brands` là `["Nike", "Adidas"]`, câu lệnh này sẽ thêm điều kiện lọc vào danh sách `predicates` để chỉ lấy các bài viết có thương hiệu là "Nike" hoặc "Adidas".

- **Chức năng**: Đoạn mã này thêm điều kiện lọc để lấy các bài viết (`Article`) có thương hiệu nằm trong danh sách `brands`. Điều này cho phép người dùng lọc các bài viết theo các thương hiệu cụ thể mà họ quan tâm.
- **Hiệu quả**: Bằng cách sử dụng `join` và `Predicate`, điều kiện lọc sẽ được thêm vào truy vấn SQL một cách tự động và tối ưu, giúp tìm kiếm chính xác và hiệu quả các bài viết theo thương hiệu mong muốn.

### 7. Lọc theo Từ khóa

```java
if (search != null && !search.isEmpty()) {
    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + search + "%")));
}
```
#### Dòng 1: `if (search != null && !search.isEmpty())`
- **Mục đích**: Kiểm tra xem chuỗi `search` có hợp lệ để thực hiện tìm kiếm hay không.
- **Logic**:
    - `search != null`: Đảm bảo rằng `search` khác `null` để tránh lỗi khi truy cập giá trị `null`.
    - `!search.isEmpty()`: Đảm bảo rằng chuỗi tìm kiếm không rỗng.

> **Ví dụ**: Nếu `search` là `"Laptop"`, câu lệnh `if` sẽ cho phép mã bên trong thực thi vì `search` không phải `null` và không rỗng.

#### Dòng 2: `predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + search + "%")));`
- **Mục đích**: Thêm điều kiện lọc để tìm kiếm các bài viết (`Article`) có tiêu đề (`title`) chứa chuỗi `search`.
- **Chi tiết**:
    - `root.get("title")`: Lấy cột `title` từ bảng `Article`. Cột này chứa tiêu đề của từng bài viết.
    - `criteriaBuilder.like(...)`: Tạo điều kiện tìm kiếm "like", cho phép so sánh tiêu đề với một mẫu chuỗi.
        - `"%"+search+"%"`: Thêm dấu `%` trước và sau `search`. Dấu `%` trong SQL `LIKE` thể hiện "bất kỳ chuỗi ký tự nào", nghĩa là tìm tất cả các tiêu đề chứa chuỗi `search` ở bất kỳ vị trí nào.
    - `criteriaBuilder.and(...)`: Thêm điều kiện "like" này vào danh sách `predicates` dưới dạng một `Predicate` sử dụng phép AND.

> **Ví dụ**: Nếu `search` là `"Laptop"`, điều kiện `criteriaBuilder.like(root.get("title"), "%Laptop%")` sẽ tạo một điều kiện để tìm tất cả các bài viết có tiêu đề chứa từ "Laptop".

- **Chức năng**: Đoạn mã trên thêm điều kiện tìm kiếm tiêu đề của các bài viết có chứa chuỗi `search`. Điều này giúp người dùng có thể tìm kiếm bài viết dựa trên các từ khóa họ quan tâm.
- **Hiệu quả**: Bằng cách sử dụng `like` và `Predicate`, truy vấn sẽ tìm kiếm các bài viết có tiêu đề phù hợp với từ khóa tìm kiếm, tạo ra một kết quả tìm kiếm linh hoạt và hiệu quả.

### 8. Lọc theo Giá

```java
if (priceLow != null && priceLow >= 0) {
    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceLow)));
}
if (priceHigh != null && priceHigh >= 0) {
    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceHigh)));
}
```
- **Chức năng**: Thêm các điều kiện lọc theo giá cho bài viết.
- **Giải thích chi tiết**:
    - Nếu `priceLow` được chỉ định và lớn hơn hoặc bằng 0, thêm điều kiện cho giá lớn hơn hoặc bằng `priceLow`.
    - Tương tự, nếu `priceHigh` được chỉ định và lớn hơn hoặc bằng 0, thêm điều kiện cho giá nhỏ hơn hoặc bằng `priceHigh`.
- **Ví dụ**: Nếu bạn muốn tìm các bài viết có giá từ 100 đến 500, đoạn mã này sẽ tạo điều kiện:
  ```sql
  SELECT * FROM Article WHERE price >= 100 AND price <= 500;
  ```

### 9. Kết hợp các Predicate


Hãy phân tích chi tiết đoạn mã sau:

```java
return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
```

### Giải thích từng phần

#### 1. `predicates.toArray(new Predicate[predicates.size()])`
- **Mục đích**: Chuyển danh sách `predicates` thành một mảng `Predicate`.
- **Chi tiết**:
    - `predicates` là một danh sách các điều kiện lọc (`Predicate`) đã được xây dựng trong đoạn mã trước.
    - `predicates.toArray(new Predicate[predicates.size()])`: Tạo một mảng `Predicate` từ danh sách `predicates`. Kích thước mảng được khởi tạo bằng số phần tử trong `predicates`.

> **Ví dụ**: Nếu `predicates` chứa 3 điều kiện lọc, `predicates.toArray(...)` sẽ tạo ra một mảng `Predicate` gồm 3 phần tử, mỗi phần tử là một điều kiện lọc.

#### 2. `criteriaBuilder.and(...)`
- **Mục đích**: Kết hợp các `Predicate` trong mảng thành một điều kiện duy nhất bằng phép `AND`.
- **Chi tiết**:
    - `criteriaBuilder.and(...)` tạo ra một `Predicate` mới từ các `Predicate` trong mảng, áp dụng phép `AND` để kết hợp tất cả điều kiện.
    - **Hiệu quả**: Khi thực thi truy vấn, các điều kiện `AND` này yêu cầu tất cả điều kiện trong mảng phải đúng để một `Article` (bài viết) được trả về trong kết quả.

> **Ví dụ**: Giả sử `predicates` chứa ba điều kiện:
- `price >= 100`
- `size IN ("S", "M", "L")`
- `brand = "Nike"`

Khi sử dụng `criteriaBuilder.and(...)`, truy vấn cuối cùng sẽ tìm các bài viết có giá từ 100 trở lên, kích cỡ là "S", "M", hoặc "L", và thuộc thương hiệu "Nike".

#### 3. `return`
- **Mục đích**: Trả về `Predicate` kết hợp tất cả điều kiện trong `predicates` bằng phép `AND`.

> **Hiệu quả**: `toPredicate` sẽ trả về một điều kiện duy nhất, đại diện cho tất cả tiêu chí lọc mà người dùng cung cấp. Điều này giúp truy vấn lọc các bài viết theo yêu cầu một cách chính xác và tối ưu.

- **Chức năng**: Dòng mã này tổng hợp tất cả điều kiện `Predicate` trong `predicates` thành một `Predicate` duy nhất, áp dụng phép `AND`.
- **Kết quả**: Khi các điều kiện được áp dụng, chỉ các bài viết (`Article`) đáp ứng tất cả điều kiện trong `predicates` mới được trả về.

### Kết luận

Class `ArticleSpecification` cho phép xây dựng các truy vấn động và linh hoạt để tìm kiếm

các bài viết theo nhiều tiêu chí khác nhau như giá, kích cỡ, danh mục, thương hiệu và từ khóa tìm kiếm. Việc sử dụng `Specification` trong Spring Data JPA giúp làm giảm lượng mã cần thiết cho việc tạo các truy vấn và dễ dàng mở rộng trong tương lai.