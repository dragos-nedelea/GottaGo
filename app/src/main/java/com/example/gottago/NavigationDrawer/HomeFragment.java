package com.example.gottago.NavigationDrawer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gottago.Announces.DataClass;
import com.example.gottago.Announces.MyAdapter;
import com.example.gottago.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<DataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    MyAdapter adapter;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewHome);
        searchView = v.findViewById(R.id.search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager ( getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager (gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog. Builder ( getActivity().getApplicationContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
//        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(getActivity().getApplicationContext(), dataList);
        recyclerView.setAdapter (adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference ( "Android Tutorials");
//        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return false;
            }
        });

        return v;
    }

    public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for(DataClass dataClass: dataList){
            if(dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            } else if(dataClass.getDataDest().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        // Load the data and update the UI

    }

}