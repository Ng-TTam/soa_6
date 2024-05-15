package com.example.tax.ServiceTest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tax.Entity.Debt;
import com.example.tax.Repository.DebtRepository;
import com.example.tax.Service.DebtService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DebtServiceTest {
    private Debt debt;

    @Autowired
    private DebtService debtService;

    @Autowired
    private DebtRepository debtRepository;
    
    //Test func tính tổng tiền nợ thuế theo tháng và năm
    @Test
    public void getSumTaxMoney_TestChuan1(){
        // trường hợp tháng và năm đều hợp lệ, và có tổng nợ thuế > 0
        String month = "Tháng 7";
        int year = 2022;
        long expected = 2960000;
        long actual = debtService.getSumTaxMoney(month, year);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumTaxMoney_TestChuan2(){
        // trường hợp tháng và năm đều hợp lệ, và có tổng nợ thuế = 0
        String month = "Tháng 12";
        int year = 2024;
        long expected = 0;

        long actual = debtService.getSumTaxMoney(month, year);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumTaxMoney_TestChuan3(){
        // trường hợp tháng không hợp lệ, năm hợp lệ
        String month = "Tháng 13";
        int year = 2022;

        long expected = 0;

        long actual = debtService.getSumTaxMoney(month, year);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumTaxMoney_TestChuan4(){
        // trường hợp tháng hợp lệ, năm không hợp lệ
        String month = "Tháng 7";
        int year = 2025;

        long expected = 0;

        long actual = debtService.getSumTaxMoney(month, year);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumTaxMoney_TestChuan5(){
        // trường hợp tháng không hợp lệ, năm không hợp lệ
        String month = "Tháng 20";
        int year = 2030;

        long expected = 0;

        long actual = debtService.getSumTaxMoney(month, year);

        Assertions.assertEquals(expected, actual);
    }



    //====================================================================================
    //Test tổng tiền theo năm với mức thu nhập
    @Test
    public void getSumDebtByIncome_TestChuan1(){
        // trường hợp năm hợp lệ, mức thu nhập hợp lệ(min <= max) trả về kết quả > 0
        int year = 2022;
        long min = 800000;
        long max = 1300000;
        long expected = 8740000;
        long actual = debtService.getSumDebtByIncome(min, max, year);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumDebtByIncome_TestChuan2(){
        // trường hợp năm hợp lệ, mức thu nhập hợp lệ(min <= max ) trả về kết quả = 0
        int year = 2022;
        long min = 80000000;
        long max = 130000000;
        long expected = 0;
        long actual = debtService.getSumDebtByIncome(min, max, year);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumDebtByIncome_TestChuan3(){
        // trường hợp năm hợp lệ, mức thu nhập không hợp lệ(min > max)
        int year = 2022;
        long min = 8000000;
        long max = 130000;

        long expected = 0;
        long actual = debtService.getSumDebtByIncome(min, max, year);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumDebtByIncome_TestChuan4(){
        // trường hợp năm không hợp lệ, mức thu nhập hợp lệ(min <= max)
        int year = 2030;
        long min = 800000;
        long max = 1300000;

        long expected = 0;
        long actual = debtService.getSumDebtByIncome(min, max, year);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumDebtByIncome_TestChuan5(){
        // trường hợp năm không hợp lệ, mức thu nhập không hợp lệ(min > max)
        int year = 2030;
        long min = 8000000;
        long max = 1300000;

        long expected = 0;
        long actual = debtService.getSumDebtByIncome(min, max, year);
        Assertions.assertEquals(expected, actual);
    }


    //============================================================
    //Test tìm danh sách nợ thuế theo tỉnh và năm
    @Test
    public void getDeabtByAddressAndYear_TestChuan1(){
        // trường hợp danh sách có 1 người
        String address = "Điện Biên";
        int year = 2024;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYear(address, year);
        Assertions.assertNotNull(list_dt);
        Assertions.assertEquals(1, list_dt.size());
        Assertions.assertEquals(2024, list_dt.get(0).getTaxYear());
        Assertions.assertTrue(list_dt.get(0).getAddress().contains(address));
    }

    @Test
    public void getDeabtByAddressAndYear_TestChuan2(){
        // trường hợp danh sách có nhiều hơn 1 người
        String address = "Bắc Giang";
        int year = 2024;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYear(address, year);
        Assertions.assertNotNull(list_dt);
        Assertions.assertEquals(4, list_dt.size());
        for (int i = 0; i < 4; i++) {
            Assertions.assertEquals(2024, list_dt.get(i).getTaxYear());
            Assertions.assertTrue(list_dt.get(i).getAddress().contains(address));
        }
    }

    @Test
    public void getDeabtByAddressAndYear_TestChuan3(){
        // trường hợp danh sách có 0 người
        String address = "Hà Nam";
        int year = 2022;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYear(address, year);
        Assertions.assertEquals(0, list_dt.size());
    }

    @Test
    public void getDeabtByAddressAndYear_TestChuan4(){
        // trường hợp địa chỉ tồn tại, năm không hợp lệ
        String address = "Điện Biên";
        int year = 2040;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYear(address, year);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYear_TestChuan5(){
        // trường hợp địa chỉ không tồn tại, năm hợp lệ
        String address = "Hello World";
        int year = 2022;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYear(address, year);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYear_TestChuan6(){
        // trường hợp địa chỉ không tồn tại, năm không hợp lệ
        String address = "Hello World";
        int year = 2040;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYear(address, year);
        Assertions.assertEquals(0, list_dt.size());
    }


    //=========================================================================
    //Test tìm danh sách nợ thuế theo tỉnh, năm và tháng
    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan1(){
        // trường hợp danh sách có 1 người
        String address = "Điện Biên";
        String month = "Tháng 1";
        int year = 2024;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertNotNull(list_dt);
        Assertions.assertEquals(1, list_dt.size());
        Assertions.assertEquals(2024, list_dt.get(0).getTaxYear());
        Assertions.assertTrue(list_dt.get(0).getAddress().contains(address));
        Assertions.assertEquals(month, list_dt.get(0).getTaxPeriod());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan2(){
        // trường hợp danh sách có nhiều hơn 1 người
        String address = "Bắc Giang";
        String month = "Tháng 3";
        int year = 2024;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertNotNull(list_dt);
        Assertions.assertEquals(2, list_dt.size());
        for (int i = 0; i < 2; i++) {
            Assertions.assertEquals(year, list_dt.get(i).getTaxYear());
            Assertions.assertTrue(list_dt.get(i).getAddress().contains(address));
            Assertions.assertEquals(month, list_dt.get(i).getTaxPeriod());
        }
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan3(){
        // trường hợp danh sách có 0 người
        String address = "Hà Nam";
        String month = "Tháng 1";
        int year = 2022;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertEquals(0, list_dt.size());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan4(){
        // trường hợp địa chỉ tồn tại, tháng hợp lệ, năm không hợp lệ
        String address = "Điện Biên";
        String month = "Tháng 1";
        int year = 2040;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan5(){
        // trường hợp địa chỉ tồn tại, tháng không hợp lệ, năm hợp lệ
        String address = "Điện Biên";
        String month = "Tháng 1";
        int year = 2023;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan6(){
        // trường hợp địa chỉ tồn tại, tháng không hợp lệ, năm không hợp lệ
        String address = "Điện Biên";
        String month = "Tháng 14";
        int year = 2034;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan7(){
        // trường hợp địa chỉ không tồn tại, thánng và năm hợp lệ
        String address = "Hello World";
        String month = "Tháng 1";
        int year = 2022;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertEquals(0, list_dt.size());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan8(){
        // trường hợp địa chỉ tồn tại, tháng không hợp lệ, năm hợp lệ
        String address = "Hello World";
        String month = "Tháng 34";
        int year = 2023;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertEquals(0, list_dt.size());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeMonth_TestChuan9(){
        // trường hợp địa chỉ tồn tại, tháng và năm không hợp lệ
        String address = "Hello World";
        String month = "Tháng 34";
        int year = 2040;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
        Assertions.assertEquals(0, list_dt.size());
    }


    //=========================================================================
    //Test tìm danh sách nợ thuế theo tỉnh, năm và quý
    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan1(){
        // trường hợp danh sách có 1 người
        String address = "Lào Cai";
        String quarter = "Quý 2";
        int year = 2022;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertNotNull(list_dt);
        Assertions.assertEquals(1, list_dt.size());
        Assertions.assertEquals(year, list_dt.get(0).getTaxYear());
        Assertions.assertTrue(list_dt.get(0).getAddress().contains(address));
        Assertions.assertEquals(quarter, list_dt.get(0).getTaxPeriod());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan2(){
        // trường hợp danh sách có nhiều hơn 1 người
        String address = "Hà Nam";
        String quarter = "Quý 1";
        int year = 2023;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertNotNull(list_dt);
        Assertions.assertEquals(6, list_dt.size());
        for (int i = 0; i < 6; i++) {
            Assertions.assertEquals(year, list_dt.get(i).getTaxYear());
            Assertions.assertTrue(list_dt.get(i).getAddress().contains(address));
            Assertions.assertEquals(quarter, list_dt.get(i).getTaxPeriod());
        }
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan3(){
        // trường hợp danh sách có 0 người
        String address = "Hà Nội";
        String quarter = "Quý 4";
        int year = 2022;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertEquals(0, list_dt.size());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan4(){
        // trường hợp địa chỉ tồn tại, quý hợp lệ, năm không hợp lệ
        String address = "Điện Biên";
        String quarter = "Quý 1";
        int year = 2040;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan5(){
        // trường hợp địa chỉ tồn tại, quý không hợp lệ, năm hợp lệ
        String address = "Điện Biên";
        String quarter = "Quý 5";
        int year = 2023;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan6(){
        // trường hợp địa chỉ tồn tại, quý không hợp lệ, năm không hợp lệ
        String address = "Điện Biên";
        String quarter = "Quý 5";
        int year = 2034;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertEquals(0, list_dt.size());
        
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan7(){
        // trường hợp địa chỉ không tồn tại, quý và năm hợp lệ
        String address = "Hello World";
        String quarter = "Quý 1";
        int year = 2022;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertEquals(0, list_dt.size());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan8(){
        // trường hợp địa chỉ tồn tại, quý không hợp lệ, năm hợp lệ
        String address = "Hello World";
        String quarter = "Quý 34";
        int year = 2023;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertEquals(0, list_dt.size());
    }

    @Test
    public void getDeabtByAddressAndYearAndTypeQuarter_TestChuan9(){
        // trường hợp địa chỉ tồn tại, quý và năm không hợp lệ
        String address = "Hello World";
        String quarter = "Quý 34";
        int year = 2040;

        List<Debt> list_dt = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
        Assertions.assertEquals(0, list_dt.size());
    }

}