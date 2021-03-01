package com.a2z.deliver.webService;


import com.a2z.deliver.models.GetImageMaster;
import com.a2z.deliver.models.chooseLocation.AddressMaster;
import com.a2z.deliver.models.chooseLocation.EditLocationMaster;
import com.a2z.deliver.models.driverInterested.DriverInterestedMaster;
import com.a2z.deliver.models.drivingLicence.DrivingLicenceMaster;
import com.a2z.deliver.models.estimationRate.GetEstimationRateMaster;
import com.a2z.deliver.models.forgotPassword.ForgotPasswordDetails;
import com.a2z.deliver.models.idProof.UploadIdProofImage;
import com.a2z.deliver.models.insurancePolicy.InsurancePolicyMaster;
import com.a2z.deliver.models.items.ItemDetailMaster;
import com.a2z.deliver.models.items.ItemMaster;
import com.a2z.deliver.models.login.LoginMaster;
import com.a2z.deliver.models.MessageModel;
import com.a2z.deliver.models.onGoing.OnGoingItemsMaster;
import com.a2z.deliver.models.onTheWay.OnTheWayMaster;
import com.a2z.deliver.models.otp.SendOtpMaster;
import com.a2z.deliver.models.toPickUp.ToPickUpMaster;
import com.a2z.deliver.models.upComeingItem.UpComingItemMaster;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import rx.Observable;

public interface WebServices {

    //@Headers("Authtoken:"+API_Params.authToken)
    @POST(APIUrl.login)
    Observable <LoginMaster> loginApi(@Body JsonObject jsonBody);

    @Multipart
    @POST(APIUrl.register)
    Observable <LoginMaster> registerApi(@Part(API_Params.deviceAccess) RequestBody deviceAccess,
                                         @Part(API_Params.deviceToken) RequestBody deviceToken,
                                         @Part(API_Params.firstName) RequestBody firstName,
                                         @Part(API_Params.lastName) RequestBody lastName,
                                         @Part(API_Params.email) RequestBody email,
                                         @Part(API_Params.mobileNumber) RequestBody mobileNumber,
                                         @Part(API_Params.password) RequestBody password,
                                         @Part(API_Params.isEdit) RequestBody isEdit,
                                         @Part(API_Params.userId) RequestBody userId,
                                         @Part MultipartBody.Part image);

    @POST(APIUrl.sendOtp)
    Observable <SendOtpMaster> sendOtpApi(@Body JsonObject jsonBody);

    @POST(APIUrl.forgotPassword)
    Observable <ForgotPasswordDetails> forgotPasswordDetailsApi(@Body JsonObject jsonBody);

    @POST(APIUrl.verifyOtp)
    Observable <MessageModel> verifyOtpApi(@Body JsonObject jsonBody);

    @POST(APIUrl.changePassword)
    Observable <MessageModel> changePassword(@Body JsonObject jsonBody);

    @POST(APIUrl.getAddressList)
    Observable <AddressMaster> getAddressList(@Body JsonObject jsonBody);

    @POST(APIUrl.getAllItems)
    Observable <ItemMaster> getAllItemApi(@Body JsonObject JsonBody);

    @POST(APIUrl.getItemDetails)
    Observable <ItemDetailMaster> getItemDetailsApi(@Body JsonObject jsonBody);

    @POST(APIUrl.addAddress)
    Observable <EditLocationMaster> editLocationApi(@Body JsonObject jsonBody);

    @Multipart
    @POST(APIUrl.uploadIdProofImage)
    Observable <UploadIdProofImage> uploadIdProofImageApi(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                                          @Part(API_Params.userId) RequestBody userId,
                                                          @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                                          @Part MultipartBody.Part image);

    @Multipart
    @POST(APIUrl.uploadDrivingLicenceImage)
    Observable <DrivingLicenceMaster> uploadDrivingLicenceImageApi(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                                                   @Part(API_Params.userId) RequestBody userId,
                                                                   @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                                                   @Part MultipartBody.Part image1,
                                                                   @Part MultipartBody.Part image2);

    @Multipart
    @POST(APIUrl.uploadRCBookImage)
    Observable <DrivingLicenceMaster> uploadRCBookImageApi(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                                           @Part(API_Params.userId) RequestBody userId,
                                                           @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                                           @Part MultipartBody.Part image1,
                                                           @Part MultipartBody.Part image2);

    @Multipart
    @POST(APIUrl.uploadInsurancePolicyImage)
    Observable <InsurancePolicyMaster> uploadInsurancePolicyImageApi(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                                                     @Part(API_Params.userId) RequestBody userId,
                                                                     @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                                                     @Part MultipartBody.Part image1,
                                                                     @Part MultipartBody.Part image2,
                                                                     @Part MultipartBody.Part image3,
                                                                     @Part MultipartBody.Part image4);

    @POST(APIUrl.getImages)
    Observable <GetImageMaster> getImagesApi(@Body JsonObject jsonBody);

    @POST(APIUrl.pickUpOrders)
    Observable <ToPickUpMaster> getTopickupOrderApi(@Body JsonObject jsonBody);

    @POST(APIUrl.onTheWayOrders)
    Observable <OnTheWayMaster> getOnTheWayOrderApi(@Body JsonObject jsonBody);

    @POST(APIUrl.onGoingItems)
    Observable <OnGoingItemsMaster> getonGoingItemsApi(@Body JsonObject jsonBody);

    @POST(APIUrl.upcomingItems)
    Observable <UpComingItemMaster> getupcomingItemsApi(@Body JsonObject jsonBody);

    @POST(APIUrl.driverInterested)
    Observable <DriverInterestedMaster> driverInterestedApi(@Body JsonObject jsonBody);

    @POST(APIUrl.getEstimationRate)
    Observable <GetEstimationRateMaster> getEstimationRateApi(@Body JsonObject jsonBody);

}
