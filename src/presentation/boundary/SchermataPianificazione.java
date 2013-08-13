package presentation.boundary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import business.entity.Intervento;
import business.entity.Paziente;
import business.entity.Pianificazione;
import com.adisys.R;
import presentation.controller.FrontController;
import presentation.controller.FrontControllerFactory;
import util.Parameter;

import java.util.LinkedList;
import java.util.List;

public class SchermataPianificazione extends Activity implements Boundary {

    private Bundle extras;
    private Parameter parameter;
    private Pianificazione pianificazione;

    private Activity activity = this;

    private AdapterView.OnItemClickListener interventoClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Parameter parameter = new Parameter();
            parameter.setValue("activity", activity);
            parameter.setValue("intervento", pianificazione.getIntervento().get(position));
            fc.processRequest("MostraSchermataIntervento", parameter);
        }
    };

    private FrontController fc = FrontControllerFactory.buildInstance();

    private void init() {
        extras = getIntent().getExtras();
        parameter = (Parameter) extras.get(Parameter.PARAMETER);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pianificazione);
        init();

        pianificazione =  (Pianificazione) fc.processRequest("ElencaInterventi", parameter);

        ListView listaInterventiView = (ListView)findViewById(R.id.listaInterventi);
        ArrayAdapter<Intervento> listaIntAdapter = new ArrayAdapter<>(this, R.layout.simple_text, pianificazione.getIntervento());
        listaInterventiView.setAdapter(listaIntAdapter);
        listaInterventiView.setOnItemClickListener(interventoClickListener);
    }
}
