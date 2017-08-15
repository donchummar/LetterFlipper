package care.work.careassignment.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import care.work.careassignment.R;
import care.work.careassignment.interfaces.ChatListConnector;

/**
 * Created by don on 15/8/17.
 */

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final char[] alphabetList;
    private ChatListConnector listConnector;

    public ChatListAdapter(Activity charListActivity, char[] alphabetList) {
        this.alphabetList = alphabetList;
        listConnector = (ChatListConnector) charListActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.char_item, null, false);
        return new CharViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CharViewHolder viewHolder = (CharViewHolder) holder;
        viewHolder.charView.setText(alphabetList[position] + "");
        viewHolder.charView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if (listConnector != null){
                 listConnector.onListItemClicked(position, alphabetList[position]);
             }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alphabetList.length;
    }

    static class CharViewHolder extends RecyclerView.ViewHolder {

        private final TextView charView;

        public CharViewHolder(View itemView) {
            super(itemView);
            charView = (TextView) itemView.findViewById(R.id.char_textview);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        listConnector = null;
    }
}
