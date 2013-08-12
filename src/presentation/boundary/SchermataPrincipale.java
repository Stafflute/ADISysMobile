package presentation.boundary;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.*;
import com.adisys.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import presentation.controller.FrontController;
import presentation.controller.FrontControllerFactory;
import util.Parameter;

import java.util.ArrayList;
import java.util.List;

public class SchermataPrincipale extends Activity implements Boundary {

    private List<String> fileList = new ArrayList<>();

    private String selectedPianificazione = null;

    private Context context;

    private Activity activity = this;

    private FrontController fc = FrontControllerFactory.buildInstance();

    private OnItemSelectedListener fileSpinnerListener = new OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedPianificazione = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            selectedPianificazione = null;
        }
    };

    private OnClickListener fileConfirmListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            //TODO implementare
            Log.d("AndroidRuntime", selectedPianificazione);
            Parameter parameter = new Parameter();
            parameter.setValue("activity", activity);
            parameter.setValue("pianificazione", selectedPianificazione);
            fc.processRequest("MostraSchermataPianificazione", parameter);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principale);

        context = this;

        Spinner fileSpinner = (Spinner)findViewById(R.id.spinner);
        Button fileConfirm = (Button)findViewById(R.id.fileConfirm);

        fileList = (List<String>) fc.processRequest("ElencaPianificazioni", null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileSpinner.setAdapter(adapter);

        fileSpinner.setOnItemSelectedListener(fileSpinnerListener);
        fileConfirm.setOnClickListener(fileConfirmListener);
    }

}
