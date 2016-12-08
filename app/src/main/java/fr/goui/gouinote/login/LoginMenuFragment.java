package fr.goui.gouinote.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.goui.gouinote.R;

/**
 * The login menu screen showing all the accessing possibilities.
 */
public class LoginMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_menu, container, false);
        return rootView;
    }

}
