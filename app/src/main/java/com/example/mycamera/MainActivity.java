package com.example.mycamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btselect,btUpload;

    private int IMG_REQUEST = 21;
    private Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=findViewById(R.id.imageView);

        btselect=findViewById(R.id.select_img);
        btUpload=findViewById(R.id.upload_img);

        btselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

    }

    private void uploadImage() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);

        byte[] imgeInByte = byteArrayOutputStream.toByteArray();


        String encodeImage = Base64.encodeToString(imgeInByte, Base64.DEFAULT);


        Call<ResponsePOJO> call = RetroClient.getInstance().
                                  getApi().uploadImages(encodeImage);


        call.enqueue(new Callback<ResponsePOJO>() {
            @Override
            public void onResponse(Call<ResponsePOJO> call, Response<ResponsePOJO> response) {

                Toast.makeText(MainActivity.this, response.body().getRemarks(), Toast.LENGTH_SHORT).show();

                if (response.body().isStatus())
                {

                }else {

                }

            }

            @Override
            public void onFailure(Call<ResponsePOJO> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();

            }
        });



    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data !=null )
        {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(),path);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}