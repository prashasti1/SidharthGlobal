package com.siddarthglobalschool.Fragnemt;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddarthglobalschool.Adapter.homeworkAdapter;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.HomeWorkResponse;
import com.siddarthglobalschool.Util.RequestUserPermission;
import com.siddarthglobalschool.Util.UtilMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Homeworkfr extends Fragment implements View.OnClickListener {


    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

public static RecyclerView rv;
public static homeworkAdapter homeworkAdapter;

    public static HomeWorkResponse GalleryList;
    ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homework, container, false);
        RequestUserPermission requestUserPermission = new RequestUserPermission(getActivity());
        requestUserPermission.verifyStoragePermissions();
        pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Ishome", false).apply();
        rv = view.findViewById(R.id.rv);

        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        hitAPI();
        return view;
    }

    private void hitAPI() {
        CustomLoader loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            UtilMethods.INSTANCE.HomeWork(getActivity(), loader);
        } else {
            UtilMethods.INSTANCE.dialogOk(getActivity(), "Attention!", "No Internet Connection");
        }

    }

    @Override
    public void onClick(View view) {

    }
    public static void CatList(final Activity activity,String str) {

        if(str.equals("[{}]")){
            rv.setVisibility(View.GONE);
        }else{
          //  GalleryList.getMessage().clear();
                rv.setVisibility(View.VISIBLE);
                Gson gson2 = new Gson();
                GalleryList = gson2.fromJson(str, new TypeToken<HomeWorkResponse>() {
                }.getType());

                if (GalleryList != null) {

                    if (GalleryList.getMessage().size() > 0) {

                        homeworkAdapter = new homeworkAdapter(GalleryList.getMessage(), activity);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                        rv.setLayoutManager(horizontalLayoutManagaer);
                        rv.setAdapter(homeworkAdapter);
                        homeworkAdapter.notifyDataSetChanged();


                    }
                }


    }}
}
