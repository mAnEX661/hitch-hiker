package autostoppista.core.http;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;
class HTTPAsyncBasic extends AsyncTask<HTTPInputBasic, Void, HTTPOutput> {
	private HTTPStartListener s;
	private HTTPonCompleteListener c;
	public HTTPAsyncBasic(){
		this.s = null;
		this.c = null;
	}
	public HTTPAsyncBasic(HTTPStartListener s){
		this.s = s;
		this.c = null;
	}
	public HTTPAsyncBasic(HTTPonCompleteListener c){
		this.s = null;
		this.c = c;
	}
	public HTTPAsyncBasic(HTTPStartListener s, HTTPonCompleteListener c){
		this.s = s;
		this.c = c;
	}
	@Override
	protected HTTPOutput doInBackground(HTTPInputBasic... params){
		try {
            String url = params[0].getPath();
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(params[0].getMethod());
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", params[0].getContentType());
            if (params[0].getAuthorization()!=null){
            	con.setRequestProperty("Authorization",params[0].getAuthorization());
            }
            String body = params[0].getBody();
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            if (body!=null)
            	wr.writeBytes(body);
            wr.flush();
            if (Math.ceil(con.getResponseCode()/100 - 4.0) > 0){
            	return new HTTPOutput(con.getResponseCode(), con.getResponseMessage(), null);
            }    
            StringBuilder response;
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            con.disconnect();
            return new HTTPOutput(con.getResponseCode(), con.getResponseMessage(),response.toString());
        } catch (MalformedURLException ex) {
        	return new HTTPOutput(600, "Error: " + ex.getMessage(), null);
        } catch (IOException ex) {
        	return new HTTPOutput(600, "Error: " + ex.getMessage(), null);
        }
	}
	@Override
	protected void onPreExecute() {
		if (s!=null)
			s.onCallStart();
		super.onPreExecute();
	}
	@Override
	protected void onPostExecute(HTTPOutput result) {
		if (c!=null)
			c.onComplete(result);
		super.onPostExecute(result);
	}
	}