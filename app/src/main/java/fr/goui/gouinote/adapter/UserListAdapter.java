package fr.goui.gouinote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.R;
import fr.goui.gouinote.model.User;
import fr.goui.gouinote.model.UserModel;

/**
 * Adapter for a list of users. It is synchronized with cache model but this can be overridden.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> implements Observer {

    private LayoutInflater mLayoutInflater;

    private List<User> mUsers;

    public UserListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mUsers = UserModel.getInstance().getUsers();
        UserModel.getInstance().addObserver(this);
    }

    /**
     * Overriding user model.
     * For default behavior don't use this method.
     *
     * @param users the list of users to display
     */
    public void setListOfUsers(List<User> users) {
        mUsers = users;
        UserModel.getInstance().deleteObserver(this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    @Override
    public UserListAdapter.UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserListViewHolder(mLayoutInflater.inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {
        holder.position = position;
        final User user = mUsers.get(position);
        if (user != null) {
            holder.nicknameTextView.setText(user.getNickname());
            if (user.getNotes() != null && !user.getNotes().isEmpty()) {
                holder.lastActivityTextView.setText("" + user.getNotes().get(0).getDate());
                holder.nbOfNotesTextView.setText(user.getNotes().size() + " note(s)");
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof UserModel) {
            mUsers = UserModel.getInstance().getUsers();
            notifyDataSetChanged();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        UserModel.getInstance().deleteObserver(this);
    }

    static class UserListViewHolder extends RecyclerView.ViewHolder {
        int position;

        @BindView(R.id.user_nickname_text_view)
        TextView nicknameTextView;

        @BindView(R.id.user_last_activity_text_view)
        TextView lastActivityTextView;

        @BindView(R.id.user_nb_of_notes_text_view)
        TextView nbOfNotesTextView;

        public UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
