package presentation.boundary;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import business.entity.Operazione;
import com.adisys.R;
import presentation.controller.FrontController;
import presentation.controller.FrontControllerFactory;
import util.Parameter;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 14/08/13
 * Time: 09:18
 * To change this template use File | Settings | File Templates.
 */
public class SchermataOperazione extends Activity {
    private Bundle extras;
    private Parameter parameter;
    private int position;
    private Operazione operazione;

    Chronometer chronometer;

    private FrontController fc = FrontControllerFactory.buildInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_operazione);

        extras = getIntent().getExtras();
        parameter = (Parameter) extras.get(Parameter.PARAMETER);

        position = (int) parameter.getValue("posizioneOperazione");
        operazione = (Operazione) parameter.getValue("operazione");

        initText();

        chronometer = (Chronometer) findViewById(R.id.cronometro);
        chronometer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        chronometer.stop();

        EditText notaTextView = (EditText) findViewById(R.id.notaValue);
        EditText misuraTextView = (EditText) findViewById(R.id.valoreRilevatoValue);
        long tempoOperazione = SystemClock.elapsedRealtime() - chronometer.getBase();

        Parameter parameter = new Parameter();
        parameter.setValue("nota", notaTextView.getText().toString());
        parameter.setValue("misura", misuraTextView.getText().toString());
        parameter.setValue("tempoOperazione", tempoOperazione);
        parameter.setValue("posizione", position);

        fc.processRequest("RegistraValori", parameter);
    }

    private void initText() {
        TextView idTextView = (TextView) findViewById(R.id.idOperazioneValue);
        idTextView.setText(operazione.getId());

        TextView nomeTextView = (TextView) findViewById(R.id.nomeOperazioneValue);
        nomeTextView.setText(operazione.getNome());

        EditText notaTextView = (EditText) findViewById(R.id.notaValue);
        notaTextView.setText(operazione.getNota());
    }
}
