package presentation.boundary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.example.ADISysMobile.R;

import java.util.LinkedList;
import java.util.List;

public class SchermataPrincipale extends Activity implements Boundary {

    private List<String> fileList = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Spinner fileSpinner = (Spinner)findViewById(R.id.spinner);

        fileList.add("aaa");
        fileList.add("bbb");
        fileList.add("ccc");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileSpinner.setAdapter(adapter);

    }

}
