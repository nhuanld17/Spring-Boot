Dưới đây là bản dịch của bài viết về **Spring Data JPA và Entity Graphs**:

---

**Spring Data JPA và Entity Graphs**

1. **Tổng Quan**  
   Đơn giản mà nói, Entity Graphs là một cách khác để mô tả một truy vấn trong JPA 2.1. Chúng ta có thể sử dụng chúng để xây dựng các truy vấn có hiệu suất tốt hơn. Trong bài hướng dẫn này, chúng ta sẽ học cách triển khai Entity Graphs với Spring Data JPA thông qua một ví dụ đơn giản.

2. **Các Entity**  
   Trước tiên, hãy tạo một mô hình gọi là `Item`, mô hình này có nhiều đặc điểm (characteristics):

```java
@Entity
public class Item {
    
    @Id
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "item")
    private List<Characteristic> characteristics = new ArrayList<>();

    // getters and setters
}
```

Bây giờ, hãy định nghĩa entity `Characteristic`:

```java
@Entity
public class Characteristic {

    @Id
    private Long id;
    private String type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;

    //Getters and Setters
}
```

Như chúng ta thấy trong đoạn mã, cả trường `characteristics` trong entity `Item` và trường `item` trong entity `Characteristic` đều được tải một cách lười biếng (lazy loading) bằng cách sử dụng tham số fetch. Vì vậy, mục tiêu của chúng ta ở đây là tải chúng một cách tham lam (eager) trong thời gian chạy.

3. **Entity Graphs**  
   Trong Spring Data JPA, chúng ta có thể định nghĩa một entity graph bằng cách sử dụng sự kết hợp của các chú thích `@NamedEntityGraph` và `@EntityGraph`. Hoặc, chúng ta cũng có thể định nghĩa các entity graph tạm thời (ad-hoc) chỉ với tham số `attributePaths` của chú thích `@EntityGraph`.

Hãy xem cách thực hiện.

3.1. **Với @NamedEntityGraph**  
Đầu tiên, chúng ta có thể sử dụng chú thích `@NamedEntityGraph` của JPA trực tiếp trên entity `Item`:

```java
@Entity
@NamedEntityGraph(name = "Item.characteristics",
    attributeNodes = @NamedAttributeNode("characteristics")
)
public class Item {
    //...
}
```

Sau đó, chúng ta có thể gắn chú thích `@EntityGraph` vào một trong các phương thức của repository:

```java
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    @EntityGraph(value = "Item.characteristics")
    Item findByName(String name);
}
```

Như đoạn mã cho thấy, chúng ta đã truyền tên của entity graph mà chúng ta đã tạo trước đó trên entity `Item` vào chú thích `@EntityGraph`. Khi chúng ta gọi phương thức, đó là truy vấn mà Spring Data sẽ sử dụng.  
Giá trị mặc định của tham số type trong chú thích `@EntityGraph` là `EntityGraphType.FETCH`. Khi chúng ta sử dụng điều này, mô-đun Spring Data sẽ áp dụng chiến lược `FetchType.EAGER` cho các thuộc tính được chỉ định. Còn cho những thuộc tính khác, chiến lược `FetchType.LAZY` sẽ được áp dụng.

Vì vậy, trong trường hợp của chúng ta, thuộc tính `characteristics` sẽ được tải một cách tham lam, mặc dù chiến lược mặc định của chú thích `@OneToMany` là lười biếng.

Một điều cần lưu ý là nếu chiến lược tải đã định nghĩa là EAGER, thì chúng ta không thể thay đổi hành vi của nó thành LAZY. Điều này được thiết kế như vậy vì các hoạt động tiếp theo có thể cần dữ liệu đã được tải tham lam vào một thời điểm sau đó trong quá trình thực thi.

3.2. **Không có @NamedEntityGraph**  
Hoặc, chúng ta cũng có thể định nghĩa một entity graph tạm thời, với `attributePaths`.  
Hãy thêm một entity graph tạm thời vào `CharacteristicsRepository` của chúng ta để tải tham lam cha `Item` của nó:

