package fr.goui.gouinote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.R;
import fr.goui.gouinote.model.Note;
import fr.goui.gouinote.model.NoteModel;

/**
 * Adapter for a list of notes. It is synchronized with cache model but this can be overridden.
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder> implements Observer {

    private LayoutInflater mLayoutInflater;

    private List<Note> mNotes;

    private boolean mIsListSpecific;

    private int mExpandedPosition = -1;

    private RecyclerView mRecyclerView;

    public NoteListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mNotes = NoteModel.getInstance().getNotes();
        NoteModel.getInstance().addObserver(this);
    }

    /**
     * Overriding note model.
     * For default behavior don't use this method.
     *
     * @param notes the list of notes to display
     */
    public void setListOfNotes(List<Note> notes) {
        mNotes = notes;
        mIsListSpecific = true;
        NoteModel.getInstance().deleteObserver(this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public NoteListAdapter.NoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteListViewHolder(mLayoutInflater.inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(NoteListViewHolder holder, final int position) {
        holder.position = position;
        final boolean isExpanded = position == mExpandedPosition;
        holder.nbOfLovesTextView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.loveImageButton.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.replyImageButton.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                TransitionManager.beginDelayedTransition(mRecyclerView);
                notifyDataSetChanged();
            }
        });
        final Note note = mNotes.get(position);
        if (note != null) {
            holder.contentTextView.setText(note.getContent());
            holder.dateTextView.setText("" + note.getDate());
            if (mIsListSpecific) {
                holder.authorTextView.setVisibility(View.GONE);
            } else {
                holder.authorTextView.setText(note.getNickname());
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof NoteModel) {
            mNotes = NoteModel.getInstance().getNotes();
            notifyDataSetChanged();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        NoteModel.getInstance().deleteObserver(this);
    }

    static class NoteListViewHolder extends RecyclerView.ViewHolder {
        int position;

        @BindView(R.id.note_author_text_view)
        TextView authorTextView;

        @BindView(R.id.note_date_text_view)
        TextView dateTextView;

        @BindView(R.id.note_content_text_view)
        TextView contentTextView;

        @BindView(R.id.note_number_of_loves_text_view)
        TextView nbOfLovesTextView;

        @BindView(R.id.note_love_image_button)
        ImageButton loveImageButton;

        @BindView(R.id.note_reply_image_button)
        ImageButton replyImageButton;

        public NoteListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
