package autostoppista.android.adapters;  

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import autostoppista.core.restcall.FeedbackReturn;
import autostoppista.app.R;

public class AdapterFeedbackItem extends ObservableCollection<FeedbackReturn> {
	private TextView nickname,comment;
	private RatingBar rating;
	
	public AdapterFeedbackItem(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.feedback_list_item, null);
        nickname = (TextView) convertView.findViewById(R.id.feedback_item_userName);
        comment = (TextView) convertView.findViewById(R.id.feedback_item_comment1);
        rating = (RatingBar) convertView.findViewById(R.id.feedback_item_rating1);
        FeedbackReturn u = getList().get(position);
        if (u!=null){
	        nickname.setText(u.Writer + " (" + u.Date +")");
	        comment.setText(u.Comment);
	        rating.setRating(u.Mark);
        }
        return convertView;
	}
}
