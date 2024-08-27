package jrt.vku.spring.Create_Hibernate_JPA_Project.common;

import jrt.vku.spring.Create_Hibernate_JPA_Project.dao.SinhVienDAOImpl;
import jrt.vku.spring.Create_Hibernate_JPA_Project.entity.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
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
			System.out.println("3. Tim sinh viên theo tên");
			System.out.println("4. Hiển thị danh sách sinh viên");
			System.out.println("5. Cập nhật sinh viên theo id");
			System.out.println("6. Cập nhật toàn bộ tên của sinh viên");
			System.out.println("7. Xóa sinh viên theo id");
			System.out.println("8. Xóa theo tên");

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
					break;
				case 3:
					System.out.println("Nhập tên sinh viên cần tìm:");
					String name = scanner.nextLine();
					List<SinhVien> listSinhVienOut = sinhVienDAOImpl.getByName(name);
					if(listSinhVienOut != null){
						for (int i = 0; i < listSinhVienOut.size(); i++) {
							System.out.println(listSinhVienOut.get(i).toString());
						}
					} else {
						System.out.println("Not found any sinh vien");
					}
					break;
				case 4:
					List<SinhVien> sinhViens = sinhVienDAOImpl.getAll();
					if(sinhViens != null){
						for (SinhVien sinhVien : sinhViens) {
							System.out.println(sinhVien.toString());
						}
					} else {
						System.out.println("Not found any sinh vien");
					}
					break;
				case 5:
					System.out.println("Nhập mã sinh viên:");
					id = scanner.nextInt();
					scanner.nextLine();
					SinhVien sinhVien = sinhVienDAOImpl.getById(id);

					if(sinhVien == null){
						System.out.println("Not found");
					} else {
						System.out.println("Nhập họ đệm:");
						String hodem = scanner.nextLine();
						System.out.println("Nhập tên:");
						String newName = scanner.nextLine();
						System.out.println("Nhập email:");
						String email = scanner.nextLine();
						sinhVien.setHoDem(hodem);
						sinhVien.setTen(newName);
						sinhVien.setEmail(email);
						sinhVienDAOImpl.update(sinhVien);
					}
					break;
				case 6:
					System.out.println("Nhập tên mới cho cả danh sách sinh viên:");
					String newName = scanner.nextLine();
					sinhVienDAOImpl.updateAllByName(newName);
					break;
				case 7:
					System.out.println("Nhập id của sinh viên cần xóa:");
					id = scanner.nextInt();
					scanner.nextLine();
					sinhVienDAOImpl.deleteById(id);
					break;
				case 8:
					System.out.println("Nhập tên cần xóa");
					name = scanner.nextLine();
					sinhVienDAOImpl.deleteByName(name);
					break;
			}
		}
	}
}
