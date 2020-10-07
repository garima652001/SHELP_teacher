package com.users.shelp_teacher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Model> itemlist1;
    public Adapter(List<Model> itemlist) {
        this.itemlist1= itemlist;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent, false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String imgurl = itemlist1.get(position).getImage();
        String title = itemlist1.get(position).getTitle();
        String name = itemlist1.get(position).getName();
        double rating = itemlist1.get(position).getRating();
        Picasso.get().load(imgurl).into(holder.itemimage);
        holder.itemtitle.setText(title);
        holder.itemname.setText(name);
        String rate = Double.toString(rating);
        holder.itemrating.setText(rate);
        holder.starrating.setRating((float) rating);
    }

    @Override
    public int getItemCount() {
        return itemlist1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimage;
        TextView itemname;
        TextView itemtitle;
        TextView itemrating;
        RatingBar starrating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimage= itemView.findViewById(R.id.cimg);
            itemname= itemView.findViewById(R.id.teachername);
            itemtitle= itemView.findViewById(R.id.ctitle);
            itemrating= itemView.findViewById(R.id.rating);
            starrating= itemView.findViewById(R.id.ratingBar);
        }
    }
}

