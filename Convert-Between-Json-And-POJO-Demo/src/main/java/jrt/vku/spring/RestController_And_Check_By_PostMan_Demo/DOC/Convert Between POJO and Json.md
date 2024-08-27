Chuyển đổi giữa JSON và Java POJO là một kỹ thuật phổ biến trong lập trình Java, đặc biệt khi làm việc với các API hoặc giao tiếp dữ liệu giữa các hệ thống. Thông thường, thư viện **Jackson** hoặc **Gson** được sử dụng để thực hiện việc chuyển đổi này.

### Sử Dụng Jackson
Jackson là một thư viện mạnh mẽ và phổ biến để xử lý JSON trong Java.

#### 1. Chuyển từ JSON sang Java POJO (Deserialization)
```java
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        String jsonString = "{\"name\":\"John\", \"age\":30}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(jsonString, Student.class);

            System.out.println(student.getName());  // Output: John
            System.out.println(student.getAge());   // Output: 30
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Student {
    private String name;
    private int age;

    // Constructors, Getters, Setters, toString()
    // ...
}
```

#### 2. Chuyển từ Java POJO sang JSON (Serialization)
```java
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("John", 30);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(student);

            System.out.println(jsonString);  // Output: {"name":"John","age":30}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Student {
    private String name;
    private int age;

    // Constructors, Getters, Setters, toString()
    // ...
}
```

### Sử Dụng Gson
Gson là một thư viện khác cũng rất phổ biến để chuyển đổi giữa JSON và Java.

#### 1. Chuyển từ JSON sang Java POJO (Deserialization)
```java
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        String jsonString = "{\"name\":\"John\", \"age\":30}";

        Gson gson = new Gson();
        Student student = gson.fromJson(jsonString, Student.class);

        System.out.println(student.getName());  // Output: John
        System.out.println(student.getAge());   // Output: 30
    }
}

class Student {
    private String name;
    private int age;

    // Constructors, Getters, Setters, toString()
    // ...
}
```

#### 2. Chuyển từ Java POJO sang JSON (Serialization)
```java
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("John", 30);

        Gson gson = new Gson();
        String jsonString = gson.toJson(student);

        System.out.println(jsonString);  // Output: {"name":"John","age":30}
    }
}

class Student {
    private String name;
    private int age;

    // Constructors, Getters, Setters, toString()
    // ...
}
```

### Lưu Ý
- **Jackson** có nhiều tính năng mạnh mẽ hơn và hỗ trợ tốt hơn cho các trường hợp phức tạp (ví dụ: xử lý các thuộc tính tùy chỉnh, định dạng ngày tháng).
- **Gson** thường được ưa chuộng vì nhẹ và dễ sử dụng.

Tùy thuộc vào yêu cầu cụ thể của dự án mà bạn có thể chọn một trong hai thư viện này để sử dụng.