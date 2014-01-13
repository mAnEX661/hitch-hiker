package autostoppista.core.gps;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSManager implements LocationListener {
    private Position P;
    public GPSManager(Context context) {
    	LocationManager locationManager;
		Criteria criteria;
		String provider;
		P = new Position();
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 1, 0, this);
		locationManager.getLastKnownLocation(provider);
    }
    public Position getPosition() {
       return P;
    }
    @Override
	public void onLocationChanged(Location location) {
    	P.Altitude=(location.getAltitude());
    	P.Longitude=(location.getLongitude());
    	P.Latitude=(location.getLatitude());
    	P.Time=(location.getTime());
    }
    @Override
    public void onProviderDisabled(String arg0) {
	}
    @Override
    public void onProviderEnabled(String arg0) {
    }
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    }
}