package com.siddarthglobalschool.Util;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.loader.content.CursorLoader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddarthglobalschool.Dashboard;
import com.siddarthglobalschool.Fragnemt.Attendancefr;
import com.siddarthglobalschool.Fragnemt.Circularfr;
import com.siddarthglobalschool.Fragnemt.Dashboardfr;
import com.siddarthglobalschool.Fragnemt.Eventfr;
import com.siddarthglobalschool.Fragnemt.Examfrfr;
import com.siddarthglobalschool.Fragnemt.Homeworkfr;
import com.siddarthglobalschool.Fragnemt.Result;
import com.siddarthglobalschool.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sunaina on 24-08-2017.
 */

public enum UtilMethods {

    INSTANCE;




    public void dialogOk(final Context context, String title, final String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public void Login(final Activity context, final String Username, String Password, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.Login(Username, Password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                editor.putString("EnrollmentNo",Username).apply();
                                UtilMethods.INSTANCE.setLoginPref(context,new Gson().toJson(response.body()).toString());
                                Intent i=new Intent(context, Dashboard.class);
                                 context.startActivity(i);


                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void marksApi(final Activity context, final String EnrollmentNo, String semester, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ResultResponse> call = git.marksApi(EnrollmentNo, semester);
            call.enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, retrofit2.Response<ResultResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                if(response.body().getMessage().equals("[]")){
                                    UtilMethods.INSTANCE.dialogOk(context,"Attention!!","No result Announced yet");

                                }else{
                           //     UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");
                                Result.CatList(context,new Gson().toJson(response.body()).toString());}
                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","Something went wrong ");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<ResultResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                }
            });

        } catch (Exception e) {
           Log.e("dfsdfsdf",e.getMessage());
        }
    }
   /* public void imgApi(final Activity context, String enrollment_no, Uri fileUri, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            File file;
            if(fileUri.getPath().contains("raw")){
                file = new File(fileUri.getPath().replace("/raw",""));
            }else{
                file = new File(fileUri.getPath());
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), enrollment_no);
            Call<ImageResponse> call = git.profileUploadApi(requestFile, descBody);
            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, retrofit2.Response<ImageResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("imgApi", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getError().equals("false")){

                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");

                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage());
                            }
                                            *//*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*//*
                        }else{
                          //  UtilMethods.INSTANCE.dialogOk(context,"Alert",response.body().getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                }
            });

        } catch (Exception e) {
           Log.e("dfsdfsdf",e.getMessage());
        }
    }*/
    public void UpdateProfile(final Activity context, final String name,
                              String gender,
                              final String dob,
                              final String address,
                              final String father_name,
                              final String mother_name,
                              final String mobile_no,
                              final String enrollment_no,
                              final String pin_code,
                              final String email,
                               String doa, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        if(!loader.isShowing())
        loader.show();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
          doa=dateFormat.format(date);
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<LoginResponse> call = git.registerApi(name,
                   gender,
                    dob,
                    address,
                   father_name,
                    mother_name,
                    mobile_no,
                     enrollment_no,
                    pin_code,
                    email,
                    doa);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("imgApi", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                           if(response.body().getStatus().equals("1")){
                               UtilMethods.INSTANCE.setLoginPref(context,new Gson().toJson(response.body()).toString());
                               pref = context.getSharedPreferences("Login", MODE_PRIVATE);
                               editor=pref.edit();
                               editor.putString("Update","1").apply();
                               UtilMethods.INSTANCE.dialogOk(context,"Attention!!","Profile Updated Successfully");
                               UtilMethods.INSTANCE.changeFragnemt(new Dashboardfr(),context.getFragmentManager(), true, "Dashboardfr");
}else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                          //  UtilMethods.INSTANCE.dialogOk(context,"Alert",response.body().getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
           Log.e("dfsdfsdf",e.getMessage());
        }
    }
    private String getRealPathFromURI(Uri contentUri,Activity activity) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(activity, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    private static String getFilePathForN(Uri uri, Activity context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return Environment.getExternalStorageDirectory()+"/"+result;
    }
    public void changePassApi(final Activity context, final String EnrollmentNo, String Password,  String newpassword, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.changePassApi(EnrollmentNo, Password,newpassword);
            call.enqueue(new Callback<LoginResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");

                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void totalMarksApi(final Activity context, final String EnrollmentNo, String Examid, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ReaultResponse> call = git.totalMarksApi(EnrollmentNo, Examid);
            call.enqueue(new Callback<ReaultResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<ReaultResponse> call, retrofit2.Response<ReaultResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                if(response.body().getMessage().equals("[]")){
                                    UtilMethods.INSTANCE.dialogOk(context,"Attention!!","No result Announced yet");

                                }else{
                                    //     UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");
                                    Result.setData(context,new Gson().toJson(response.body()).toString());}

                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getStatus());
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                          //  UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<ReaultResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void eventApi(final Activity context, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<EventResponse> call = git.eventApi();
            call.enqueue(new Callback<EventResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<EventResponse> call, retrofit2.Response<EventResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("EventResponse", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                Eventfr.CatList(context, new Gson().toJson(response.body()).toString());
                             //   UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");

                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<EventResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  public void examTypeApi(final Activity context, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<EventResponse> call = git.examTypeApi();
            call.enqueue(new Callback<EventResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<EventResponse> call, retrofit2.Response<EventResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("EventResponse", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                Examfrfr.CatList(context, new Gson().toJson(response.body()).toString());
                             //   UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");

                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<EventResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  public void attendanceApi(final Activity context,String enroll, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AttendanceResponse> call = git.attendanceApi(enroll);
            call.enqueue(new Callback<AttendanceResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<AttendanceResponse> call, retrofit2.Response<AttendanceResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("EventResponse", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                Attendancefr.CatList(context, new Gson().toJson(response.body()).toString());
                             //   UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");

                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  public void noticeApi(final Activity context,  final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
      loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<NoticeResponse> call = git.noticeApi();
            call.enqueue(new Callback<NoticeResponse>() {
                @Override
                public void onResponse(Call<NoticeResponse> call, retrofit2.Response<NoticeResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                Circularfr.CatList(context,new Gson().toJson(response.body()).toString());
                               // UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");

                            }else{
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<NoticeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
           Log.e("mess",e.getMessage()) ;
        }
    }
 public void noticeApi2(final Activity context,final TextView txt,final TextView noticecount, final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
      //loader.show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<NoticeResponse> call = git.noticeApi();
            call.enqueue(new Callback<NoticeResponse>() {
                @Override
                public void onResponse(Call<NoticeResponse> call, retrofit2.Response<NoticeResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){

                                Gson gson2 = new Gson();
                                NoticeResponse   GalleryList = gson2.fromJson(new Gson().toJson(response.body()).toString(), new TypeToken<NoticeResponse>() {
                                }.getType());
                                txt.setText(GalleryList.getMessage().get(0).getDescription());
                                int zxzx=GalleryList.getMessage().size()-pref.getInt("oldnoticecount",0);
                                if(zxzx==0){
                                noticecount.setText("Notice");
                                    noticecount.setTextColor(context.getResources().getColor(R.color.black));
                                }
                                else if(zxzx>0){
                                noticecount.setText("Notice ("+zxzx+")");
                                    noticecount.setTextColor(context.getResources().getColor(R.color.red));
                                }
                                else{
                                noticecount.setText("Notice");
                                    noticecount.setTextColor(context.getResources().getColor(R.color.black));}
                                 editor.putInt("oldnoticecount",GalleryList.getMessage().size()).apply();
                               // UtilMethods.INSTANCE.dialogOk(context,"Attention!!",response.body().getMessage()+"");

                            }else{
                           //     UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                          //  UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<NoticeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
           Log.e("mess",e.getMessage()) ;
        }
    }
  public void HomeWork(final Activity context,final CustomLoader loader) {
        pref = context.getSharedPreferences("Login", MODE_PRIVATE);
        editor = pref.edit();
        try {
            loader.show();
            LoginResponse loginSenderResponse= UtilMethods.INSTANCE.getLoginPref(context);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<HomeWorkResponse> call = git.homeworkApi(loginSenderResponse.getMessage().getClass_id(), loginSenderResponse.getMessage().getSection_id());
            call.enqueue(new Callback<HomeWorkResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<HomeWorkResponse> call, retrofit2.Response<HomeWorkResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                Homeworkfr.CatList(context,new Gson().toJson(response.body()).toString());
                            }else{
                             //   UtilMethods.INSTANCE.dialogOk(context,"Attention!!","enrollment_no or password not match!");
                            }
                                            /*   Intent i = new Intent(context, Form.class);
                            context.startActivity(i);
                            activity.finish();*/
                        }else{
                            UtilMethods.INSTANCE.dialogOk(context,"Alert","Invalid Username password");
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<HomeWorkResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLoginPref(Context context, String toString) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Login",toString).apply();
    }

    public String getLoginPrefd(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        return sharedPreferences.getString("Login","");
    }

    public void changeFragnemt(Fragment signupFr, FragmentManager fragmentManager, boolean b, String ste) {
        if (b) {
            fragmentManager.beginTransaction().replace(R.id.frame, signupFr).addToBackStack(ste)
                    .commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.frame, signupFr)
                    .commit();
        }
    }

    public LoginResponse getLoginPref(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        sharedPreferences.getString("Login","");
        Gson gson=new Gson();
        return gson.fromJson(sharedPreferences.getString("Login",""),
                new TypeToken<LoginResponse>() {
                }.getType());
    }


    public void UpdateProfileImage(final Activity context, final String name,
                              String gender,
                              final String dob,
                              final String address,
                              final String father_name,
                              final String mother_name,
                              final String mobile_no,
                              final String enrollment_no,
                              final String pin_code,
                              final String email,
                              final String doa, final String base64, final CustomLoader loader) {

        try {

            final String str;
            if(gender.equals("Boy")){
                str="Male";
            }else{
                str="Female";
            }

            final EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ProfileResponse> call=git.profileUploadApi(enrollment_no,base64);
            call.enqueue(new Callback<ProfileResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<ProfileResponse> call, retrofit2.Response<ProfileResponse> response) {
                 /*   if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }*/
                    Log.e("Login", new Gson().toJson(response.body()).toString());

                    try {
                        if (response.body() != null ) {
                            if(response.body().getStatus().equals("1")){
                                UtilMethods.INSTANCE.UpdateProfile(context,name,
                                        str,
                                        dob,
                                        address,
                                        father_name,
                                        mother_name,
                                        mobile_no,
                                        enrollment_no,
                                        pin_code,
                                        email,
                                        doa,loader);

                            }else{
                                if (loader != null) {
                                    if (loader.isShowing())
                                        loader.dismiss();
                                }
                                UtilMethods.INSTANCE.dialogOk(context,"Attention!!","Something went wrong");
                            }
                        }
                    } catch (Exception e) {

                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }





    }
}
