package jrt.vku.spring.Create_Hibernate_JPA_Project.entity;

import jakarta.persistence.*;

import java.util.Scanner;

@Entity
@Table(name = "sinhvien")
public class SinhVien {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ho_dem", length = 45)
	private String hoDem;

	@Column(name = "ten", length = 45)
	private String ten;

	@Column(name = "email", length = 45)
	private String email;

	public SinhVien() {
	}

	public SinhVien(String ten, int id, String hoDem) {
		this.ten = ten;
		this.id = id;
		this.hoDem = hoDem;
	}

	public SinhVien(String email, String hoDem, int id, String ten) {
		this.email = email;
		this.hoDem = hoDem;
		this.id = id;
		this.ten = ten;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setHoDem(String hoDem) {
		this.hoDem = hoDem;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getEmail() {
		return email;
	}

	public String getHoDem() {
		return hoDem;
	}

	public int getId() {
		return id;
	}

	public String getTen() {
		return ten;
	}

	@Override
	public String toString() {
		return "SinhVien{" +
				"email='" + email + '\'' +
				", id=" + id +
				", hoDem='" + hoDem + '\'' +
				", ten='" + ten + '\'' +
				'}';
	}

	public void input(){
		Scanner k = new Scanner(System.in);
		System.out.println("Họ đệm:");
		this.setHoDem(k.nextLine());
		System.out.println("Tên:");
		this.setTen(k.nextLine());
		System.out.println("Email:");
		this.setEmail(k.nextLine());
	}
}
