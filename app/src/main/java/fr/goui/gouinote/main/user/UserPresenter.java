package fr.goui.gouinote.main.user;

import fr.goui.gouinote.GouinoteApplication;
import fr.goui.gouinote.exception.ExceptionHandler;
import fr.goui.gouinote.model.User;
import fr.goui.gouinote.network.NetworkService;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for a specific user.
 */
class UserPresenter implements IUserPresenter {

    private IUserView mView;

    private Subscription mSubscription;

    private User mUser;

    @Override
    public void attachView(IUserView view) {
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
    public void load(String nickname) {
        mView.showProgressBar();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        GouinoteApplication application = GouinoteApplication.get(mView.getContext());
        NetworkService service = application.getNetworkService();
        mSubscription = service.getUser(nickname)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        mView.showResult(mUser);
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorMessage = e.getMessage();
                        if (e instanceof HttpException) {
                            errorMessage = ExceptionHandler.getMessage(e);
                        }
                        mView.showError(errorMessage);
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onNext(User user) {
                        mUser = user;
                    }
                });
    }
}
