package com.gmail.hanivisushiva.insurehub;




import com.gmail.hanivisushiva.insurehub.Model.AdminCustomerRequestsModel.AdminCustomerRequestsModel;
import com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel.AdminOffersModel;
import com.gmail.hanivisushiva.insurehub.Model.AdminPolicyRequestModel.AdminPolicyRequestModel;
import com.gmail.hanivisushiva.insurehub.Model.AllReferals.AllReferals;
import com.gmail.hanivisushiva.insurehub.Model.BannerImagesModel.BannerImagesModel;
import com.gmail.hanivisushiva.insurehub.Model.CategoryModel.CategoryModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerAllRequestsModel.CustomerAllRequestsModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerAllServicesModel.CustomerAllServicesModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerModel.CustomerModel;
import com.gmail.hanivisushiva.insurehub.Model.CustomerMyRewards.CustomerMyRewards;
import com.gmail.hanivisushiva.insurehub.Model.CustomerPolicy.CustomerPolicy;
import com.gmail.hanivisushiva.insurehub.Model.CustomerProducts.CustomerProducts;
import com.gmail.hanivisushiva.insurehub.Model.CustomerServicePartners.CustomerServicePartners;
import com.gmail.hanivisushiva.insurehub.Model.DashBoardModel.DashBoardModel;
import com.gmail.hanivisushiva.insurehub.Model.LoginModel.LoginModel;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderAllBillsModel.ServiceProviderAllBillsModel;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderDashBoardModel.ServiceProviderDashBoardModel;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderModel.ServiceProviderModel;
import com.gmail.hanivisushiva.insurehub.Model.UpdateModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> user_login(
            @Field("txt_user") String username,
            @Field("txt_password") String password
    );

    @POST("categories.php")
    Call<CategoryModel> getCategory();

    @FormUrlEncoded
    @POST("addcategory.php")
    Call<UpdateModel> add_new_category(
            @Field("category") String  category
    );

    @FormUrlEncoded
    @POST(" editcategory.php")
    Call<UpdateModel> edit_category(
            @Field("category") String  category,
            @Field("id") String  id

    );

    @FormUrlEncoded
    @POST("delcategory.php")
    Call<UpdateModel> delete_category(
            @Field("id") String  id
    );


    @POST("serviceproviders.php")
    Call<ServiceProviderModel> getServiceProvider();


    @POST("customers.php")
    Call<CustomerModel> getCustomersList();

    @Multipart
    @POST("addserviceprovider.php")
    Call<UpdateModel> addServiceProvider(
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("drpcategory") RequestBody category,
            @Part("mail") RequestBody mail,
            @Part("website") RequestBody website,
            @Part("contact") RequestBody contact,
            @Part("description") RequestBody description,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part file
    );


    @Multipart
    @POST("addcustomer.php")
    Call<UpdateModel> addNewCustomer(
            @Part("firstname") RequestBody firstname,
            @Part("lastname") RequestBody lastname,
            @Part("gender") RequestBody gender,
            @Part("dob") RequestBody dob,
            @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("pincode") RequestBody pincode,
            @Part("pan") RequestBody pan,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part file
    );


    @Multipart
    @POST("editserviceprovider.php")
    Call<UpdateModel> editServiceProvider(
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("drpcategory") RequestBody category,
            @Part("mail") RequestBody mail,
            @Part("website") RequestBody website,
            @Part("contact") RequestBody contact,
            @Part("description") RequestBody description,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("id") RequestBody id,
            @Part MultipartBody.Part file
    );


    @Multipart
    @POST("editserviceprovider.php")
    Call<UpdateModel> editServiceProviderWithoutImage(
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("drpcategory") RequestBody category,
            @Part("mail") RequestBody mail,
            @Part("website") RequestBody website,
            @Part("contact") RequestBody contact,
            @Part("description") RequestBody description,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("id") RequestBody id
    );


    @Multipart
    @POST("editcustomer.php")
    Call<UpdateModel> editNewCustomer(
            @Part("firstname") RequestBody firstname,
            @Part("lastname") RequestBody lastname,
            @Part("gender") RequestBody gender,
            @Part("dob") RequestBody dob,
            @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("pincode") RequestBody pincode,
            @Part("pan") RequestBody pan,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("id") RequestBody id,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("editcustomer.php")
    Call<UpdateModel> editNewCustomerWithoutImage(
            @Part("firstname") RequestBody firstname,
            @Part("lastname") RequestBody lastname,
            @Part("gender") RequestBody gender,
            @Part("dob") RequestBody dob,
            @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("pincode") RequestBody pincode,
            @Part("pan") RequestBody pan,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("id") RequestBody id
    );

    @FormUrlEncoded
    @POST("delcustomer.php")
    Call<UpdateModel> delete_customer(
            @Field("id") String  id
    );

    @FormUrlEncoded
    @POST("delserviceprovider.php")
    Call<UpdateModel> delete_serviceProvider(
            @Field("id") String  id
    );

    @FormUrlEncoded
    @POST("s_addbill.php")
    Call<UpdateModel> serviceProvider_addBills(
            @Field("cardno") String  cardno,
            @Field("amount") String  amount,
            @Field("discount") String  discount,
            @Field("total") String  total,
            @Field("billdate") String bill_date,
            @Field("id") String  id
    );



    @FormUrlEncoded
    @POST("c_addservice.php")
    Call<UpdateModel> user_add_service(
            @Field("id") String  id,
            @Field("drpcategory") String  category
    );


    @FormUrlEncoded
    @POST("c_request.php")
    Call<UpdateModel> user_raise_request(
            @Field("name") String  name,
            @Field("phone") String  phone,
            @Field("request") String  request,
            @Field("id") String  id
    );


    @FormUrlEncoded
    @POST("s_bills.php")
    Call<ServiceProviderAllBillsModel> service_provider_get_all_bills(
            @Field("id") String  sid
    );

    @FormUrlEncoded
    @POST("s_editbills.php")
    Call<UpdateModel> serviceProvider_EditBills(
            @Field("cardno") String  cardno,
            @Field("amount") String  amount,
            @Field("discount") String  discount,
            @Field("total") String  total,
            @Field("bid") String  id
    );


    @POST("bills.php")
    Call<ServiceProviderAllBillsModel> admin_get_all_bills();

    @POST("custrequests.php")
    Call<AdminCustomerRequestsModel> admin_customer_request_policy();

    @POST("custpolicyrequests.php")
    Call<AdminPolicyRequestModel> admin_policy_requests();


    @FormUrlEncoded
    @POST("approvepolicyrequests.php")
    Call<UpdateModel> admin_approve_request(
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("rejectpolicyrequests.php")
    Call<UpdateModel> admin_reject_request(
            @Field("id") String id
    );


    @Multipart
    @POST("addbannerads.php")
    Call<UpdateModel> addBanners(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("catserviceproviders.php")
    Call<ServiceProviderModel> getCatServiceProvider(
            @Field("categoryid") String category
    );

    @FormUrlEncoded
    @POST("addcustcategory.php")
    Call<UpdateModel> add_customer_category(
            @Field("id") String id,
            @Field("categoryid") String category,
            @Field("drpservices") String services,
            @Field("cardno") String cardno
    );


    @POST("bannerads.php")
    Call<BannerImagesModel> getBanners();

    @FormUrlEncoded
    @POST("delbannerads.php")
    Call<UpdateModel> deleteBanners(
            @Field("id") String id
    );


    @POST("dashboard.php")
    Call<DashBoardModel> getDashBoardDetails();

    @FormUrlEncoded
    @POST("s_dashboard.php")
    Call<ServiceProviderDashBoardModel> getServiceProviderDashBoardDetails(
            @Field("id") String id
    );



    @FormUrlEncoded
    @POST("c_services.php")
    Call<CustomerAllServicesModel> getCustomerAllServices(
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("c_requests.php")
    Call<CustomerAllRequestsModel> getCustomerAllRequests(
            @Field("id") String id
    );



    @Multipart
    @POST("addrewards.php")
    Call<UpdateModel> admin_add_rewards(
            @Part("description") RequestBody description,
            @Part("id") RequestBody id,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("addoffers.php")
    Call<UpdateModel> admin_add_offers(
            @Part MultipartBody.Part file
    );


    @Multipart
    @POST("addcustpolicy.php")
    Call<UpdateModel> uploadMultiple(
            @Part("description") RequestBody description,
            @Part("id") RequestBody rid,
            @Part("size") RequestBody size,
            @Part List<MultipartBody.Part> files);


    @FormUrlEncoded
    @POST("deleteoffers.php")
    Call<UpdateModel> deleteOffers(
            @Field("id") String id
    );

    @POST("offers.php")
    Call<AdminOffersModel> getOffers();

    @FormUrlEncoded
    @POST("c_rewards.php")
    Call<CustomerMyRewards> get_customer_rewards(
            @Field("id") String  id
    );


    @FormUrlEncoded
    @POST("c_policies.php")
    Call<CustomerPolicy> getCustomerPolicy(
            @Field("id") String  id
    );

    @POST("products.php")
    Call<CustomerProducts> getCustomerProducts();


    @POST("servicepartners.php")
    Call<CustomerServicePartners> getServicePartners();

    @FormUrlEncoded
    @POST("refer.php")
    Call<UpdateModel> referAndEarn(
            @Field("id") String  id,
            @Field("name") String  name,
            @Field("phone") String  phone,
            @Field("requirement") String  requirement,
            @Field("email") String  email,
            @Field("description") String  description
    );


    @POST("referals.php")
    Call<AllReferals> getAllReferals();

    @FormUrlEncoded
    @POST("referalby.php")
    Call<CustomerModel> getReferalDetails(
            @Field("uid") String  id
    );


    @FormUrlEncoded
    @POST("s_enquiry.php")
    Call<UpdateModel> serviceProviderEnquiry(
            @Field("id") String  id,
            @Field("name") String  name,
            @Field("phone") String  phone,
            @Field("request") String  request
    );

































//    @Multipart delbannerads.php


//    @POST("a_addemployee.php")
//    Call<AddNewUserModel> add_new_employee(
//            @Part("txtName") RequestBody name,
//            @Part("txtPassword") RequestBody password,
//            @Part("txtEmail") RequestBody email,
//            @Part("txtPhone") RequestBody phone,
//            @Part MultipartBody.Part file);










}
