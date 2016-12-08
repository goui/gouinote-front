package fr.goui.gouinote.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouinote.R;

/**
 * The login menu screen showing all the accessing possibilities.
 */
public class LoginMenuFragment extends Fragment {

    /**
     * The menu buttons click listener.
     */
    private LoginMenuListener mMenuListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_menu, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mMenuListener = (LoginMenuListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement LoginMenuListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMenuListener = null;
    }

    @OnClick(R.id.login_sign_in_button)
    public void onAccessAccountClick() {
        mMenuListener.onSignInClick();
    }

    @OnClick(R.id.login_create_account_button)
    public void onCreateAccountClick() {
        mMenuListener.onCreateAccountClick();
    }

    @OnClick(R.id.login_guest_button)
    public void onGuestClick() {
        mMenuListener.onBeAGuestClick();
    }
}
