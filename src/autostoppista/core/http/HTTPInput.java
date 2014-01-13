package autostoppista.core.http;
import java.util.HashMap;
import autostoppista.core.restcall.Login;
public class HTTPInput {
	private String path, method;
	private Login l;
	private HashMap<String, String> params;
	public HTTPInput(String path, String method, HashMap<String, String> params) {
		this.path = path;
		this.params = params;
		this.method = method;
	}
	public HTTPInput(String path, String method, HashMap<String, String> params, Login l) {
		this.path = path;
		this.params = params;
		this.method = method;
		this.l = l;
	}
	public String getPath() {
		return path;
	}
	public HashMap<String, String> getParams() {
		return params;
	}
	public String getMethod() {
		return method;
	}
	public String getUrlEncodedParams() {
		if(params==null) return null;
		String out = "" ;
		for (String k : params.keySet()){
			out += k +"="+ params.get(k)+"&";
		}
		out = out.substring(0, out.length());
		return out;
	}
	public String getloginInfo(){
		if(l==null) return null;
		return l.getAuthenticationHttpHeader();
	}
}