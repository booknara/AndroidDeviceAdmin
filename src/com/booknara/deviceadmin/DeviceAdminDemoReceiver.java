package com.booknara.deviceadmin;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * DeviceAdminReceiver
 * 
 * @Author : Daehee Han(bookdori81@gmail.com)
 * @Date : Jan 23, 2014 
 * @Version : 1.0.0
 */
public class DeviceAdminDemoReceiver extends DeviceAdminReceiver {
	private static final String CNAME = DeviceAdminDemoReceiver.class.getSimpleName();
	private static final String SETTING_PACKAGE = "com.android.settings";
	
	/* Allow other admins to change the password again without entering it. */
	private static final int RESET_PASSWORD_NOT_REQUIRE_ENTRY = 0;
    private static int RESET_PASSWORD_TIME_OUT = 5 * 1000;
    private static String TEMP_PASSWORD = "1";

	private DevicePolicyManager mDevicepolicymanager;
	
	/** Called when this application is approved to be a device administrator. */
	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		Log.d(CNAME, "onEnabled");
	}

    private void openPackageName(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addCategory("android.intent.category.LAUNCHER");
        context.startActivity(intent);
    }

    private void resetPassword(Context context, SharedPreferences sharedpreferences) {
    	mDevicepolicymanager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    	// Setting reset system password
    	mDevicepolicymanager.resetPassword(TEMP_PASSWORD, RESET_PASSWORD_NOT_REQUIRE_ENTRY);
    	mDevicepolicymanager.lockNow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	mDevicepolicymanager.resetPassword("", 0);
            }
        }, RESET_PASSWORD_TIME_OUT);
    }
    
	@Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        final SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // TODO Sending Email Service
        openPackageName(context, SETTING_PACKAGE);
        resetPassword(context, sharedpreferences);
        
        return "Disabling Device Administrator means your child could change and uninstall ScreenTime.";
    }
	
	/** Called when this application is no longer the device administrator. */
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Log.d(CNAME, "onDisabled");
	}
}