package jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.entity;

public class ErrorRespone {
	private int status;
	private String message;
	private long timestamp;
	
	public ErrorRespone(int status, String message) {
		this.status = status;
		this.message = message;
		timestamp = System.currentTimeMillis();
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
}
