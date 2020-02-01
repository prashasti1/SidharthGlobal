package com.siddarthglobalschool.Fragnemt;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddarthglobalschool.Adapter.ResultAdapter;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.AttendanceResponse;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.EventResponse;
import com.siddarthglobalschool.Util.UtilMethods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.vo.DateData;

public class Attendancefr extends Fragment implements View.OnClickListener {


    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static LinearLayout event;
    public static RecyclerView rv;
    public static ResultAdapter homeworkAdapter;
    TextView title;
    public static AttendanceResponse GalleryList;
    ImageView back;
    public static  MCalendarView calendarView;
    public static CalendarView calendarView2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attendance, container, false);
        pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Ishome", false).apply();
        rv = view.findViewById(R.id.rv);

        back = view.findViewById(R.id.back);
        event = view.findViewById(R.id.event);
        calendarView = (MCalendarView) view.findViewById(R.id.calendar_exp);
        calendarView2 = (CalendarView) view.findViewById(R.id.calendarView);

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

            UtilMethods.INSTANCE.attendanceApi(getActivity(),pref.getString("EnrollmentNo",""), loader);
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
            GalleryList = gson2.fromJson(str, new TypeToken<AttendanceResponse>() {
            }.getType());

            if (GalleryList != null) {

                if (GalleryList.getMessage().size() > 0) {
                    ArrayList<DateData> dates=new ArrayList<>();
                    for (int i = 0; i < GalleryList.getMessage().size(); i++) {
                        String[] str2=GalleryList.getMessage().get(i).getEvent_date().split("-");
                        dates.add(new DateData(Integer.parseInt(str2[0]),Integer.parseInt(str2[1]),Integer.parseInt(str2[2])));

                    }
                    List<EventDay> events = new ArrayList<>();


                    Calendar calendar = Calendar.getInstance();
                    events.add(new EventDay(calendar, R.drawable.calender));
                    for(int i=0;i<dates.size();i++) {
                     //   Calendar calendar = Calendar.getInstance();
                     //   calendar.set(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay());
                     //   events.add(new EventDay(calendar, R.drawable.calender));
                     //   calendarView.markDate(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay());//mark multiple dates with this code.

                        calendarView.markDate(
                                new DateData(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay()).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)
                                ));

                    }


                    calendarView2.setEvents(events);
                    calendarView.setMarkedCell(activity.getResources().getColor(R.color.green));

                }
            }


        }
    }
}
