### `@Transient` Annotation trong Hibernate

#### Giới thiệu:
Trong Hibernate, annotation `@Transient` được sử dụng để báo cho Hibernate biết rằng một thuộc tính nào đó của entity **không được ánh xạ (mapping)** với một cột trong bảng cơ sở dữ liệu. Điều này có nghĩa là thuộc tính đó sẽ **không được lưu trữ** hoặc **không tham gia** vào quá trình ghi hoặc đọc từ cơ sở dữ liệu.

#### Giải thích chi tiết:
- Mặc định, khi bạn định nghĩa một class (entity) trong Hibernate và mỗi thuộc tính trong class sẽ được ánh xạ (mapping) với các cột tương ứng trong bảng của cơ sở dữ liệu.
- Tuy nhiên, có những trường hợp bạn không muốn một số thuộc tính trong class của mình được lưu trữ trong cơ sở dữ liệu (vì nó có thể là thuộc tính tạm thời hoặc tính toán trong runtime). Để làm điều đó, bạn sử dụng annotation `@Transient`.

#### Cách sử dụng `@Transient`:
Khi bạn khai báo một thuộc tính trong entity với `@Transient`, Hibernate sẽ bỏ qua thuộc tính này trong quá trình xử lý dữ liệu (như lưu hoặc lấy từ cơ sở dữ liệu).

#### Ví dụ:
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Employee {

    @Id
    private Long id;
    private String name;
    private double salary;

    @Transient // Thuộc tính này không ánh xạ với cơ sở dữ liệu
    private double bonus; // Giá trị bonus chỉ dùng trong runtime, không lưu trữ trong DB

    // Constructor, getters, setters...

    public Employee(Long id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Tính toán bonus (ví dụ tạm thời)
    public double calculateBonus() {
        this.bonus = this.salary * 0.1; // 10% tiền lương là bonus
        return this.bonus;
    }

    // Getters và Setters
    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
```

#### Giải thích ví dụ:
- `Employee` là một entity đại diện cho bảng trong cơ sở dữ liệu với các cột như `id`, `name`, và `salary`.
- Thuộc tính `bonus` được đánh dấu bằng `@Transient`, vì vậy nó sẽ **không được lưu trữ** trong cơ sở dữ liệu. Thay vào đó, nó được tính toán dựa trên lương của nhân viên thông qua phương thức `calculateBonus()`.
- Khi dữ liệu của entity `Employee` được lưu vào cơ sở dữ liệu (thông qua Hibernate), chỉ có `id`, `name`, và `salary` được lưu. Thuộc tính `bonus` sẽ không được lưu trữ và cũng không được lấy từ cơ sở dữ liệu.

#### Khi nào sử dụng `@Transient`?
- Khi bạn có các thuộc tính chỉ mang tính chất **tạm thời**, không cần lưu trữ vào cơ sở dữ liệu.
- Khi có các giá trị **tính toán động** trong runtime và bạn không muốn những giá trị này chiếm không gian trong cơ sở dữ liệu.
- Các thuộc tính như bộ đếm, các biến hỗ trợ cho logic trong ứng dụng mà không cần ánh xạ vào bảng cơ sở dữ liệu.

#### Lưu ý:
- `@Transient` khác với các từ khóa `transient` trong Java chuẩn. Trong Java, từ khóa `transient` sẽ không cho phép thuộc tính đó được **serialize**. Trong khi `@Transient` trong Hibernate liên quan đến việc không lưu thuộc tính vào cơ sở dữ liệu.
- Tuy nhiên, cả `@Transient` và `transient` trong Java có thể được sử dụng cùng nhau nếu muốn thuộc tính không được ánh xạ vào cơ sở dữ liệu và cũng không được serialize.

#### Kết luận:
Annotation `@Transient` rất hữu ích khi bạn có những thuộc tính chỉ cần dùng trong bộ nhớ (RAM) mà không cần lưu trữ chúng vào cơ sở dữ liệu. Điều này giúp tối ưu hóa không gian lưu trữ cũng như tránh các thông tin không cần thiết xuất hiện trong cơ sở dữ liệu.