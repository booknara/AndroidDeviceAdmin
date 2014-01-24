package com.booknara.deviceadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	private static final String CNAME = MainActivity.class.getSimpleName();
	
	private ToggleButton toggleButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toggleButton = (ToggleButton) super.findViewById(R.id.toggle_device_admin);

		init();
	}
	
	public void init () {
		DeviceAdminUtil.initDPM(this);
		DeviceAdminUtil.initComponent(this, DeviceAdminDemoReceiver.class);
		
    	boolean admin = DeviceAdminUtil.isDeviceAdmin();
    	Log.i(CNAME, "admin : " + admin);
    	toggleButton.setChecked(admin);
		toggleButton.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		if (isChecked) {
			if (DeviceAdminUtil.isDeviceAdmin())
				return;

			// Activate device administration
			DeviceAdminUtil.registerDeviceAdmin(this, DeviceAdminUtil.DEVICE_ADMIN_REQUEST);
		} else {
			DeviceAdminUtil.unregisterDeviceAdmin();
			Log.i(CNAME, "Device Admin Disabled");
			Toast.makeText(this, "Device Admin Disabled", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case DeviceAdminUtil.DEVICE_ADMIN_REQUEST:
			if (resultCode == Activity.RESULT_OK) {
				Log.i(CNAME, "Administration enabled!");
				Toast.makeText(this, "Administration enabled!", Toast.LENGTH_SHORT).show();
			} else {
				Log.i(CNAME, "Administration enable FAILED!");
				toggleButton.setChecked(false);
			}
			
			return;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}