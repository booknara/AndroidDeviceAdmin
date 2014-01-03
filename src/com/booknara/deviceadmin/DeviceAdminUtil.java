package com.booknara.deviceadmin;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class DeviceAdminUtil {
	public static final int DEVICE_ADMIN_REQUEST = 9;
	
	private static DevicePolicyManager mDevicePolicyManager;
	private static ComponentName mComponentName;

	// Suppress default constructor for noninstantiability
    private DeviceAdminUtil() { }
    
    public static DevicePolicyManager getDevicePolicyManager() {
        return mDevicePolicyManager;
    }
    
    public static void setDevicePolicyManager(
            final DevicePolicyManager devicePolicyManager) {
        mDevicePolicyManager = devicePolicyManager;
    }
    
    public static ComponentName getComponentName() {
        return mComponentName;
    }

    public static void setComponentName(final ComponentName componentName) {
        mComponentName = componentName;
    }
    
    public static void initDPM(final Activity activity) {
        if (mDevicePolicyManager == null) {
            setDevicePolicyManager((DevicePolicyManager) activity
                    .getSystemService(Context.DEVICE_POLICY_SERVICE));
        }
    }

    public static <T> void initComponent(final Activity activity, final Class<T> reciever) {
        if (mComponentName == null) {
            setComponentName(new ComponentName(activity, reciever));
        }
    }

    public static boolean isDeviceAdmin() {
        return mDevicePolicyManager.isAdminActive(mComponentName);
    }
    
    public static void registerDeviceAdmin(final Activity activity, final int requestCode) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, getComponentName());
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Needs to run as a Device Administrator to prevent uninstalling the app");
        activity.startActivityForResult(intent, requestCode);
    }

    public static void unregisterDeviceAdmin() {
        if (mDevicePolicyManager.isAdminActive(mComponentName))
            mDevicePolicyManager.removeActiveAdmin(mComponentName);
    }
}
