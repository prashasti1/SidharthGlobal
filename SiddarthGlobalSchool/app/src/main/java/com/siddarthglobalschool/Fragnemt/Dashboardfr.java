package com.siddarthglobalschool.Fragnemt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddarthglobalschool.Dashboard;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.ApplicationConstant;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.LoginResponse;
import com.siddarthglobalschool.Util.UtilMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Dashboardfr extends Fragment implements View.OnClickListener {



View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RelativeLayout rv_profile,rl_homework,rv_circular,rv_result,rl_changepassword,rl_calender,rv_atten;
    TextView tv_date,noticecount;
    ImageView logout,iv_user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_blank, container, false);
        pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Ishome", true).apply();
        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        rv_profile=view.findViewById(R.id.rv_profile);
        rl_homework=view.findViewById(R.id.rl_homework);
        iv_user=view.findViewById(R.id.iv_user);
        rl_changepassword=view.findViewById(R.id.rl_changepassword);
        rv_circular=view.findViewById(R.id.rv_circular);
        rv_atten=view.findViewById(R.id.rv_atten);
        tv_date=view.findViewById(R.id.tv_date);
        rl_calender=view.findViewById(R.id.rl_calender);
        rv_result=view.findViewById(R.id.rv_result);
        logout=view.findViewById(R.id.logout);
       txt = view.findViewById(R.id.text);
        noticecount= view.findViewById(R.id.noticecount);
        txt.setSelected(true);
        tv_date.setText("Today : "+date);
        rv_profile.setOnClickListener(this);
        rl_homework.setOnClickListener(this);
        rv_circular.setOnClickListener(this);
        rv_result.setOnClickListener(this);
        rl_changepassword.setOnClickListener(this);
        rl_calender.setOnClickListener(this);
        logout.setOnClickListener(this);
        rv_atten.setOnClickListener(this);
        LoginResponse loginSenderResponse= UtilMethods.INSTANCE.getLoginPref(getActivity());
        Glide
                .with(getActivity())
                .load(ApplicationConstant.INSTANCE.baseUrl+"/schoolManagement/"+loginSenderResponse.getMessage().getProfile_pic())
                .centerCrop()
                .placeholder(R.drawable.s)
                .into(iv_user);
        hitAPI();
        return view;
    }
    TextView txt;
    @Override
    public void onClick(View view) {
       if(view==rv_profile) {
           UtilMethods.INSTANCE.changeFragnemt(new Profilefr(),getFragmentManager(),true,"Dashboardfr");
       }if(view==rl_homework) {
           UtilMethods.INSTANCE.changeFragnemt(new Homeworkfr(),getFragmentManager(),true,"Homeworkfr");
       }if(view==rv_circular) {
           UtilMethods.INSTANCE.changeFragnemt(new Circularfr(),getFragmentManager(),true,"Circularfr");
       }if(view==rv_result) {
           UtilMethods.INSTANCE.changeFragnemt(new Examfrfr(),getFragmentManager(),true,"Result");
       }if(view==rv_atten) {
           UtilMethods.INSTANCE.changeFragnemt(new Attendancefr(),getFragmentManager(),true,"Attendancefr");
       }if(view==rl_calender) {
           UtilMethods.INSTANCE.changeFragnemt(new Eventfr(),getFragmentManager(),true,"Eventfr");
       }if(view==rl_changepassword) {
         dialod();;
       }if(view==logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    UtilMethods.INSTANCE.setLoginPref(getActivity(),"");
                   editor.putString("Update","").apply();
                    dialogInterface.dismiss();
                    getActivity().finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

       }
    }
    private void hitAPI() {
        CustomLoader loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            UtilMethods.INSTANCE.noticeApi2(getActivity(),txt,noticecount, loader);
        } else {
            UtilMethods.INSTANCE.dialogOk(getActivity(), "Attention!", "No Internet Connection");
        }

    }
    private void dialod() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Dialog dialog = new Dialog(getActivity());
        final View layout = inflater.inflate(R.layout.resetpassword, (ViewGroup) getActivity().findViewById(R.id.root));
        final EditText password1 = (EditText) layout.findViewById(R.id.EditText_Pwd1);
        final EditText password2 = (EditText) layout.findViewById(R.id.EditText_Pwd2);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        builder.setTitle("Enter Password");
        builder.setView(layout);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String strPassword1 = password1.getText().toString();
                String strPassword2 = password2.getText().toString();
                if(strPassword1.isEmpty()){
                    password1.setError("Field is empty");

                }else
                if(strPassword2.isEmpty()){
                    password2.setError("Field is empty");

                }else{
                    if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                        CustomLoader loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                        UtilMethods.INSTANCE.changePassApi(getActivity(),pref.getString("EnrollmentNo",""),strPassword1,strPassword2, loader);

                    } else {
                        UtilMethods.INSTANCE.dialogOk(getActivity(), "Attention!", "No Internet Connection");
                    }

                }
            }
        });
        dialog=builder.create();
        dialog.show();
    }
}
