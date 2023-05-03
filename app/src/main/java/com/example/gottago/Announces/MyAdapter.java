package com.example.gottago.Announces;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gottago.NavigationDrawer.AboutFragment;
import com.example.gottago.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;
    private List<DataClass> filteredList; // store filtered list
    private String criteria; // store selected criteria for filtering

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.filteredList = new ArrayList<>(dataList); // initialize filtered list with all data

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view, context, dataList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getDataTitle());
        holder.recDest.setText (dataList.get(position).getDataDest());
        String formattedTime = DateFormat.getDateTimeInstance().format(dataList.get(position).getTimestamp());
        holder.recTime.setText(formattedTime);


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra( "Image", dataList.get (holder.getAdapterPosition ()).getDataImage());
                intent.putExtra("Number", dataList.get(holder.getAdapterPosition()).getNumberValue());
                intent.putExtra( "Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra( "Destination", dataList.get(holder.getAdapterPosition()).getDataDest());
                intent.putExtra( "Start", dataList.get(holder.getAdapterPosition()).getDataStart());
                intent.putExtra( "Title", dataList.get(holder.getAdapterPosition ()).getDataTitle());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("uid", dataList.get(holder.getAdapterPosition()).getUid());
                intent.putExtra("Fee", dataList.get(holder.getAdapterPosition()).getSwitchValue());
                intent.putExtra("userName", dataList.get(holder.getAdapterPosition()).getDataUserName());
                context.startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }

}



class MyViewHolder extends RecyclerView.ViewHolder {

    private String key;
    ImageView recImage;
    TextView recTitle, recDest, recTime;
    CardView recCard;
    List<DataClass> dataList;


    public MyViewHolder(@NonNull View itemView, Context context, List<DataClass> dataList) {
        super(itemView);

        recImage = itemView. findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDest = itemView. findViewById(R.id.recDest);
        recTitle = itemView. findViewById(R.id.recTitle);
        recTime = itemView.findViewById(R.id.recTime);
        this.dataList = dataList;


        recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Set the value of announcementKey when an item is clicked
                    DataClass clickedItem = dataList.get(position);
                    key = clickedItem.getKey();

                    DataClass clickedAnnouncement = dataList.get(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("Image", clickedAnnouncement.getDataImage());
                    intent.putExtra("Number", clickedAnnouncement.getNumberValue());
                    intent.putExtra("Description", clickedAnnouncement.getDataDesc());
                    intent.putExtra("Destination", clickedAnnouncement.getDataDest());
                    intent.putExtra("Title", clickedAnnouncement.getDataTitle());
                    intent.putExtra("Key", clickedAnnouncement.getKey());
                    intent.putExtra("uid", clickedAnnouncement.getUid());
                    intent.putExtra("Fee", clickedAnnouncement.getSwitchValue());
                    context.startActivity(intent);
                }
            }
        });
    }
}
