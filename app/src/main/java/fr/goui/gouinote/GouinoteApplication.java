package fr.goui.gouinote;

import android.app.Application;
import android.content.Context;

import fr.goui.gouinote.model.User;
import fr.goui.gouinote.network.NetworkService;

/**
 * Created by Goui-MSI on 25/11/2016.
 */
public class GouinoteApplication extends Application {

    private NetworkService mNetworkService;

    private User mConnectedUser;

    public static GouinoteApplication get(Context context) {
        return (GouinoteApplication) context.getApplicationContext();
    }

    public NetworkService getNetworkService() {
        if (mNetworkService == null) {
            mNetworkService = NetworkService.Factory.create();
        }
        return mNetworkService;
    }

    public User getConnectedUser() {
        return mConnectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        mConnectedUser = connectedUser;
    }
}
