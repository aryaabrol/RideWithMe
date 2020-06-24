package com.example.ridewithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class request extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText name,phone,hr,min,ampm,from,to;
    private TextView textView;
    private ProgressDialog dialog;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        textView = (TextView) findViewById(R.id.dates);
        name=(EditText) findViewById(R.id.name);
        phone=(EditText) findViewById(R.id.phonenumber);
        hr=(EditText) findViewById(R.id.hr);
        min=(EditText) findViewById(R.id.min);
        button = (Button) findViewById(R.id.submit);
        from=(EditText) findViewById(R.id.from);
        to=(EditText) findViewById(R.id.to);
        ampm=(EditText) findViewById(R.id.ampm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name1=name.getText().toString();
            String phone1=phone.getText().toString();
            String hr1=hr.getText().toString();
            String min1=min.getText().toString();
            String ampm1=ampm.getText().toString();
            String time1=hr1+":"+min1+ampm1;
            String from1=from.getText().toString();
            String to1=to.getText().toString();
            String date1=textView.getText().toString();

                if(TextUtils.isEmpty(name1)) Toast.makeText(getApplicationContext(),"Please enter name",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(phone1)) Toast.makeText(getApplicationContext(),"Please enter phone number",Toast.LENGTH_SHORT).show();

                else{
                    dialog=new ProgressDialog(request.this);
                    dialog.setMessage("Please wait while checking credentials");
                    dialog.show();

                    AddData(name1,phone1,time1,from1,to1,date1);
                }


            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new date();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void AddData(final String name1, final String phone1,final String time1, final String from1, final String to1, final String date1) {
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Requests").child(date1).child(phone1).exists())){
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("name",name1);
                    hashMap.put("phone",phone1);
                    hashMap.put("from",from1);
                    hashMap.put("to",to1);
                    hashMap.put("time",time1);
                    hashMap.put("date",date1);
                    ref.child("Requests").child(date1).child(phone1).updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Request Added Successfully",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                }
                            });

                }
                else
                    Toast.makeText(getApplicationContext(),"Request with phone number already exists on the date",Toast.LENGTH_LONG).show();
                    dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        textView.setText(currentDateString);

    }
}
