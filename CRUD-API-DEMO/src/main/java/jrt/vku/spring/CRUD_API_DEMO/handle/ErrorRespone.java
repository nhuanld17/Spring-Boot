package jrt.vku.spring.CRUD_API_DEMO.handle;

public class ErrorRespone{
	private int status;
	private String message;
	private Long timeStamp;
	
	public ErrorRespone(int status, String message) {
		this.status = status;
		this.message = message;
		this.timeStamp = System.currentTimeMillis();
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Long getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
