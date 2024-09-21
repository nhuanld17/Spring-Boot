package jrt.vku.spring.security_JPA.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUser {
	@NotBlank(message = "Thông tin bắt buộc")
	@Size(min = 1, message = "Độ dài tối thiểu phải là 1")
	private String username;
	
	
	@NotBlank(message = "Thông tin bắt buộc")
	@Size(min = 8, message = "Độ dài tối thiểu là 8")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[\\W_]).{8,}$",
			message = "Mật khẩu phải chứa ít nhất 1 chữ số và 1 ký tự đặc biệt")
	private String password;
	
	@NotBlank(message = "Thông tin bắt buộc")
	private String firstName;
	
	@NotBlank(message = "Thông tin bắt buộc")
	private String lastName;
	
	@NotBlank(message = "Thông tin bắt buộc")
	@Email(message = "Email không hợp lệ")
	private String email;
	
	public RegisterUser() {
	}
	
	public RegisterUser(String email, String firstName, String lastName, String password, String username) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
