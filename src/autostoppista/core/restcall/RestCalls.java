package autostoppista.core.restcall;
import java.util.HashMap;
import autostoppista.core.http.HTTPCalls;
import autostoppista.core.http.HTTPInput;
import autostoppista.core.http.HTTPStartListener;
import autostoppista.core.http.HTTPonCompleteListener;
public class RestCalls {
	private static String DOMINE;
	public static final String PASSENGER = "PASSENGER";
	public static final String DRIVER = "DRIVER";
	public static String getDOMINE() {
		return DOMINE;
	}
	public static void setDOMINE(String domine) {
		DOMINE = domine;
	}
	public static void MiServeUnPassaggio(double Latitude, double Longitude, String Comment, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("Latitude",Double.toString(Latitude));
        param.put("Longitude",Double.toString(Longitude));
        param.put("Text",Comment);
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/MiServeUnPassaggio/", "POST", param,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void Accetto(String DriverName, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("UserName", DriverName);
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/Accetto", "POST", param,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void GetPassengersList(double Latitude, double Longitude, HTTPStartListener s, HTTPonCompleteListener c, Login l){
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("Latitude",Double.toString(Latitude));
        param.put("Longitude",Double.toString(Longitude));
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/GetPassengersList", "POST", param, l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void GetFeedbackListPassenger(String Passenger, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
		HashMap<String, String> param = new HashMap<String, String>();
	    param.put("UserName",Passenger);
		HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/GetFeedbackListPassenger", "POST", param, l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void GetFeedbackListDriver(String Driver, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
		HashMap<String, String> param = new HashMap<String, String>();
	    param.put("UserName", Driver);
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/GetFeedbackListDriver", "POST", param,l);
        HTTPCalls.start(restInput,s,c);
	}	
	public static void GetFeedbackToEditPassenger(HTTPStartListener s, HTTPonCompleteListener c, Login l) {
		HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/GetFeedbackToEditPassenger", "POST", null,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void GetFeedbackToEditDriver(HTTPStartListener s, HTTPonCompleteListener c, Login l) {
		HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/GetFeedbackToEditDriver", "POST", null,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void DriverSavePassengerFeedback(int FeedbackID, String Comment, float Rate, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
		HashMap<String, String> param = new HashMap<String, String>();
	    param.put("FeedbackID", Integer.toString(FeedbackID));
	    param.put("Comment", Comment);
	    param.put("Rate",Float.toString(Rate));
		HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/DriverSavePassengerFeedback", "POST", param, l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void PassengerSaveDriverFeedback(int FeedbackID, String Comment, float Rate, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
		HashMap<String, String> param = new HashMap<String, String>();
	    param.put("FeedbackID", Integer.toString(FeedbackID));
	    param.put("Comment", Comment);
	    param.put("Rate",Float.toString(Rate));
		HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/PassengerSaveDriverFeedback", "POST", param, l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void miSonoConnesso(String GCM_ID, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("GCM_ID",GCM_ID);
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/miSonoConnesso", "POST", param,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void miSonoDisconnesso(HTTPStartListener s, HTTPonCompleteListener c, Login l) {
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/autostop/miSonoDisconnesso", "POST", null,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void getProfileInfo(HTTPStartListener s, HTTPonCompleteListener c, Login l) {
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/profiles/getprofileinfo/", "POST", null, l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void deleteUserAndAccount(HTTPStartListener s, HTTPonCompleteListener c, Login l){
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/Profiles/deleteUserAndAccount", "POST", null,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void editAccount(String UserName, String Password, String Role, String Name, String Surname, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("UserName", UserName);
        param.put("Password", Password);
        param.put("Role", Role);
        param.put("Name", Name);
        param.put("Surname", Surname);
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/Profiles/editAccount", "POST", param,l);
        HTTPCalls.start(restInput,s,c);
	}
	public static void createAccount(String UserName, String Password, String Role, String Name, String Surname, HTTPStartListener s, HTTPonCompleteListener c, Login l) {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("UserName", UserName);
        param.put("Password", Password);
        param.put("Role", Role);
        param.put("Name", Name);
        param.put("Surname", Surname);
        HTTPInput restInput = new HTTPInput(DOMINE + "/api/Profiles/createAccount", "POST", param,l);
        HTTPCalls.start(restInput,s,c);
	}
}