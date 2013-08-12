package presentation.boundary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.adisys.R;
import util.Parameter;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 12/08/13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
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

        Log.d("AndroidRuntime",(parameter != null) + "");
    }
}
