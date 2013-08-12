package presentation.boundary;

import com.adisys.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import presentation.controller.FrontController;
import presentation.controller.FrontControllerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SchermataPrincipale extends Activity implements Boundary {

    private List<String> fileList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FrontController fc = FrontControllerFactory.buildInstance();

        Spinner fileSpinner = (Spinner)findViewById(R.id.spinner);

        fileList = (List<String>) fc.processRequest("ElencaPianificazioni", null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileSpinner.setAdapter(adapter);
    }

}
