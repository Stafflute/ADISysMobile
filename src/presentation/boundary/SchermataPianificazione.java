package presentation.boundary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import business.entity.Intervento;
import business.entity.Pianificazione;
import com.adisys.R;
import presentation.controller.FrontController;
import presentation.controller.FrontControllerFactory;
import util.Parameter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SchermataPianificazione extends Activity implements Boundary {

    private Bundle extras;
    private Parameter parameter;
    private Pianificazione pianificazione;

    private Activity activity = this;
    private Context context = this;

    private AdapterView.OnItemClickListener interventoClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String selectedIntervento = pianificazione.getIntervento().get(position).getId();
            if (!idSet.contains(selectedIntervento)) {
                Parameter parameter = new Parameter();
                parameter.setValue(ACTIVITY, activity);
                parameter.setValue("intervento", pianificazione.getIntervento().get(position));
                fc.processRequest("MostraSchermataIntervento", parameter);
            }
        }
    };

    private FrontController fc = FrontControllerFactory.buildInstance();

    private void init() {
        extras = getIntent().getExtras();
        parameter = (Parameter) extras.get(Parameter.PARAMETER);
        Intervento.pazienteLabel = context.getString(R.string.patient);
    }

    SelectiveInterventoArrayAdapter listaIntAdapter;
    ListView listaInterventiView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pianificazione);
        init();

        pianificazione = (Pianificazione) fc.processRequest("ElencaInterventi", parameter);

        listaInterventiView = (ListView) findViewById(R.id.listaInterventi);
        listaIntAdapter = new SelectiveInterventoArrayAdapter(this, R.layout.simple_text, pianificazione.getIntervento());
        listaInterventiView.setOnItemClickListener(interventoClickListener);

        refreshPianificazione();
    }

    private boolean toValidate = true;

    private void refreshPianificazione() {
        parameter.setValue("validazione", toValidate);
        idSet = (Set<String>) fc.processRequest("LeggiFileJournaling", parameter);
        listaIntAdapter.setIdSet(idSet);
        listaInterventiView.setAdapter(listaIntAdapter);
        toValidate = false;
    }

    Set<String> idSet = new HashSet<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DEFAULT_REQUEST) {

            if (resultCode == RESULT_OK) {
                listaInterventiView.setClickable(false);
                parameter.setValue("validazione", toValidate);
                idSet = (Set<String>) fc.processRequest("LeggiFileJournaling", parameter);
                listaIntAdapter.setIdSet(idSet);
                listaInterventiView.setAdapter(listaIntAdapter);
                listaInterventiView.setClickable(true);
            }
        }
    }
}

class SelectiveInterventoArrayAdapter extends ArrayAdapter<Intervento> {

    public SelectiveInterventoArrayAdapter(Context context, int textViewResourceId, List<Intervento> objects) {
        super(context, textViewResourceId, objects);

        this.objects = objects;
    }

    List<Intervento> objects;
    private Set<String> idSet;

    private static final float TRANSPARENT = 0.5f;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        String id = objects.get(position).getId();

        if (idSet.contains(id)) {
            view.setClickable(false);
            view.setAlpha(TRANSPARENT);
        }

        return view;
    }

    public void setIdSet(Set<String> idSet) {
        this.idSet = idSet;
    }
}
