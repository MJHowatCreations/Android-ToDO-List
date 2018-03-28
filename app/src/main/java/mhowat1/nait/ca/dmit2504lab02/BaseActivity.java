package mhowat1.nait.ca.dmit2504lab02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by mhowat1 on 3/14/2018.
 */

public class BaseActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences settings;




    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_show_preferences:
            {
                Intent intent = new Intent(this, MyPreferenceActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_archive_page:
            {
                Intent intent = new Intent(this, ArchiveView.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_main_page:
            {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
