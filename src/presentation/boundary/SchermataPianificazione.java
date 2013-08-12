package presentation.boundary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import business.entity.Intervento;
import business.entity.Paziente;
import com.adisys.R;
import util.Parameter;

import java.util.LinkedList;
import java.util.List;

public class SchermataPianificazione extends Activity implements Boundary {

    private Bundle extras;
    private Parameter parameter;

    private void init() {
        extras = getIntent().getExtras();
        parameter = (Parameter) extras.get(Parameter.PARAMETER);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pianificazione);
        init();

        //test
        List<Intervento> listaInterventi = new LinkedList<>();
        Intervento intrv = new Intervento();
        intrv.setId("1");
        Paziente paz = new Paziente();
        paz.setNome("nome");
        paz.setCognome("cognome");
        intrv.setPaziente(paz);
        listaInterventi.add(intrv);
        //END test
        ListView listaInterventiView = (ListView)findViewById(R.id.listaInterventi);
        ArrayAdapter<Intervento> listaIntAdapter = new ArrayAdapter<Intervento>(this, R.layout.simple_text, listaInterventi);
        listaInterventiView.setAdapter(listaIntAdapter);

        Log.d("AndroidRuntime",(parameter.getValue("pianificazione")).toString());
    }
}
