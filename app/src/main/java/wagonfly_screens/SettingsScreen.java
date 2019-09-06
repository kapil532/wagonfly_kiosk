package wagonfly_screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wagonfly.R;

import kartify_base.BaseActivity;
import kartify_base.CommonClass;


public class SettingsScreen extends BaseActivity implements OnItemSelectedListener,CompoundButton.OnCheckedChangeListener {
    Spinner spinnerOsversions;
    TextView selVersion;
    String[] langaugeKey = {"", "message_hn", "message_en", "message_gm"};

    private String[] state =
            {"", "Hindi", "English", "German"};
    private RelativeLayout header_back;
    private TextView login_logut,aboutus;

    private static final int MENU_LOGIN = Menu.FIRST;
    private static final int MENU_EDIT_LOGIN = Menu.FIRST;

    private Toolbar mToolbar;
    private CheckBox checkBox2;
    private CheckBox checkBox3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        checkBox2 =(CheckBox)findViewById(R.id.checkBox2);
        checkBox3 =(CheckBox)findViewById(R.id.checkBox3);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });







        SwitchCompat switchCompat2 = (SwitchCompat) findViewById(R.id.Switch);
        switchCompat2.setOnCheckedChangeListener(this);
        if(CommonClass.returnGenericData("NOTIFICATION_CHECKED","NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("true"))
        {
            checkBox3.setAlpha(1f);
            checkBox2.setAlpha(1f);
            checkBox2.setClickable(true);
            checkBox3.setClickable(true);
            switchCompat2.setChecked(true);
        }
        else if(CommonClass.returnGenericData("NOTIFICATION_CHECKED","NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("false"))
        {
            checkBox3.setAlpha(0.2f);
            checkBox2.setAlpha(0.2f);
            checkBox2.setClickable(false);
            checkBox3.setClickable(false);
            switchCompat2.setChecked(false);
        }
        else
        {
            Log.d("CHEKCED ","CHECKED --->");
            checkBox3.setAlpha(1f);
            checkBox2.setAlpha(1f);
            checkBox3.setChecked(true);
            checkBox2.setChecked(true);
            switchCompat2.setChecked(true);
        }

        if(CommonClass.returnGenericData("checkBox2_NOTIFICATION_CHECKED","checkBox2_NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("true"))
        {
            checkBox2.setChecked(true);
        }
        else if(CommonClass.returnGenericData("checkBox2_NOTIFICATION_CHECKED","checkBox2_NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("false"))
        {
            checkBox2.setChecked(false);
        }
        else
        {
            checkBox2.setChecked(true);
        }
         if(CommonClass.returnGenericData("checkBox3_NOTIFICATION_CHECKED","checkBox3_NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("true"))
        {
            checkBox3.setChecked(true);
        }
        else if(CommonClass.returnGenericData("checkBox3_NOTIFICATION_CHECKED","checkBox3_NOTIFICATION_CHECKED_PRE",this).equalsIgnoreCase("false"))
        {
            checkBox3.setChecked(false);
        }
        else
         {
             checkBox3.setChecked(true);
         }



        System.out.println(state.length);
        /*selVersion = (TextView) findViewById(R.id.selVersion);
        spinnerOsversions = (Spinner) findViewById(R.id.osversions);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);
        spinnerOsversions.setOnItemSelectedListener(this);*/

    }

	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return true;
	}*/

    //Just uncomment then three dot will come
    // sign in and sign out will automatically come
/* @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        String text = "";
        if (CommonClass.returnGenericData("divrt_user_authtoken", "USER", this).length() > 10) {
            text = "Sign Out";
        } else {
            text = "Sign In";
        }


       // menu.add(0, MENU_EDIT_LOGIN, Menu.NONE, "Edit Account");
        menu.add(0, MENU_LOGIN, Menu.NONE, text);

        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENU_LOGIN:
                return true;

          /*  case MENU_EDIT_LOGIN:
                //loginOrLogout();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerOsversions.setSelection(position);
        String selState = (String) spinnerOsversions.getSelectedItem();

        if (!(selState.equalsIgnoreCase(state[0]))) {
            Log.d("MESSAGE", "MESSAGE" + "http://abhs.in/" + langaugeKey[position]);
           // PostData.getData(SettingsScreen.this, "http://abhs.in/" + langaugeKey[position], mpost);
        }


    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }



    @Override
    protected void onStop() {
        super.onStop();
        CommonClass.saveGenericData(""+checkBox2.isChecked(),"checkBox2_NOTIFICATION_CHECKED","checkBox2_NOTIFICATION_CHECKED_PRE",this);
        CommonClass.saveGenericData(""+checkBox3.isChecked(),"checkBox3_NOTIFICATION_CHECKED","checkBox3_NOTIFICATION_CHECKED_PRE",this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.Switch:
                Log.d("switch_compat", isChecked + "");
                if (isChecked)
                {
                    checkBox2.setAlpha(1f);
                    checkBox3.setAlpha(1f);
                    checkBox2.setClickable(true);
                    checkBox3.setClickable(true);
                }
                else {
                    checkBox3.setAlpha(0.2f);
                    checkBox2.setAlpha(0.2f);
                    checkBox2.setClickable(false);
                    checkBox3.setClickable(false);
                }
                CommonClass.saveGenericData(""+isChecked,"NOTIFICATION_CHECKED","NOTIFICATION_CHECKED_PRE",this);
                break;

        }
    }
}