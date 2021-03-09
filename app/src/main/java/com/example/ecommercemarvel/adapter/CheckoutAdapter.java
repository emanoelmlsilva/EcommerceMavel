package com.example.ecommercemarvel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.database.ComicDAOEntity;
import com.example.ecommercemarvel.database.ComicDAOImp;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    private List<ComicDAOEntity> comicDAOEntities = new ArrayList<>();
    private View view;

    public CheckoutAdapter(Context context){
        comicDAOEntities = ComicDAOImp.getInstance(context).getComics();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_adapter, parent, false);
        this.view = view;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ComicDAOEntity comicDAOEntity = comicDAOEntities.get(position);
        holder.getTxtTitle().setText(comicDAOEntity.getTitle());
        holder.getTxtPrice().setText("$"+comicDAOEntity.getPrice());
        holder.getTxtAmount().setText("Qt: "+comicDAOEntity.getAmount());
        String url = comicDAOEntity.getImageUrl();
        if(comicDAOEntity.getRare()){
            holder.imgRare.setVisibility(View.VISIBLE);
        }
        Glide.with(view)
                .load(url)
                .placeholder(R.drawable.no_image)
                .into(holder.getImgComic());
    }

    @Override
    public int getItemCount() {
        return comicDAOEntities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgComic, imgRare;
        private TextView txtTitle, txtPrice, txtAmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgComic = itemView.findViewById(R.id.imgComicCk);
            imgRare = itemView.findViewById(R.id.imgRareCk);
            txtTitle = itemView.findViewById(R.id.txtTitleCk);
            txtPrice = itemView.findViewById(R.id.txtPriceCk);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }

        public ImageView getImgComic() {
            return imgComic;
        }

        public ImageView getImgRare() {
            return imgRare;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }

        public TextView getTxtAmount() {
            return txtAmount;
        }
    }
}
