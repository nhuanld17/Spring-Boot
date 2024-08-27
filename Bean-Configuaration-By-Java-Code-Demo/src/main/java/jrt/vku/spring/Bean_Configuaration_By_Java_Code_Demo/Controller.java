package jrt.vku.spring.Bean_Configuaration_By_Java_Code_Demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private Calculator myCalculator;

	@Autowired
	public Controller(@Qualifier("getMath") Calculator myCalculator) {
		this.myCalculator = myCalculator;
	}

	@GetMapping("/canBacHai")
	public String tinhCanBacHai(@RequestParam("value") double x){
		return "SQRT("+x+") = " + myCalculator.canBacHai(x);
	}

	@GetMapping("/binhPhuong")
	public String tinhBinhPhuong(@RequestParam("value") double x){
		return "("+x+"^2) = " + myCalculator.binhPhuong(x);
	}

}
