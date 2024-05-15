package com.example.tax.ServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tax.Entity.Person;
import com.example.tax.Repository.PersonRepository;
import com.example.tax.Service.PersonService;

import jakarta.transaction.Transactional;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonServiceTest {

    @Autowired
    private PersonService ps_service;

    @Autowired
    private PersonRepository ps_Repository;

    //Test lấy ra toàn bộ danh sách Person
    @Test
    public void getAllPerSon_TestChuan(){
        // Kiểm tra danh sách trả về có rỗng không
        List <Person> list_ps = ps_service.getAllPerSon();
        Assertions.assertNotNull(list_ps);
    }

    //Test lấy ra toàn bộ danh sách Person
    @Test
    public void getAllPersonsOrderedByName_TestChuan(){
        // Kiểm tra danh sách trả về có rỗng không
        List <Person> list_ps = ps_service.getAllPersonOderByName();
        Assertions.assertNotNull(list_ps);
    }

    //Test tìm kiếm theo tên
    @Test
    public void getAllPersonByKeyword_TestChuan1(){
        //trường hợp chỉ có đúng 1 người 
        String keyword = "Nguyễn Đăng Tùng";
        List<Person> list_ps = ps_service.getAllPersonByKeyword(keyword);

        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(1, list_ps.size());
        Assertions.assertTrue(list_ps.get(0).getName().contains(keyword));
    }

    @Test
    public void getAllPersonByKeyword_TestChuan2(){
        //trường hợp chỉ có nhiều hơn 1 người 
        String keyword = "Tùng";
        List <Person> list_ps = ps_service.getAllPersonByKeyword(keyword);
        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(2, list_ps.size());
        for (int i = 0; i < 2; i++) {
            Assertions.assertTrue(list_ps.get(i).getName().toString().contains(keyword));
        }
    }

    @Test
    public void getAllPersonByKeyword_TestChuan3(){
        //trường hợp không có người nào trùng khớp với từ khóa 
        String keyword = "Hell World";
        List <Person> list_ps = ps_service.getAllPersonByKeyword(keyword);
        Assertions.assertEquals(0,list_ps.size());
    }
    

    //Test tìm kiếm theo ID
    @Test
    public void getPerSonById_TestChuan1(){
        //trường hợp có giá trị tìm thấy(1 giá trị duy nhất)
        long ps_id = 1;
        Optional<Person> op_ps = ps_service.getPerSonById(ps_id);
        Assertions.assertTrue(op_ps.isPresent());
        Assertions.assertEquals(ps_id, op_ps.get().getId());
    }

    @Test
    public void getPerSonById_TestChuan2(){
        //trường hợp không có giá trị tìm thấy
        long ps_id = 100000;
        Optional<Person> op_ps = ps_service.getPerSonById(ps_id);
        Assertions.assertFalse(op_ps.isPresent());
    }

    //Test tìm kiếm theo tỉnh
    @Test
    public void getPersonsByAddress_TestChuan1(){
        //trường hợp có đúng 1 người được tìm thấy
        String address = "An Giang";
        List<Person> list_ps = ps_service.getPersonsByAddress(address);
        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(1, list_ps.size());
        Assertions.assertTrue(list_ps.get(0).getAddress().contains(address));
    }

    @Test
    public void getPersonsByAddress_TestChuan2(){
        //trường hợp có nhiều hơn 1 người được tìm thấy
        String address = "Bạc Liêu";
        List<Person> list_ps = ps_service.getPersonsByAddress(address);
        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(2, list_ps.size());
        for (int i = 0; i < 2; i++) {
            Assertions.assertTrue(list_ps.get(i).getAddress().contains(address));
        }
    }

    @Test
    public void getPersonsByAddress_TestChuan3(){
        //trường hợp 0 người được tìm thấy
        String address = "Bình Phước";
        List<Person> list_ps = ps_service.getPersonsByAddress(address);
        Assertions.assertEquals(0,list_ps.size());
    }    

    //Test Tìm kiếm theo mức thu nhập
    @Test
    public void getPersonByIncome_TestChuan1(){
        //trường hợp 1 người được tìm thấy
        String income = "Đến 5 triệu đồng";
        long num_expected = 5000000;
        
        List<Person> list_ps = ps_service.getPersonByIncome(income);
        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(1, list_ps.size());
        Assertions.assertTrue( list_ps.get(0).getIncome() >= 0 & list_ps.get(0).getIncome() <= num_expected);
    }

    @Test
    public void getPersonByIncome_TestChuan2(){
        //trường hợp nhiều hơn 1 người được tìm thấy
        String income = "Trên 52 triệu - 80 triệu đồng";
        long min_expected = 52000000;
        long max_expected = 80000000;
        
        List<Person> list_ps = ps_service.getPersonByIncome(income);
        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(4, list_ps.size());
        for (int i = 0; i < 4; i++) {
            Assertions.assertTrue(list_ps.get(i).getIncome() > min_expected & list_ps.get(i).getIncome() <= max_expected );
        }
    }

    @Test
    public void getPersonByIncome_TestChuan3(){
        //trường hợp 0 người được tìm thấy
        String income = "Trên 999 triệu đồng";
        
        List<Person> list_ps = ps_service.getPersonByIncome(income);
        Assertions.assertEquals(0,list_ps.size());
    }

    @Test
    public void getPersonByIncome_TestChuan4(){
        //trường hợp mức thu nhập không hợp lệ(max < min)
        String income = "Trên 52 triệu - 10 triệu đồng";
        List<Person> list_ps = ps_service.getPersonByIncome(income);
        Assertions.assertEquals(0,list_ps.size());
    }

    //Test tìm kiếm theo cả tỉnh và mức thu nhập
    @Test 
    public void getPersonsByAddressAndIncome_TestChuan1(){
        // trường hợp có 1 người được tìm thấy
        String address = "Hải Dương";
        String income = "Trên 32 triệu - 52 triệu đồng";
        long min_expected = 32000000;
        long max_expected = 52000000;
        List<Person> list_ps = ps_service.getPersonsByAddressAndIncome(address, income);
        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(1, list_ps.size());
        Assertions.assertTrue(list_ps.get(0).getAddress().contains(address));
        Assertions.assertTrue( list_ps.get(0).getIncome() > min_expected & list_ps.get(0).getIncome() <= max_expected);
    }

    @Test 
    public void getPersonsByAddressAndIncome_TestChuan2(){
        // trường hợp nhiều hơn 1 người được tìm thấy
        String address = "Hà Nội";
        String income = "Trên 18 triệu - 32 triệu đồng";
        long min_expected = 18000000;
        long max_expected = 32000000;
        List<Person> list_ps = ps_service.getPersonsByAddressAndIncome(address, income);
        Assertions.assertNotNull(list_ps);
        Assertions.assertEquals(3, list_ps.size());
        for (int i = 0; i < 3; i++) {
            Assertions.assertTrue(list_ps.get(i).getAddress().contains(address));
            Assertions.assertTrue( list_ps.get(i).getIncome() > min_expected & list_ps.get(i).getIncome() <= max_expected);
        }
    }

    @Test 
    public void getPersonsByAddressAndIncome_TestChuan3(){
        // trường hợp có người thuộc tỉnh nhưng không có mức thu nhập này và ngược lại
        String address = "Hậu Giang";
        String income = "Trên 5 triệu - 10 triệu đồng";
        List<Person> list_ps = ps_service.getPersonsByAddressAndIncome(address, income);
        Assertions.assertEquals(0,list_ps.size());
    }

    @Test 
    public void getPersonsByAddressAndIncome_TestChuan4(){
        // trường hợp tỉnh tồn tại, mức thu nhập không hợp lệ
        String address = "Hậu Giang";
        String income = "Trên 10 triệu - 5 triệu đồng";
        List<Person> list_ps = ps_service.getPersonsByAddressAndIncome(address, income);
        Assertions.assertEquals(0,list_ps.size());
        
    }

    @Test 
    public void getPersonsByAddressAndIncome_TestChuan5(){
        // trường hợp tỉnh không tồn tại, mức thu nhập hợp lệ
        String address = "Hồng Kông";
        String income = "Trên 5 triệu - 10 triệu đồng";
        List<Person> list_ps = ps_service.getPersonsByAddressAndIncome(address, income);
        Assertions.assertEquals(0,list_ps.size());
        
    }

    @Test 
    public void getPersonsByAddressAndIncome_TestChuan6(){
        // trường hợp tỉnh không tồn tại, mức thu nhập không hợp lệ
        String address = "Hồng Kông";
        String income = "Trên 10 triệu - 5 triệu đồng";
        List<Person> list_ps = ps_service.getPersonsByAddressAndIncome(address, income);
        Assertions.assertEquals(0,list_ps.size());
        
    }
}