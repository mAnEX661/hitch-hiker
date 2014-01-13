package autostoppista.core.gps;

import java.util.Date;

import android.location.Location;
public class Position
	   	{
	double Latitude ;
	double Longitude;
	double Altitude;
	long Time;
	public float GetDistance(Position Other) {
		float [] distance = new float[2];
		Location.distanceBetween(this.Latitude, this.Longitude, Other.getLatitude(), Other.getLongitude(), distance);
		return distance[0];
	}
	public double getLatitude() {
		return Latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public double getAltitude() {
		return Altitude;
	}
	public Date getDateTime() {
		Date d = new Date(Time);
		return d;
	}
	@Override
	public String toString() {
		return "Lat: " + String.valueOf(Latitude) + 
				"\nLon: " + String.valueOf(Longitude) + 
				"\nAlt: " + String.valueOf(Altitude) + 
				"\nTime: " + getDateTime().toString(); 
	}
	
}
