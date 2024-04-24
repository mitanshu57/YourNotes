package com.example.yournotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yournotes.databinding.EachRvBinding;

import javax.security.auth.callback.Callback;

public class Adapter extends ListAdapter<Note,Adapter.ViewHolder>{

    public Adapter()
    {
        super(CALLBACK);

    }

    private static final DiffUtil.ItemCallback<Note> CALLBACK=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDesc().equals(newItem.getDesc());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note =getItem(position);
        holder.binding.titlee.setText(note.getTitle());
        holder.binding.desx.setText(note.getDesc());
    }

    public Note getNote(int position)
    {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    EachRvBinding binding;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        binding=EachRvBinding.bind(itemView);
    }
}
}
