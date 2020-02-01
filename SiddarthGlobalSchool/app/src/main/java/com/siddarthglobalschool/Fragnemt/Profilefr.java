package com.siddarthglobalschool.Fragnemt;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.ApplicationConstant;
import com.siddarthglobalschool.Util.LoginResponse;
import com.siddarthglobalschool.Util.LoginSenderResponse;
import com.siddarthglobalschool.Util.UtilMethods;

public class Profilefr extends Fragment {
    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RelativeLayout rv_profile;
    ImageView back;
    TextView tv_name,tv_class,tv_section,tv_father,tv_Mobile,tv_address,tv_gender,tv_pin;
    de.hdodenhof.circleimageview.CircleImageView iv_user;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.profile, container, false);
        pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Ishome", false).apply();
        LoginResponse loginSenderResponse= UtilMethods.INSTANCE.getLoginPref(getActivity());

        tv_name=view.findViewById(R.id.tv_name);
        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        tv_class=view.findViewById(R.id.tv_class);
        tv_father=view.findViewById(R.id.tv_father);
        tv_section=view.findViewById(R.id.tv_section);
        tv_Mobile=view.findViewById(R.id.tv_Mobile);
        tv_address=view.findViewById(R.id.tv_address);
        iv_user=view.findViewById(R.id.iv_user);
        tv_gender=view.findViewById(R.id.tv_gender);
        tv_pin=view.findViewById(R.id.tv_pin);
        tv_name.setText(loginSenderResponse.getMessage().getName()+"");
        tv_class.setText(loginSenderResponse.getMessage().getClass_name()+"");
        tv_father.setText(loginSenderResponse.getMessage().getGuardian_name()+"");
        tv_section.setText(loginSenderResponse.getMessage().getSection_name()+"");
        tv_address.setText(loginSenderResponse.getMessage().getAddress()+"");
        tv_Mobile.setText(loginSenderResponse.getMessage().getMobile_no()+"");
        tv_name.setText(loginSenderResponse.getMessage().getName()+"");
        tv_gender.setText(loginSenderResponse.getMessage().getGender()+"");
        tv_pin.setText(loginSenderResponse.getMessage().getPin_code()+"");
        if(loginSenderResponse.getMessage().getProfile_pic()!=null){

        Glide
                .with(getActivity())
                .load(ApplicationConstant.INSTANCE.baseUrl+"/schoolManagement/"+loginSenderResponse.getMessage().getProfile_pic())
                .centerCrop()
                .placeholder(R.drawable.s)
                .into(iv_user);

        }
        return view;
    }
}
