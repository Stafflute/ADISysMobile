package presentation.boundary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import business.entity.Intervento;
import business.entity.Operazione;
import com.adisys.R;
import presentation.controller.FrontController;
import presentation.controller.FrontControllerFactory;
import util.DateConverter;
import util.Parameter;

import java.util.List;

public class SchermataIntervento extends Activity implements Boundary {

    private Context context;

    private Bundle extras;
    private Parameter parameter;
    private Intervento intervento;

    private Activity activity = this;

    private FrontController fc = FrontControllerFactory.buildInstance();

    private static final String LIST_POINT = " - ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intervento);

        context = this;

        init();
    }

    private void init() {
        extras = getIntent().getExtras();
        parameter = (Parameter) extras.get(Parameter.PARAMETER);

        intervento = (Intervento) parameter.getValue("intervento");

        initTabs();
        initTexts(intervento);
        initListViews(intervento);
        initButtons();

    }

    private void initTabs() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabEsecuzione = tabHost.newTabSpec("tabIndicatorEsecuzione");
        tabEsecuzione.setIndicator(context.getText(R.string.intervention_title));
        tabEsecuzione.setContent(R.id.tabEsecuzioneContent);
        tabHost.addTab(tabEsecuzione);

        TabHost.TabSpec tabDettagliIntervento = tabHost.newTabSpec("tabIndicatorIntervento");
        tabDettagliIntervento.setIndicator(context.getText(R.string.details));
        tabDettagliIntervento.setContent(R.id.tabDettagliInterventoContent);
        tabHost.addTab(tabDettagliIntervento);
    }

    private void initTexts(Intervento intervento) {
        TextView idTextView = (TextView) findViewById(R.id.idValue);
        idTextView.append(intervento.getId());

        TextView cittaTextView = (TextView) findViewById(R.id.cittaValue);
        cittaTextView.append(intervento.getCitta());

        TextView capTextView = (TextView) findViewById(R.id.capValue);
        capTextView.append(intervento.getCap());

        TextView indirizzoTextView = (TextView) findViewById(R.id.indirizzoValue);
        indirizzoTextView.append(intervento.getIndirizzo());

        TextView dataTextView = (TextView) findViewById(R.id.dataValue);
        dataTextView.append(intervento.getData().toString(DateConverter.EUROPEAN_DATE_FORMAT));

        TextView oraTextView = (TextView) findViewById(R.id.oraValue);
        oraTextView.append(intervento.getOra().toString(DateConverter.EUROPEAN_TIME_FORMAT));

        TextView idPazienteTextView = (TextView) findViewById(R.id.idPazienteValue);
        idPazienteTextView.append(intervento.getPaziente().getId());

        TextView nomePazienteTextView = (TextView) findViewById(R.id.nomePazienteValue);
        nomePazienteTextView.append(intervento.getPaziente().getNome());

        TextView cognomePazienteTextView = (TextView) findViewById(R.id.cognomePazienteValue);
        cognomePazienteTextView.append(intervento.getPaziente().getCognome());

        TextView dataPazienteTextView = (TextView) findViewById(R.id.dataPazienteValue);
        dataPazienteTextView.append(intervento.getPaziente().getData().toString(DateConverter.EUROPEAN_DATE_FORMAT));

        TextView idInfermiereTextView = (TextView) findViewById(R.id.idInfermiereValue);
        idInfermiereTextView.append(intervento.getInfermiere().getId());

        TextView nomeInfermiereTextView = (TextView) findViewById(R.id.nomeInfermiereValue);
        nomeInfermiereTextView.append(intervento.getInfermiere().getNome());

        TextView cognomeInfermiereTextView = (TextView) findViewById(R.id.cognomeInfermiereValue);
        cognomeInfermiereTextView.append(intervento.getInfermiere().getCognome());
    }

    private ListView operazioneListView;

    private void initListViews(Intervento intervento) {
        List<Operazione> operazioneList = intervento.getOperazione();
        operazioneListView = (ListView) findViewById(R.id.listaOperazioni);
        operazioneListView.setOnItemClickListener(operazioneClickListener);

        ArrayAdapter<Operazione> operazioneArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_text, operazioneList);
        operazioneListView.setAdapter(operazioneArrayAdapter);

        List<String> rubrica = intervento.getPaziente().getNumeroCellulare();
        LinearLayout rubricaLinearLayout = (LinearLayout) findViewById(R.id.rubricaList);

        for (String numero : rubrica) {
            TextView textView = (TextView) View.inflate(this, R.layout.rubrica_text, null);
            textView.setText(LIST_POINT + numero);
            rubricaLinearLayout.addView(textView);
        }
    }

    private AdapterView.OnItemClickListener operazioneClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!isExecutable) {
                fc.processRequest("MostraSchermataOperazione", null);
            }
        }
    };

    public void onDestroy() {
        super.onDestroy();
        fc.processRequest("InterrompiEsecuzione", null);
    }

    private boolean isExecutable = true;
    private Button executeButton;
    private Button finishButton;
    private Button abortButton;

    private static final float OPAQUE = 1;
    private static final float TRANSPARENT = 0.5f;

    private View.OnClickListener executeInterventoListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            synchronized (this) {
                if (isExecutable) {
                    isExecutable = false;
                    operazioneListView.setClickable(true);
                    operazioneListView.setAlpha(OPAQUE);
                    executeButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                    abortButton.setVisibility(View.VISIBLE);
                    fc.processRequest("EseguiIntervento", parameter);
                }
            }
        }
    };

    private View.OnClickListener abortInterventoListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            synchronized (this) {
                if (!isExecutable) {
                    isExecutable = true;
                    operazioneListView.setClickable(false);
                    operazioneListView.setAlpha(TRANSPARENT);
                    executeButton.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.GONE);
                    abortButton.setVisibility(View.GONE);
                    fc.processRequest("InterrompiEsecuzione", null);
                }
            }
        }
    };

    private void initButtons() {
        executeButton = (Button) findViewById(R.id.eseguiIntervento);
        executeButton.setOnClickListener(executeInterventoListener);

        finishButton = (Button) findViewById(R.id.finisciIntervento);
        abortButton = (Button) findViewById(R.id.annullaIntervento);

        abortButton.setOnClickListener(abortInterventoListener);
    }
}
