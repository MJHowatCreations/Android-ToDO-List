package mhowat1.nait.ca.dmit2504lab02;
import android.preference.PreferenceActivity;
import android.os.Bundle;

/**
 * Created by mhowat1 on 3/14/2018.
 */

public class MyPreferenceActivity extends PreferenceActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferences);
    }
}
