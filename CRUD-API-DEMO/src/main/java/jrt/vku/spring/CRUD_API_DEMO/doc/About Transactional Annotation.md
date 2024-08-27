`@Transactional` là một annotation trong Spring Framework dùng để quản lý các giao dịch (transactions) trong ứng dụng. Khi một phương thức hoặc một lớp được đánh dấu với `@Transactional`, Spring sẽ tự động quản lý giao dịch cho phương thức đó, đảm bảo rằng tất cả các thao tác trong phương thức được thực hiện trong cùng một giao dịch. Nếu có bất kỳ ngoại lệ nào xảy ra trong quá trình thực thi, giao dịch sẽ bị rollback (khôi phục lại trạng thái ban đầu) để đảm bảo tính toàn vẹn của dữ liệu.

### Giải thích chi tiết

- **Giao dịch (Transaction)**: Giao dịch là một đơn vị công việc mà tất cả các thao tác phải được thực hiện thành công hoặc không thực hiện gì cả. Trong cơ sở dữ liệu, một giao dịch thường bao gồm nhiều thao tác CRUD (Create, Read, Update, Delete) và phải tuân theo các thuộc tính ACID (Atomicity, Consistency, Isolation, Durability).

- **Tính năng của `@Transactional`**:
    - **Atomicity**: Đảm bảo tất cả các thao tác trong giao dịch đều thành công hoặc tất cả đều bị hủy bỏ (rollback) nếu có lỗi xảy ra.
    - **Isolation**: Đảm bảo các giao dịch khác không can thiệp vào giao dịch hiện tại.
    - **Propagation**: Quản lý cách giao dịch được mở rộng hoặc chia sẻ giữa các phương thức khi chúng gọi lẫn nhau.
    - **Rollback**: Tự động rollback giao dịch khi có ngoại lệ xảy ra, có thể cấu hình để rollback cho các loại ngoại lệ cụ thể.

### Ví dụ thực tế

Giả sử bạn đang phát triển một hệ thống ngân hàng và cần viết mã để xử lý việc chuyển tiền giữa hai tài khoản. Bạn cần đảm bảo rằng cả việc rút tiền từ tài khoản nguồn và nạp tiền vào tài khoản đích phải được thực hiện trong cùng một giao dịch. Nếu một trong hai thao tác thất bại, toàn bộ giao dịch phải bị hủy bỏ.

#### 1. Lớp Entity

```java
package com.example.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private Double balance;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
```

#### 2. Lớp DAO (Data Access Object)

```java
package com.example.bank.dao;

import com.example.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);
}
```

#### 3. Lớp Service

```java
package com.example.bank.service;

import com.example.bank.dao.AccountDAO;
import com.example.bank.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {

    private final AccountDAO accountDAO;

    @Autowired
    public BankService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Transactional
    public void transferMoney(String fromAccount, String toAccount, Double amount) {
        Account sourceAccount = accountDAO.findByAccountNumber(fromAccount);
        Account targetAccount = accountDAO.findByAccountNumber(toAccount);

        if (sourceAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in source account.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        // Nếu có lỗi xảy ra sau đây, tất cả thay đổi sẽ bị rollback
        accountDAO.save(sourceAccount);
        accountDAO.save(targetAccount);
    }
}
```

#### 4. Lớp Controller

```java
package com.example.bank.controller;

import com.example.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestParam String fromAccount,
                                @RequestParam String toAccount,
                                @RequestParam Double amount) {
        bankService.transferMoney(fromAccount, toAccount, amount);
        return "Transfer successful!";
    }
}
```

### Giải thích

1. **Lớp `BankService`**: Phương thức `transferMoney` được đánh dấu với `@Transactional`, nghĩa là tất cả các thao tác trong phương thức này (rút tiền từ tài khoản nguồn, nạp tiền vào tài khoản đích) sẽ được thực hiện trong cùng một giao dịch. Nếu xảy ra bất kỳ lỗi nào trong quá trình xử lý, như không đủ tiền trong tài khoản nguồn, giao dịch sẽ bị rollback, và không có thay đổi nào được áp dụng vào cơ sở dữ liệu.

2. **Quản lý giao dịch**: Spring sẽ tự động bắt đầu một giao dịch khi phương thức `transferMoney` được gọi. Nếu không có lỗi xảy ra, giao dịch sẽ được commit (xác nhận) và các thay đổi sẽ được lưu vào cơ sở dữ liệu. Nếu có lỗi xảy ra, giao dịch sẽ bị rollback và cơ sở dữ liệu sẽ trở về trạng thái ban đầu, đảm bảo không có giao dịch nào bị thực hiện một cách nửa vời.

### Kết luận

`@Transactional` là một công cụ mạnh mẽ trong Spring để đảm bảo tính toàn vẹn dữ liệu khi thực hiện các thao tác nghiệp vụ phức tạp. Nó giúp bạn quản lý giao dịch một cách dễ dàng và hiệu quả, giảm thiểu rủi ro lỗi trong hệ thống, và đảm bảo rằng dữ liệu luôn nhất quán ngay cả khi có lỗi xảy ra.