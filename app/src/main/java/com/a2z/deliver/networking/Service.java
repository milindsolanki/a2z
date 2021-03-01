package com.a2z.deliver.networking;

import android.util.Log;

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
import com.a2z.deliver.webService.API_Params;
import com.a2z.deliver.webService.WebServices;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ennur on 6/25/16.
 */
public class Service {
    private final WebServices networkService;

    public Service(WebServices networkService) {
        this.networkService = networkService;
    }

    /*-------------------------------------Change Password----------------------------------------*/
    public Subscription getChangePassword(final JsonObject jsonObject, final Service.GetChangePasswordCallback callback) {

        return networkService.changePassword( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends MessageModel>>( ) {
                    @Override
                    public Observable <? extends MessageModel> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <MessageModel>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(MessageModel cityListResponse) {
                        callback.onSuccess( cityListResponse );

                    }
                } );
    }

    public interface GetChangePasswordCallback {
        void onSuccess(MessageModel cityListResponse);

        void onError(NetworkError networkError);
    }

    /*---------------------------------------Sign-Up----------------------------------------------*/
    public Subscription getSignup(@Part(API_Params.deviceAccess) RequestBody deviceAccess,
                                  @Part(API_Params.deviceToken) RequestBody deviceToken,
                                  @Part(API_Params.firstName) RequestBody firstName,
                                  @Part(API_Params.lastName) RequestBody lastName,
                                  @Part(API_Params.email) RequestBody email,
                                  @Part(API_Params.mobileNumber) RequestBody mobileNumber,
                                  @Part(API_Params.password) RequestBody password,
                                  @Part(API_Params.isEdit) RequestBody isEdit,
                                  @Part(API_Params.userId) RequestBody userId,
                                  @Part MultipartBody.Part image, final Service.GetSignUpCallback callback) {

        return networkService.registerApi( deviceAccess, deviceToken, firstName, lastName, email, mobileNumber, password, isEdit, userId, image )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends LoginMaster>>( ) {
                    @Override
                    public Observable <? extends LoginMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <LoginMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(LoginMaster loginMaster) {
                        callback.onSuccess( loginMaster );

                    }
                } );
    }

    public interface GetSignUpCallback {

        void onSuccess(LoginMaster loginMaster);

        void onError(NetworkError networkError);
    }

    /*----------------------------------------Login-----------------------------------------------*/
    public Subscription getLogin(final JsonObject jsonObject, final Service.GetLoginCallback callback) {

        return networkService.loginApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends LoginMaster>>( ) {
                    @Override
                    public Observable <? extends LoginMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <LoginMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(LoginMaster loginMaster) {
                        callback.onSuccess( loginMaster );

                    }
                } );
    }

    public interface GetLoginCallback {
        void onSuccess(LoginMaster loginMaster);

        void onError(NetworkError networkError);
    }

    /*---------------------------------------Otp--------------------------------------------------*/
    public Subscription getSendOtp(final JsonObject jsonObject, final Service.GetSendOtpCallback callback) {

        return networkService.sendOtpApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends SendOtpMaster>>( ) {
                    @Override
                    public Observable <? extends SendOtpMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <SendOtpMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(SendOtpMaster sendOtpMaster) {
                        callback.onSuccess( sendOtpMaster );

                    }
                } );
    }

    public interface GetSendOtpCallback {
        void onSuccess(SendOtpMaster sendOtpMaster);

        void onError(NetworkError networkError);
    }

    /*-----------------------------------------Forget-Password------------------------------------*/
    public Subscription getForgetPassword(final JsonObject jsonObject, final Service.GetForgetPasswordCallback callback) {

        return networkService.forgotPasswordDetailsApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends ForgotPasswordDetails>>( ) {
                    @Override
                    public Observable <? extends ForgotPasswordDetails> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <ForgotPasswordDetails>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(ForgotPasswordDetails forgotPasswordDetails) {
                        callback.onSuccess( forgotPasswordDetails );

                    }
                } );
    }

    public interface GetForgetPasswordCallback {
        void onSuccess(ForgotPasswordDetails forgotPasswordDetails);

        void onError(NetworkError networkError);
    }


    /*---------------------------------------------Otp-Verification-------------------------------*/

    public Subscription getOtpVerificatio(final JsonObject jsonObject, final Service.GetOtpCallback callback) {

        return networkService.verifyOtpApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends MessageModel>>( ) {
                    @Override
                    public Observable <? extends MessageModel> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <MessageModel>( ) {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(MessageModel messageModel) {
                        callback.onSuccess( messageModel );

                    }
                } );
    }

    public interface GetOtpCallback {
        void onSuccess(MessageModel messageModel);

        void onError(NetworkError networkError);
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Get Item List Api Call */
    public Subscription getItemList(final JsonObject jsonObject, final Service.GetItemListCallback callback) {

        return networkService.getAllItemApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends ItemMaster>>( ) {
                    @Override
                    public Observable <? extends ItemMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <ItemMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(ItemMaster itemMaster) {
                        callback.onSuccess( itemMaster );

                    }
                } );
    }

    public interface GetItemListCallback {
        void onSuccess(ItemMaster itemMaster);

        void onError(NetworkError networkError);
    }


    /*----------------------------------------Item Details Summary--------------------------------*/
    public Subscription getItemSummaryList(final JsonObject jsonObject, final Service.GetItemDetailsCallback callback) {

        return networkService.getItemDetailsApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends ItemDetailMaster>>( ) {
                    @Override
                    public Observable <? extends ItemDetailMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <ItemDetailMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( "Network", e.getMessage( ).toString( ) );
                        callback.onError( new NetworkError( e ) );
                    }

                    @Override
                    public void onNext(ItemDetailMaster itemDetailMaster) {
                        callback.onSuccess( itemDetailMaster );

                    }
                } );
    }

    public interface GetItemDetailsCallback {
        void onSuccess(ItemDetailMaster itemDetailMaster);

        void onError(NetworkError networkError);
    }

    /*--------------------------------------------Profile------------------------------------------*/
    public Subscription getProfile(@Part(API_Params.deviceAccess) RequestBody deviceAccess,
                                   @Part(API_Params.deviceToken) RequestBody deviceToken,
                                   @Part(API_Params.firstName) RequestBody firstName,
                                   @Part(API_Params.lastName) RequestBody lastName,
                                   @Part(API_Params.email) RequestBody email,
                                   @Part(API_Params.mobileNumber) RequestBody mobileNumber,
                                   @Part(API_Params.password) RequestBody password,
                                   @Part(API_Params.isEdit) RequestBody isEdit,
                                   @Part(API_Params.userId) RequestBody userId,
                                   @Part MultipartBody.Part image, final Service.GetProfileCallback callback) {

        return networkService.registerApi( deviceAccess, deviceToken, firstName, lastName, email, mobileNumber, password, isEdit, userId, image )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends LoginMaster>>( ) {
                    @Override
                    public Observable <? extends LoginMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <LoginMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(LoginMaster loginMaster) {
                        callback.onSuccess( loginMaster );

                    }
                } );
    }

    public interface GetProfileCallback {
        void onSuccess(LoginMaster loginMaster);

        void onError(NetworkError networkError);
    }

    /*----------------------------------------Address List--------------------------------*/
    public Subscription getAddressList(final JsonObject jsonObject, final Service.GetAddressListCallback callback) {

        return networkService.getAddressList( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends AddressMaster>>( ) {
                    @Override
                    public Observable <? extends AddressMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <AddressMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(AddressMaster addressMaster) {
                        callback.onSuccess( addressMaster );

                    }
                } );
    }

    public interface GetAddressListCallback {
        void onSuccess(AddressMaster addressMaster);

        void onError(NetworkError networkError);
    }

    /*----------------------------------------Edit Address--------------------------------*/
    public Subscription getEditAddress(final JsonObject jsonObject, final Service.GetEditAddressCallback callback) {

        return networkService.editLocationApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends EditLocationMaster>>( ) {
                    @Override
                    public Observable <? extends EditLocationMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <EditLocationMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(EditLocationMaster editLocationMaster) {
                        callback.onSuccess( editLocationMaster );

                    }
                } );
    }

    public interface GetEditAddressCallback {
        void onSuccess(EditLocationMaster editLocationMaster);

        void onError(NetworkError networkError);
    }

    /*---------------------------------------Upload Id Proof Image-----------------------------------------------------*/

    public Subscription getUpdateIdProofImage(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                              @Part(API_Params.userId) RequestBody userId,
                                              @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                              @Part MultipartBody.Part image,
                                              final Service.GetUpdateIdProofCallback callback) {

        return networkService.uploadIdProofImageApi( isUpdate, userId, isUpdateImage, image )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends UploadIdProofImage>>( ) {
                    @Override
                    public Observable <? extends UploadIdProofImage> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <UploadIdProofImage>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( "success", "0" );
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(UploadIdProofImage uploadIdProofImage) {
                        Log.e( "success", "1" );
                        callback.onSuccess( uploadIdProofImage );

                    }
                } );
    }


    public interface GetUpdateIdProofCallback {
        void onSuccess(UploadIdProofImage uploadIdProofImage);

        void onError(NetworkError networkError);
    }

    /*-----------------------------------------------Get Uploaddriving Licenceimage---------------------------------------------------------*/

    public Subscription getUploadDrivingLicenceImage(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                                     @Part(API_Params.userId) RequestBody userId,
                                                     @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                                     @Part MultipartBody.Part image1,
                                                     @Part MultipartBody.Part image2, final Service.GetUploadDrivingLicenceImageCallback callback) {

        return networkService.uploadDrivingLicenceImageApi( isUpdate, userId, isUpdateImage, image1, image2 )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends DrivingLicenceMaster>>( ) {
                    @Override
                    public Observable <? extends DrivingLicenceMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <DrivingLicenceMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( "success", "0" );
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(DrivingLicenceMaster drivingLicenceMaster) {
                        Log.e( "success", "1" );
                        callback.onSuccess( drivingLicenceMaster );

                    }
                } );
    }

    public interface GetUploadDrivingLicenceImageCallback {

        void onSuccess(DrivingLicenceMaster drivingLicenceMaster);

        void onError(NetworkError networkError);

    }


    /*---------------------------------------------Get Upload Insurance Policy Image---------------------------------------------------*/

    public Subscription getUploadInsurancePolicyImage(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                                      @Part(API_Params.userId) RequestBody userId,
                                                      @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                                      @Part MultipartBody.Part image1,
                                                      @Part MultipartBody.Part image2,
                                                      @Part MultipartBody.Part image3,
                                                      @Part MultipartBody.Part image4,
                                                      final Service.GetUploadInsurancePolicyImageCallback callback) {

        return networkService.uploadInsurancePolicyImageApi( isUpdate, userId, isUpdateImage, image1, image2, image3, image4 )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends InsurancePolicyMaster>>( ) {
                    @Override
                    public Observable <? extends InsurancePolicyMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <InsurancePolicyMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( "success", "0" );
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(InsurancePolicyMaster insurancePolicyMaster) {
                        callback.onSuccess( insurancePolicyMaster );
                    }


                } );
    }

    public interface GetUploadInsurancePolicyImageCallback {

        void onSuccess(InsurancePolicyMaster insurancePolicyMaster);

        void onError(NetworkError networkError);
    }

    /*----------------------------------------------Get Upload RCBook Image------------------------------------------------------*/
    public Subscription getUploadRCBookImage(@Part(API_Params.isUpdate) RequestBody isUpdate,
                                             @Part(API_Params.userId) RequestBody userId,
                                             @Part(API_Params.isUpdateImage) RequestBody isUpdateImage,
                                             @Part MultipartBody.Part image1,
                                             @Part MultipartBody.Part image2, final Service.GetUploadRCBookImageCallback callback) {

        return networkService.uploadRCBookImageApi( isUpdate, userId, isUpdateImage, image1, image2 )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends DrivingLicenceMaster>>( ) {
                    @Override
                    public Observable <? extends DrivingLicenceMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <DrivingLicenceMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( "success", "0" );
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(DrivingLicenceMaster drivingLicenceMaster) {
                        Log.e( "success", "1" );
                        callback.onSuccess( drivingLicenceMaster );

                    }
                } );
    }

    public interface GetUploadRCBookImageCallback {
        void onSuccess(DrivingLicenceMaster drivingLicenceMaster);

        void onError(NetworkError networkError);
    }

    /*---------------------------------------------Get Image------------------------------------------------------*/
    public Subscription getImage(final JsonObject jsonObject, final Service.GetImageCallback callback) {

        return networkService.getImagesApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends GetImageMaster>>( ) {
                    @Override
                    public Observable <? extends GetImageMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <GetImageMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(GetImageMaster getImageMaster) {
                        callback.onSuccess( getImageMaster );

                    }
                } );
    }

    public interface GetImageCallback {
        void onSuccess(GetImageMaster getImageMaster);

        void onError(NetworkError networkError);

    }

    /*----------------------------------------------Get Pick Up Orders-----------------------------------------------------*/
    public Subscription getPickUpOrders(final JsonObject jsonObject, final Service.GetPickupOrderCallback callback) {

        return networkService.getTopickupOrderApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends ToPickUpMaster>>( ) {
                    @Override
                    public Observable <? extends ToPickUpMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <ToPickUpMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(ToPickUpMaster toPickUpMaster) {
                        callback.onSuccess( toPickUpMaster );
                    }
                } );
    }

    public interface GetPickupOrderCallback {
        void onSuccess(ToPickUpMaster toPickUpMaster);

        void onError(NetworkError networkError);
    }

    /*------------------------------------------Get OnThe Way Orders--------------------------------------------------*/

    public Subscription getOnTheWayOrders(final JsonObject jsonObject, final Service.GetOnTheWayOrderCallback callback) {

        return networkService.getOnTheWayOrderApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends OnTheWayMaster>>( ) {
                    @Override
                    public Observable <? extends OnTheWayMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <OnTheWayMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(OnTheWayMaster onTheWayMaster) {
                        callback.onSuccess( onTheWayMaster );
                    }
                } );
    }

    public interface GetOnTheWayOrderCallback {
        void onSuccess(OnTheWayMaster onTheWayMaster);

        void onError(NetworkError networkError);
    }

    /*---------------------------------------onGoingItems-------------------------------------------------------*/


    public Subscription getOnGoingItems(final JsonObject jsonObject, final Service.GetOnGoingItemsCallback callback) {

        return networkService.getonGoingItemsApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends OnGoingItemsMaster>>( ) {
                    @Override
                    public Observable <? extends OnGoingItemsMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <OnGoingItemsMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(OnGoingItemsMaster onGoingItemsMaster) {
                        callback.onSuccess( onGoingItemsMaster );
                    }
                } );
    }


    public interface GetOnGoingItemsCallback {
        void onSuccess(OnGoingItemsMaster onGoingItemsMaster);

        void onError(NetworkError networkError);

    }
    /*----------------------------------------Get Upcoming Item---------------------------------------------------*/

    public Subscription getUpGomingItems(final JsonObject jsonObject, final Service.GetUpComingItemsCallback callback) {

        return networkService.getupcomingItemsApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends UpComingItemMaster>>( ) {
                    @Override
                    public Observable <? extends UpComingItemMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <UpComingItemMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(UpComingItemMaster upComingItemMaster) {
                        callback.onSuccess( upComingItemMaster );
                    }
                } );
    }

    public interface GetUpComingItemsCallback {
        void onSuccess(UpComingItemMaster upComingItemMaster);

        void onError(NetworkError networkError);

    }

    /*---------------------------------------------Driver Interested--------------------------------------------*/

    public Subscription getDriverInterested(final JsonObject jsonObject, final Service.GetDriverInterestedCallback callback) {

        return networkService.driverInterestedApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends DriverInterestedMaster>>( ) {
                    @Override
                    public Observable <? extends DriverInterestedMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <DriverInterestedMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(DriverInterestedMaster driverInterestedMaster) {
                        callback.onSuccess( driverInterestedMaster );
                    }
                } );
    }

    public interface GetDriverInterestedCallback {
        void onSuccess(DriverInterestedMaster driverInterestedMaster);

        void onError(NetworkError networkError);
    }



    /*------------------------------------------Get EstimationRate-------------------------------------------------*/


    public Subscription getEstimationRate(final JsonObject jsonObject, final Service.GetEstimationRateCallBack callback) {

        return networkService.getEstimationRateApi( jsonObject )
                .subscribeOn( Schedulers.io( ) )
                .observeOn( AndroidSchedulers.mainThread( ) )
                .onErrorResumeNext( new Func1 <Throwable, Observable <? extends GetEstimationRateMaster>>( ) {
                    @Override
                    public Observable <? extends GetEstimationRateMaster> call(Throwable throwable) {
                        return Observable.error( throwable );
                    }
                } )
                .subscribe( new Subscriber <GetEstimationRateMaster>( ) {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError( new NetworkError( e ) );

                    }

                    @Override
                    public void onNext(GetEstimationRateMaster getEstimationRateMaster) {
                        callback.onSuccess( getEstimationRateMaster );
                    }
                } );
    }

    public interface GetEstimationRateCallBack {

        void onSuccess(GetEstimationRateMaster getEstimationRateMaster);

        void onError(NetworkError networkError);
    }

}