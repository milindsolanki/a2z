package com.a2z.deliver.deps;


import com.a2z.deliver.activities.drivingLicence.DrivingLicenceActivity;
import com.a2z.deliver.activities.editLocation.EditLocationActivity;
import com.a2z.deliver.activities.filter.FilterActivity;
import com.a2z.deliver.activities.idProof.IdProofActivity;
import com.a2z.deliver.activities.insurancePolicy.InsurancePolicyActivity;
import com.a2z.deliver.activities.Profile.ProfileActivity;
import com.a2z.deliver.activities.changePassword.ChangePasswordActivity;
import com.a2z.deliver.activities.chooseLocation.ChooseLocationActivity;
import com.a2z.deliver.activities.home.HomeActivity;
import com.a2z.deliver.activities.ItemSummary.ItemSummaryActivity;
import com.a2z.deliver.activities.inviteFriends.InviteFriendsActivity;
import com.a2z.deliver.activities.login.LoginActivity;
import com.a2z.deliver.activities.mainActivity.MainActivity;
import com.a2z.deliver.activities.otpVerification.OTPActivity;
import com.a2z.deliver.activities.pickupDetails.PickUpDetailsActivity;
import com.a2z.deliver.activities.registrationsCertificate.RegistrationsCertificateActivity;
import com.a2z.deliver.activities.signUp.SignupActivity;
import com.a2z.deliver.fragments.TransectionHistory.TransectionHistoryFragment;
import com.a2z.deliver.fragments.onGoingItem.OngoingFragment;
import com.a2z.deliver.fragments.home.HomeFragment;
import com.a2z.deliver.fragments.onTheWay.OnTheWayFragment;
import com.a2z.deliver.fragments.toPickUp.ToPickUpFragment;
import com.a2z.deliver.fragments.upComingItem.UpcomingFragment;
import com.a2z.deliver.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ennur on 6/28/16.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void injectChangePassword(ChangePasswordActivity homeActivity);
    void injectChooseLocation(ChooseLocationActivity chooseLocationActivity);
    void injectHome(HomeActivity homeActivity);
    void injectLogin (LoginActivity loginActivity);
    void injectSignup (SignupActivity signupActivity);
    void injectHomeFragment (HomeFragment homeFragment);
    void injectOtp(OTPActivity otpActivity);
    void injectMain(MainActivity mainActivity);
    void injectItems(ItemSummaryActivity itemSummaryActivity);
    void injectProfile(ProfileActivity profileActivity);
    void injectDrivingLicence(DrivingLicenceActivity drivingLicenceActivity);
    void injectInviteFriends(InviteFriendsActivity inviteFriendsActivity);
    void injectInsurancePolicy(InsurancePolicyActivity insurancePolicyActivity);
    void injectEditLocation(EditLocationActivity editLocationActivity);
    void injectIdProof(IdProofActivity idProofActivity);
    void injectRegistrationsCertificate(RegistrationsCertificateActivity registrationsCertificateActivity);
    void injectFilter(FilterActivity filterActivity);
    void injectTopickupOrders(ToPickUpFragment toPickUpFragment);
    void injectOnGoingItem(OngoingFragment ongoingFragment);
    void injectOnTheWayOrders(OnTheWayFragment onTheWayFragment);
    void injectUpComing(UpcomingFragment upcomingFragment);
    void injectPickupDetails (PickUpDetailsActivity pickUpDetailsActivity);
    void injectTransectionHistory(TransectionHistoryFragment transectionHistoryFragment);
}
