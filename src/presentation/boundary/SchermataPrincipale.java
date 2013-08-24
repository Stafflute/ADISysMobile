package presentation.boundary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import business.applicationservice.transfer.PianificazioneFile;
import com.adisys.R;
import presentation.controller.FrontController;
import presentation.controller.FrontControllerFactory;
import util.Parameter;

import java.util.ArrayList;
import java.util.List;

public class SchermataPrincipale extends Activity implements Boundary {

    private List<PianificazioneFile> fileList = new ArrayList<>();

    private PianificazioneFile selectedPianificazione = null;

    private Context context;

    private Activity activity = this;

    private FrontController fc = FrontControllerFactory.buildInstance();

    private OnItemSelectedListener fileSpinnerListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedPianificazione = fileList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            selectedPianificazione = null;
        }
    };

    private OnClickListener fileConfirmListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Parameter parameter = new Parameter();
            parameter.setValue(ACTIVITY, activity);
            parameter.setValue("pianificazione", selectedPianificazione);
            Object valid = fc.processRequest("VerificaPianificazione", parameter);
            if (valid != null) {
                fc.processRequest("MostraSchermataPianificazione", parameter);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principale);

        context = this;

        Parameter parameter = new Parameter();
        parameter.setValue("activity", activity);
        fc.processRequest("AvviaServiziPrincipali", parameter);

        Spinner fileSpinner = (Spinner) findViewById(R.id.spinner);
        Button fileConfirm = (Button) findViewById(R.id.fileConfirm);

        fileList = (List<PianificazioneFile>) fc.processRequest("ElencaPianificazioni", null);

        ArrayAdapter<PianificazioneFile> adapter = new ArrayAdapter<>(this, R.layout.little_text, fileList);
        adapter.setDropDownViewResource(R.layout.little_text);
        fileSpinner.setAdapter(adapter);

        fileSpinner.setOnItemSelectedListener(fileSpinnerListener);
        fileConfirm.setOnClickListener(fileConfirmListener);
    }
}
