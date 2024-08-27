package jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.entity;

public class Student {
	private int id;
	private String name;
	private int age;
	private String major;
	private double GPA;
	
	public Student(int id, String name, int age, double GPA, String major) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.GPA = GPA;
		this.major = major;
	}
	
	public Student() {
	
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setGPA(double GPA) {
		this.GPA = GPA;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public double getGPA() {
		return GPA;
	}
	
	public int getId() {
		return id;
	}
	
	public String getMajor() {
		return major;
	}
	
	public String getName() {
		return name;
	}
}
