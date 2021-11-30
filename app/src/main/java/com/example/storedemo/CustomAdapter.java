package com.example.storedemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CustomAdapter extends FirebaseRecyclerAdapter<dataholder,CustomAdapter.myviewholder> {

    public CustomAdapter(@NonNull FirebaseRecyclerOptions<dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final dataholder model) {
        holder.roll.setText(model.getRoll());
        holder.name.setText(model.getName());
        holder.course.setText(model.getCourse());
        holder.duration.setText(model.getDuration());

        //add listener with edit image and work with Update function

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_content))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                EditText roll = myview.findViewById(R.id.uroll);
                EditText name = myview.findViewById(R.id.uname);
                EditText course = myview.findViewById(R.id.ucourse);
                EditText duration = myview.findViewById(R.id.uduration);
                Button submit = myview.findViewById(R.id.usubmit);

                roll.setText(model.getRoll());
                name.setText(model.getName());
                course.setText(model.getCourse());
                duration.setText(model.getDuration());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("roll",roll.getText().toString());
                        map.put("name",name.getText().toString());
                        map.put("course",course.getText().toString());
                        map.put("duration",duration.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("students")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

        //Delete any part

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(holder.name.getContext());
                builder.setIcon(R.drawable.question);
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete ? ");
                builder.setCancelable(false);

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("students")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_layout,parent,false);
        return new myviewholder(view);
    }

    /*Activity context;
    List<dataholder> list;*/

   /* public CustomAdapter(Activity context, List<dataholder> list) {
        super(context, R.layout.sample_layout, list);
        this.context = context;
        this.list = list;
    }
    */
    /*
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sample_layout,null,true);

        dataholder obj= list.get(position);
        TextView t1 = view.findViewById(R.id.rollTextViewId);
        TextView t2 = view.findViewById(R.id.nameTextViewId);
        TextView t3 = view.findViewById(R.id.courseTextViewId);
        TextView t4 = view.findViewById(R.id.durationTextViewId);

        t1.setText(obj.getRoll());
        t2.setText(obj.getName());
        t3.setText(obj.getCourse());
        t4.setText(obj.getDuration());

        return view;
    }*/

    class myviewholder extends RecyclerView.ViewHolder{

        ImageView edit, delete;
        TextView roll, name, course, duration;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            roll = itemView.findViewById(R.id.rollTextViewId);
            name = itemView.findViewById(R.id.nameTextViewId);
            course = itemView.findViewById(R.id.courseTextViewId);
            duration = itemView.findViewById(R.id.durationTextViewId);

            edit = itemView.findViewById(R.id.editIcon);
            delete = itemView.findViewById(R.id.deleteIcon);

        }
    }
}
