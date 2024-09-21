package jrt.vku.spring.security_JPA.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jrt.vku.spring.security_JPA.dao.RoleRepository;
import jrt.vku.spring.security_JPA.entity.Role;
import jrt.vku.spring.security_JPA.entity.User;
import jrt.vku.spring.security_JPA.service.UserService;
import jrt.vku.spring.security_JPA.web.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/register")
public class RegisterController {
	private UserService userService;
	private RoleRepository roleRepository;
	
	@Autowired
	public RegisterController(UserService userService, RoleRepository roleRepository) {
		this.userService = userService;
		this.roleRepository = roleRepository;
	}
	
	@GetMapping("/showRegisterForm")
	public String showRegisterForm(Model model) {
		// Tạo 1 RegisterUser rỗng truyền vào model để gửi tới form đăng kí
		RegisterUser registerUser = new RegisterUser();
		model.addAttribute("registerUser", registerUser);
		return "register/form";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder data) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		data.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@PostMapping("/process")
	public String process(@Valid @ModelAttribute("registerUser") RegisterUser registerUser,
	                      BindingResult result,
	                      Model model,
	                      HttpSession session){
		String username = registerUser.getUsername();
		
		// Form validation
		if (result.hasErrors()) {
			return "register/form";
		}
		
		// Kiểm tra user đã tồn tại
		User user = userService.findByUsername(username);
		if (user != null) {
			model.addAttribute("registerUser", new RegisterUser());
			model.addAttribute("my_error", "Tài khoản đã tồn tại");
			return "register/form";
		}
		
		// Nếu chưa tồn tại thì lưu lại
		User newUser = new User();
		newUser.setUsername(registerUser.getUsername());
		newUser.setPassword(new BCryptPasswordEncoder().encode(registerUser.getPassword()));
		newUser.setFirstName(registerUser.getFirstName());
		newUser.setLastName(registerUser.getLastName());
		newUser.setEmail(registerUser.getEmail());
		
		Role defaultRole = roleRepository.findByName("ROLE_USER");
		Collection<Role> roles = new ArrayList<>();
		roles.add(defaultRole);
		newUser.setRoles(roles);
		
		userService.saveUser(newUser);
		
		// thông báo thành công
		session.setAttribute("my_user", newUser);
		return "register/confirmation";
	}
}
