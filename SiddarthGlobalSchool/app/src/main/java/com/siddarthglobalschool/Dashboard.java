package com.siddarthglobalschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.siddarthglobalschool.Fragnemt.Dashboardfr;
import com.siddarthglobalschool.Fragnemt.Profilefr;
import com.siddarthglobalschool.Fragnemt.UpdateProfilefr;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.LoginResponse;
import com.siddarthglobalschool.Util.UtilMethods;

public class Dashboard extends AppCompatActivity {
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        LoginResponse loginSenderResponse = UtilMethods.INSTANCE.getLoginPref(this);
        pref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        if (!loginSenderResponse.getMessage().getMobile_no().equals("")) {
            UtilMethods.INSTANCE.changeFragnemt(new Dashboardfr(), getFragmentManager(), true, "Dashboardfr");
        } else {
            if (pref.getString("Update", "").equals("1")){
                UtilMethods.INSTANCE.changeFragnemt(new Dashboardfr(), getFragmentManager(), true, "Dashboardfr");
            }else{
                UtilMethods.INSTANCE.changeFragnemt(new UpdateProfilefr(), getFragmentManager(), true, "Profilefr");
            }
        }
    }

    @Override
    public void onBackPressed() {

            if (!pref.getBoolean("Ishome", false)) {
                super.onBackPressed();
            } else {
                new AlertDialog.Builder(this).setTitle("Want to exit ?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).show();
            }

    }
}
