package fr.goui.gouinote.main.note;

import fr.goui.gouinote.IPresenter;

/**
 * Presenter interface for the list of all notes.
 */
public interface INotesPresenter extends IPresenter<INotesView> {

    void load();
}
