package business.listener.gps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import business.applicationservice.ApplicationServiceGeneral;
import business.entity.GPS;
import util.ErrorPrinter;

import java.io.IOException;
import java.util.List;

public class AdressParser {
    private static final int FIRST = 0;
    private static final int DEFAULT_MAX_RESULT = 5;

    public static GPS getCoordinates(String strAddress) {
        Context context = ApplicationServiceGeneral.activity.getApplicationContext();
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GPS gps = null;


        try {
            address = coder.getFromLocationName(strAddress, DEFAULT_MAX_RESULT);

            if (address != null) {

                Address location = address.get(FIRST);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                gps = new GPS();
                gps.setLatitudine(latitude);
                gps.setLongitudine(longitude);
            }
        } catch (IOException e) {
            ErrorPrinter.print(e);
        }


        return gps;
    }
}
