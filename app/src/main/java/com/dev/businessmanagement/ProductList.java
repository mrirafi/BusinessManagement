package com.dev.businessmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        productMyAdapter adapter = new productMyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductList.this));

        viewArray();


    }
    //=========================== On Create End =======================================

    private class productMyAdapter extends RecyclerView.Adapter <productMyAdapter.myViewHolders>{

        private class myViewHolders extends RecyclerView.ViewHolder{

            TextView productNameTextView, priceTextView, quantityTextView, productCodeTextView;
            ImageView productImageView;

            public myViewHolders(@NonNull View itemView) {
                super(itemView);

                productNameTextView = itemView.findViewById(R.id.productNameTextView);
                priceTextView = itemView.findViewById(R.id.priceTextView);
                quantityTextView = itemView.findViewById(R.id.quantityTextView);
                productCodeTextView = itemView.findViewById(R.id.productCodeTextView);
                productImageView = itemView.findViewById(R.id.productImageView);



            }
        }

        @NonNull
        @Override
        public myViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.item_card, parent, false);


            return new myViewHolders(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolders holder, int position) {

            hashMap = arrayList.get(position);

            String productsName = hashMap.get("productsname");
            String price = hashMap.get("price");
            String quantity = hashMap.get("quantity");
            String productCode = hashMap.get("productcode");
            String image = hashMap.get("image");

            String imgUrl = "https://meghpy.com/snsports/"+image;

            holder.productNameTextView.setText(productsName);
            holder.priceTextView.setText(price);
            holder.quantityTextView.setText(quantity);
            holder.productCodeTextView.setText(productCode);

            Picasso.get().load(imgUrl)
                    .placeholder(R.drawable.baseline_image_24).into(holder.productImageView);



        }

        @Override
        public int getItemCount() {
            return arrayList.size();

        }



    }
    //-----------------------------------------------------------

    private void viewArray(){
        arrayList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://meghpy.com/snsports/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                for (int x=0; x<response.length(); x++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(x);
                        String productsname = jsonObject.getString("productsname");
                        String price = jsonObject.getString("price");
                        String quantity = jsonObject.getString("quantity");
                        String productcode = jsonObject.getString("productcode");
                        String image = jsonObject.getString("image");


                        hashMap = new HashMap<>();
                        hashMap.put("productsname", productsname);
                        hashMap.put("price", price);
                        hashMap.put("quantity", quantity);
                        hashMap.put("productcode", productcode);
                        hashMap.put("image", image);

                        arrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ProductList.this);
        requestQueue.add(jsonArrayRequest);

    }
    //-----------------------------------------------------------




    //=========================== The Code End here ====================================
}