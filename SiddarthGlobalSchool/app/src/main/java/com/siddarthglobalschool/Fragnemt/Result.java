package com.siddarthglobalschool.Fragnemt;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddarthglobalschool.Adapter.ResultAdapter;
import com.siddarthglobalschool.Adapter.circularAdapter;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.NoticeResponse;
import com.siddarthglobalschool.Util.ReaultResponse;
import com.siddarthglobalschool.Util.ResultResponse;
import com.siddarthglobalschool.Util.UtilMethods;

import org.w3c.dom.Text;

public class Result extends Fragment implements View.OnClickListener {


    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

public static RecyclerView rv;
public static ResultAdapter homeworkAdapter;
    public static  TextView title ,totalmarks,obtainmarks;
    public static ResultResponse GalleryList;
    public static ReaultResponse GalleryList2;

    ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);
        pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Ishome", false).apply();
        rv = view.findViewById(R.id.rv);

        back=view.findViewById(R.id.back);
        totalmarks=view.findViewById(R.id.totalmarks);
        obtainmarks=view.findViewById(R.id.obtainmarks);

        title=view.findViewById(R.id.title);
        title.setText(pref.getString("flag",""));
        hitAPI(pref.getString("ExamID",""));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void hitAPI(String s) {
        CustomLoader loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            UtilMethods.INSTANCE.marksApi(getActivity(),pref.getString("EnrollmentNo",""),s ,loader);
            UtilMethods.INSTANCE.totalMarksApi(getActivity(),pref.getString("EnrollmentNo",""),s ,loader);
        } else {
            UtilMethods.INSTANCE.dialogOk(getActivity(), "Attention!", "No Internet Connection");
        }

    }

    @Override
    public void onClick(View view) {

    }

    public static void CatList(final Activity activity,String str) {
        double totalmatks=0;
        double maxmarks=0;
double percentage=0;
        if(str.equals("[{}]")){
            rv.setVisibility(View.GONE);
        }else{
          //  GalleryList.getMessage().clear();
                rv.setVisibility(View.VISIBLE);
                Gson gson2 = new Gson();
                GalleryList = gson2.fromJson(str, new TypeToken<ResultResponse>() {
                }.getType());

                if (GalleryList != null) {

                    if (GalleryList.getMessage().size() > 0) {

                        homeworkAdapter = new ResultAdapter(GalleryList.getMessage(), activity);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                        rv.setLayoutManager(horizontalLayoutManagaer);
                        rv.setAdapter(homeworkAdapter);
                        homeworkAdapter.notifyDataSetChanged();


                    }
                }


    }}
    public static void setData(final Activity activity,String str) {
        Gson gson2 = new Gson();
        GalleryList2 = gson2.fromJson(str, new TypeToken<ReaultResponse>() {
        }.getType());
      obtainmarks.setText("Obtained Marks: "+GalleryList2.getMessage().get(0).getObtained_marks());
      totalmarks.setText("Total Marks: "+GalleryList2.getMessage().get(0).getTotal_marks());
    }
}
