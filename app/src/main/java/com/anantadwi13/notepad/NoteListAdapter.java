package com.anantadwi13.notepad;

import android.icu.util.ValueIterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private Listener listener;
    private List<Note> list = new ArrayList<>();

    public List<Note> getList() {
        return list;
    }

    public void setList(List<Note> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public NoteListAdapter(@Nullable Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            cardView = itemView.findViewById(R.id.card);
        }

        public void bind(final int pos){
            Note note = getList().get(pos);
            if (note == null) return;

            tvTitle.setText(note.getTitle());
            tvContent.setText(note.getContent());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onClick(pos);
                }
            });
        }
    }

    public interface Listener{
        void onClick(int pos);
    }
}
