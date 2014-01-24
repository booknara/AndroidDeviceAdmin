package com.booknara.deviceadmin;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private void lockScreen(Context context, SharedPreferences sharedpreferences) {
        DevicePolicyManager devicepolicymanager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // TODO Using sharedpreferences 
    	// Setting reset system password
//        devicepolicymanager.resetPassword("password", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        devicepolicymanager.lockNow();
    }
    
	@Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
		final Context ctx = context;
        final SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // TODO Sending Email Service
//        openPackageName(ctx, SETTING_PACKAGE);
        openPackageName(context, context.getApplicationContext().getPackageName());
        lockScreen(ctx, sharedpreferences);
        
		Log.d(CNAME, "onDisableRequested()");
        return "";
    }
	
	/** Called when this application is no longer the device administrator. */
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Log.d(CNAME, "onDisabled");
	}
}
