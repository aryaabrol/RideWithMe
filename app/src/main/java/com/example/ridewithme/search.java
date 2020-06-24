package com.example.ridewithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class search extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    private TextView date;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        date=(TextView) findViewById(R.id.Date);
        layoutManager = new LinearLayoutManager(search.this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new date();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                load(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    protected void load(String t) {

        final DatabaseReference listref= FirebaseDatabase.getInstance().getReference().child("Requests");

        FirebaseRecyclerOptions<list> options=
                new FirebaseRecyclerOptions.Builder<list>()
                .setQuery(listref.child(t),list.class)
                .build();

        FirebaseRecyclerAdapter<list,listviewholder> adapter=new FirebaseRecyclerAdapter<list, listviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull listviewholder holder, int position, @NonNull list model) {
            holder.name1.setText(model.getName());
            holder.number.setText(model.getPhone());
            holder.from.setText(model.getFrom());
            holder.to.setText(model.getTo());
            holder.time.setText(model.getTime());
            }

            @NonNull
            @Override
            public listviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchitem,parent,false);
                listviewholder holder=new listviewholder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        date.setText(currentDateString);

    }
}
