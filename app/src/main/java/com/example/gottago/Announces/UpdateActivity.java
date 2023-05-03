package com.example.gottago.Announces;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gottago.MainActivity;
import com.example.gottago.NavigationDrawer.HomeFragment;
import com.example.gottago.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    ImageView updateImage;
    ImageButton backBtn;
    Button updateBtn;
    EditText updateDesc, updateNumber, updateDest, updateStart, updateTitle;
    Switch FeeSwitch;
    String title, desc, dest, start, userName;
    String imageUrl;
    String key, oldImageUrl;
    Uri uri;
    int num;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    boolean isForFree;
    boolean isImageSetByUser = false; // new variable to track if the image was set by the user or not

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Unsaved Changes")
                .setMessage("Are you sure you want to discard unsaved changes?")
                .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Discard changes and close activity
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // get the current user's name
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userName = currentUser.getDisplayName();
        }

        updateBtn = findViewById(R.id.updateSaveBtn);
        updateDesc = findViewById(R.id.updateDesc);
        updateDest = findViewById(R.id.updateDest);
        updateStart = findViewById(R.id.updateStart);
        updateImage = findViewById(R.id.updateImage);
        updateTitle = findViewById(R.id.updateTitle);
        updateNumber = findViewById(R.id.updateNumber);
        FeeSwitch = findViewById(R.id.FeeSwitch);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                            isImageSetByUser = true; // set the variable to true since the image was set by the user
                        } else {
                            Toast.makeText(UpdateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(UpdateActivity.this).load(bundle.getString("Image")).into(updateImage);
            updateTitle.setText(bundle.getString("Title"));
            updateDesc.setText(bundle.getString("Description"));
            updateNumber.setText(Integer.toString(bundle.getInt("Number")));
            updateDest.setText(bundle.getString("Destination"));
            updateStart.setText(bundle.getString("Start"));
            key = bundle.getString("Key");
            oldImageUrl = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials").child(key);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void saveData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();

        if (!isFinishing()) {
            dialog.show();
        }

        if(isImageSetByUser) {
            storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(uri.getLastPathSegment());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    updateData();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        } else {
            imageUrl = oldImageUrl;
            updateData();
            dialog.dismiss();
        }
    }

    public void updateData() {

        // Get the current time
        long timestamp = System.currentTimeMillis();

        title = updateTitle.getText().toString().trim();
        desc = updateDesc.getText().toString();
        dest = updateDest.getText().toString().trim();
        start = updateStart.getText().toString();
        num = Integer.parseInt(updateNumber.getText().toString());
        isForFree = FeeSwitch.isChecked();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();
            int index = email.indexOf("@");
            String username = email.substring(0, index);
            userName = username;
        }
        DataClass dataClass = new DataClass(title, num, desc, dest, start, imageUrl, uid, isForFree, timestamp, userName);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (imageUrl.equals(oldImageUrl)) {
                        // Image was not changed
                        Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Image was changed
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}