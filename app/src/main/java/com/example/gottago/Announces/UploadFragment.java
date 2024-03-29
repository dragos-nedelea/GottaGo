package com.example.gottago.Announces;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.gottago.MainActivity;
import com.example.gottago.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class UploadFragment extends Fragment {

    ImageView uploadImage, backBtn;
    Button uploadSaveBtn;
    EditText uploadTopic, uploadNumber, uploadDest, uploadStart, uploadDesc;
    String title, desc, dest, start, userName;
    Switch FeeSwitch;
    String imageURL;
    Uri uri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload, container, false);

        uploadImage = v.findViewById(R.id.uploadImage);
        uploadTopic = v.findViewById(R.id.uploadTopic);
        uploadDest = v.findViewById(R.id.uploadDest);
        uploadStart = v.findViewById(R.id.uploadStart);
        uploadDesc = v.findViewById(R.id.uploadDesc);
        uploadSaveBtn = v.findViewById(R.id.uploadSaveBtn);
        uploadNumber = v.findViewById(R.id.uploadNumber);
        FeeSwitch = v.findViewById(R.id.FeeSwitch);
        uploadNumber = v.findViewById(R.id.uploadNumber);
        backBtn = v.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

//    Pick an imane from gallery
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        }else {
                            Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                            // Set the default image from drawable
                            uploadImage.setImageResource(R.drawable.default_image);
                        }
                    }
                }

        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        uploadSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return v;
    }

    public void saveData() {

        if (uri == null) {
            // Set default image from Firebase Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images/default_image.jpg");
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imageURL = uri.toString();
                    uploadData();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed to retrieve default image", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            dialog.show();
            }
        });
    }
    public void uploadData() {

        // Get the current time
        long timestamp = System.currentTimeMillis();

        title = uploadTopic.getText().toString();
        desc = uploadDesc.getText().toString();
        dest = uploadDest.getText().toString();
        start = uploadStart.getText().toString();
        int num = Integer.parseInt(uploadNumber.getText().toString());
        boolean isForFree = FeeSwitch.isChecked();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();
            int index = email.indexOf("@");
            String username = email.substring(0, index);
            userName = username;
        }




        DataClass dataClass = new DataClass(title, num, desc, dest, start, imageURL, uid, isForFree, timestamp, userName);

        // Add a new key to the database reference
        String Key = FirebaseDatabase.getInstance().getReference("Android Tutorials").push().getKey();

        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(Key)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}