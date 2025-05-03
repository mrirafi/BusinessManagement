package com.dev.businessmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.card.MaterialCardView;


public class HomeFragment extends Fragment {

    MaterialCardView addStock, addCustomer, addExpense, addSell, addPayment, addProductList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_home, container, false);

        addStock = myView.findViewById(R.id.addStock);
        addCustomer = myView.findViewById(R.id.addCustomer);
        addProductList = myView.findViewById(R.id.addProductList);


        addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), StockProduct.class));

            }
        });

        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), CustomerDetails.class));

            }
        });

        addProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ProductList.class));

            }
        });








        return myView;
    }

}