package autostoppista.core.http;
public class HTTPInputBasic {
	private String path, method, Authorization, body, contentType;
	public HTTPInputBasic(String path, String method, String Authorization,String contentType, String body) {
		this.path = path;
		this.body = body;
		this.method = method;
		this.Authorization = Authorization;
		this.contentType = contentType;
	}
	public String getPath() {
		return path;
	}
	public String getBody() {
		return body;
	}
	public String getMethod() {
		return method;
	}
	public String getAuthorization() {
		return Authorization;
	}
	public String getContentType() {
		return contentType;
	}
}