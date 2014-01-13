package autostoppista.core.restcall;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
public class LoginSaver {
	SharedPreferences s;
	String username, password;
	private final static String USER = "USER";
	private final static String PASS = "PASS";
	public LoginSaver(Activity a) {
		s = a.getPreferences(0);
	}
	public void saveLoginData(Login l) {
		if ((l.Username != null || l.Username.replaceAll(" ", "") != "") && 
			(l.Password != null || l.Password.replaceAll(" ", "") != "")){
			Editor e = s.edit();
			e.putString(USER, l.Username);
			e.putString(PASS, l.Password);
			e.commit();
		} 
	}
	public Login getLoginData() {
		username = s.getString(USER, "");
		password = s.getString(PASS, "");
		if ((username != null || username.replaceAll(" ", "") != "") && 
			(password != null || password.replaceAll(" ", "") != "")){
			Login l = new Login();
			l.setLoginInfo(username,password);
			return l;
		} 
		return null;
	}
}