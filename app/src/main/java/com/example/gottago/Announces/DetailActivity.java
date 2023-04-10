package com.example.gottago.Announces;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gottago.MainActivity;
import com.example.gottago.NavigationDrawer.HomeFragment;
import com.example.gottago.R;
import com.example.gottago.SharedPrefsUtils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailNumber, detailTitle, detailDest;
    Switch FeeSwitch;
    ImageView detailImage;
    FloatingActionButton deleteBtn, editBtn;
    FloatingActionMenu floatMenu;
    String uid;
    String key;
    String imageUrl = "";

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
        floatMenu = findViewById(R.id.floatMenu);
        detailNumber = findViewById(R.id.detailNumber);
//        FeeSwitch = findViewById(R.id.detailFee);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailDesc.setText(bundle.getInt("Number"));
            detailDest.setText(bundle.getString("Destination"));
            detailTitle.setText(bundle.getString("Title"));
            imageUrl = bundle.getString("Image");
            uid = bundle.getString("uid");
            key = bundle.getString("Key");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

//            if (SharedPrefsUtils.getUid(this).equals(uid)) {
//               floatMenu.setVisibility(View.VISIBLE);
//            } else if (SharedPrefsUtils.getUid(this) == null) {
//                floatMenu.setVisibility(View.GONE);
//            } else {
//                floatMenu.setVisibility(View.GONE);
//            }
        }
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeFragment.class));
                        finish();
                    }
                });
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
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }
}