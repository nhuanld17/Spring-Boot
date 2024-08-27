package jrt.vku.spring.CRUD_JPQL_DEMO.common;

import jrt.vku.spring.CRUD_JPQL_DEMO.dao.SinhVienDAOImpl;
import jrt.vku.spring.CRUD_JPQL_DEMO.entity.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
public class AppConfig {
	
	@Bean
	@Autowired
	public CommandLineRunner getRunner(SinhVienDAOImpl sinhVienDAOImpl){
		return args -> {
			printMenu(sinhVienDAOImpl);
		};
	}
	
	private void printMenu(SinhVienDAOImpl sinhVienDAOImpl) {
		SinhVien sinhVien = new SinhVien();
		Scanner scanner = new Scanner(System.in);
		int id;
		List<SinhVien> sinhViens = new ArrayList<SinhVien>();
		while (true){
			System.out.println("======================================");
			System.out.println("1. Thêm sinh viên mới");
			System.out.println("2. Tìm sinh viên theo ID");
			System.out.println("3. In danh sách sinh viên");
			System.out.println("4. Tìm sinh viên theo tên");
			System.out.println("5. Cập nhật thông tin sinh viên");
			System.out.println("6. Set toàn bộ sinh viên có cùng 1 tên");
			System.out.println("7. Xóa sinh viên theo id");
			System.out.println("8. Xóa sinh viên theo tên");
			
			System.out.println("Nhập lựa chọn:");
			int choice = scanner.nextInt();
			
			switch (choice) {
				case 1:
					scanner.nextLine();
					sinhVien.input();
					sinhVienDAOImpl.save(sinhVien);
					System.out.println("Thêm sinh viên mới thành công");
					break;
				case 2:
					System.out.println("Nhập id của sinh viên cần tìm:");
					id = scanner.nextInt();
					scanner.nextLine();
					sinhVien = sinhVienDAOImpl.findById(id);
					if (sinhVien == null) {
						System.out.println("Sinh viên có id " + id+" không được tìm thấy");
					} else {
						System.out.println(sinhVien.toString());
					}
					break;
				case 3:
					sinhViens = sinhVienDAOImpl.findAll();
					if (sinhViens.isEmpty()) {
						System.out.println("Danh sách sinh viên trống");
					} else {
						for (SinhVien si : sinhViens) {
							System.out.println(si.toString());
						}
					}
					break;
				case 4:
					scanner.nextLine();
					System.out.println("Nhập tên sinh viên để tìm kiếm:");
					String name = scanner.nextLine();
					sinhViens = sinhVienDAOImpl.getByName(name);
					
					if (sinhViens.isEmpty()) {
						System.out.println("Không có sinh viên nào tên là " + name);
					} else {
						for (SinhVien si : sinhViens) {
							System.out.println(si.toString());
						}
					}
					break;
				case 5:
					System.out.println("Nhập id của sinh viên cần cập nhật:");
					id = scanner.nextInt();
					scanner.nextLine();
					
					sinhVien = sinhVienDAOImpl.findById(id);
					if (sinhVien == null) {
						System.out.println("Sinh viên này không tồn tại");
					} else {
						System.out.println("Nhập họ đệm mới:");
						sinhVien.setHoDem(scanner.nextLine());
						System.out.println("Nhập tên mới:");
						sinhVien.setTen(scanner.nextLine());
						System.out.println("Nhập email mới:");
						sinhVien.setEmail(scanner.nextLine());
						
						sinhVienDAOImpl.updateInfo(sinhVien);
					}
					break;
				case 6:
					scanner.nextLine();
					System.out.println("Nhập tên cần update cho toàn bộ sinh viên:");
					name = scanner.nextLine();
					sinhVienDAOImpl.setAllToOneName(name);
					break;
				case 7:
					System.out.println("Nhâp id của sinh viên cần xóa:");
					id = scanner.nextInt();
					scanner.nextLine();
					sinhVien = sinhVienDAOImpl.findById(id);
					if (sinhVien == null) {
						System.out.println("Không có sinh viên nào có id là " + id);
					} else {
						sinhVienDAOImpl.deleteById(id);
					}
					break;
				case 8:
					scanner.nextLine();
					System.out.println("Nhập tên của những sinh viên cần xóa");
					name = scanner.nextLine();
					sinhVienDAOImpl.deleteByName(name);
					break;
			}
		}
	}
}
