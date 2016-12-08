package fr.goui.gouinote.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.goui.gouinote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginAccountFragment extends Fragment {

    /**
     * Argument key.
     */
    public static final String IS_CREATION = "is_creation";

    /**
     * Flag to know if this is an account creation or a sign in.
     */
    private boolean mIsCreation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_account, container, false);
        mIsCreation = getArguments().getBoolean(IS_CREATION);
        return rootView;
    }

}
