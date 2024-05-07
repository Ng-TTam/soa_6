package com.example.tax.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tax.Entity.Debt;
import com.example.tax.Entity.Person;
import com.example.tax.Entity.Tax;
import com.example.tax.Service.AccountService;
import com.example.tax.Service.DebtService;
import com.example.tax.Service.HistoryService;
import com.example.tax.Service.PersonService;
import com.example.tax.Service.TaxService;

@RestController
@CrossOrigin
public class RestAPIController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private DebtService debtService;
	
	@Autowired
	private TaxService taxService;
	
	@GetMapping("/list/person/{address}/{income}")
	public ResponseEntity<List<Person>> apiGetPersonListByAddressAndIncome(
			@PathVariable("address") String address, @PathVariable("income") String income) {
		List<Person> persons = new ArrayList<>();
	    try {
	        if (address.equals("Tất cả") && !income.equals("Tất cả")){
	            persons = personService.getPersonByIncome(income);
	        } 
	        else if (!address.equals("Tất cả") && income.equals("Tất cả")){
	            persons = personService.getPersonsByAddress(address);
	        } 
	        else if (address.equals("Tất cả") && income.equals("Tất cả")){
	        	persons = personService.getAllPersonOderByName();
	        } 
	        else {
	            persons = personService.getPersonsByAddressAndIncome(address, income);
	        }
	        return ResponseEntity.ok(persons);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
	    }
	}
	
	@GetMapping("/list/person/{keyword}")
	public ResponseEntity<List<Person>> apiGetPersonsContainingKeyword(@PathVariable String keyword) {
		List<Person> persons = new ArrayList<>();
	    try {
	        persons = personService.getAllPersonByKeyword(keyword);
	        return ResponseEntity.ok(persons);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
	    }
	}
	
	@GetMapping(value = "/statistical/{yearSelected}")
	public List<List<Long>> statisticalByYear (@PathVariable int yearSelected) {

        List<Long> months = new ArrayList<>();
        List<Long> quarters = new ArrayList<>();
        List<Long> taxMonthList = new ArrayList<>();
        List<Long> debMonthList = new ArrayList<>();
        List<Long> taxQuarterList = new ArrayList<>();
        List<Long> debQuarterList = new ArrayList<>();
        List<List<Long>>result = new ArrayList<>();
        
        for(long i = 1 ; i <= 12 ; i++) {
        	months.add(i);
        }
    	result.add(months);
        for(long i = 1 ; i <= 4 ; i++) {
        	quarters.add(i);
        }
        result.add(quarters);
        for(int i = 1 ; i <= 12 ; i++) {
        	long tax = historyService.getTotalMoneyByMonthAndYear(i, yearSelected);
        	taxMonthList.add(tax);
        }
        result.add(taxMonthList);
        
        for(int i = 1 ; i <= 12 ; i++) {
        	long debt = debtService.getSumTaxMoney("tháng "+i, yearSelected);
        	debMonthList.add(debt);
        }
        result.add(debMonthList);
        
        for(int i = 3 ; i <= 12 ; i+=3) {
        	long tax = historyService.getTotalMoneyByQuarterAndYear(i, yearSelected);
        	taxQuarterList.add(tax);
        }
        result.add(taxQuarterList);
        
        for(int i = 1 ; i <= 4 ; i++) {
        	long debt = debtService.getSumTaxMoney("quý "+i, yearSelected);
        	debQuarterList.add(debt);
        }
        result.add(debQuarterList);
        
		return result;
	}
	
	@GetMapping(value = "/statistical/income/{yearSelected}")
	public List<List<Long>> statisticalByIncome (@PathVariable int yearSelected) {
		List<List<Long>> results = new ArrayList<>();
		List<Long> list1 = new ArrayList<>();
		List<Long> list2 = new ArrayList<>();
        
        long sum1 = historyService.getTotalMoneyByIncome(0, 250000, yearSelected);
        long sum2 = historyService.getTotalMoneyByIncome(500000, 1000000, yearSelected);
        long sum3 = historyService.getTotalMoneyByIncome(1500000, 2700000, yearSelected);
        long sum4 = historyService.getTotalMoneyByIncome(3600000, 6400000, yearSelected);
        long sum5 = historyService.getTotalMoneyByIncome(8000000, 13000000, yearSelected);
        long sum6 = historyService.getTotalMoneyByIncome(15600000, 24000000, yearSelected);
        long sum7 = historyService.getTotalMoneyByIncome(28000000, 1000000000, yearSelected);
        long sum15 = sum1+sum2+sum3+sum4+sum5+sum6+sum7;
        
        long sum8 = debtService.getSumDebtByIncome(0, 250000, yearSelected);
        long sum9 = debtService.getSumDebtByIncome(500000, 1000000, yearSelected);
        long sum10 = debtService.getSumDebtByIncome(1500000, 2700000, yearSelected);
        long sum11 = debtService.getSumDebtByIncome(3600000, 6400000, yearSelected);
        long sum12 = debtService.getSumDebtByIncome(8000000, 13000000, yearSelected);
        long sum13 = debtService.getSumDebtByIncome(15600000, 24000000, yearSelected);
        long sum14 = debtService.getSumDebtByIncome(28000000, 1000000000, yearSelected);
        long sum16 = sum8+sum9+sum10+sum11+sum12+sum13+sum14;
        
        list1.add(sum1);list1.add(sum2);list1.add(sum3);list1.add(sum4);list1.add(sum5);list1.add(sum6);list1.add(sum7);
        list1.add(sum15);results.add(list1);
        list2.add(sum8);list2.add(sum9);list2.add(sum10);list2.add(sum11);list2.add(sum12);list2.add(sum13);list2.add(sum14);
        list2.add(sum16);results.add(list2);
        
		return results;
	}
	
	@GetMapping(value = "/debt/{address}/{year}/{type}/{month}/{quarter}")
	public ResponseEntity<List<Debt>>getDebtByFilter(Model model,@PathVariable("address") String address,
			@PathVariable("year") int year, @PathVariable("type") String type,
			@PathVariable("month") String month, @PathVariable("quarter") String quarter){  
		List<Debt> debts = new ArrayList<>();
		try {
			if (type.equals("Tất cả")) {
				debts = debtService.getDeabtByAddressAndYear(address, year);
			}
			else if (type.equals("Theo tháng")) {
				debts = debtService.getDeabtByAddressAndYearAndTypeMonth(address, year, month);
			}
			else if (type.equals("Theo quý")) {
				debts = debtService.getDeabtByAddressAndYearAndTypeQuarter(address, year, quarter);
			}
			return ResponseEntity.ok(debts);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
		}
	}
	
	@GetMapping(value = "/api/taxes")
	public List<Tax> getListTax(){		
		return taxService.getAllTax();
	}
	
	@PostMapping(value = "/api/taxes/update")
	public ResponseEntity<String> updateListTax(@RequestBody List<Tax> listTaxes){		
		taxService.updateTax(listTaxes);
		return ResponseEntity.ok("Data saved successfully");
	}
}
//<script>
//var currentPageButton = null;
//var fetchedData = [];
//var currentPage = 1;
//
//document.querySelector('.btn-info').addEventListener('click', function() {
//	var address = document.getElementById("address").value;
//    var year = document.getElementById("year").value;
//    var type = document.getElementById("type").value;
//    var month = document.getElementById("month").value;
//    var quarter = document.getElementById("quarter").value;
//
//    if (address === "Chọn tỉnh") {
//	    alert("Chưa chọn tỉnh");
//	    return; 
//	}
//	if (year === "Chọn năm") {
//	    alert("Chưa chọn năm");
//	    return; 
//	}
//	if (type === "Hình thức") {
//	    alert("Chưa chọn hình thức đóng thuế");
//	    return; 
//	}
//	if (month === "Chọn tháng") {
//		if (type === "Theo tháng"){
//			alert("Chưa chọn tháng");
//    	    return; 
//		} 	   
//	}
//	if (quarter === "Chọn quý") {
//		if (type === "Theo quý"){
//			alert("Chưa chọn quý");
//    	    return; 
//		} 	   
//	}
//    
//	var url = '/debt/' + encodeURIComponent(address) + '/' + encodeURIComponent(year) 
//	+ '/' + encodeURIComponent(type) + '/' + encodeURIComponent(month) + '/' + encodeURIComponent(quarter);
//
//    fetch(url)
//        .then(response => response.json())
//        .then(data => {
//            fetchedData = data;
//            checkLengthData(fetchedData);
//            showPage(currentPage);
//        })
//        .catch(error => {
//            console.error('Error:', error);
//        });
//    function checkLengthData(fetchedData){
//    	if (fetchedData.length === 0) {
//            document.getElementById('blockEmpty').style.display = 'block';
//            document.getElementById('blockTable').style.display = 'none';
//            return;
//        } else {
//            document.getElementById('blockEmpty').style.display = 'none';
//            document.getElementById('blockTable').style.display = 'block';
//        }
//    }
//});
//
//
//function showPage(pageNumber) {
//    var tableBody = document.querySelector('tbody');
//    tableBody.innerHTML = '';
//
//    var rowsPerPage = 11;
//    var startIndex = (pageNumber - 1) * rowsPerPage;
//    var endIndex = startIndex + rowsPerPage;
//    var currentPageData = fetchedData.slice(startIndex, endIndex);
//
//    currentPage = pageNumber;
//    if (currentPageData.length === 0) {
//        document.getElementById('blockPagination').style.display = 'none';
//        return;
//    } else {
//        document.getElementById('blockPagination').style.display = 'block';
//    }
//
//    currentPageData.forEach(function(person, index) {
//        var row = '<tr>';
//        row += '<th>' + (startIndex + index + 1) + '</th>';
//        row += '<th>' + person.name + '</th>';
//        row += '<th>' + person.taxCode + '</th>';
//        row += '<th>' + person.phoneNumber + '</th>';
//        row += '<th>' + person.address + '</th>';
//        row += '<th><a href="/detail/' + person.id + '">Xem</a></th>';
//        row += '<th><a href="/history/' + person.id + '">Xem</a></th>';
//        row += '</tr>';
//        tableBody.innerHTML += row;
//    });
//
//    updatePagination();
//
//    // Gọi hàm selectPage để chọn ô số trang hiện tại
//    if (currentPageButton !== null) {
//        selectPage(currentPageButton);
//    }
//}
//
//function renderPageNumbers(totalPages) {
//    var pageNumberContainer = document.getElementById('page-numbers1');
//    pageNumberContainer.innerHTML = '';
//
//    for (var i = 1; i <= totalPages; i++) {
//        var button = document.createElement('button');
//        button.textContent = i;
//        button.classList.add('page-number');
//        button.onclick = function() {
//            showPage(parseInt(this.textContent));
//        };
//        pageNumberContainer.appendChild(button);
//    }
//}
//
//function updatePagination() {
//    var totalPages = Math.ceil(fetchedData.length / 11);
//    renderPageNumbers(totalPages);
//
//    var pageButtons = document.querySelectorAll('#page-numbers1 button');
//    pageButtons.forEach(function(button, index) {
//        if (index + 1 === currentPage) {
//            button.classList.add('active');
//        } else {
//            button.classList.remove('active');
//        }
//    });
//}
//
//
//function prevPage1() {
//    if (currentPage > 1) {
//        showPage(currentPage - 1);
//    }
//}
//
//function nextPage1() {
//    var totalPages = Math.ceil(fetchedData.length / 11);
//    if (currentPage < totalPages) {
//        showPage(currentPage + 1);
//    }
//}
//
//function selectPage(pageNumberButton) {
//    // Loại bỏ lớp 'active' khỏi ô số trang hiện tại
//    if (currentPageButton !== null) {
//        currentPageButton.classList.remove('active');
//    }
//    // Thêm lớp 'active' vào ô số trang mới được chọn
//    pageNumberButton.classList.add('active');
//    // Lưu trữ ô số trang mới được chọn
//    currentPageButton = pageNumberButton;
//}
//
//showPage(currentPage);
//
//
//</script>
