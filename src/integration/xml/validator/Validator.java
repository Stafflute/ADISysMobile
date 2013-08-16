package integration.xml.validator;

import android.content.res.AssetManager;
import android.util.Log;
import business.applicationservice.ApplicationServiceGeneral;
import mf.javax.xml.transform.stream.StreamSource;
import mf.javax.xml.validation.Schema;
import mf.javax.xml.validation.SchemaFactory;
import mf.org.apache.xerces.jaxp.validation.XMLSchemaFactory;
import org.xml.sax.SAXException;
import util.ErrorPrinter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Validator {
    public static boolean validate(File xmlFile, String schemaAssetPath) {
        Boolean valid = false;
        AssetManager assetManager = ApplicationServiceGeneral.activity.getAssets();
        SchemaFactory schemaFactory = new XMLSchemaFactory();

        try {
            InputStream inputStream = assetManager.open(schemaAssetPath);
            Schema schema = schemaFactory.newSchema(new StreamSource(inputStream));
            mf.javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            valid = true;
        } catch (SAXException e) {
            Log.i("AndroidRuntime", "File " + xmlFile + " not valid");
        } catch (IOException e) {
            ErrorPrinter.print(e);
        }

        return valid;
    }
}
