package autostoppista.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import autostoppista.app.MainActivity;
import autostoppista.app.ModeUse;
import autostoppista.core.http.HTTPOutput;
import autostoppista.core.http.HTTPonCompleteListener;
import autostoppista.core.restcall.FeedbackReturn;
import autostoppista.core.restcall.RestCalls;
import autostoppista.app.R;

public class Fragment_feedbackForm extends ExtendedFragment {
	private Button send;
	private RatingBar rating;
	private EditText comment;
	private TextView caption;
	private FeedbackReturn f;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.feedback_form, container, false);
		send = (Button) view.findViewById(R.id.feedbackForm_button_ok);
		rating = (RatingBar) view.findViewById(R.id.feedbackForm_rating);
		comment = (EditText) view.findViewById(R.id.feedbackForm_commentText);
		caption = (TextView) view.findViewById(R.id.feedbackForm_userName);
		if (getMode()==ModeUse.Consume){ 
			comment.setEnabled(false);
			comment.setFocusable(false);
			rating.setEnabled(false);
			rating.setFocusable(false);
			send.setEnabled(false);
			send.setVisibility(View.GONE);
			if (f!=null){
				comment.setText(f.Comment);
				rating.setRating(f.Mark);
			}
			caption.setText("Dettagli sul feedback:");
		}
		
		if (getMode()==ModeUse.Manage){
			comment.setEnabled(true);
			comment.setFocusable(true);
			rating.setEnabled(true);
			rating.setFocusable(true);
			send.setEnabled(true);
			send.setVisibility(View.VISIBLE);
			if (getRole().equals(RestCalls.PASSENGER)){
				caption.setText("Valuta il conducente.");
			} else {
				caption.setText("Valuta il passeggero.");
			}
		}
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (getRole().equals(RestCalls.PASSENGER)) {
					RestCalls.PassengerSaveDriverFeedback(f.ID,comment.getText().toString(),rating.getRating(), cfi, 
					new HTTPonCompleteListener() {
					@Override
					public void onComplete(HTTPOutput r) {
						if (r.getCode()>=400){
							Toast.makeText(parent.getApplicationContext(),
									"Si è verificato un errore:\n"+r.getMessage(),
									Toast.LENGTH_LONG).show();
						} else {
							if (!r.getData().contains("ERROR ")){
								Toast.makeText(parent, "Salvato", Toast.LENGTH_LONG).show();
								getParent().switchFragment(Fragment_userDetail.newAssignParent(parent, null));
							} else {
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getData(),
										Toast.LENGTH_LONG).show();
							}
						}
					}
					}, getLogin());
				}
				if (getRole().equals(RestCalls.DRIVER)) {
					RestCalls.DriverSavePassengerFeedback(f.ID,comment.getText().toString(),rating.getRating(), cfi, 
						new HTTPonCompleteListener() {
						@Override
						public void onComplete(HTTPOutput r) {
							if (r.getCode()>=400){
								Toast.makeText(parent.getApplicationContext(),
										"Si è verificato un errore:\n"+r.getMessage(),
										Toast.LENGTH_LONG).show();
							} else {
								if (!r.getData().contains("ERROR ")){
									Toast.makeText(parent, "Salvato", Toast.LENGTH_LONG).show();
									getParent().switchFragment(Fragment_userDetail.newAssignParent(parent, null));
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
		});
		ll = (ViewGroup) view;
		setWaitMode(false);
		return view;
	}
	public FeedbackReturn getFeedback() {
		return f;
	}
	public void setFeedback(FeedbackReturn f) {
		this.f = f; 
	}
	public static final Fragment_feedbackForm newAssignParent(MainActivity parent, FeedbackReturn f) {
		Fragment_feedbackForm fl = new Fragment_feedbackForm();
		fl.f = f;
		fl.parent = parent;return fl;
	}	
}
