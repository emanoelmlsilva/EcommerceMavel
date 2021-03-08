package com.example.ecommercemarvel.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.database.ComicDAO;
import com.example.ecommercemarvel.database.ComicDAOEntity;
import com.example.ecommercemarvel.database.ComicDAOImp;
import com.example.ecommercemarvel.model.Comic;

import java.util.Objects;

public class DetailFragment extends Fragment {
    private Comic comic;
    private Button btnClose, btnPurchase;
    private TextView txtTitle, txtDescription;
    private Spinner spinner;
    private ImageView imgComic, imgRare;
    private ComicDAOImp dataBase;
    private Context context;

    public DetailFragment(Comic comic, Context context) {
        this.comic = comic;
        dataBase = ComicDAOImp.getInstance(context);
        this.context = context;
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
        generateValueSpinner(100);
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

        btnPurchase.setOnClickListener(view -> {
            ComicDAOEntity comicDAOEntity = dataBase.getComicById(comic.getId());
            if(comicDAOEntity != null){
                comicDAOEntity.setAmount(comicDAOEntity.getAmount() + Integer.parseInt(spinner.getSelectedItem().toString()));
            }else{

                String url = comic.getThumbnail().getPath() + "." + comic.getThumbnail().getExtension();

                comicDAOEntity = new ComicDAOEntity();
                comicDAOEntity.setId(comic.getId());
                comicDAOEntity.setTitle(comic.getTitle());
                comicDAOEntity.setDescription(comic.getDescription());
                comicDAOEntity.setImageUrl(url);
                comicDAOEntity.setPrice(comic.getPrices().get(0).getPrice());
                comicDAOEntity.setAmount(Integer.parseInt(spinner.getSelectedItem().toString()));
                comicDAOEntity.setRare(comic.getRare());
                dataBase.insertComic(comicDAOEntity);
            }

            Toast.makeText(context, "Utilize o Cupom: Cupom10 para ter 10% de desconto em qualquer quadrinho e Cupom25 para ter 25% nos quadrinhos raros", Toast.LENGTH_LONG).show();
        });
    }

    private void generateValueSpinner(int maxValue){
        String[] arrayValue = new String[maxValue];
        for(int i = 0; i < maxValue; i++){
            arrayValue[i] = i+1 + "";
        }
        spinner.setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayValue));
    }
}