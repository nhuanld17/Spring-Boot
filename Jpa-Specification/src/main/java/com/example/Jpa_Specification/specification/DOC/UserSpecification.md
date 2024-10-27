Dưới đây là một giải thích chi tiết về class `UserSpecification`, với từng phần được mô tả rõ ràng và dễ hiểu. Mục tiêu của class này là tạo ra các tiêu chí tìm kiếm có thể được sử dụng trong truy vấn đến cơ sở dữ liệu cho đối tượng `User` trong một ứng dụng Spring.

### 1. Khai Báo Class và Thuộc Tính

```java
public class UserSpecification implements Specification<User> {
	private SearchCriteria criteria;
	
	public UserSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}
```

- **`public class UserSpecification implements Specification<User>`**: Class này implements `Specification<User>`, cho phép nó tạo ra các tiêu chí truy vấn cho đối tượng `User`.

- **`private SearchCriteria criteria;`**: Đối tượng `SearchCriteria` chứa thông tin về các tiêu chí tìm kiếm, bao gồm tên trường, giá trị và toán tử (như `>`, `<`, `:`).

- **`public UserSpecification(SearchCriteria criteria)`**: Constructor nhận vào một `SearchCriteria` và gán nó cho thuộc tính `criteria`. Điều này cho phép class biết phải tìm kiếm cái gì và bằng cách nào.

### 2. Phương Thức `toPredicate`

```java
@Override
public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
```

- **`toPredicate`**: Phương thức này là nơi mà logic để chuyển đổi `SearchCriteria` thành một đối tượng `Predicate` được định nghĩa. `Predicate` là một điều kiện cho truy vấn, được sử dụng để lọc kết quả.
- **`Root<User> root`**: Đại diện cho thực thể `User` trong truy vấn.
- **`CriteriaQuery<?> query`**: Đại diện cho truy vấn hiện tại.
- **`CriteriaBuilder builder`**: Cung cấp các phương thức để xây dựng các đối tượng `Predicate`.

### 3. Các Điều Kiện Tìm Kiếm

Dưới đây là ba điều kiện chính mà phương thức này kiểm tra:

#### 3.1. Toán Tử Lớn Hơn (`>`)

```java
if (criteria.getOperation().equalsIgnoreCase(">")) {
	return builder.greaterThan(
			root.<String>get(criteria.getKey()), (String) criteria.getValue()
	);
}
```

- **`criteria.getOperation().equalsIgnoreCase(">")`**: Kiểm tra xem toán tử có phải là `>` hay không.
- **`builder.greaterThan(...)`**: Tạo một điều kiện so sánh lớn hơn cho trường tương ứng.
- **`root.<String>get(criteria.getKey())`**: Lấy giá trị của trường trong thực thể `User`.
- **`(String) criteria.getValue()`**: Ép kiểu giá trị tìm kiếm thành `String`.

**Ví dụ**: Nếu `criteria` có `key` là `"age"`, `value` là `30`, thì phương thức sẽ tạo ra điều kiện `age > 30`.

#### 3.2. Toán Tử Nhỏ Hơn (`<`)

```java
else if (criteria.getOperation().equalsIgnoreCase("<")) {
	return builder.lessThan(
			root.<String>get(criteria.getKey()), (String) criteria.getValue()
	);
}
```

- **`criteria.getOperation().equalsIgnoreCase("<")`**: Kiểm tra toán tử có phải là `<` hay không.
- **`builder.lessThan(...)`**: Tạo một điều kiện so sánh nhỏ hơn cho trường tương ứng.

**Ví dụ**: Nếu `criteria` có `key` là `"age"`, `value` là `30`, phương thức sẽ tạo ra điều kiện `age < 30`.

#### 3.3. Toán Tử Bằng (`:`)

```java
else if (criteria.getOperation().equalsIgnoreCase(":")) {
	if (root.get(criteria.getKey()).getJavaType() == String.class) {
		return builder.like(
				root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%"
		);
	} else {
		return builder.equal(root.get(criteria.getKey()), criteria.getValue());
	}
}
```

- **`criteria.getOperation().equalsIgnoreCase(":")`**: Kiểm tra toán tử có phải là `:` hay không.
- **`root.get(criteria.getKey()).getJavaType() == String.class`**: Kiểm tra xem trường có kiểu là `String` hay không.
- **`builder.like(...)`**: Nếu trường là `String`, tạo điều kiện so sánh tương tự như SQL `LIKE`, cho phép tìm kiếm chuỗi con.
- **`builder.equal(...)`**: Nếu trường không phải là `String`, tạo điều kiện so sánh bằng.

**Ví dụ**:
- Nếu `criteria` có `key` là `"name"`, `value` là `"John"`, phương thức sẽ tạo điều kiện tìm kiếm `name LIKE '%John%'`.
- Nếu `criteria` có `key` là `"age"`, `value` là `30`, phương thức sẽ tạo điều kiện `age = 30`.

### 4. Trả Về `null` Nếu Không Khớp

```java
return null;
```

Nếu không có điều kiện nào khớp, phương thức sẽ trả về `null`, có nghĩa là không có tiêu chí nào để áp dụng cho truy vấn.

### Ví Dụ Tổng Quan

Giả sử chúng ta có class `SearchCriteria` được định nghĩa như sau:

```java
public class SearchCriteria {
	private String key;
	private String operation;
	private Object value;

	public SearchCriteria(String key, String operation, Object value) {
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	// Getters
	public String getKey() { return key; }
	public String getOperation() { return operation; }
	public Object getValue() { return value; }
}
```

#### Ví dụ Cách Hoạt Động của `UserSpecification`

Giả sử chúng ta có một yêu cầu tìm kiếm từ client với `SearchCriteria` như sau:

- `criteria1`: `new SearchCriteria("age", ">", 25)`
- `criteria2`: `new SearchCriteria("name", ":", "John")`

Khi tạo các `UserSpecification` từ các `SearchCriteria` này:

```java
Specification<User> spec1 = new UserSpecification(criteria1);
Specification<User> spec2 = new UserSpecification(criteria2);
```

Khi gọi phương thức `toPredicate`, nó sẽ lần lượt tạo ra các `Predicate` cho từng tiêu chí:

1. Đối với `criteria1` (age > 25), `toPredicate` sẽ trả về một điều kiện so sánh lớn hơn cho trường `age`.
2. Đối với `criteria2` (name: "John"), `toPredicate` sẽ trả về điều kiện so sánh bằng cho trường `name`, với điều kiện `LIKE`.

Cuối cùng, các `Predicate` này có thể được kết hợp lại trong một truy vấn để tìm kiếm người dùng trong cơ sở dữ liệu thỏa mãn tất cả các điều kiện tìm kiếm.

### Kết Luận

Class `UserSpecification` cung cấp một cách linh hoạt để xây dựng các tiêu chí tìm kiếm cho thực thể `User` trong ứng dụng Spring. Bằng cách sử dụng các toán tử khác nhau và điều kiện so sánh, bạn có thể dễ dàng thực hiện các truy vấn phức tạp và tìm kiếm thông tin theo cách phù hợp với yêu cầu của người dùng.