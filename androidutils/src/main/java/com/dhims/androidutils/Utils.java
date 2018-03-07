package com.dhims.androidutils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility functions.
 *
 * @author Dhimant Desai (dhimant1990@gmail.com)
 */

public class Utils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String MD5(String str) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isTablet(Activity mActivity) {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels / displayMetrics.densityDpi;
        int height = displayMetrics.heightPixels / displayMetrics.densityDpi;

        double screenDiagonal = Math.sqrt(width * width + height * height);
        return (screenDiagonal >= 9.0);
    }

    public static int getWidth(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }

    public static int getHeight(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return height;
    }

    public static int getDpAsPixels(int sizeInDp) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        float scale = displaymetrics.density;
        return (int) (sizeInDp * scale + 0.5f);
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = Color.WHITE;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } else {
            return null;
        }

//        bitmap.recycle(); test now
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }

        return output;
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 96; // Replaced the 1 by a 96
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 96; // Replaced the 1 by a 96

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static boolean checkPermission(Context mContext, String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == 1;
    }

    public static void checkpermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissionsList = new ArrayList<>();

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.CAMERA);
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.READ_CONTACTS);
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.RECEIVE_SMS);
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (permissionsList.size() > 0) {
                ActivityCompat.requestPermissions((Activity) context, permissionsList.toArray(new String[permissionsList.size()]), 0);
            }

        }
    }

    public static Bitmap compressImage(Bitmap bitmap, int targetWidth) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int targetHeight = (int) (originalHeight * targetWidth / (double) originalWidth); // casts to avoid truncating
        if (originalHeight < originalWidth) {
            bitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
        } else {
            bitmap = Bitmap.createScaledBitmap(bitmap, targetHeight, targetWidth, true);
        }
        return bitmap;
    }

    public static Date localToGMT() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        Date gmt = new Date(sdf.format(date));
        return gmt;
    }

    public static Date gmttoLocalDate(Date date) {
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        Date local = new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
        return local;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static String encodeStr(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }

    public static String decodeStr(String str) throws UnsupportedEncodingException {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }

    }

    public static long getCurrentToUtcTime(long timeStemp) {
        Calendar mCalendar = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
        mCalendar.setTimeInMillis(timeStemp);
        mCalendar.setTimeZone(TimeZone.getDefault());
        long millies = mCalendar.getTimeInMillis();
        return millies;
    }

    public static long getUtcTime() {
        Calendar mCalendar = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
        long millies = mCalendar.getTimeInMillis();
        return millies;
    }

    /**
     * Open Keyboard
     */
    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

}