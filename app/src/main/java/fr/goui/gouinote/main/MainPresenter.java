package fr.goui.gouinote.main;

import fr.goui.gouinote.GouinoteApplication;
import fr.goui.gouinote.R;
import fr.goui.gouinote.model.Note;
import fr.goui.gouinote.model.NoteModel;
import fr.goui.gouinote.network.NetworkService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter of the main screen.
 */
class MainPresenter implements IMainPresenter {

    private IMainView mView;

    private Subscription mSubscription;

    private boolean mIsAdded;

    @Override
    public void attachView(IMainView view) {
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
    public void load(String noteContent) {
        mView.showProgressBar();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        GouinoteApplication application = GouinoteApplication.get(mView.getContext());
        NetworkService service = application.getNetworkService();
        String nickname = application.getConnectedUser().getNickname();
        final Note note = new Note();
        note.setNickname(nickname);
        note.setContent(noteContent);
        note.setDate(System.currentTimeMillis());
        mSubscription = service.addNote(nickname, note)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        if (mIsAdded) {
                            NoteModel.getInstance().addNote(note);
                        } else {
                            mView.showError(mView.getContext().getString(R.string.Error_note_has_not_been_added));
                        }
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onNext(Boolean isAdded) {
                        mIsAdded = isAdded;
                    }
                });
    }
}
