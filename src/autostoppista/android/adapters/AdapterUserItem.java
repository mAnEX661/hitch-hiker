package autostoppista.android.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import autostoppista.core.restcall.UserInfo;
import autostoppista.app.R;

public class AdapterUserItem extends ObservableCollection<UserInfo> {
	private TextView name,comment;
	
	public AdapterUserItem(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.user_list_item, null);
        name = (TextView) convertView.findViewById(R.id.user_list_name);
        comment = (TextView) convertView.findViewById(R.id.user_list_comment);
        
        UserInfo u = getList().get(position);
        name.setText(u.getName()+ " " +u.getSurname());
        comment.setText(u.getText());
        return convertView;
    }  
}
