package com.example.guardianwatch;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {

    //회원가입하기
    @POST("signup")
    Call<ResponseBody> createUser(@Body UserData user);


    //    @FormUrlEncoded
//    @POST("signup")
//    Call<SignUpResult> signUp(@Field("id") String id, @Field("password") String password);

    //로그인하기
    @POST("login")
    Call<ResponseBody> loginUser(@Body UserData user);

//    Call<LoginResult> login(@Field("id") String id, @Field("password") String password);

//    @POST("add_kid")
//    Call<ResponseBody> addChild(@Body ChildData childData);

    //아이 등록하기
    @FormUrlEncoded
    @POST("add_kid")
    Call<ResponseBody> addKid(@Field("id") String id,
                              @Field("name") String name,
                              @Field("gender") String gender,
                              @Field("year") String year,
                              @Field("month") String month,
                              @Field("day") String day,
                              @Field("place") String place,
//                            @Field("image") String image,
                              @Field("represent") String represent
    );

    //아이 목록 가져오기
    @GET("get_kids/{id}")
    Call<ResponseBody> getKids(@Path("id") String id);

    //아이 편집 시에 정보 가져오기
    @GET("get_kid/{id}/{name}")
    Call<ResponseBody> getKid(@Path("id") String id, @Path("name") String name);

    //아이 편집 후 반영하기
    @FormUrlEncoded
    @PUT("change_kid/{user_id}/{user_name}")
    Call<ResponseBody> editKid(
            @Path("user_id") String user_id,
            @Path("user_name") String user_name,
            @Field("id") String id,
            @Field("name") String name,
            @Field("gender") String gender,
            @Field("year") String year,
            @Field("month") String month,
            @Field("day") String day,
            @Field("place") String place,
            @Field("image") String image
    );

    @FormUrlEncoded
    @PUT("represent/{id}")
    Call<ResponseBody> changeRepresent(
            @Path("id") String user_id, @Field("name") String represent);

    @DELETE("delete/{id}/{name}")
    Call<ResponseBody> deleteChild(
            @Path("id") String id, @Path("name") String name);

    // http://210.102.178.157:8000/heatmap/:id/:name/:year/:month/:day
    @GET("heatmap/{id}/{name}/{year}/{month}/{day}")
    Call<ResponseBody> getHeatMap(
            @Path("id") String id,
            @Path("name") String name,
            @Path("year") String year,
            @Path("month") String month,
            @Path("day") String day);
    //http://210.102.178.157:8000/pathmap/:id/:name/:year/:month/:day
    @GET("pathmap/{id}/{name}/{year}/{month}/{day}")
    Call<ResponseBody> getPathMap(
            @Path("id") String id,
            @Path("name") String name,
            @Path("year") String year,
            @Path("month") String month,
            @Path("day") String day);
    // http://210.102.178.157:8000/BEV/:id/:name/:year/:month/:day/:ch
//    @GET("BEV/{id}/{name}/{year}/{month}/{day}/{ch}")
//    Call<ResponseBody> getBEV(
//            @Path("id") String id,
//            @Path("name") String name,
//            @Path("year") String year,
//            @Path("month") String month,
//            @Path("day") String day,
//            @Path("ch") String ch);

    @GET("analysis/{id}/{name}/{year}/{month}/{day}/{ch}")
    Call<ResponseBody> getAnalysis(
            @Path("id") String id,
            @Path("name") String name,
            @Path("year") String year,
            @Path("month") String month,
            @Path("day") String day,
            @Path("ch") String ch);

}
