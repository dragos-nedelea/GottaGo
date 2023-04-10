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

import java.time.Instant;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getDataTitle());
        holder.recDest.setText (dataList.get(position).getDataDest());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra( "Image", dataList.get (holder.getAdapterPosition ()).getDataImage());
                intent.putExtra("Number", dataList.get(holder.getAdapterPosition()).getNumberValue());
                intent.putExtra( "Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra( "Destination", dataList.get(holder.getAdapterPosition()).getDataDest());
                intent.putExtra( "Title", dataList.get(holder.getAdapterPosition ()).getDataTitle());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Fee", dataList.get(holder.getAdapterPosition()).getSwitchValue());
                context.startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}



class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView recImage;
    TextView recTitle, recDest;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView. findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDest = itemView. findViewById(R.id.recDest);
        recTitle = itemView. findViewById(R.id.recTitle);

    }
}
