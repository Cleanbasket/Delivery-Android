package com.spacosa.android.catchloc.sdk;

import java.util.ArrayList;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.spacosa.android.catchloc.libs.CatchLocBeaconInfo;
import com.spacosa.android.catchloc.libs.CatchLocLocation;
import com.spacosa.android.catchloc.libs.CatchLocMessageInfo;
import com.spacosa.android.catchloc.libs.CatchLocResult;
import com.spacosa.android.catchloc.libs.CatchlocLibs;

import android.content.Context;
import android.os.Bundle;

public class CatchlocSDK {

    static public CatchLocResult startCatchLoc(Context context, String partner_key, String member_key) {
    	return CatchlocLibs.startCatchLoc(context, partner_key, member_key, true);
    }

    static public CatchLocResult startCatchLoc(Context context, String partner_key, String member_key, boolean location_service) {
    	return CatchlocLibs.startCatchLoc(context, partner_key, member_key, location_service);
    }
    
    static public CatchLocResult startLocationService(Context context, int interval) {
    	return CatchlocLibs.startLocationService(context, interval);
	}

    static public void stopLocationService(Context context) {
    	CatchlocLibs.stopLocationService(context);
	}

    static public CatchLocBeaconInfo getCatchLocBeaconInfo(Bundle bundle) {
    	return CatchlocLibs.getCatchLocBeaconInfo(bundle);
	}

    static public CatchLocMessageInfo getCatchLocMessageInfo(Bundle bundle) {
    	return CatchlocLibs.getCatchLocMessageInfo(bundle);
	}

    static public CatchLocLocation getCatchLocLocation(Bundle bundle) {
    	return CatchlocLibs.getCatchLocLocation(bundle);
	}
    
    static public void addBleGeofence(Context context, int ble_sn, boolean check_in, boolean check_out, long interval, int sensitivity) {
    	CatchlocLibs.addBleGeofence(context, ble_sn, check_in, check_out, interval, sensitivity);
    }

    static public void removeBleGeofence(Context context, int ble_sn) {
    	CatchlocLibs.removeBleGeofence(context, ble_sn);
    }

    static public void removeBleGeofence(Context context) {
    	CatchlocLibs.removeBleGeofence(context);
    }
    
    static public void resetBleGeofence(Context context) {
    	CatchlocLibs.resetBleGeofence(context);
    }
    
    static public void startBeaconService(Context context, int ScanMode) {
    	CatchlocLibs.startBeaconService(context, ScanMode);
    }

    static public void stopBeaconService(Context context) {
    	CatchlocLibs.stopBeaconService(context);
    }

	public static void setLocationInterval(Context context, int interval) {
		CatchlocLibs.setLocationInterval(context, interval);
	}
	
	public static int getLocationInterval(Context context) {
		return CatchlocLibs.getLocationInterval(context);
	}

	public static String getMemberPincode(Context context) {
		return CatchlocLibs.getMemberPincode(context);
	}
	
	public static boolean isGcmPushService(Context context) {
		return CatchlocLibs.isGcmPushService(context);
	}
	
	public static boolean isCatchLocLogin(Context context) {
		return CatchlocLibs.isCatchLocLogin(context);
	}
	
	public static void setGcmPushService(Context context, boolean enable) {
		CatchlocLibs.setGcmPushService(context, enable);
	}

    public static void openStatusManager(Context context) {
    	CatchlocLibs.openStatusManager(context);
    }
    
    public static String getMemberKeyDefault(Context context) {
    	return CatchlocLibs.getMemberKeyDefault(context);
    }
    
    public static String getMemberKey(Context context) {
    	return CatchlocLibs.getMemberKey(context);
    }

    public static String getPartnerKey(Context context) {
    	return CatchlocLibs.getPartnerKey(context);
    }

    public static String getMemberName(Context context) {
    	return CatchlocLibs.getMemberName(context);
    }

    public static String getPartnerID(Context context) {
    	return CatchlocLibs.getPartnerID(context);
    }
    
    public static String getPartnerName(Context context) {
    	return CatchlocLibs.getPartnerName(context);
    }
    
    public static ArrayList<CatchLocBeaconInfo> getMyLinkList(Context context) {
    	return CatchlocLibs.getMyLinkList(context);
    }
    
    public static void startLocationUpdate(Context context) {
    	CatchlocLibs.startLocationUpdate(context);
    }

    public static void stopLocationUpdate(Context context) {
    	CatchlocLibs.stopLocationUpdate(context);
    }
    
    public static CatchLocLocation getLastLocation(Context context) {
    	return CatchlocLibs.getLastLocation(context);
    }
    
    public static CatchLocResult sendMessageGroup(Context context, CatchLocMessageInfo message) {
    	return CatchlocLibs.sendMessageGroup(context, message);
    }
    
    public static String getGeoCodeAddress(Context context, double latitude, double longitude) {
    	return CatchlocLibs.getGeoCodeAddress(context, latitude, longitude);
    }

    public static CatchLocResult setWithdrawClientMember(Context context) {
    	return CatchlocLibs.setWithdrawClientMember(context);
    }

    public static BitmapDescriptor getMemberMarker(Context context, String img_path) {
    	return CatchlocLibs.getMemberMarker(context, img_path);
    }
    
    public static long getLocationEnableTime(Context context) {
    	return CatchlocLibs.getLocationEnableTime(context);
    }

    public static void setLocationEnableTime(Context context, long time) {
    	CatchlocLibs.setLocationEnableTime(context, time);
    }
    
}
