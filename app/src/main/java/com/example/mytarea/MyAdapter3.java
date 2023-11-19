package com.example.mytarea;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class MyAdapter3 extends RecyclerView.Adapter<MyViewHolder3> {

    private Context context;
    private List<DataClass3> dataList;

    public MyAdapter3(Context context, List<DataClass3> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item3, parent, false);
        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        DataClass3 currentItem = dataList.get(position);

        holder.recTitle.setText(currentItem.getDataTitle());
        holder.recLang7.setText(calculateTimeDifference(currentItem.getStartTime(), currentItem.getEndTime()));

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Clic en: " + currentItem.getDataTitle(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Title", currentItem.getDataTitle());
                intent.putExtra("StartTime", currentItem.getStartTime());
                intent.putExtra("EndTime", currentItem.getEndTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private String calculateTimeDifference(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

            Date dateStartTime = sdf.parse(startTime);
            Date dateEndTime = sdf.parse(endTime);

            long differenceMillis = dateEndTime.getTime() - dateStartTime.getTime();
            int hours = (int) (differenceMillis / (1000 * 60 * 60));
            int minutes = (int) ((differenceMillis / (1000 * 60)) % 60);

            return String.format(Locale.getDefault(), "%d:%02d", hours, minutes);

        } catch (ParseException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    public void searchDataList(List<DataClass3> searchList) {
        dataList.clear();
        dataList.addAll(searchList);
        notifyDataSetChanged();
    }
}

class MyViewHolder3 extends RecyclerView.ViewHolder {

    TextView recTitle, recLang7;
    CardView recCard;

    public MyViewHolder3(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
        recTitle = itemView.findViewById(R.id.recTitle);
        recLang7 = itemView.findViewById(R.id.recLang7);
    }
}
