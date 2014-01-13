package autostoppista.app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import autostoppista.android.fragments.ExtendedFragment;
import autostoppista.android.fragments.Fragment_Login;
import autostoppista.android.fragments.Fragment_userDetail;
import autostoppista.android.fragments.Fragment_userWaiting;
import autostoppista.core.gcm.GCM;
import autostoppista.core.gcm.GcmBroadcastReceiver;
import autostoppista.core.gps.GPSManager;
import autostoppista.core.gps.Position;
import autostoppista.core.restcall.Login;
import autostoppista.core.restcall.LoginSaver;
import autostoppista.core.restcall.UserInfo;
import autostoppista.core.restcall.RestCalls;

public class MainActivity extends Activity{
	protected Login l;
	protected LoginSaver ls;
	protected GPSManager gps;
	protected UserInfo prin;
	protected ModeUse mode;
	private ArrayList<String> ids = new ArrayList<String>();
	private GCM a;
	public static boolean IsRunning = false;
	private ExtendedFragment actualFragment = Fragment_Login.newAssignParent(this);
	public Login getLogin() {
		return l;
	}
	public void clearListIDs() {
		ids.clear();
	}
	public ArrayList<String> getListIDs() {
		return  ids;
	}
	public Position getPosition() {
		return gps.getPosition();
	}
	public LoginSaver getLoginSaver() {
		return ls;
	}
	public String getRole() {
		return prin.getRole();
	}
	public UserInfo getProfileInfo() {
		return prin;
	}
	public void setProfileInfo(UserInfo prin) {
		this.prin = prin;
	}
	public GCM getGCM() {
		return a;
	}
	public void Init() {
		if (ls == null) {
			ls = new LoginSaver(this);
		}
		if (ls != null) {
			l = ls.getLoginData();
		}
		if (a == null) {
			a = new GCM(this);
			a.Init();
		}
		if (gps == null) {
			gps = new GPSManager(getApplicationContext());
		}
	}
	public void switchFragment(ExtendedFragment f) {
		actualFragment = f;
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.replace(R.id.main_container, f);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
		Init();
	}
	public void setMode(ModeUse m) {
		mode = m;
	}
	public ModeUse getMode() {
		return mode;
	}
	public void closeApplication() {
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RestCalls.setDOMINE("http://lap2.azurewebsites.net");
		Init();
		setContentView(R.layout.activity_main);
		switchFragment(Fragment_Login.newAssignParent(this));
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void HandleGCMMessage(Bundle bu)  {
		String msgtype = bu.getString("Type","");
		String msgmess = bu.getString("Message");
		if ("UPL".equals(msgtype) && getRole().equals(RestCalls.DRIVER)) { 
			if (actualFragment instanceof Fragment_userWaiting) {
				actualFragment.putMessage(null);
			}
		}
		if ("PCO".equals(msgtype) && getRole().equals(RestCalls.PASSENGER)) { 
			if (actualFragment instanceof Fragment_userWaiting) {
				actualFragment.putMessage(msgmess);
			}
		}
		if ("ACC".equals(msgtype) && getRole().equals(RestCalls.DRIVER)) { 
			if (actualFragment instanceof Fragment_userDetail) {
				actualFragment.putMessage(msgmess);
			}
		}
	}
	@Override
	protected void onResume() {
		IsRunning = true;
		Init();
		GcmBroadcastReceiver.main = this;
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		IsRunning = true;
		GcmBroadcastReceiver.main = null;
	}
}
