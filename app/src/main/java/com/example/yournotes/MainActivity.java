package com.example.yournotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yournotes.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                intent.putExtra("type", "addMode");
                startActivityForResult(intent, 1);
            }
        });

        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
        Adapter adapter=new Adapter();
        binding.rv.setAdapter(adapter);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.RIGHT)
                {
                    Toast.makeText(MainActivity.this, "Note has been deleted successfully.", Toast.LENGTH_SHORT).show();
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                }
                // Inside onSwiped method of ItemTouchHelper.SimpleCallback
                // Inside onSwiped method of ItemTouchHelper.SimpleCallback
                else {
                    Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                    intent.putExtra("type", "update");
                    Note noteToUpdate = adapter.getNote(viewHolder.getAdapterPosition());
                    intent.putExtra("title", noteToUpdate.getTitle());
                    intent.putExtra("desc", noteToUpdate.getDesc()); // Pass description
                    intent.putExtra("id", noteToUpdate.getId());
                    startActivityForResult(intent, 2);
                }



            }
        }).attachToRecyclerView(binding.rv);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1)
        {
            String title=data.getStringExtra("title");
            String desc=data.getStringExtra("desc");
            Note note=new Note(title,desc);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note has been added successfully.", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==2)
        {
            String title=data.getStringExtra("title");
            String desc=data.getStringExtra("desc");
            Note note=new Note(title,desc);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);
            Toast.makeText(this, "Note has been updated successfully.", Toast.LENGTH_SHORT).show();
        }
    }
}