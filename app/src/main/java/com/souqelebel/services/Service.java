package com.souqelebel.services;


import com.souqelebel.models.AddOrderModel;
import com.souqelebel.models.BankDataModel;
import com.souqelebel.models.FavoriteDataModel;
import com.souqelebel.models.MainCategoryDataModel;
import com.souqelebel.models.NotificationCount;
import com.souqelebel.models.NotificationDataModel;
import com.souqelebel.models.OrderDataModel;
import com.souqelebel.models.OrderModel;
import com.souqelebel.models.PlaceGeocodeData;
import com.souqelebel.models.PlaceMapDetailsData;
import com.souqelebel.models.ProductDataModel;
import com.souqelebel.models.ProductModel;
import com.souqelebel.models.SettingModel;
import com.souqelebel.models.Slider_Model;
import com.souqelebel.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {


    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("phone_code") String phone_code,
                          @Field("phone") String phone

    );

    @FormUrlEncoded
    @POST("api/logout")
    Call<ResponseBody> logout(@Header("Authorization") String user_token,
                              @Field("phone_token") String phone_token,
                              @Field("soft_type") String soft_type


    );

    @FormUrlEncoded
    @POST("api/update-firebase")
    Call<ResponseBody> updatePhoneToken(@Header("Authorization") String Authorization,
                                        @Field("phone_token") String phone_token,
                                        @Field("soft_type") String soft_type
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signUpWithoutImage(@Field("name") String name,
                                       @Field("phone_code") String phone_code,
                                       @Field("phone") String phone,
                                       @Field("email") String email
    );

    @Multipart
    @POST("api/register")
    Call<UserModel> signUpWithImage(@Part("name") RequestBody name,
                                    @Part("phone_code") RequestBody phone_code,
                                    @Part("phone") RequestBody phone,
                                    @Part MultipartBody.Part logo


    );

    @GET("api/sttings")
    Call<SettingModel> getSetting(@Header("lang") String lang

    );


    @GET("api/slider")
    Call<Slider_Model> get_slider();


   /*

    @GET("api/get-most-sale")
    Call<ProductDataModel> getMostSeller(@Query("pagination") String pagination);


    @GET("api/get-box")
    Call<ProductDataModel> getFamilyBoxes(@Query("pagination") String pagination);

    @GET("api/category-product")
    Call<CategoryProductDataModel> getCategoryProducts(@Query("pagination") String pagination,
                                                       @Query("user_id") int user_id);

    @GET("api/genaral-search")
    Call<ProductDataModel> Search(@Query("pagination") String pagination,
                                  @Query("user_id") int user_id,
                                  @Query("search_name") String search_name,
                                  @Query("have_offer") String have_offer,
                                  @Query("product_type") String product_type,

                                  @Query("departemnt_id") String departemnt_id
    );

    @GET("api/products-by-dep")
    Call<ProductDataModel> getOffersProducts(@Query("pagination") String pagination,
                                             @Query("departemnt_id") String departemnt_id,
                                             @Query("user_id") String user_id,
                                             @Query("page") int page
    );*/

    @GET("api/product")
    Call<ProductModel> Product_detials(@Query("product_id") String product_id);

    @GET("api/one-order")
    Call<OrderModel> order_detials(@Query("order_id") int order_id);

   /* @GET("api/brands")
    Call<MainCategoryDataModel> getBrands(
            @Query("pagination") String pagination
    );

    @GET("api/category")
    Call<MainCategoryDataModel> getCategory(
            @Query("pagination") String pagination
    );*/

    @GET("api/banks")
    Call<BankDataModel> getBanks();

    @FormUrlEncoded
    @POST("api/favorite-action")
    Call<ResponseBody> favoriteAction(@Header("Authorization") String Authorization,
                                          @Field("product_id") int product_id);

    @GET("api/my-favorites")
    Call<FavoriteDataModel> getMyFavoriteProducts(@Header("Authorization") String Authorization);

    @GET("api/my-notification")
    Call<NotificationDataModel> getNotification(@Header("Authorization") String user_token


    );

    @FormUrlEncoded
    @POST("api/delete-notification")
    Call<ResponseBody> deleteNotification(@Header("Authorization") String user_token,
                                          @Field("notification_id") int notification_id
    );

    @GET("api/count-unread")
    Call<NotificationCount> getUnreadNotificationCount(@Header("Authorization") String user_token
    );

    @GET("api/my-orders")
    Call<OrderDataModel> getOrders(@Header("Authorization") String user_token,
                                   @Query("user_id") String user_id,
                                   @Query("pagination") String pagination,
                                   @Query("page") int page,
                                   @Query("limit_per_page") int limit_per_page

    );

    @POST("api/create-order")
    Call<OrderModel> createOrder(
            @Header("Authorization") String Authorization,
            @Body AddOrderModel addOrderModel)
            ;

    @FormUrlEncoded
    @POST("api/find-coupon")
    Call<SettingModel> getCouponValue(@Field("coupon_num") String coupon_num);

    @Multipart
    @POST("api/update-profile")
    Call<UserModel> editClientProfileWithImage(@Header("Authorization") String Authorization,
                                               @Part("name") RequestBody name,
                                               @Part MultipartBody.Part logo

    );

    @Multipart
    @POST("api/update-profile")
    Call<UserModel> editClientProfileWithoutImage(@Header("Authorization") String Authorization,
                                                  @Part("name") RequestBody name
    );

    @FormUrlEncoded
    @POST("api/add-rate")
    Call<ResponseBody> rate(
            @Header("Authorization") String Authorization,
            @Field("product_id") String product_id,
            @Field("user_id") String user_id,
            @Field("rate") String rate
    );

    @FormUrlEncoded
    @POST("api/send-partner")
    Call<ResponseBody> bepartner(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("address") String address
    );



    @GET("api/category-product")
    Call<MainCategoryDataModel> getMainCategory_Products();

    @GET("api/search-product")
    Call<ProductDataModel> search(@Query("user_id") String user_id,
                                  @Query("pagination") String pagination,
                                  @Query("page") int page,
                                  @Query("search_name") String search_name

                                  );
}