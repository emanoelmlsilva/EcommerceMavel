package com.example.ecommercemarvel.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.adapter.CheckoutAdapter;
import com.example.ecommercemarvel.database.ComicDAOEntity;
import com.example.ecommercemarvel.database.ComicDAOImp;
import com.example.ecommercemarvel.model.Comic;

public class CheckoutFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView txtTotal;
    private Button btnApply;
    private EditText editCoupon;
    private CheckoutAdapter checkoutAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checkout, container, false);

        txtTotal = root.findViewById(R.id.txtTotal);
        float valorInit = calcValorTotal("");
        txtTotal.setText(String.format("$ %.2f",valorInit));
        btnApply = root.findViewById(R.id.btnApply);
        editCoupon = root.findViewById(R.id.editTextCoupon);

        checkoutAdapter = new CheckoutAdapter(getContext());
        recyclerView = root.findViewById(R.id.recyclerCheckout);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(checkoutAdapter);

        btnApply.setOnClickListener(v -> {
            float valor = calcValorTotal(editCoupon.getText().toString());
            txtTotal.setText(String.format("$ %.2f",valor));
        });

        return root;
    }

    private float calcValorTotal(String coupon){
        float total = 0;
        for(ComicDAOEntity comicDAOEntity : ComicDAOImp.getInstance(getContext()).getComics()){
            float valor = comicDAOEntity.getPrice() * comicDAOEntity.getAmount();
            if(coupon.equals("Cupom10")){
                total += valor * 0.10;
            }else if(coupon.equals("Cupom25") && comicDAOEntity.getRare()){
                total += valor * 0.25;
            }else{
                total += valor;
            }
        }
        return total;
    }
}