package integration.xml.validator;

import android.content.res.AssetManager;
import android.util.Log;
import business.applicationservice.ApplicationServiceGeneral;
import utility.ErrorPrinter;

import java.io.File;


public class Validator {
    public static boolean validate(File xmlFile, String schemaAssetPath) {

        Boolean valid = false;
        AssetManager assetManager = ApplicationServiceGeneral.activity.getAssets();

        //TODO not yet implemented

        //SchemaFactory schemaFactory = new XMLSchemaFactory();

        /*
        try {
            InputStream inputStream = assetManager.open(schemaAssetPath);
            Schema schema = schemaFactory.newSchema(new StreamSource(inputStream));
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            valid = true;
        } catch (SAXException e) {
            Log.i("AndroidRuntime", "File " + xmlFile + " not valid");
        } catch (IOException e) {
            ErrorPrinter.print(e);
        }
        */

        return true;
    }
}
