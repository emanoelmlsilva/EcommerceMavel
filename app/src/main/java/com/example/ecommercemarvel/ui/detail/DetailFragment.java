package com.example.ecommercemarvel.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.model.Comic;

import java.util.Objects;

public class DetailFragment extends Fragment {
    private Comic comic;
    private Button btnClose, btnPurchase;
    private TextView txtTitle, txtDescription;
    private Spinner spinner;
    private ImageView imgComic, imgRare;

    public DetailFragment(Comic comic) {
        this.comic = comic;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        initComponets(root);

        setDataComponets(root);

        setOnClicks();

        return root;
    }

    private void initComponets(View view){
        btnClose = view.findViewById(R.id.btnClose);
        btnPurchase = view.findViewById(R.id.btnPurchase);
        txtTitle = view.findViewById(R.id.txtTitleDetail);
        txtDescription = view.findViewById(R.id.txtDescription);
        spinner = view.findViewById(R.id.spinnerAmount);
        imgComic = view.findViewById(R.id.imgDetail );
        imgRare = view.findViewById(R.id.imgRareDetail);
    }

    private void setDataComponets(View view){
        txtTitle.setText(comic.getTitle());
        String description = comic.getDescription() != null ? comic.getDescription() : "";
        txtDescription.setText("Descrição: "+description);
        if(comic.getRare()){
            imgRare.setVisibility(View.VISIBLE);
        }
        String url = comic.getThumbnail().getPath() + "." + comic.getThumbnail().getExtension();

        Glide.with(view)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(imgComic);
    }

    private void setOnClicks(){
        btnClose.setOnClickListener(view -> {
            getFragmentManager().popBackStack();
        });
    }
}