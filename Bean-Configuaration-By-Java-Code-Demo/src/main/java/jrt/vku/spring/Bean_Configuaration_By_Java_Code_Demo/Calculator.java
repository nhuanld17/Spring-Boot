package jrt.vku.spring.Bean_Configuaration_By_Java_Code_Demo;


/*
	Giả sử đây là 1 class nằm trong một thư viện viết sẵn trong java
	và không thể sửa đổi code trong này được, tất nhiên là không thể
	thêm @Component ở đây được
*/
public class Calculator {

	public double canBacHai(double value){
		return Math.sqrt(value);
	}

	public double binhPhuong(double value){
		return Math.pow(value, 2);
	}
}
