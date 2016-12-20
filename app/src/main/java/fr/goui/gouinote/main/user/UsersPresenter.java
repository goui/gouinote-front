package fr.goui.gouinote.main.user;

import java.util.List;

import fr.goui.gouinote.GouinoteApplication;
import fr.goui.gouinote.model.User;
import fr.goui.gouinote.model.UserModel;
import fr.goui.gouinote.network.NetworkService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter of the list of all users.
 */
class UsersPresenter implements IUsersPresenter {

    private IUsersView mView;

    private Subscription mSubscription;

    private List<User> mUsers;

    @Override
    public void attachView(IUsersView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    @Override
    public void load() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        GouinoteApplication application = GouinoteApplication.get(mView.getContext());
        NetworkService service = application.getNetworkService();
        mSubscription = service.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {
                        UserModel.getInstance().setUsers(mUsers);
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onNext(List<User> users) {
                        mUsers = users;
                    }
                });
    }
}
