package okeanos.restApp.resources;

public class ResponseError {

	private String message;

	public ResponseError(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return this.message;
	}
}
