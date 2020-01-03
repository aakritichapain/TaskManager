package com.aakriti.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.aakriti.taskmanager.api.UsersAPI;
import com.aakriti.taskmanager.model.Users;
import com.aakriti.taskmanager.url.URL;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    ImageView imgProgileImg;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imgProgileImg = findViewById(R.id.imgProgileImg);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddTaskActivity.class));
            }
        });

    loadCurrentUser();
}

    private void loadCurrentUser() {

        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        Call<Users> userCall = usersAPI.getUserDetails(URL.token);

        userCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DashboardActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                String imgPath = URL.imagePath +  response.body().getImage();

                Picasso.get().load(imgPath).into(imgProgileImg);


//                StrictModeClass.StrictMode();
//                try {
//                    URL url = new URL(imgPath);
//                    imgProgileImg.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                Toast.makeText(DashboardActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
