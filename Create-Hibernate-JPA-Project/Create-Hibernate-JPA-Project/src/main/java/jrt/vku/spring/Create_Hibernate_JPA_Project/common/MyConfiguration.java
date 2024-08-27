package jrt.vku.spring.Create_Hibernate_JPA_Project.common;

import jrt.vku.spring.Create_Hibernate_JPA_Project.dao.SinhVienDAOImpl;
import jrt.vku.spring.Create_Hibernate_JPA_Project.entity.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class MyConfiguration {


	@Autowired
	@Bean
	public CommandLineRunner getRunner(SinhVienDAOImpl sinhVienDAOImpl){
		return runner -> {
			printMenu(sinhVienDAOImpl);
		};
	}

	public void printMenu(SinhVienDAOImpl sinhVienDAOImpl){
		Scanner scanner = new Scanner(System.in);
		while (true){
			System.out.println("++++++++++++++++++++++++++++++++++");
			System.out.println("MENU");
			System.out.println("1. Thêm sinh viên");
			System.out.println("2. Tìm sinh viên");
			System.out.println("3. Xóa sinh viên");
			System.out.println("4. Thoát");

			System.out.println("Lựa chọn: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice){
				case 1:
					SinhVien sinhVienIn = new SinhVien();
					sinhVienIn.input();
					sinhVienDAOImpl.save(sinhVienIn);
					break;
				case 2:
					System.out.println("Nhập id sinh viên cần tìm:");
					int id = scanner.nextInt();
					SinhVien sinhVienOut = sinhVienDAOImpl.getById(id);
					if(sinhVienOut != null){
						System.out.println(sinhVienOut.toString());
					} else {
						System.out.println("Khong tim thay sinh vien co id la " + id);
					}
			}
		}
	}
}
