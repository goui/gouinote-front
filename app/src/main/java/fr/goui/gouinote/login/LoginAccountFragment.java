package fr.goui.gouinote.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouinote.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginAccountFragment extends Fragment implements ILoginAccountView {

    @BindView(R.id.account_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.account_nickname_edit_text)
    EditText mNicknameEditText;

    @BindView(R.id.account_password_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.account_ok_button)
    Button mOKButton;

    /**
     * Argument key.
     */
    public static final String IS_CREATION = "is_creation";

    /**
     * Flag to know if this is an account creation or a sign in.
     */
    private boolean mIsCreation;

    private ILoginAccountPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_account, container, false);
        mIsCreation = getArguments().getBoolean(IS_CREATION);
        ButterKnife.bind(this, rootView);

        mPresenter = new LoginAccountPresenter();
        mPresenter.attachView(this);

        return rootView;
    }

    @OnClick(R.id.account_ok_button)
    public void onClick() {
        String nickname = mNicknameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        if (!TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(password)) {
            mPresenter.load(nickname, password, mIsCreation);
        }
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
    public void hideButton() {
        mOKButton.setVisibility(View.GONE);
    }

    @Override
    public void showButton() {
        mOKButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void lockControls() {
        mNicknameEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);
    }

    @Override
    public void unlockControls() {
        mNicknameEditText.setEnabled(true);
        mPasswordEditText.setEnabled(true);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
