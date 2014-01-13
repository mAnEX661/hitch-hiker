package autostoppista.android.fragments;

import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import autostoppista.app.MainActivity;
import autostoppista.app.ModeUse;
import autostoppista.core.gps.Position;
import autostoppista.core.http.HTTPStartListener;
import autostoppista.core.restcall.Login;
import autostoppista.core.restcall.LoginSaver;
import autostoppista.app.R;

public abstract class ExtendedFragment extends Fragment implements HTTPStartListener {
	protected MainActivity parent;
	protected ViewGroup ll;
	protected ExtendedFragment cfi = this;
	public Login getLogin() {
		return parent.getLogin();
	}
	public void Init() {
		parent.Init();
	}
	public MainActivity getParent() {
		return parent;
	}
	public LoginSaver getLoginSaver() {
		return parent.getLoginSaver();
	}
	public ModeUse getMode() {
		return parent.getMode();
	}
	public void setMode(ModeUse m) {
		parent.setMode(m);
	}
	public Position getPosition() {
		return parent.getPosition();
	}
	public String getRole() {
		return parent.getRole();
	}
    public void setWaitMode(boolean e) {
    	ViewGroup wLayout = (ViewGroup) parent.findViewById(R.id.wait);
    	if (e) wLayout.setVisibility(View.VISIBLE); else wLayout.setVisibility(View.INVISIBLE);
    	for ( int i = 0; i < ll.getChildCount();  i++ ){
    	    View view = ll.getChildAt(i);
    	    view.setEnabled(!e);
    	}
	}
	@Override
	public void onCallStart() {
		setWaitMode(true);
	}
	public void putMessage(String message) {}
}

