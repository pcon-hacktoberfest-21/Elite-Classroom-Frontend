package com.example.elite_classroom.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elite_classroom.Fragments.Schedule_Class_Fragment;
import com.example.elite_classroom.Models.Recycler_Models.Get_Classes_List;
import com.example.elite_classroom.Models.Retrofit_Models.Schedule_Class_Request;
import com.example.elite_classroom.R;

import java.util.ArrayList;
import java.util.List;

public class Owner_Fragment_Adapter extends RecyclerView.Adapter<Owner_Fragment_Adapter.ViewHolder> {

    List<Get_Classes_List> list1;
    Context context;

    public Owner_Fragment_Adapter(Context context, ArrayList<Get_Classes_List> list) {
        this.list1=list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.get_class_recycler_view_single_row, viewGroup, false);
        return new Owner_Fragment_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

         viewHolder.class_name.setText(list1.get(i).getClass_name());
         viewHolder.owner_name.setText(list1.get(i).getOwner_name());
         viewHolder.students_no.setText(list1.get(i).getNumber_of_participants()+" "+ "students");
         viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Bundle b = new Bundle();
                 b.putString("class_code", list1.get(i).getClass_code());
                  Schedule_Class_Fragment schedule_class_fragment= new Schedule_Class_Fragment();
                  schedule_class_fragment.setArguments(b);
                 ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        schedule_class_fragment,"Schedule_FRAGMENT").commit();
//                 Intent intent = new Intent(context, ClassActivity.class);
//                 intent.putExtra("owner_id",list1.get(i).getOwner_id());
//                 intent.putExtra("owner_name",list1.get(i).getOwner_name());
//                 intent.putExtra("class_name",list1.get(i).getClass_name());
//                 intent.putExtra("class_code",list1.get(i).getClass_code());
//                 context.startActivity(intent);
             }
         });

    }


    @Override
    public int getItemCount() {
        return list1.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent_layout ;
        TextView  class_name;
        TextView  owner_name;
        TextView students_no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           parent_layout = itemView.findViewById(R.id.parent_layout);
           class_name= itemView.findViewById(R.id.class_name) ;
           owner_name = itemView.findViewById(R.id.owner_name);
           students_no = itemView.findViewById(R.id.students_no);
        }
    }
}
