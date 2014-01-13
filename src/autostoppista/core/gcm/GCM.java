package autostoppista.core.gcm;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.widget.Toast;
import autostoppista.app.MainActivity;
import autostoppista.core.http.HTTPCalls;
import autostoppista.core.http.HTTPInputBasic;
import autostoppista.core.http.HTTPStartListener;
import autostoppista.core.http.HTTPonCompleteListener;
import autostoppista.core.restcall.RestCalls;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GCM {
    private MainActivity a;
	private static final String GCM_SENDER_ID = "131654239725";
	private static final String GOOGLE_API_KEY = "AIzaSyCM1xZjqgAw8qrfmh-y6bfGzRUiCfGzr8Y";
    private GoogleCloudMessaging gcm;
    private String regid;
    public GCM(MainActivity a) {
		this.a = a;
	}
    public void Init() {
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(a);
            regid = getRegistrationId(a.getApplicationContext());

            if (regid.isEmpty()) {
                register();
            }
        }
    }
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(a);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, a,
                        9000).show();
            } else {
            	Toast.makeText(a, "Questo dispositivo non è supportato.", Toast.LENGTH_LONG).show();
                a.finish();
            }
            return false;
        }
        return true;
    }
    public String getID() {
		return regid;
	}
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = a.getSharedPreferences(GCM.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("registration_id", regId);
        editor.putInt("appVersion", appVersion);
        editor.commit();
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = a.getSharedPreferences(GCM.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString("registration_id", "");
        if (registrationId.isEmpty()) {
            return "";
        }
        int registeredVersion = prefs.getInt("appVersion", Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }
    private void register() {
		AsyncTask<Void, Void, Void> u = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
			    	if (gcm == null) {
					    gcm = GoogleCloudMessaging.getInstance(a.getApplicationContext());
					}
					regid = gcm.register(GCM_SENDER_ID);
					RestCalls.miSonoConnesso(regid, null, null, a.getLogin());
					storeRegistrationId(a.getApplicationContext(), regid);
				} catch(IOException e) {}
				return null;
			}
		};
    	u.execute((Void[])null);
	}
    public void send(Message a, HTTPStartListener s, HTTPonCompleteListener c) {
		HTTPCalls.startBasic(new HTTPInputBasic("https://android.googleapis.com/gcm/send", "POST", "key=" + GOOGLE_API_KEY, "application/json", new Gson().toJson(a)), s, c);
    }
    public void send(Map<String, Object> data ,List<String> IDs, HTTPStartListener s, HTTPonCompleteListener c) {
		Message a = new Message();
		a.registration_ids = IDs;
		a.data = data;
		HTTPCalls.startBasic(new HTTPInputBasic("https://android.googleapis.com/gcm/send", "POST", "key=" + GOOGLE_API_KEY, "application/json", new Gson().toJson(a)), s, c);
    }
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    public String getRegId(){
    	return regid;
    }
}
