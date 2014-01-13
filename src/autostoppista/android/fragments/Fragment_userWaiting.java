package autostoppista.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import autostoppista.android.adapters.AdapterUserItem;
import autostoppista.app.MainActivity;
import autostoppista.app.ModeUse;
import autostoppista.core.http.HTTPOutput;
import autostoppista.core.http.HTTPonCompleteListener;
import autostoppista.core.restcall.UserInfo;
import autostoppista.core.restcall.RestCalls;
import autostoppista.app.R;
import com.google.gson.Gson;

public class Fragment_userWaiting extends ExtendedFragment {
	private ListView listview;
	private Button manage;
	AdapterUserItem aa;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		setMode(ModeUse.Consume);
		View view = inflater.inflate(R.layout.user_waiting,container, false);
		aa= new AdapterUserItem(parent.getBaseContext(),R.layout.user_list_item);
		listview = (ListView) view.findViewById(R.id.user_waiting_ListView);
		listview.setAdapter(aa);
		manage = (Button) view.findViewById(R.id.user_waiting_button_Mafeedback);
		if (getRole().equals(RestCalls.PASSENGER)) manage.setVisibility(View.GONE);
		else manage.setVisibility(View.VISIBLE);
		manage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setMode(ModeUse.Manage);
				getParent().switchFragment(Fragment_userDetail.newAssignParent(parent,null));				
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				UserInfo u = (UserInfo) adapter.getItemAtPosition(position);
				parent.switchFragment(Fragment_userDetail.newAssignParent(parent, u));
			}
		});
		ll = (ViewGroup) view;
		setWaitMode(false);
		return view;
	}
	
	private void popolateListViewFromRest() {
		RestCalls.GetPassengersList(getPosition().getLatitude(), getPosition().getLongitude(), cfi,
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
						aa.clear();
						Gson g = new Gson();
						UserInfo[] p = g.fromJson(r.getData(), UserInfo[].class);
						for (UserInfo i : p){aa.add(i);}
					} else {
						Toast.makeText(parent.getApplicationContext(),
								"Si è verificato un errore:\n"+r.getData(),
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}, getLogin());
	}
	public static final Fragment_userWaiting newAssignParent(MainActivity parent) {
		Fragment_userWaiting fl = new Fragment_userWaiting();
		fl.parent = parent;
		return fl;
	}
	@Override
	public void putMessage(String message) {
		if (getRole().equals(RestCalls.DRIVER)){
			popolateListViewFromRest();
		}
		if (getRole().equals(RestCalls.PASSENGER)){
			Gson g = new Gson();
			UserInfo p = g.fromJson(message, UserInfo.class);
			parent.getListIDs().add(p.getGCM_ID());
			aa.add(p);
		}
	}
}
