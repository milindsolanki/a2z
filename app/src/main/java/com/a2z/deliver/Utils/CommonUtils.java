package com.a2z.deliver.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a2z.deliver.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    private static Toast mToast;
    static enumDensity density = null;
    static HashMap<String, String> deviceInfoParams;
    private static ProgressDialog progressDialog;

    public static boolean isPasswordValid(String psw) {
        Matcher matcher = null;
        try {
            Pattern pattern;
            final String PSW_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
            pattern = Pattern.compile(PSW_PATTERN);
            matcher = pattern.matcher(psw);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return matcher.matches();
    }

    public static void setImageUsingGlide(Activity activity, String url, ImageView imageView, final ProgressBar progressBar){
        if (progressBar != null)
            progressBar.setVisibility( View.VISIBLE );
        Glide.with( activity )
                .load( url )
                .apply( new RequestOptions()
                        .placeholder( R.drawable.ic_placeholder_image )
                        .error( R.drawable.ic_error_image ) )
                .listener( new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (progressBar != null)
                            progressBar.setVisibility( View.GONE );
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null)
                            progressBar.setVisibility( View.GONE );
                        return false;
                    }
                } )
                .into( imageView );
    }
    /**
     * Exit with kill process
     */
    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * Exit application
     *
     * @param activity
     */
    public static void exit(Activity activity) {
        exit((Context) activity);
        System.exit(0);

    }

    /**
     * Exit with finish context
     *
     * @param context Context of activity of an activity
     */
    public static void exit(Context context) {
        Activity activity = (Activity) context;
        activity.finish();
    }

    public static void makeToast(Context context, String toast) {
        if (context == null)
            return;

        if (mToast == null) {
            mToast = Toast.makeText(context, toast, Toast.LENGTH_SHORT);
        }

        mToast.setText(toast);
        mToast.setGravity(android.view.Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void makeLongToast(Context context, String toast) {
        if (context == null)
            return;

        if (mToast == null) {
            mToast = Toast.makeText(context, toast, Toast.LENGTH_LONG);
        }
        mToast.setGravity(android.view.Gravity.CENTER, 0, 0);
        mToast.setText(toast);
        mToast.show();
    }

    /**
     * Get Device model
     *
     * @return
     */
    public static String getMobileModel() {
        // Device model
        return Build.MODEL;
    }

    /**
     * Get Device Manufacturer
     *
     * @return
     */
    public static String getMobileManufacturer() {
        // Device model
        return Build.MANUFACTURER;
    }

    /**
     * Get Device product
     *
     * @return
     */
    public static String getMobileProduct() {
        // Device model
        return Build.PRODUCT;
    }

    /**
     * Get Device fingerprint
     *
     * @return
     */
    public static String getMobileFingerprint() {
        // Device model
        return Build.FINGERPRINT;
    }

    /**
     * Get Device ID
     *
     * @return
     */
    public static String getMobileId() {
        // Device model
        return Build.ID;
    }

    /**
     * Get Device Android version
     *
     * @return
     */
    public static String getAndroidVersion() {
        // Android version
        return Build.VERSION.RELEASE;
    }

    /**
     * Get Device Android version integer
     *
     * @return
     */
    public static int getAndroidVersionInt() {
        // Android version
        return Build.VERSION.SDK_INT;
    }

    /**
     * Check if network is available
     *
     * @param context Application context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static Boolean isInstalled(Context context, String packageName) {

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent == null) {
            return false;
        } else {
            return true;
        }
    }

    public static AlertDialog showNoInternetConnectionDialog(
            final Context context, final boolean isConnectionCritical) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage( R.string.utils__no_connection);
        builder.setTitle(R.string.utils__no_connection_title);
        builder.setPositiveButton(R.string.utils__settings,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(
                                Settings.ACTION_WIFI_SETTINGS));// .ACTION_NETWORK_OPERATOR_SETTINGS));//.ACTION_WIRELESS_SETTINGS));
                    }
                });

        if (isConnectionCritical)
            builder.setNegativeButton(R.string.utils__exit,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            CommonUtils.exit(context);
                        }
                    });
        else
            builder.setNegativeButton(R.string.utils__abort, null);

        if (isConnectionCritical)
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    CommonUtils.exit(context);
                }
            });

        return builder.show();
    }

    /**
     * Showing alert dialog when network is not available in offline mode
     *
     * @param context              application context
     * @param isConnectionCritical It means if network is necessary dialog will show and cancelable will be false
     * @return
     */
    public static AlertDialog showNoInternetConnectionDialogOfflineMsg(
            final Context context, final boolean isConnectionCritical) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage(R.string.utils__no_connection);
        builder.setTitle(R.string.utils__no_connection_title);
        builder.setPositiveButton(R.string.utils__settings,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(
                                Settings.ACTION_WIFI_SETTINGS));// .ACTION_NETWORK_OPERATOR_SETTINGS));//.ACTION_WIRELESS_SETTINGS));
                    }
                });

        if (isConnectionCritical)
            builder.setNegativeButton(R.string.utils__exit,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            CommonUtils.exit(context);
                        }
                    });
        else
            builder.setNegativeButton(R.string.utils__offline, null);

        if (isConnectionCritical)
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    CommonUtils.exit(context);
                }
            });

        return builder.show();
    }

    /**
     * Showing alert dialog when server is not available
     *
     * @param context    Application context
     * @param isCritical It means if server connection is necessary dialog will show and cancelable will be false
     * @return
     */
    public static AlertDialog showServerNotAvailableDialog(
            final Context context, final boolean isCritical) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage(R.string.utils__no_server);
        builder.setTitle(R.string.utils__no_server_title);

        if (isCritical)
            builder.setNegativeButton(R.string.utils__exit,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            CommonUtils.exit(context);
                        }
                    });
        else
            builder.setNegativeButton(R.string.utils__abort, null);

        if (isCritical)
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    CommonUtils.exit(context);
                }
            });

        return builder.show();
    }

    /**
     * Changing default language
     *
     * @param context       Application context
     * @param language_code Lang code to FA or EN - BR and etc.
     * @param title         Will set to activity
     */
    public static void changeLanguage(Context context, String language_code,
                                      String title) {
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code);
        res.updateConfiguration(conf, dm);

        Activity activity = (Activity) context;
        activity.setTitle(title);
    }

    /**
     * Changing default language
     *
     * @param context       Application context
     * @param language_code Lang code to FA or EN - BR and etc.
     */
    public static void changeLanguage(Context context, String language_code) {
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code);
        res.updateConfiguration(conf, dm);
    }

    /**
     * Show keyboard of a View
     *
     * @param context Application context
     * @param view    Edit text or another view that you want hide the keyboard
     */
    public static void showKeyboard(Context context, @NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * Hide keyboard of a View
     *
     * @param context Application context
     * @param view    Edit text or another view that you want hide the keyboard
     */
    public static void hideKeyboard(Context context, @NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Hide keyboard
     *
     * @param activity Activity
     */
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (null != view)
            hideKeyboard(activity, view);
    }

    /**
     * Hide keyboard
     *
     * @param fragment Fragment
     */
    public static void hideKeyboard(Fragment fragment) {
        View view = fragment.getActivity().getCurrentFocus();
        hideKeyboard(fragment.getContext(), view);
    }

    /**
     * Get Display Width
     *
     * @param context Application context
     * @return
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static int getDisplayWidth(Context context) {
        Activity activity = (Activity) context;
        if (Integer.valueOf(Build.VERSION.SDK_INT) < 13) {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            return display.getWidth();
        } else {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
    }

    /**
     * Get display height
     *
     * @param context Application context
     * @return
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static int getDisplayHeight(Context context) {
        Activity activity = (Activity) context;
        if (Integer.valueOf(Build.VERSION.SDK_INT) < 13) {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            return display.getHeight();
        } else {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
    }

    /**
     * Play a sound
     *
     * @param context Application context
     * @param rawID   Raw integer id in resource
     */
    public static void playSound(Context context, int rawID) {
        MediaPlayer mp = MediaPlayer.create(context, rawID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mp.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
        } else {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();
    }

    /**
     * Get Application name
     *
     * @param context Application context
     * @return

     */
    public static String getApplicationName(Context context)
            throws PackageManager.NameNotFoundException {
        // Application version
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                context.getPackageName(), 0);
        return pInfo.packageName;

    }

    /**
     * Get Application version name
     *
     * @param context Application context
     * @return

     */
    public static String getApplicationVersionName(Context context) throws PackageManager.NameNotFoundException {
        // Application version
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                context.getPackageName(), 0);
        return pInfo.versionName;
    }

    /**
     * Get application version code
     *
     * @param context Application context
     * @return
     */
    public static int getApplicationVersionCode(Context context) throws PackageManager.NameNotFoundException {
        // Application version
        PackageInfo pInfo = null;
        pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        return pInfo.versionCode;
    }

    /**
     * Get android ID
     *
     * @param context Application context
     * @return
     */
    public static String getAndroidID(Context context) {

        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    /**
     * Play notification sound
     *
     * @param context Application context
     */
    public static void playNotificationSound(Context context) {
        Uri notification = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

    /**
     * Checking a service is running or not
     *
     * @param context   Application context
     * @param myService Set your service class
     * @return
     */
    public static boolean isServiceRunning(Context context, Class<?> myService) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (myService.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checking a email that user entered is in a correct format or not
     *
     * @param email Email parameter
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static HashMap<String, String> getDeviceInfoParamsForUrl(Context context) throws UnsupportedEncodingException, PackageManager.NameNotFoundException, PackageManager.NameNotFoundException {
        if (deviceInfoParams == null) {
            deviceInfoParams = new HashMap<>();

            deviceInfoParams.put("androidVersionName", CommonUtils.getAndroidVersion());
            deviceInfoParams.put("androidVersionId", CommonUtils.getAndroidVersionInt() + "");
            deviceInfoParams.put("androidId", CommonUtils.getAndroidID(context));
            deviceInfoParams.put("mobileModel", CommonUtils.getMobileModel());
            deviceInfoParams.put("mobileManufacturer", CommonUtils.getMobileManufacturer());
            deviceInfoParams.put("mobileId", CommonUtils.getMobileId());
            deviceInfoParams.put("mobileProduct", CommonUtils.getMobileProduct());
            deviceInfoParams.put("applicationName", CommonUtils.getApplicationName(context));
            deviceInfoParams.put("applicationVersionName", CommonUtils.getApplicationVersionName(context));
            deviceInfoParams.put("applicationVersionCode", CommonUtils.getApplicationVersionCode(context) + "");
            deviceInfoParams.put("screenWidth", CommonUtils.getDisplayWidth(context) + "");
            deviceInfoParams.put("screenHeight", CommonUtils.getDisplayWidth(context) + "");
            deviceInfoParams.put("screenDensity", CommonUtils.getDisplayDensity(context) + "");
            deviceInfoParams.put("screenDensityName", CommonUtils.getDisplaySize(context).toString());
        }
        return new HashMap<>(deviceInfoParams);
    }

    /**
     * Get Display density
     *
     * @param context Application context
     * @return
     */
    public static int getDisplayDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.densityDpi;
    }

    /**
     * Get display size
     *
     * @param context Application context
     * @return
     */
    public static enumDensity getDisplaySize(Context context) {
        if (density == null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            density = enumDensity.fromFloat(metrics.density);
        }
        return density;
    }

    /**
     * Making a string of currency and convert it to a format with separators
     *
     * @param value
     * @return
     */
    public static String moneySeparator(String value) {
        return moneySeparator(value, ",");
    }

    /**
     * Making a string of currency and convert it to a format with separators with specific separator
     *
     * @param value     Money value
     * @param separator Your specific separator
     * @return
     */
    public static String moneySeparator(String value, String separator) {
        String result = "";
        int len = value.length();
        int loop = (len / 3);

        int start = 0;
        int end = len - (loop * 3);

        result = value.substring(start, end);

        for (int i = 0; i < loop; i++) {
            start = end;
            end += 3;
            if (result.equals(""))
                result = value.substring(start, end);
            else
                result = result + separator + value.substring(start, end);
        }

        return result;
    }

    /**
     * Making empty all application data in cache
     *
     * @param context Application context
     * @return
     */
    public static Boolean emptyAllApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());

        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("databases")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }

        return true;
    }

    /**
     * Delete directory and subdirectory
     *
     * @param dir File dir address
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir != null & dir.isDirectory()) {
            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                    return false;
            }
        }
        return dir.delete();
    }

    /**
     * Checking install package permission
     *
     * @param context Application context
     * @return
     */
    public static boolean checkInstallPackagesPermission(Context context) {
        String permission = "android.permission.INSTALL_PACKAGES";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checking write sms permission
     *
     * @param context Application context
     * @return
     */
    public static boolean checkWriteSMSPermission(Context context) {
        String permission = "android.permission.WRITE_SMS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checking read sms permission
     *
     * @param context Application context
     * @return
     */
    public static boolean checkReadSMSPermission(Context context) {
        String permission = "android.permission.READ_SMS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Checking if a string number or not
     *
     * @param value string value
     * @return is Number
     */
    public static boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Getting all installed packages
     *
     * @param context Application context
     * @return all installed applications list
     */
    public static List<PackageInfo> getAllInstalledApplication(Context context) {
        final PackageManager pm = context.getPackageManager();
        // get a list of installed apps.
        List<PackageInfo> packages = pm
                .getInstalledPackages(PackageManager.GET_META_DATA);

        return packages;
    }

    public static String getFrontPackageName(Context ctx) {

        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();

        return processes.get(0).processName;
    }

    public static boolean isMyServiceRunning(Context ctx, String serviceClassName) {
        final ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equalsIgnoreCase(serviceClassName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMyServiceRunning(Context ctx, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    /**
     * check if current device is Tablet
     *
     * @param context Application context
     * @return isTablet
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * check if current device state is Portrait
     *
     * @param context Application context
     * @return isPortrait
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * get device current timestamp
     *
     * @return isPortrait
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Enumeration of standard density of displays
     */
    public static enum enumDensity {
        xxxhdpi(4.0f), xxhdpi(3.0f), xhdpi(2.0f), hdpi(1.5f), tvdpi(1.33f), mdpi(
                1.0f), ldpi(0.75f);

        private Float value;

        enumDensity(Float v) {
            setValue(v);
        }

        public static enumDensity fromFloat(Float v) {
            if (v != null) {
                for (enumDensity s : enumDensity.values()) {
                    if (v.equals(s.getValue())) {
                        return s;
                    }
                }
                return enumDensity.xxxhdpi;
            }
            return null;
        }

        public Float getValue() {
            return value;
        }

        public void setValue(Float value) {
            this.value = value;
        }
    }

    /**
     * Apply typeface to a plane text and return spannableString
     *
     * @param text     Text that you want to apply typeface
     * @param typeface Typeface that you want to apply to your text
     * @return spannableString
     */
    public static SpannableString applyTypefaceToString(String text, final Typeface typeface) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new MetricAffectingSpan() {
                                    @Override
                                    public void updateMeasureState(TextPaint p) {
                                        p.setTypeface(typeface);

                                        // Note: This flag is required for proper typeface rendering
                                        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
                                    }

                                    @Override
                                    public void updateDrawState(TextPaint tp) {
                                        tp.setTypeface(typeface);

                                        // Note: This flag is required for proper typeface rendering
                                        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
                                    }
                                }, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    public static String getFormattedDate(String date, String currentFormat, String newFormat) {
        String formatedDate = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat(currentFormat);
            SimpleDateFormat dateFormat = new SimpleDateFormat(newFormat);
            String fDate = dateFormat.format(df.parse(date));
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(new Date( date ));

            String[] days = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
            String day = days[calendar.get( Calendar.DAY_OF_WEEK )- 1];
            formatedDate = day + ", " + fDate;
        } catch (ParseException e) {
            e.printStackTrace();
        } return formatedDate;
    }


    public static String getUtcTime(String dateAndTime) {
        Date d = parseDate(dateAndTime);

        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        // Convert Local Time to UTC
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(d);
    }

    public static Date parseDate(String date) {

        if (date == null) {
            return null;
        }

        StringBuffer sbDate = new StringBuffer();
        sbDate.append(date);
        String newDate = null;
        Date dateDT = null;

        try {
            newDate = sbDate.substring(0, 19).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rDate = newDate.replace("T", " ");
        String nDate = rDate.replaceAll("-", "/");

        try {
            dateDT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).parse(nDate);
            // Log.v( TAG, "#parseDate dateDT: " + dateDT );
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDT;
    }

    public static void setEditTextError(EditText editText, String errorMsg){
        editText.setError( errorMsg );
        editText.requestFocus();
    }
    public static void setTextViewError(TextView textView, String errorMsg){
        textView.setError( errorMsg );
        textView.requestFocus();
    }

    public static void setProgressDialog(Activity activity, String title, String message){
        progressDialog = new ProgressDialog( activity );
        progressDialog.setTitle( title );
        progressDialog.setMessage( message );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setCancelable( false );
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView view, String imageUrl) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_placeholder_image);
        requestOptions.error(R.drawable.ic_error_image);
        Glide.with(view.getContext())

                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .into(view);
    }


}
