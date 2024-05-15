package com.example.tax.ServiceTest;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tax.Entity.HistoryDTO;
import com.example.tax.Repository.HistoryRepository;
import com.example.tax.Service.HistoryService;
import com.example.tax.Service.PersonService;

import jakarta.transaction.Transactional;
import lombok.val;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HistoryServiceTest {
    @Autowired
    HistoryService historyService;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    PersonService personService;

    //test tìm kiếm lịch sử đóng thuế theo id
    @Test
    public void getTaxHistoryByPersonId_TestChuan1(){
        // trường hợp id không tồn tại không đi vào vòng for
        long ps_id = 351;

        List <HistoryDTO> list_hDTO = historyService.getTaxHistoryByPersonId(ps_id);
        Assertions.assertEquals(0, list_hDTO.size());
    }

    @Test
    public void getTaxHistoryByPersonId_TestChuan2(){
        // trường hợp id tồn tại, đi vào vòng for 1 lần - có duy nhất 1 bản ghi
        long ps_id = 7;
        String expected = "Hà Tiến Trung";

        List <HistoryDTO> list_hDTO = historyService.getTaxHistoryByPersonId(ps_id);
        Assertions.assertNotNull(list_hDTO);
        Assertions.assertEquals(1, list_hDTO.size());
        Assertions.assertEquals(expected, list_hDTO.get(0).getName());
    }

    @Test
    public void getTaxHistoryByPersonId_TestChuan3(){
        // trường hợp id tồn tại, đi vào vòng for nhiều hơn 1 lần - có nhiều hơn 1 bản ghi
        long ps_id = 24;
        String name_expected = "Lê Chí Cương";
        int size_expected = 2;

        List <HistoryDTO> list_hDTO = historyService.getTaxHistoryByPersonId(ps_id);
        Assertions.assertNotNull(list_hDTO);
        Assertions.assertEquals(size_expected, list_hDTO.size());
        for (int i = 0; i < size_expected; i++) {
            Assertions.assertEquals(name_expected, list_hDTO.get(i).getName());
        }
    }

    //=================================================================================
    //Test func tính tổng tiền thuế theo tháng và năm
    @Test
    public void getTotalMoneyByMonthAndYear_TestChuan1(){
        //trường hợp có trả về giá trị > 0
        int year = 2022;
        int thang = 1;
        long sum_expected = 3180000;
        long sum_actual = historyService.getTotalMoneyByMonthAndYear(thang, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByMonthAndYear_TestChuan2(){
        //trường hợp có trả về giá trị = 0
        int year = 2023;
        int thang = 1;
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByMonthAndYear(thang, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByMonthAndYear_TestChuan3(){
        //trường hợp dữ liệu năm đúng, tháng sai
        int year = 2022;
        int thang = 13;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByMonthAndYear(thang, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByMonthAndYear_TestChuan4(){
        //trường hợp dữ liệu năm sai, tháng đúng
        int year = -1022;
        int thang = 10;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByMonthAndYear(thang, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByMonthAndYear_TestChuan5(){
        //trường hợp dữ liệu năm sai, tháng sai
        int year = 50000;
        int thang = 13;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByMonthAndYear(thang, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    //===========================================================
    //Test func tính tổng tiền thuế theo năm và theo quý
    @Test
    public void getTotalMoneyByQuarterAndYear_TestChuan1(){
        //trường hợp có trả về giá trị > 0
        int year = 2022;
        int quarter = 1; //quý 1, 2, 3
        long sum_expected = 11040000;
        long sum_actual = historyService.getTotalMoneyByQuarterAndYear(quarter, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByQuarterAndYear_TestChuan2(){
        //trường hợp có trả về giá trị = 0
        int year = 2021;
        int quarter = 1;
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByQuarterAndYear(quarter, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByQuarterAndYear_TestChuan3(){
        //trường hợp đữ liệu năm đúng, quý sai
        int year = 2022;
        int quarter = 13;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByQuarterAndYear(quarter, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByQuarterAndYear_TestChuan4(){
        //trường hợp đữ liệu năm sai, quý đúng
        int year = -1022;
        int quarter = 10;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByQuarterAndYear(quarter, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByQuarterAndYear_TestChuan5(){
        //trường hợp đữ liệu năm sai, quý sai
        int year = 50000;
        int quarter = 13;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByQuarterAndYear(quarter, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }


    //======================================================================
    //Test func tính tổng theo năm và theo mức đóng thuế >min <=max
    @Test
    public void getTotalMoneyByIncome_TestChuan1(){
        //trường hợp không có bản ghi nào tại cả min và max
        int year = 2022;
        long min = 830000;
        long max = 840000;
        long sum_expected = 4175000;
        long sum_actual = historyService.getTotalMoneyByIncome(min, max, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByIncome_TestChuan2(){
        //trường hợp có bản ghi tại cả min và max
        int year = 2022;
        long min = 829000;
        long max = 839000;
        long sum_expected = 4175000;
        long sum_actual = historyService.getTotalMoneyByIncome(min, max, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByIncome_TestChuan3(){
        //trường hợp có bản ghi tại cả min và max (min = max)
        int year = 2022;
        long min = 829000;
        long max = 829000;
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByIncome(min, max, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    public void getTotalMoneyByIncome_TestChuan4(){
        //trường hợp  min > max 
        int year = 2022;
        long min = 839000;
        long max = 829000;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByIncome(min, max, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

    @Test
    public void getTotalMoneyByIncome_TestChuan5(){
        //trường hợp năm không hợp lệ và min < max
        int year = -1023;
        long min = 829000;
        long max = 839000;
        
        long sum_expected = 0;
        long sum_actual = historyService.getTotalMoneyByIncome(min, max, year);

        Assertions.assertEquals(sum_expected, sum_actual);
    }

}
