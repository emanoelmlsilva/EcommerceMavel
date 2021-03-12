package com.example.ecommercemarvel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.model.Comic;
import com.example.ecommercemarvel.ui.detail.DetailFragment;

import java.util.List;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.MyViewHolder> {

    private List<Comic> comics;
    private DetailFragment detailFragment;
    private FragmentManager fragmentManager;

    public ComicAdapter(List<Comic> comicList, Context context){
        this.comics = comicList;
        this.fragmentManager =  ((FragmentActivity) context).getSupportFragmentManager();;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comic comic = this.comics.get(position);
        String url = comic.getThumbnail().getPath() + "." + comic.getThumbnail().getExtension();
        if(comic.getRare()){
            holder.imgRare.setVisibility(View.VISIBLE);
        }else{
            holder.imgRare.setVisibility(View.GONE);
        }
        Glide.with(holder.getView())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(holder.getImgComic());
        holder.getTitle().setText(comic.getTitle());
        holder.getCardView().setOnClickListener(view -> {
            detailFragment = new DetailFragment(comic, view.getContext());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, detailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    public void updateList(List<Comic> comics){
        this.comics = null;
        this.comics = comics;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.comics.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView title;
        private ImageView imgComic, imgRare;
        private View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardComic);
            title = itemView.findViewById(R.id.txtComic);
            imgComic = itemView.findViewById(R.id.imgComic);
            imgRare = itemView.findViewById(R.id.imgRare);
            view = itemView;

        }

        public CardView getCardView() {
            return cardView;
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImgComic() {
            return imgComic;
        }

        public View getView() {
            return view;
        }

        public ImageView getImgRare() {
            return imgRare;
        }
    }
}
