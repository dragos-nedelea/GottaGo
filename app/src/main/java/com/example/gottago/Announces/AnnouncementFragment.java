package com.example.gottago.Announces;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gottago.NavigationDrawer.HomeFragment;
import com.example.gottago.R;
import com.example.gottago.SharedPrefs.SharedPrefsUtils;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnnouncementFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseRef;
    private List<DataClass> mAnnouncementList;

    TextView detailDesc, detailNumber, detailTitle, detailDest, detailOwner;

    FloatingActionButton deleteBtn, editBtn;
    String key;
    String imageUrl = "";
    private ValueEventListener mDBListener;
    private String mUid;
    String default_image_url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcement, container, false);

        // Inflate the activity_detail layout to get access to its views
//        View detailView = inflater.inflate(R.layout.activity_detail, null);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAnnouncementList = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext(), mAnnouncementList);
        mRecyclerView.setAdapter (adapter);

        mUid = SharedPrefsUtils.getUid(getActivity());
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Android Tutorials");
        Query query = mDatabaseRef.orderByChild("uid").equalTo(mUid);
        mDBListener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAnnouncementList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    DataClass announcement = postSnapshot.getValue(DataClass.class);
                    if (announcement.getUid().equals(mUid)) { // check item's uid with user's uid
                        mAnnouncementList.add(announcement);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Bundle extras = getArguments();
        if (extras != null) {
            key = extras.getString("key");
        }

//        deleteBtn = detailView.findViewById(R.id.deleteBtn);
//        editBtn = detailView.findViewById(R.id.editBtn);
//        detailTitle = detailView.findViewById(R.id.detailTitle);
//        detailNumber = detailView.findViewById(R.id.detailNumber);
//        detailDesc = detailView.findViewById(R.id.detailDesc);
//        detailDest = detailView.findViewById(R.id.detailDest);
        default_image_url = "gs://gottago-13352.appspot.com/Android Images/default_image.jpg";



//        Log.d(" MyApp", "Key: " + key);



//        // Set onClickListener for delete button
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//
////                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
////                builder.setCancelable(false);
////                builder.setView(R.layout.progress_layout);
////                final AlertDialog dialog = builder.create();
////                dialog.show();
//
//                if (key != null) {
//                    reference.child(key).removeValue();
//                }
//                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), HomeFragment.class);
//                startActivity(intent);
////                finish();
//
//            }
//        });
//
//
//        // Set onClickListener for edit button
//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Edit Announcement");
//                builder.setMessage("Are you sure you want to edit this announcement?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(getActivity(), UpdateActivity.class)
//                                .putExtra("Title", detailTitle.getText().toString())
//                                .putExtra("Number", Integer.parseInt(detailNumber.getText().toString()))
//                                .putExtra("Description", detailDesc.getText().toString())
//                                .putExtra("Destination", detailDest.getText().toString())
//                                .putExtra("Image", imageUrl)
//                                .putExtra("Key", key);
//
//                        startActivity(intent);
//                    }
//                });
//                builder.setNegativeButton("No", null);
//                builder.show();
//            }
//        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}

