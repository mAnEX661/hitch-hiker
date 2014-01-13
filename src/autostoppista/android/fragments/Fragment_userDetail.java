package autostoppista.android.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import autostoppista.android.adapters.AdapterFeedbackItem;
import autostoppista.app.MainActivity;
import autostoppista.app.ModeUse;
import autostoppista.core.gcm.Message;
import autostoppista.core.http.HTTPOutput;
import autostoppista.core.http.HTTPonCompleteListener;
import autostoppista.core.restcall.FeedbackReturn;
import autostoppista.core.restcall.FeedbackSummary;
import autostoppista.core.restcall.UserInfo;
import autostoppista.core.restcall.RestCalls;
import autostoppista.app.R;

import com.google.gson.Gson;

public class Fragment_userDetail extends ExtendedFragment {
	private UserInfo us;
	private ListView listview;
	private Button ok;
	private RatingBar rb;
	private TextView caption;
	AdapterFeedbackItem aa ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_detail,container, false);
		aa = new AdapterFeedbackItem(parent.getBaseContext(),R.layout.feedback_list_item);
		listview = (ListView) view.findViewById(R.id.user_detail_listView);
		ok = (Button) view.findViewById(R.id.user_detail_button_ok);
		caption = (TextView) view.findViewById(R.id.user_detail_name);
		rb = (RatingBar) view.findViewById(R.id.user_detail_rating);
		listview.setAdapter(aa);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (getRole().equals(RestCalls.PASSENGER)){
					Message a = new Message();
					a.addData("Type", "ACC").addData("Message", us.getUserName());
					a.registration_ids = parent.getListIDs();
					parent.getGCM().send(a, cfi, null);
					RestCalls.Accetto(getUser().getUserName(), cfi,
					new HTTPonCompleteListener() {
						@Override
						public void onComplete(HTTPOutput r) {
							setWaitMode(false);
							if (r.getCode()>=400){
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getMessage(),
										Toast.LENGTH_LONG).show();
							} else {
								Gson g = new Gson();
								String out = g.fromJson(r.getData(), String.class);
								if (!r.getData().contains("ERROR ")){
									Toast.makeText(parent.getApplicationContext(),
											"Richiesta confermata",
											Toast.LENGTH_SHORT).show();
									getParent().switchFragment(Fragment_userMainpage.newAssignParent(parent));
								} else {
									Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+out,
										Toast.LENGTH_LONG).show();
								}
							}
						} 
					}, getLogin());
				} 
				if (getRole().equals(RestCalls.DRIVER)){
					Message m = new Message();
					m.addID(us.getGCM_ID()).addData("Type", "PCO").addData("Message",parent.getProfileInfo());
					parent.getGCM().send(m, cfi, null);
				}
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				FeedbackReturn u = (FeedbackReturn) adapter.getItemAtPosition(position);
				parent.switchFragment(Fragment_feedbackForm.newAssignParent(parent, u));
			}
		});
		ll = (ViewGroup) view;	
		setWaitMode(false);
		if (getMode()==ModeUse.Consume) {
			caption.setText(getUser().getName() + " " + getUser().getSurname());
			ok.setVisibility(View.VISIBLE);
			rb.setVisibility(View.VISIBLE);
			caption.setVisibility(View.VISIBLE);
		}
		else {
			ok.setVisibility(View.GONE);
			rb.setVisibility(View.GONE);
			caption.setVisibility(View.GONE);
		}
		if (getMode().equals(ModeUse.Consume)){
			if (getRole().equals(RestCalls.PASSENGER)){
				RestCalls.GetFeedbackListDriver(us.getUserName(), cfi, 
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
								Gson g = new Gson();
								FeedbackSummary f = g.fromJson(r.getData(), FeedbackSummary.class);
								caption.setText(us.getName()+ " " +us.getSurname());
								rb.setRating(f.ComplessiveMark);
								for (FeedbackReturn i : f.List){aa.add(i);}
							} else {
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getData(),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}, getLogin());
			}
			if (getRole().equals(RestCalls.DRIVER)){
				RestCalls.GetFeedbackListPassenger(us.getUserName(), cfi, 
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
								Gson g = new Gson();
								FeedbackSummary f = g.fromJson(r.getData(), FeedbackSummary.class);
								caption.setText(us.getName()+ " " +us.getSurname());
								rb.setRating(f.ComplessiveMark);
								for (FeedbackReturn i : f.List){aa.add(i);}
							} else {
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getData(),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}, getLogin());
			}
		}
		if (getMode().equals(ModeUse.Manage)){
			if (getRole().equals(RestCalls.PASSENGER)){
				RestCalls.GetFeedbackToEditPassenger(cfi, 
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
								Gson g = new Gson();
								FeedbackSummary f = g.fromJson(r.getData(), FeedbackSummary.class);
								caption.setText(parent.getProfileInfo().getName()+ " " +parent.getProfileInfo().getSurname());
								rb.setRating(f.ComplessiveMark);;
								for (FeedbackReturn i : f.List){aa.add(i);}
							} else {
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getData(),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}, getLogin());
			}
			if (getRole().equals(RestCalls.DRIVER)){
				RestCalls.GetFeedbackToEditDriver(cfi, 
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
								Gson g = new Gson();
								FeedbackSummary f = g.fromJson(r.getData(), FeedbackSummary.class);
								caption.setText(parent.getProfileInfo().getName()+ " " +parent.getProfileInfo().getSurname());
								rb.setRating(f.ComplessiveMark);
								for (FeedbackReturn i : f.List){aa.add(i);}
							} else {
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getData(),
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}, getLogin());
			}
		}
		return view;
	}
	public void putMessage(String m){
		if (parent.getProfileInfo().getRole().equals(RestCalls.DRIVER)){
			Gson g =  new Gson();
			String s = g.fromJson(m, String.class);
			setWaitMode(false);
			if (s.equals(parent.getProfileInfo().getUserName())){
				AlertDialog alertDialog;
				alertDialog = new AlertDialog.Builder(parent).create();
				alertDialog.setTitle("Notifica");
				alertDialog.setMessage("L'utente ha accettato la tua offerta!");
				alertDialog.show();
				parent.switchFragment(Fragment_userWaiting.newAssignParent(parent));
			} else {
				AlertDialog alertDialog;
				alertDialog = new AlertDialog.Builder(parent).create();
				alertDialog.setTitle("Notifica");
				alertDialog.setMessage("L'utente ha rifiutato la tua offerta!");
				alertDialog.show();
				parent.switchFragment(Fragment_userWaiting.newAssignParent(parent));
			}
		}  
	}
	public UserInfo getUser() {
		return us;
	}
	public void setUser(UserInfo us) {
		this.us = us;
	}
	public static final Fragment_userDetail newAssignParent(MainActivity parent, UserInfo u) {
		Fragment_userDetail fl = new Fragment_userDetail();
		fl.parent = parent;
		fl.setUser(u);
		return fl;
	}
}
