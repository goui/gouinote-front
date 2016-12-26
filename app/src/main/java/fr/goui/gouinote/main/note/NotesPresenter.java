package fr.goui.gouinote.main.note;

import java.util.List;

import fr.goui.gouinote.GouinoteApplication;
import fr.goui.gouinote.exception.ExceptionHandler;
import fr.goui.gouinote.model.Note;
import fr.goui.gouinote.model.NoteModel;
import fr.goui.gouinote.network.NetworkService;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter of the list of all notes.
 */
class NotesPresenter implements INotesPresenter {

    private INotesView mView;

    private Subscription mSubscription;

    private List<Note> mNotes;

    @Override
    public void attachView(INotesView view) {
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
        mSubscription = service.getAllNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Note>>() {
                    @Override
                    public void onCompleted() {
                        NoteModel.getInstance().setNotes(mNotes);
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
                    public void onNext(List<Note> notes) {
                        mNotes = notes;
                    }
                });
    }
}
