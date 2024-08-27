`TypedQuery` và `Query` là hai interface trong Java Persistence API (JPA) dùng để thực hiện các truy vấn đối với cơ sở dữ liệu. Chúng có một số khác biệt quan trọng mà bạn cần hiểu để biết khi nào nên sử dụng loại nào.

### 1. **Query**

- **Mô tả**: `Query` là một interface tổng quát hơn, cho phép bạn thực hiện các truy vấn JPQL (Java Persistence Query Language), SQL gốc (native SQL), hoặc các truy vấn động.
- **Đặc điểm**: `Query` không bắt buộc phải chỉ định kiểu dữ liệu trả về, vì vậy nó có thể trả về các kết quả dưới dạng danh sách của các đối tượng không được gõ chặt (unchecked).
- **Sử dụng**: Khi bạn không biết trước hoặc không quan tâm đến kiểu dữ liệu cụ thể của kết quả trả về, hoặc khi bạn muốn thực hiện một truy vấn động với các điều kiện không thể xác định trước.
- **Ví dụ**:
  ```java
  Query query = entityManager.createQuery("SELECT s.firstName, s.lastName FROM Student s WHERE s.age > :age");
  query.setParameter("age", 20);
  List<Object[]> students = query.getResultList();

  for (Object[] student : students) {
      System.out.println("First Name: " + student[0] + ", Last Name: " + student[1]);
  }
  ```

### 2. **TypedQuery<T>**

- **Mô tả**: `TypedQuery` là một interface con của `Query`, được sử dụng khi bạn muốn thực hiện một truy vấn và biết trước kiểu dữ liệu của kết quả trả về. `TypedQuery` yêu cầu bạn phải chỉ định kiểu dữ liệu cụ thể (generic type `T`) mà truy vấn sẽ trả về.
- **Đặc điểm**: `TypedQuery` cung cấp khả năng kiểm tra kiểu (type-checking) ở thời gian biên dịch (compile-time), giúp tránh các lỗi tiềm ẩn khi chuyển đổi kiểu dữ liệu.
- **Sử dụng**: Khi bạn biết trước kiểu dữ liệu của kết quả trả về và muốn đảm bảo an toàn kiểu (type safety).
- **Ví dụ**:
  ```java
  TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.age > :age", Student.class);
  query.setParameter("age", 20);
  List<Student> students = query.getResultList();

  for (Student student : students) {
      System.out.println(student.getFirstName() + " " + student.getLastName());
  }
  ```

### **So sánh `Query` và `TypedQuery`**

- **Type Safety (An toàn kiểu)**:
    - `TypedQuery` an toàn kiểu hơn, vì kiểu dữ liệu được chỉ định rõ ràng và được kiểm tra ở thời gian biên dịch.
    - `Query` không an toàn kiểu, vì kết quả có thể được trả về dưới dạng danh sách của các đối tượng kiểu `Object` hoặc các đối tượng không xác định rõ kiểu.

- **Sử dụng**:
    - **Sử dụng `TypedQuery`**: Khi bạn biết trước kiểu dữ liệu mà truy vấn sẽ trả về (ví dụ: `Student`, `Order`, `Product`), và muốn đảm bảo an toàn kiểu.
    - **Sử dụng `Query`**: Khi truy vấn của bạn trả về nhiều kiểu dữ liệu khác nhau hoặc kết quả không thể được xác định rõ ràng trước (ví dụ: khi truy vấn trả về kết quả hỗn hợp từ nhiều bảng).

### **Khi nào sử dụng `Query` và `TypedQuery`**

- **Sử dụng `Query`**:
    - Khi bạn thực hiện truy vấn gốc SQL (native query).
    - Khi truy vấn trả về các kiểu dữ liệu hỗn hợp hoặc không xác định trước.
    - Khi bạn muốn thực hiện một truy vấn động mà kết quả có thể thay đổi tùy thuộc vào điều kiện đầu vào.

- **Sử dụng `TypedQuery`**:
    - Khi bạn muốn truy vấn với kết quả trả về là các thực thể cụ thể hoặc các đối tượng có kiểu dữ liệu rõ ràng.
    - Khi bạn muốn kiểm tra an toàn kiểu trong quá trình biên dịch.

### **Ví dụ thực tế**

- **Ví dụ với `Query`**:
  ```java
  Query query = entityManager.createNativeQuery("SELECT * FROM students WHERE age > :age", Student.class);
  query.setParameter("age", 20);
  List<Student> students = query.getResultList();

  for (Student student : students) {
      System.out.println(student.getFirstName() + " " + student.getLastName());
  }
  ```

- **Ví dụ với `TypedQuery`**:
  ```java
  TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.age > :age", Student.class);
  query.setParameter("age", 20);
  List<Student> students = query.getResultList();

  for (Student student : students) {
      System.out.println(student.getFirstName() + " " + student.getLastName());
  }
  ```

Như vậy, việc chọn sử dụng `Query` hay `TypedQuery` phụ thuộc vào yêu cầu cụ thể của bạn về kiểu dữ liệu và mức độ kiểm soát an toàn kiểu mà bạn cần trong ứng dụng của mình.