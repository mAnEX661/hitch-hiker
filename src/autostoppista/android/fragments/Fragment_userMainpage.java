package autostoppista.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import autostoppista.app.MainActivity;
import autostoppista.app.ModeUse;
import autostoppista.core.http.HTTPOutput;
import autostoppista.core.http.HTTPonCompleteListener;
import autostoppista.core.restcall.RestCalls;

import autostoppista.app.R;
import com.google.gson.Gson;

public class Fragment_userMainpage extends ExtendedFragment {
	private Button request, feedbackmanage;
	private EditText detail;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_main_page,container, false);
		request = (Button) view.findViewById(R.id.user_main_page_button_request);
		feedbackmanage = (Button) view.findViewById(R.id.user_main_page_feedback_manage);
		detail = (EditText) view.findViewById(R.id.user_main_page_detail);
		request.setOnClickListener(new View.OnClickListener() {
			@Override 
			public void onClick(View arg0) {
				RestCalls.MiServeUnPassaggio(getPosition().getLatitude(), 
				getPosition().getLongitude(), 
				detail.getText().toString(), cfi, 
				new HTTPonCompleteListener() { 
					@Override
					public void onComplete(HTTPOutput r) {
						setWaitMode(false);  
						if (r.getCode()>=400){
							Toast.makeText(parent.getApplicationContext(),
									"Si è verificato un errore:\n"+r.getMessage(),
									Toast.LENGTH_LONG).show();
						} else {
							if (!r.getData().contains("ERROR ")){
								setMode(ModeUse.Consume);
								Gson js = new Gson();
								String s = js.fromJson(r.getData(), String.class);
								if (s.startsWith("OK")){
									getParent().switchFragment(Fragment_userWaiting.newAssignParent(parent));
								} else {
									Toast.makeText(parent.getApplicationContext(),
											"Si è verificato un errore.\n"+s,
											Toast.LENGTH_LONG).show();
								}
							} else {
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getData(),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}, getLogin());
			}
		});
		feedbackmanage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setMode(ModeUse.Manage);
				getParent().switchFragment(Fragment_userDetail.newAssignParent(parent, null));
			}
		});
		ll = (ViewGroup) view;
		setWaitMode(false);
		return view;
	}
	public static final Fragment_userMainpage newAssignParent(MainActivity parent) {
		Fragment_userMainpage fl = new Fragment_userMainpage();
		fl.parent = parent;
		return fl;
	}
}
