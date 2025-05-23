package com.shamim.note_to_self;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ListItemHolder> {

    private List<Note> mNoteList;
    private MainActivity mMainActivity;

    public NoteAdapter(MainActivity mainActivity, List<Note> noteList) {
        mMainActivity = mainActivity;
        mNoteList = noteList;
    }

    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.mTitle.setText(note.getTitle());

        if(note.getDescription().length() > 15) {
            holder.mDescription.setText(note.getDescription().substring(0, 15));
        } else {
            holder.mDescription.setText(note.getDescription().substring(0, note.getDescription().length() / 2));
        }

        if(note.isIdea()){
            holder.mStatus.setText(R.string.idea_text);
        } else if(note.isImportant()){
            holder.mStatus.setText(R.string.important_text);
        } else if(note.isTodo()){
            holder.mStatus.setText(R.string.todo_text);
        }
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        TextView mDescription;
        TextView mStatus;

        public ListItemHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.textViewTitle);
            mDescription = view.findViewById(R.id.textViewDescription);
            mStatus = view.findViewById(R.id.textViewStatus);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mMainActivity.showNote(getAdapterPosition());
        }
    }
}
