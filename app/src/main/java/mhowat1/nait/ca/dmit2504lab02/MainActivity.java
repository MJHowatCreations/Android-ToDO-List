package mhowat1.nait.ca.dmit2504lab02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner listSpinner;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listSpinner = (Spinner) findViewById(R.id.main_activity_spinner);
        btnAdd = (Button) findViewById(R.id.main_activity_add_button);


        btnAdd.setOnClickListener(this);
        listSpinner.setOnItemSelectedListener(this);
        loadSpinnerData();

    }

    private void loadSpinnerData() {
        DBManager db = new DBManager(this);
        List<String> lists = db.getAllLists();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lists);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        listSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
