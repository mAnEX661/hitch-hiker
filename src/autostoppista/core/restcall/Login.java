package autostoppista.core.restcall;
import Decoder.BASE64Encoder;
public class Login {
	String Username, Password;
	public String getAuthenticationHttpHeader() {
		if (Username == "" || Username == null || Password == ""|| Password == null){
			return null;
		}
		BASE64Encoder b64e = new BASE64Encoder();
        return "Basic " + b64e.encode((Username+":"+Password).getBytes()) ;
	}
	public String[] getLoginInfo() {
		String[] s = new String[2];
		s[0] = Username;
		s[1] = Password;
		return s;
 	}
	public Login setLoginInfo(String...credentials) {
		Username = credentials[0];
		Password = credentials[1];
		return this;
	}
	public String getUsername() {
		return Username;
	}
	public String getPassword() {
		return Password;
	}
}