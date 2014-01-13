package autostoppista.core.http;
public class HTTPOutput {
	int code;
	String message;
	String data;
	public HTTPOutput(int code, String message, String data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public String getData() {
		return data;
	}
}