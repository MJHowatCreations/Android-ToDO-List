package mhowat1.nait.ca.dmit2504lab02;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class ArchiveView extends ListActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
    SharedPreferences settings;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // new ToDoGetter().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_view);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        settings.registerOnSharedPreferenceChangeListener(this);

        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        displayArchive();
    }




    private void displayArchive() {
        String[] fields = new String[]{"CREATED_DATE", "LIST_TITLE", "CONTENT", "COMPLETED_FLAG"};
        int [] ids = new int[]{R.id.archive_list_cell_date_time, R.id.archive_list_cell_item,
                R.id.archive_list_cell_description, R.id.archive_list_cell_completed};
        adapter =  new SimpleAdapter(this, items, R.layout.archive_row, fields, ids);
        populateList();
        setListAdapter(adapter);
    }

    private void populateList() {
        items.clear();
        BufferedReader in;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Lab02Get.jsp?ALIAS=" + settings.getString("username", "Matthew") + "&PASSWORD=" + settings.getString("user_password", "password")));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = in.readLine()) != null) {
                HashMap<String, String> temp = new HashMap<String, String>();

                line = "Date: " + line;
                temp.put("CREATED_DATE", line);
                line = in.readLine();

                line = "Item Title: " + line;
                temp.put("LIST_TITLE", line);
                line = in.readLine();

                temp.put("CONTENT", line);
                line = in.readLine();
                if (line.equals("0")) {
                    line = "Uncompleted";
                } else {
                    line = "Completed";
                }

                temp.put("COMPLETED_FLAG", line);
                items.add(temp);
            }
            in.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error:" + e, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }



    private class ToDoGetter extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            items.clear();
            BufferedReader in;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://www.youcode.ca/Lab02Get.jsp?ALIAS=" + settings.getString("username", "Matthew") + "&PASSWORD=" + settings.getString("user_password", "password")));
                HttpResponse response = client.execute(request);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = in.readLine()) != null) {
                    HashMap<String, String> temp = new HashMap<String, String>();

                    line = "Date: " + line;
                    temp.put("CREATED_DATE", line);
                    line = in.readLine();

                    line = "Item Title: " + line;
                    temp.put("LIST_TITLE", line);
                    line = in.readLine();

                    temp.put("CONTENT", line);
                    line = in.readLine();
                    if (line.equals("0")) {
                        line = "Uncompleted";
                    } else {
                        line = "Completed";
                    }

                    temp.put("COMPLETED_FLAG", line);
                    items.add(temp);
                }
                in.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error:" + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

}


