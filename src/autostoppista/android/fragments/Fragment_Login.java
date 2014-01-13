package autostoppista.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import autostoppista.app.MainActivity;
import autostoppista.core.http.HTTPOutput;
import autostoppista.core.http.HTTPonCompleteListener;
import autostoppista.core.restcall.Login;
import autostoppista.core.restcall.UserInfo;
import autostoppista.core.restcall.RestCalls;
import autostoppista.app.R;

import com.google.gson.Gson;

public class Fragment_Login extends ExtendedFragment{
	private Button save, cancel;
	private EditText username, password;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_form,container, false);
		save = (Button) view.findViewById(R.id.login_button_save);
		cancel = (Button) view.findViewById(R.id.login_button_cancel);
		username = (EditText) view.findViewById(R.id.login_userName);
		password = (EditText) view.findViewById(R.id.login_password);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				RestCalls.getProfileInfo(cfi,
				new HTTPonCompleteListener() {
					@Override
					public void onComplete(HTTPOutput r) {
						if (r.getCode()>=400){
							setWaitMode(false);
							Toast.makeText(parent.getApplicationContext(),
									"Si è verificato un errore:\n"+r.getMessage(),
									Toast.LENGTH_LONG).show();
						} else {
							if (!r.getData().contains("ERROR ")){
								setWaitMode(false);
								Gson js = new Gson();
								UserInfo prin = js.fromJson(r.getData(), UserInfo.class);
								
								Login l = new Login();
								l.setLoginInfo(username.getText().toString(),password.getText().toString());
								getLoginSaver().saveLoginData(l);
								getParent().Init();
								prin.setGCM_ID(parent.getGCM().getRegId());
								RestCalls.miSonoConnesso(parent.getGCM().getRegId(), null, null, l);
								getParent().setProfileInfo(prin);
								if (getRole().equalsIgnoreCase(RestCalls.PASSENGER)) {
									getParent().switchFragment(Fragment_userMainpage.newAssignParent(parent));
								} else if (getRole().equalsIgnoreCase(RestCalls.DRIVER)) {
									getParent().switchFragment(Fragment_userWaiting.newAssignParent(parent));
								} else {
									Toast.makeText(parent.getApplicationContext(),
											"Versione non supportata!",
											Toast.LENGTH_LONG).show();
								}
							} else {
								Toast.makeText(parent.getApplicationContext(),
									"Si è verificato un errore:\n"+r.getData(),
									Toast.LENGTH_LONG).show();
							}
						}
					}
				},
				new Login().setLoginInfo(username.getText().toString(), password.getText().toString()));
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				parent.closeApplication();				
			}
		});
		username.setText(getLoginSaver().getLoginData().getUsername());
		password.setText(getLoginSaver().getLoginData().getPassword());
		ll = (ViewGroup) view;
		setWaitMode(false);
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	public static final Fragment_Login newAssignParent(MainActivity parent) {
		Fragment_Login fl = new Fragment_Login();
		fl.parent = parent;
		return fl;
	}
}
