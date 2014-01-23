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

    private void resetPassword(Context context, SharedPreferences sharedpreferences) {
        DevicePolicyManager devicepolicymanager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // TODO Using sharedpreferences 
        devicepolicymanager.resetPassword("password", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        devicepolicymanager.lockNow();
    }
    
	@Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
		final Context ctx = context;
        final SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // TODO Sending Email Service
        openPackageName(ctx, SETTING_PACKAGE);
        resetPassword(ctx, sharedpreferences);
        
		Log.d(CNAME, "onDisableRequested()");
        return "onDisableRequested";
    }
	
	/** Called when this application is no longer the device administrator. */
	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Log.d(CNAME, "onDisabled");
	}

	// Optional methods 
	@Override
	public void onPasswordChanged(Context context, Intent intent) {
		super.onPasswordChanged(context, intent);
		Log.d(CNAME, "onPasswordChanged");
	}

	@Override
	public void onPasswordFailed(Context context, Intent intent) {
		super.onPasswordFailed(context, intent);
		Log.d(CNAME, "onPasswordFailed");
	}

	@Override
	public void onPasswordSucceeded(Context context, Intent intent) {
		super.onPasswordSucceeded(context, intent);
		Log.d(CNAME, "onPasswordSucceeded");
	}

}
