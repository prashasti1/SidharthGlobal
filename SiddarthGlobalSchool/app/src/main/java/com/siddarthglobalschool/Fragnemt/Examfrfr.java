package com.siddarthglobalschool.Fragnemt;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddarthglobalschool.Adapter.ResultAdapter;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.EventResponse;
import com.siddarthglobalschool.Util.UtilMethods;

public class Examfrfr extends Fragment implements View.OnClickListener {


    View view;
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static LinearLayout event;
    public static RecyclerView rv;
    public static ResultAdapter homeworkAdapter;
    TextView title;
    public static EventResponse GalleryList;
    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam, container, false);
        pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Ishome", false).apply();
        rv = view.findViewById(R.id.rv);

        back = view.findViewById(R.id.back);
        event = view.findViewById(R.id.event);

        title = view.findViewById(R.id.title);

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
            UtilMethods.INSTANCE.examTypeApi(getActivity(), loader);
        } else {
            UtilMethods.INSTANCE.dialogOk(getActivity(), "Attention!", "No Internet Connection");
        }

    }

    @Override
    public void onClick(View view) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void CatList(final Activity activity, String str) {

        if (str.equals("[{}]")) {

        } else {
            Gson gson2 = new Gson();
            GalleryList = gson2.fromJson(str, new TypeToken<EventResponse>() {
            }.getType());

            if (GalleryList != null) {

                if (GalleryList.getMessage().size() > 0) {

                    for (int i = 0; i < GalleryList.getMessage().size(); i++) {
                        TextView tv = new TextView(activity);
                        tv.setText(GalleryList.getMessage().get(i).getExam_name());
                        tv.setBackground(activity.getResources().getDrawable(R.drawable.rectw));
                        tv.setPadding(15, 15, 15, 15);
                        tv.setTextColor(activity.getResources().getColor(R.color.black));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,0,0,20);
                        tv.setLayoutParams(params);
                        tv.setTextSize(15);
                        tv.setTypeface(null, Typeface.BOLD);
                        event.addView(tv);
                        final int finalI = i;
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editor.putString("flag",GalleryList.getMessage().get(finalI).getExam_name()).apply();
                                editor.putString("ExamID",GalleryList.getMessage().get(finalI).getId()).apply();
                                UtilMethods.INSTANCE.changeFragnemt(new Result(),activity.getFragmentManager(),true,"Dashboardfr");
                            }
                        });
                    }


                }
            }


        }
    }
}
