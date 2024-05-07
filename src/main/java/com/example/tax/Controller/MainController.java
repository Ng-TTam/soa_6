package com.example.tax.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tax.Entity.Account;
import com.example.tax.Entity.Debt;
import com.example.tax.Entity.HistoryDTO;
import com.example.tax.Entity.Person;
import com.example.tax.Service.AccountService;
import com.example.tax.Service.DebtService;
import com.example.tax.Service.HistoryService;
import com.example.tax.Service.PersonService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private DebtService debtService;
	
	@GetMapping(value = "/admin")
	public String home(HttpSession session, Model model,HttpServletRequest request,
			@CookieValue(value = "c_user", defaultValue = "") String user,
			@CookieValue(value = "c_pass", defaultValue = "") String pass,
			@CookieValue(value = "c_rm", defaultValue = "0") String rm) {
		
		model.addAttribute("username", user);
		model.addAttribute("password", pass);
		model.addAttribute("rm", Integer.parseInt(rm));
		return "/login";
	}
	
	@PostMapping(value = "/home")
	public String loginAdmin(HttpServletRequest req,HttpServletResponse res,Model model,HttpSession session) {
		String name = req.getParameter("user");
		String pass = req.getParameter("pass");
		String remember = req.getParameter("remember");
		
		Cookie cook_user = new Cookie("c_user", name);
        Cookie cook_pass = new Cookie("c_pass", pass);
        Cookie cook_remember = new Cookie("c_rm", remember);
        if (remember!=null){
            cook_user.setMaxAge(60*60*24*7);
            cook_pass.setMaxAge(60*60*24*7);
            cook_remember.setMaxAge(60*60*24*7);
        }
        else {
            cook_user.setMaxAge(0);
            cook_pass.setMaxAge(0);
            cook_remember.setMaxAge(0);
        }
        res.addCookie(cook_user);
        res.addCookie(cook_pass);
        res.addCookie(cook_remember);
            
        if (!accountService.checkAccount(name, pass)) {
            session.setAttribute("messAdmin", "Sai tài khoản hoặc mật khẩu");
        	session.removeAttribute("checkLoginAdmin");
            return "redirect:/admin";
        }
        else {
        	Account account = accountService.getAccountLogin(name, pass);
        	session.removeAttribute("checkLoginAdmin");
            session.setAttribute("account", account); 
            session.removeAttribute("messAdmin");
            return "/home";
        }    
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("account");
		session.removeAttribute("messAdmin");
		session.removeAttribute("checkLoginAdmin");
		return"redirect:/admin";
	}
	
	@GetMapping(value = "/report")
	public String redirectToReport () {
		return "/home";
	}
	
	@GetMapping(value = "/list/person")
	public String personList(Model model) {
		return"personList";
	}
	
	@GetMapping(value = "/history/{personId}")
	public String taxHistory(Model model,@PathVariable Long personId) {
		List<HistoryDTO> list = new ArrayList<>();
		list = historyService.getTaxHistoryByPersonId(personId);
		model.addAttribute("historys", list);
		return"history";
	}
	
	@GetMapping(value = "/onback/list/person")
	public String onBack(){
		return"personList";
	}
	
	@GetMapping(value = "/statistical")
	public String statistical(){  
		return"statistical";
	}
	
	@GetMapping(value = "/debt")
	public String getDebt(){  
		return"debt";
	}
	
	// setting
	
	@GetMapping(value = "/setting")
	public String setting(){  
		return"setting";
	}
	
}
