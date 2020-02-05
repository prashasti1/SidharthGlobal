package com.siddarthglobalschool.Util;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EndPointInterface {
@FormUrlEncoded
    @POST("/schoolManagement/weblogin.php?")
    Call<LoginResponse> Login(@Field("enrollment_no") String Username,
                              @Field("password") String Password);
@FormUrlEncoded
    @POST("/schoolManagement/attendanceApi.php?")
    Call<AttendanceResponse> attendanceApi(@Field("enrollment_no") String Username);



@FormUrlEncoded
    @POST("/schoolManagement/homeworkApi.php?")
    Call<HomeWorkResponse> homeworkApi(@Field("class_id") String Username,
                              @Field("section_id") String Password);


    @POST("/schoolManagement/eventApi.php?")
    Call<EventResponse> eventApi();

 @POST("/schoolManagement/examTypeApi.php?")
    Call<EventResponse> examTypeApi();




   // http://siddharthaglobalschoollko.in/schoolManagement/totalMarksApi.php?enrollment_no=SGSsixC7481&exam_name=2
    @FormUrlEncoded
    @POST("/schoolManagement/totalMarksApi.php?")
    Call<ReaultResponse> totalMarksApi(@Field("enrollment_no") String Username,
                                      @Field("exam_name") String exam_name);

    @POST("/schoolManagement/noticeApi.php?")
    Call<NoticeResponse> noticeApi();

    @FormUrlEncoded
    @POST("/schoolManagement/changePassApi.php?")
        Call<LoginResponse> changePassApi(@Field("enrollment_no") String Username,
                              @Field("old_password") String Password,@Field("new_password") String new_password);
@FormUrlEncoded
    @POST("/schoolManagement/profileUploadApi.php?")
        Call<ProfileResponse> profileUploadApi(@Field("enrollment_no") String Username,
                             @Field("base64_file") String base64_file);

   // http://siddharthaglobalschoollko.in/schoolManagement/profileUploadApi.php?base64_file='V2ViZWFzeXN0ZXAgOik='&enrollment_no=111

    @FormUrlEncoded
 @POST("/schoolManagement/marksApi.php?")
        Call<ResultResponse> marksApi(@Field("enrollment_no") String Username,
                              @Field("exam_name") String Password);
    @Multipart
 @POST("/schoolManagement/imgApi.php?apicall=uploadpic")
        Call<ImageResponse> imgApi(@Part("pic\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("enrollment_no") RequestBody desc);



    @FormUrlEncoded
    @POST("/schoolManagement/registerApi.php?")
    Call<LoginResponse> registerApi(@Field("name") String name,
                                     @Field("gender") String gender,
                                     @Field("dob") String dob,
                                     @Field("address") String address,
                                     @Field("father_name") String father_name,
                                     @Field("mother_name") String mother_name,
                                     @Field("mobile_no") String mobile_no,
                                     @Field("enrollment_no") String enrollment_no,
                                     @Field("pin_code") String pin_code,
                                     @Field("email") String email,
                                     @Field("doa") String doa);


  @FormUrlEncoded
    @POST("/schoolManagement/deviceTokenApi.php?")
    Call<deviceResponse> deviceTokenApi(@Field("enrollment_no") String enrollment_no,
                                     @Field("deviceId") String deviceId,
                                     @Field("deviceToken") String deviceToken);


}
