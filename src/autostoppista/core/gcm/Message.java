package autostoppista.core.gcm;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {
	public List<String> registration_ids = new ArrayList<String>();
	public Map<String,Object> data = new HashMap<String,Object>();
		public Message addID(String id) {
		registration_ids.add(id);
		return this;
	}
	public Message delID(String id) {
		registration_ids.remove(id);
		return this;
	}
	public Message addData(String key, Object value) {
		data.put(key, value);
		return this;
	}
}
