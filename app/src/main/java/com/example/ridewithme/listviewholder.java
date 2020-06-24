package com.example.ridewithme;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class listviewholder extends RecyclerView.ViewHolder {
    public TextView name1,time,from,to,number;
    public View view;

    public listviewholder(@NonNull View itemView) {
        super(itemView);
        name1=itemView.findViewById(R.id.name2);
        time=itemView.findViewById(R.id.time2);
        from=itemView.findViewById(R.id.from2);
        to=itemView.findViewById(R.id.to2);
        number=itemView.findViewById(R.id.phone2);
        view=itemView;
    }
}