```java
public interface CharacteristicsRepository 
  extends JpaRepository<Characteristic, Long> {
    
    @EntityGraph(attributePaths = {"item"})
    Characteristic findByType(String type);    
}
```

Điều này sẽ tải thuộc tính `item` của entity `Characteristic` một cách tham lam, mặc dù entity của chúng ta khai báo một chiến lược tải lười biếng cho thuộc tính này.  
Điều này rất tiện lợi vì chúng ta có thể định nghĩa entity graph trực tiếp thay vì tham chiếu đến một entity graph có tên đã tồn tại.

4. **Trường Hợp Kiểm Tra**  
   Bây giờ mà chúng ta đã định nghĩa các entity graph, hãy tạo một trường hợp kiểm tra để xác minh:

```java
@DataJpaTest
@RunWith(SpringRunner.class)
@Sql(scripts = "/entitygraph-data.sql")
public class EntityGraphIntegrationTest {
   
    @Autowired
    private ItemRepository itemRepo;
    
    @Autowired
    private CharacteristicsRepository characteristicsRepo;
    
    @Test
    public void givenEntityGraph_whenCalled_shouldReturnDefinedFields() {
        Item item = itemRepo.findByName("Table");
        assertThat(item.getId()).isEqualTo(1L);
    }
    
    @Test
    public void givenAdhocEntityGraph_whenCalled_shouldReturnDefinedFields() {
        Characteristic characteristic = characteristicsRepo.findByType("Rigid");
        assertThat(characteristic.getId()).isEqualTo(1L);
    }
}
```

Bài kiểm tra đầu tiên sẽ sử dụng entity graph được định nghĩa bằng cách sử dụng chú thích `@NamedEntityGraph`.

Hãy xem truy vấn SQL được tạo ra bởi Hibernate:

```sql
select 
    item0_.id as id1_10_0_,
    characteri1_.id as id1_4_1_,
    item0_.name as name2_10_0_,
    characteri1_.item_id as item_id3_4_1_,
    characteri1_.type as type2_4_1_,
    characteri1_.item_id as item_id3_4_0__,
    characteri1_.id as id1_4_0__
from 
    item item0_ 
left outer join 
    characteristic characteri1_ 
on 
    item0_.id=characteri1_.item_id 
where 
    item0_.name=?
```

Để so sánh, hãy loại bỏ chú thích `@EntityGraph` khỏi repository và kiểm tra truy vấn:

```sql
select 
    item0_.id as id1_10_,
    item0_.name as name2_10_ 
from 
    item item0_ 
where 
    item0_.name=?
```

Từ những truy vấn này, chúng ta có thể thấy rõ rằng truy vấn được tạo ra mà không có chú thích `@EntityGraph` không tải bất kỳ thuộc tính nào của entity `Characteristic`. Kết quả là, nó chỉ tải entity `Item`.

Cuối cùng, hãy so sánh truy vấn Hibernate của bài kiểm tra thứ hai với chú thích `@EntityGraph`:

```sql
select 
    characteri0_.id as id1_4_0_,
    item1_.id as id1_10_1_,
    characteri0_.item_id as item_id3_4_0_,
    characteri0_.type as type2_4_0_,
    item1_.name as name2_10_1_ 
from 
    characteristic characteri0_ 
left outer join 
    item item1_ 
on 
    characteri0_.item_id=item1_.id 
where 
    characteri0_.type=?
```

Và truy vấn mà không có chú thích `@EntityGraph`:

```sql
select 
    characteri0_.id as id1_4_,
    characteri0_.item_id as item_id3_4_,
    characteri0_.type as type2_4_ 
from 
    characteristic characteri0_ 
where 
    characteri0_.type=?
```

5. **Kết Luận**  
   Trong bài hướng dẫn này, chúng ta đã học cách sử dụng Entity Graphs của JPA trong Spring Data. Với Spring Data, chúng ta có thể tạo nhiều phương thức repository khác nhau liên kết với các entity graph khác nhau.

--- 

Hy vọng rằng bản dịch này giúp bạn hiểu rõ hơn về chủ đề này!