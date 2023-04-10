package com.example.gottago.Announces;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gottago.R;
import com.example.gottago.SharedPrefsUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseRef;
    private List<DataClass> mAnnouncementList;
    private ValueEventListener mDBListener;
    private String mUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcement, container, false);

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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}

