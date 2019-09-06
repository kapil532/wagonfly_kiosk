package push_notification;

import android.util.Log;


import kartify_base.CommonClass;

/**
 * Created by tapan on 30/09/16.
 */
public class MyFirebaseInstanceIDService
{/*extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        CommonClass.saveGenericData("0","FCM_REGI","FCM_REGI_PREF",getApplicationContext());
        CommonClass.saveGenericData(refreshedToken,"FCM_TOKEN","FCM_TOKEN_PREF",getApplicationContext());
        //sendRegistrationToServer(refreshedToken);

    }
    *//**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */

}
