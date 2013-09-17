package integration.listener.gps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import business.applicationservice.ApplicationServiceGeneral;
import business.entity.GPS;
import business.entity.Intervento;
import utility.ErrorPrinter;

import java.io.IOException;
import java.util.List;

public class AddressParser {
    private static final int FIRST = 0;
    private static final int DEFAULT_MAX_RESULT = 5;

    private static final String COMMA = ", ";

    public static GPS getCoordinates(Intervento intervento) {
        String strAddress = intervento.getIndirizzo() + COMMA + intervento.getCap() + COMMA + intervento.getCitta();

        Context context = ApplicationServiceGeneral.activity.getApplicationContext();
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GPS gps = null;

        Log.d("AndroidRuntime", "Geocoder.isPresent() = " + Geocoder.isPresent());
        if (Geocoder.isPresent()) {
            try {
                address = coder.getFromLocationName(strAddress, DEFAULT_MAX_RESULT);

                if ((address != null) && (address.size() > 0)) {

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
        }

        return gps;
    }
}
