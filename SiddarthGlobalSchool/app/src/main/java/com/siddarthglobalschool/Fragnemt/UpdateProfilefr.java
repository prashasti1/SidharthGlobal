package com.siddarthglobalschool.Fragnemt;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.loader.content.CursorLoader;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.ApplicationConstant;
import com.siddarthglobalschool.Util.CustomLoader;
import com.siddarthglobalschool.Util.EndPoints;
import com.siddarthglobalschool.Util.LoginResponse;
import com.siddarthglobalschool.Util.RequestUserPermission;
import com.siddarthglobalschool.Util.UtilMethods;
import com.siddarthglobalschool.Util.VolleyMultipartRequest;
import com.siddarthglobalschool.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class UpdateProfilefr extends Fragment {
    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RelativeLayout rv_profile;
    ImageView back;
    EditText tv_name, tv_class, tv_section, tv_father, tv_Mobile, tv_address, tv_pin, tv_mother, tv_Email;
    TextView tv_dob;
    de.hdodenhof.circleimageview.CircleImageView iv_user;
    Button but_submit;
    String ClassID, SectionID;
    RadioButton rd_male, rd_female;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.updateprofile, container, false);
        RequestUserPermission requestUserPermission = new RequestUserPermission(getActivity());
        requestUserPermission.verifyStoragePermissions();
        pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Ishome", true).apply();
        LoginResponse loginSenderResponse = UtilMethods.INSTANCE.getLoginPref(getActivity());

        tv_name = view.findViewById(R.id.tv_name);
        back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        tv_class = view.findViewById(R.id.tv_class);
        tv_father = view.findViewById(R.id.tv_father);
        tv_section = view.findViewById(R.id.tv_section);
        tv_Mobile = view.findViewById(R.id.tv_Mobile);
        tv_address = view.findViewById(R.id.tv_address);
        iv_user = view.findViewById(R.id.iv_user);
        rd_female = view.findViewById(R.id.rd_female);
        tv_dob = view.findViewById(R.id.tv_dob);
        rd_male = view.findViewById(R.id.rd_male);
        tv_pin = view.findViewById(R.id.tv_pin);
        tv_mother = view.findViewById(R.id.tv_mother);
        tv_Email = view.findViewById(R.id.tv_Email);
        but_submit = view.findViewById(R.id.but_submit);

        tv_class.setText(loginSenderResponse.getMessage().getClass_name() + "");

        tv_section.setText(loginSenderResponse.getMessage().getSection_name() + "");
        ClassID = pref.getString("EnrollmentNo", "");
        if (loginSenderResponse.getMessage().getProfile_pic() != null) {
            Glide
                    .with(getActivity())
                    .load(ApplicationConstant.INSTANCE.baseUrl + "/uploads/" + loginSenderResponse.getMessage().getProfile_pic())
                    .centerCrop()
                    .placeholder(R.drawable.s)
                    .into(iv_user);
        }
        but_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAPI();
            }
        });
        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    calender1(tv_dob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        captureImageInitialization();
        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    Uri mImageCaptureUri, mImageCaptureUri1;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private android.app.AlertDialog dialog;

    private void captureImageInitialization() {
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());
        final String[] items = new String[]{"Take from camera", "Take from file"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, items);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg"));
                    mImageCaptureUri1 = mImageCaptureUri;

                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            mImageCaptureUri);
                    try {
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), "This device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    FromCard();
                }
            }
        });

        dialog = builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 5 && resultCode == RESULT_OK
                && null != data) {

            mImageCaptureUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            try {
                Cursor cursor = getActivity().getContentResolver().query(mImageCaptureUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                photo = BitmapFactory.decodeFile(picturePath);
                Glide.with(getActivity()).load(new File(picturePath)).into(iv_user);
            } catch (Exception e) {
                String path = mImageCaptureUri.getPath();
                Glide.with(getActivity()).load(new File(path)).into(iv_user);
                e.printStackTrace();
            }
            // iv_user.setImageBitmap(photo);


        }
        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();
                break;
            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                try {
                    Bitmap photo = BitmapFactory.decodeFile(mImageCaptureUri.getPath());
                    ByteArrayOutputStream bytesda = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, bytesda);
                    iv_user.setImageBitmap(photo);
                    //  iv_user.setImageURI(mImageCaptureUri);
                    logobytes = bytesda.toByteArray();
                    String root = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root + "/saved_images");
                    myDir.mkdirs();
                    String fname = "Image-" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    File file = new File(myDir, fname);
                    if (file.exists()) file.delete();
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
                        out.flush();
                        out.close();
                        this.mImageCaptureUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory() + "/saved_images/", fname));
                    } catch (Exception es) {
                        es.printStackTrace();
                    }
                } catch (Exception es) {
                    es.printStackTrace();
                }
                break;

            case CROP_FROM_CAMERA:
                getcrop(data);
                break;

        }
    }

    byte[] logobytes = null;
    Bitmap photo = null;

    Bitmap theImage;

    private void getcrop(Intent data) {

        Bundle extras = data.getExtras();
        Uri extra = data.getData();
        try {
            if (extras != null) {
                photo = extras.getParcelable("data");
                ByteArrayOutputStream bytesda = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 50, bytesda);
                iv_user.setImageBitmap(photo);
                logobytes = bytesda.toByteArray();
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();
                String fname = "Image-" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                File file = new File(myDir, fname);
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
                    out.flush();
                    out.close();
                    mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory() + "/saved_images/", fname));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (extra != null) {
                try {
                    photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), extra);
                    ByteArrayOutputStream bytesda = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, bytesda);
                    iv_user.setImageBitmap(photo);
                    logobytes = bytesda.toByteArray();
                    String root = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root + "/saved_images");
                    myDir.mkdirs();
                    String fname = "Image-" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    File file = new File(myDir, fname);
                    if (file.exists()) file.delete();
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
                        out.flush();
                        out.close();
                        mImageCaptureUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory() + "/saved_images/", fname));
                    } catch (Exception es) {
                        es.printStackTrace();
                    }
                } catch (IOException es) {
                    es.printStackTrace();
                }

            }
        }
        if (extra != null) {
            try {
                photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), extra);
                ByteArrayOutputStream bytesda = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 50, bytesda);
                iv_user.setImageBitmap(photo);

                logobytes = bytesda.toByteArray();
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();
                fname = "Image-" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                //assign it to a string(your choice).
                File file = new File(myDir, fname);
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
                    out.flush();
                    out.close();
                    mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory() + "/saved_images/", fname));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    String fname, filePath;

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(
                intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(getActivity(), "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setDataAndType(mImageCaptureUri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 256);
            intent.putExtra("outputY", 256);
            intent.putExtra("return-data", true);
            if (size == 1) {
                Intent i = new Intent(intent);
                mImageCaptureUri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "tmp_avatar_"
                        + String.valueOf(System.currentTimeMillis())
                        + ".jpg"));
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        mImageCaptureUri);
                ResolveInfo res = list.get(0);
                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));
                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();
                    co.title = getActivity().getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getActivity().getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));
                    cropOptions.add(co);
                }
                CropOptionAdapter adapter = new CropOptionAdapter(
                        getActivity().getApplicationContext(), cropOptions);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getActivity().getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            //   mImageCaptureUri = null;
                        }
                    }
                });
                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    public static class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }

    public static class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.crop_selector, options);
            mOptions = options;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    private void hitAPI() {
        if (Validate()) {
            CustomLoader loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            //encode image to base64 string
            loader.show();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encoded = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            final String gender;
            if (rd_female.isChecked()) {
                gender = rd_female.getText().toString();
            } else {
                gender = rd_male.getText().toString();
            }
            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                UtilMethods.INSTANCE.UpdateProfileImage(getActivity(),
                        tv_name.getText().toString(),
                        gender,
                        tv_dob.getText().toString(),
                        tv_address.getText().toString(),
                        tv_father.getText().toString(),
                        tv_mother.getText().toString(),
                        tv_Mobile.getText().toString(),
                        ClassID,
                        tv_pin.getText().toString(),
                        tv_Email.getText().toString(),
                        tv_dob.getText().toString(), encoded, loader);


            } else {
                UtilMethods.INSTANCE.dialogOk(getActivity(), "Attention!", "No Internet Connection");
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(), uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private boolean Validate() {
        int i = 0;
        if (tv_name.getText().toString().isEmpty()) {
            tv_name.setError("Enter Name");
            i = 1;
        }
        if (tv_dob.getText().toString().isEmpty()) {
            tv_dob.setError("Enter DOB");
            i = 1;
        }
        if (tv_address.getText().toString().isEmpty()) {
            tv_address.setError("Enter Address");
            i = 1;
        }
        if (!rd_female.isChecked() && !rd_male.isChecked()) {
            rd_female.setError("Select Gender");
            i = 1;
        }
        if (tv_Mobile.getText().toString().isEmpty()) {
            tv_Mobile.setError("Enter Mobile No");
            i = 1;
        }
        if (tv_father.getText().toString().isEmpty()) {
            tv_father.setError("Enter Father's Name");
            i = 1;
        }
        if (tv_mother.getText().toString().isEmpty()) {
            tv_mother.setError("Enter Mother's Name");
            i = 1;
        }
        if (tv_pin.getText().toString().isEmpty()) {
            tv_pin.setError("Enter Pin Code");
            i = 1;
        }
        if (tv_Email.getText().toString().isEmpty()) {
            tv_Email.setError("Enter Email Address");
            i = 1;
        }
        if (tv_Mobile.getText().toString().isEmpty()) {
            tv_Mobile.setError("Enter MobileNo");
            i = 1;
        }
        if (i == 0) {
            return true;
        } else {
            return false;
        }
    }

    String date1, month1, year1;

    public void calender1(final TextView et) throws ParseException {
        Log.e("fsdf", et.getText().toString());
        if (et.getText().toString().equals("")) {
            final Dialog alertDialog1 = new Dialog(getActivity());
            alertDialog1.setContentView(R.layout.customdatelayout);
            Locale.setDefault(new Locale("en", "GB"));
            final DatePicker dp1 = (DatePicker) alertDialog1.findViewById(R.id.DatePicker);


            final TextView tv_error = (TextView) alertDialog1.findViewById(R.id.tv_error);
            Button bt1 = (Button) alertDialog1.findViewById(R.id.submit);
            alertDialog1.show();
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat sdfmt2 = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        int date = dp1.getDayOfMonth();
                        int montha = dp1.getMonth() + 1;
                        int year = dp1.getYear();
                        if (date < 10) {
                            date1 = "0" + date;
                        } else
                            date1 = date + "";
                        if (montha < 10) {
                            month1 = "0" + montha;
                        } else
                            month1 = "" + montha;
                        year1 = String.valueOf(dp1.getYear());
                        Date dDate = sdfmt2.parse(date1 + "-" + month1 + "-" + year1);
                        et.setText(month1 + "/" + date1 + "/" + year1);
                        alertDialog1.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } else {
            String date = et.getText().toString().trim().replace("/", ",");
            String[] date11 = date.split(",");
            final Dialog alertDialog1 = new Dialog(getActivity());
            alertDialog1.setContentView(R.layout.customdatelayout);
            final DatePicker dp1 = (DatePicker) alertDialog1.findViewById(R.id.DatePicker);
            dp1.updateDate(Integer.parseInt(date11[2]), Integer.parseInt(date11[1]) - 1, Integer.parseInt(date11[0]));
            final TextView tv_error = (TextView) alertDialog1.findViewById(R.id.tv_error);
            Button bt1 = (Button) alertDialog1.findViewById(R.id.submit);
            alertDialog1.show();
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat sdfmt2 = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        int date = dp1.getDayOfMonth();
                        int montha = dp1.getMonth() + 1;
                        int year = dp1.getYear();
                        if (date < 10) {
                            date1 = "0" + date;
                        } else
                            date1 = date + "";
                        if (montha < 10) {
                            month1 = "0" + montha;
                        } else
                            month1 = "" + montha;
                        year1 = String.valueOf(dp1.getYear());
                        Date dDate = sdfmt2.parse(date1 + "-" + month1 + "-" + year1);
                        et.setText(date1 + "-" + month1 + "-" + year1);
                        alertDialog1.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        final String tags = "sssa";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest;
        volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enrollment_no", "SGSsixA2720");
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void FromCard() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
        // Intent i = new Intent(Intent.ACTION_PICK,
        //  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 5);
    }
}
