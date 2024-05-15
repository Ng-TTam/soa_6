package com.example.tax.ServiceTest;

import com.example.tax.Entity.Account;
import com.example.tax.Service.AccountService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AcccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    public void checkAccount_TestChuan1(){
        // trường hợp user và password đều đúng
        String user = "nhom6";
        String password = "123";

        boolean check = accountService.checkAccount(user, password);
        Assertions.assertTrue(check);
    }

    @Test
    public void checkAccount_TestChuan2(){
        // trường hợp user sai và password đúng
        String user = "nhom10";
        String password = "123";

        boolean check = accountService.checkAccount(user, password);
        Assertions.assertFalse(check);
    }

    @Test
    public void checkAccount_TestChuan3(){
        // trường hợp user đúng và password sai
        String user = "nhom6";
        String password = "12345";

        boolean check = accountService.checkAccount(user, password);
        Assertions.assertFalse(check);
    }

    @Test
    public void getAccountLogin_TestChuan1(){
        //trường hợp người dùng tồn tại
        String user = "nhom6";
        String password = "123";

        Account account = accountService.getAccountLogin(user, password);
        Assertions.assertEquals(user, account.getUsername());
        Assertions.assertEquals(password, account.getPassword());
    }

    @Test
    public void getAccountLogin_TestChuan2(){
        //trường hợp người dùng không tồn tại
        String user = "nhom6";
        String password = "12345";

        Account account = accountService.getAccountLogin(user, password);
        Assertions.assertNull(account);
    }
}
