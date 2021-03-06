package fr.goui.gouinote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.R;
import fr.goui.gouinote.main.user.UserClickEvent;
import fr.goui.gouinote.model.User;
import fr.goui.gouinote.model.UserModel;

/**
 * Adapter for a list of users. It is synchronized with cache model but this can be overridden.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> implements Observer {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<User> mUsers;

    public UserListAdapter(Context context) {
        mContext = context;
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
    public void onBindViewHolder(final UserListViewHolder holder, int position) {
        holder.position = position;
        final User user = mUsers.get(position);
        if (user != null) {
            holder.nicknameTextView.setText(user.getNickname());
            if (user.getNotes() != null && !user.getNotes().isEmpty()) {
                holder.lastActivityTextView.setText(getDateText(user.getNotes().get(user.getNotes().size() - 1).getDate()));
                holder.nbOfNotesTextView.setText(user.getNotes().size() + " note(s)");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new UserClickEvent(user, holder.avatarImageView));
                }
            });
        }
    }

    private String getDateText(long noteTime) {
        long seconds = (System.currentTimeMillis() - noteTime) / 1000L;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String dateText = "";
        if (days > 0) {
            dateText = "" + days;
            dateText += " " + mContext.getString(R.string.days_ago);
        } else if (hours > 0) {
            dateText = "" + hours;
            dateText += " " + mContext.getString(R.string.hours_ago);
        } else if (minutes > 0) {
            dateText = "" + minutes;
            dateText += " " + mContext.getString(R.string.minutes_ago);
        } else if (seconds > 0) {
            dateText = "" + seconds;
            dateText += " " + mContext.getString(R.string.seconds_ago);
        }
        return dateText;
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

        @BindView(R.id.user_avatar_image_view)
        ImageView avatarImageView;

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
