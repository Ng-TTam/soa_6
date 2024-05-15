package com.example.tax.ServiceTest;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.tax.Entity.Tax;
import com.example.tax.Service.TaxService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaxServiceTest {
    @Autowired
	TaxService taxService;

    @Autowired
    TransactionTemplate transactionTemplate;

    //Test trả về toàn bộ bản ghi
    @Test
    public void getAllTax_TestChuan(){
        List <Tax> list_tax = taxService.getAllTax();

        Assertions.assertEquals(7, list_tax.size());
    }

    //lưu cấu hình
    @Test
    public void updateTax_TestChuan1(){
        // Không có dữ liệu thay đổi
        List<Tax> listTax = taxService.getAllTax();

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertTrue(check);

                    //lấy ra kiểm tra
                    List<Tax> listTaxNew = taxService.getAllTax();
                    Assertions.assertEquals(listTaxNew, listTax);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void updateTax_TestChuan2(){
        // có 1 dữ liệu thay đổi
        List<Tax> listTax = taxService.getAllTax();
        listTax.get(0).setAmountLargest(10000);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertTrue(check);

                    //lấy ra kiểm tra
                    List<Tax> listTaxNew = taxService.getAllTax();
                    Assertions.assertEquals(listTaxNew, listTax);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void updateTax_TestChuan3(){
        // có nhiều hơn 1 dữ liệu thay đổi
        List<Tax> listTax = taxService.getAllTax();
        listTax.get(0).setAmountLargest(10000);
        listTax.get(1).setAmountLeast(10000);
        listTax.get(1).setAmountLargest(5000000);
        listTax.get(2).setAmountLeast(5000000);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertTrue(check);

                    //lấy ra kiểm tra
                    List<Tax> listTaxNew = taxService.getAllTax();
                    Assertions.assertEquals(listTaxNew, listTax);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void updateTax_TestChuan4(){
        // dữ liệu thay đổi bị sai amountLeast > AmountLargest
        List<Tax> listTax = taxService.getAllTax();
        listTax.get(1).setAmountLargest(10000);
        listTax.get(1).setAmountLeast(100000);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertFalse(check);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void updateTax_TestChuan5(){
        // dữ liệu thay đổi bị sai amountLeast = AmountLargest
        List<Tax> listTax = taxService.getAllTax();
        listTax.get(0).setAmountLargest(10000);
        listTax.get(0).setAmountLeast(10000);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertFalse(check);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void updateTax_TestChuan6(){
        // dữ liệu thay đổi bị sai mức thuế sau lớn hơn mức thuế trước
        List<Tax> listTax = taxService.getAllTax();
        listTax.get(1).setPercentOfTax(10);
        listTax.get(2).setPercentOfTax(5);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertFalse(check);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void updateTax_TestChuan7(){
        // dữ liệu thay đổi bị sai mức thuế cuối = 100%
        List<Tax> listTax = taxService.getAllTax();
        listTax.get(6).setPercentOfTax(100);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertFalse(check);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void updateTax_TestChuan8(){
        // dữ liệu thay đổi bị sai mức thuế cuối > 100%
        List<Tax> listTax = taxService.getAllTax();
        listTax.get(6).setPercentOfTax(101);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Boolean check = taxService.updateTax(listTax);
                    Assertions.assertFalse(check);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
