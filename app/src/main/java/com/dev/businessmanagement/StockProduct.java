package com.dev.businessmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class StockProduct extends AppCompatActivity {

    TextInputEditText inputProductName, inputProductPrice, inputProductQuantity, inputProductCode;
    ProgressBar progressBar;
    Button buttonSubmit;
    TextView tvChangePhoto;
    ImageView imageProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_product);

        inputProductName = findViewById(R.id.inputProductName);
        inputProductPrice = findViewById(R.id.inputProductPrice);
        inputProductQuantity = findViewById(R.id.inputProductQuantity);
        inputProductCode = findViewById(R.id.inputProductCode);
        progressBar = findViewById(R.id.progressBar);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        tvChangePhoto = findViewById(R.id.tvChangePhoto);
        imageProduct = findViewById(R.id.imageProduct);

        progressBar.setVisibility(View.GONE);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayReq();


            }
        });




        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode()==Activity.RESULT_OK){

                    Intent intent = result.getData();
                    Uri uri = intent.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                        imageProduct.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });



        tvChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImagePicker.with(StockProduct.this)
                        .maxResultSize(1000, 1000)
                        .compress(1024)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent intent) {
                                imagePickerLauncher.launch(intent);
                                return null;
                            }
                        });


            }
        });





    }
    //============================ On Create End ====================

    //---------------------------------------------------------------


    //---------------------------------------------------------------

    private void arrayReq(){

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://meghpy.com/snsports/userarray.php";

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageProduct.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String image64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        String productsname = inputProductName.getText().toString();
        String price = inputProductPrice.getText().toString();
        String quantity = inputProductQuantity.getText().toString();
        String productcode = inputProductCode.getText().toString();


        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key", "13579");
            jsonObject.put("productsname", productsname);
            jsonObject.put("price", price);
            jsonObject.put("quantity", quantity);
            jsonObject.put("productcode", productcode);
            jsonObject.put("image", image64);

            jsonArray.put(jsonObject);
            
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);


                try {
                    JSONObject jsonObject1 = response.getJSONObject(0);
                    String status = jsonObject1.getString("");
                    if (status.contains("Successful")){
                        new AlertDialog.Builder(StockProduct.this)
                                .setTitle("Server Response")
                                .setMessage("Server Sucess")
                                .show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);


            }
        });

        if (productsname.length()>0 && price.length()>0 && quantity.length()>0){
            RequestQueue requestQueue = Volley.newRequestQueue(StockProduct.this);
            requestQueue.add(jsonArrayRequest);
        }else {
            inputProductName.setError("Product Name is required");
            inputProductPrice.setError("Price is required");
            inputProductQuantity.setError("Quantity is required");

        }

    }


    //---------------------------------------------------------------

    //======================== Code Ends Here ==========================
}