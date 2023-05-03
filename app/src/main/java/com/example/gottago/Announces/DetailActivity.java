package com.example.gottago.Announces;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gottago.NavigationDrawer.HomeFragment;
import com.example.gottago.R;
import com.example.gottago.SharedPrefs.SharedPrefsUtils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailNumber, detailTitle, detailDest, detailOwner, detailStart, FeeSwitch;
    ImageView detailImage;
    ImageButton backBtn;
    FloatingActionButton deleteBtn, editBtn, callBtn;
    String uid;
    String key;
    String imageUrl = "";
    String default_image_url;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        detailDest = findViewById(R.id.detailDest);
        detailImage = findViewById(R.id.detailImage);
        deleteBtn = findViewById(R.id.deleteBtn);
        editBtn = findViewById(R.id.editBtn);
        detailNumber = findViewById(R.id.detailNumber);
        backBtn = findViewById(R.id.backBtn);
        callBtn = findViewById(R.id.callBtn);
        detailOwner = findViewById(R.id.detailOwner);
        detailStart = findViewById(R.id.detailStart);
        default_image_url = "gs://gottago-13352.appspot.com/Android Images/default_image.jpg";
        FeeSwitch = findViewById(R.id.detailFee);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailNumber.setText(Integer.toString(bundle.getInt("Number")));
            detailDest.setText(bundle.getString("Destination"));
            detailTitle.setText(bundle.getString("Title"));
            detailTitle.setText(bundle.getString("Start"));
            imageUrl = bundle.getString("Image");
            uid = bundle.getString("uid");
            key = bundle.getString("Key");
            detailOwner.setText((bundle.getString("userName")));
            detailStart.setText((bundle.getString("Start")));
            boolean isFee = bundle.getBoolean("Fee");
            String feeString = isFee ? "yes" : "no";
            FeeSwitch.setText(feeString);
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);


//            Show floatingMenu when user is logged in & he' the owner of the announce
            if (SharedPrefsUtils.getUid(this).equals(uid)) {
               deleteBtn.setVisibility(View.VISIBLE);
               editBtn.setVisibility(View.VISIBLE);
               callBtn.setVisibility(View.GONE);
//            Hide floatingMenu when user is not logged in & show floating callBtn
            } else if (SharedPrefsUtils.getUid(this) == null) {
                deleteBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.GONE);
                callBtn.setVisibility(View.VISIBLE);
//            Hide floatingMenu when user is not the owner & show floating callBtn
            } else {
                deleteBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.GONE);
                callBtn.setVisibility(View.VISIBLE);
            }
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");


                    if (key != null) {
                        reference.child(key).removeValue();
                    }
                    Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeFragment.class));
                    finish();

            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("Title",detailTitle.getText().toString())
                        .putExtra("Number", Integer.parseInt(detailNumber.getText().toString()))
                        .putExtra("Description",detailDesc.getText().toString())
                        .putExtra("Destination",detailDest.getText().toString())
                        .putExtra("userName",detailOwner.getText().toString())
                        .putExtra("Start",detailStart.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);

                startActivity(intent);

            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + detailNumber.getText().toString()));
                startActivity(intent);
            }
        });
    }
}