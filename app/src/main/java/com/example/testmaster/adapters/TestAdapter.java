package com.example.testmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmaster.R;
import com.example.testmaster.activities.TestActivity;
import com.example.testmaster.models.Test;
import com.example.testmaster.utils.ColorPicker;
import com.example.testmaster.utils.IconPicker;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder>{
    private ArrayList<Test> data;
    private Context context;

    public TestAdapter(Context context, ArrayList<Test> test){
        this.data = test;
        this.context = context;
    }

    @Override
    public TestViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.test_item,parent,false);

        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        String title = data.get(position).title;
        holder.testTitle.setText(title);
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()));
        holder.testIcon.setImageResource(IconPicker.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+data.get(position).title,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("DATE",data.get(position).title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{
        TextView testTitle;
        ImageView testIcon;
        CardView cardContainer;

        public TestViewHolder( View itemView) {
            super(itemView);
            testTitle = itemView.findViewById(R.id.testTitle);
            testIcon = itemView.findViewById(R.id.testIcon);
            cardContainer = itemView.findViewById(R.id.cardContainer);
        }
    }
}