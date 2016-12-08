package fr.goui.gouinote.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import fr.goui.gouinote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginAccountFragment extends Fragment implements ILoginAccountView {

    @BindView(R.id.account_progress_bar)
    ProgressBar mProgressBar;

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

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
