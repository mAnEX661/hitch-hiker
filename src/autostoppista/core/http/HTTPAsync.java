package autostoppista.core.http;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;
class HTTPAsync extends AsyncTask<HTTPInput, Void, HTTPOutput> {
	private HTTPStartListener s;
	private HTTPonCompleteListener c;
	public HTTPAsync(){
		this.s = null;
		this.c = null;
	}
	public HTTPAsync(HTTPStartListener s){
		this.s = s;
		this.c = null;
	}
	public HTTPAsync(HTTPonCompleteListener c){
		this.s = null;
		this.c = c;
	}
	public HTTPAsync(HTTPStartListener s, HTTPonCompleteListener c){
		this.s = s;
		this.c = c; 
	}
	@Override
	public HTTPOutput doInBackground(HTTPInput... inp){
		try {
            String url = inp[0].getPath();
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(inp[0].getMethod());
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (inp[0].getloginInfo()!=null){
            	con.setRequestProperty("Authorization",inp[0].getloginInfo());
            }
            String urlParameters = inp[0].getUrlEncodedParams();
            con.setDoOutput(true);
            if (urlParameters!=null){
            	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
               	wr.writeBytes(urlParameters);
               	wr.flush();
            }
            if (con.getResponseCode()>=400){
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
        	return new HTTPOutput(600, "Error: "+ ex.getMessage(),null);
        } catch (IOException ex) {
        	return new HTTPOutput(601, "Error: "+ ex.getMessage(),null);
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