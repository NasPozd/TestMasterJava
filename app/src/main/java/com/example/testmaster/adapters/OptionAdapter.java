package com.example.testmaster.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmaster.R;
import com.example.testmaster.models.Question;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder>{
    public List<String> options;
    public Question question;
    Context context;
    @RequiresApi(api = Build.VERSION_CODES.R)
    public OptionAdapter(Context context, Question question){
        this.context = context;
        this.question = question;
        options = List.of(question.option1,question.option2,question.option3,question.option4);
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.option_layout,parent,false);

        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder( OptionViewHolder holder, int position) {
        String data = options.get(position);
        holder.option.setText(data);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Toast.makeText(context,""+options.get(position),Toast.LENGTH_SHORT).show();
                question.userAnswer = options.get(position);
                Log.d("TESTTAG",question.userAnswer + " " + options.get(position));
                notifyDataSetChanged();
            }

        });
        if((question.userAnswer != "") && (question.userAnswer == options.get(position))){
            holder.itemView.setBackgroundResource(R.drawable.option_selected_bg);
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mainLayout;
        TextView option;
        public OptionViewHolder( View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.test_option_list);
            option = itemView.findViewById(R.id.option_test);

        }
    }
}
