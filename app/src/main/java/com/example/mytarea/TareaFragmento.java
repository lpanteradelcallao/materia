package com.example.mytarea;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
    public class TareaFragmento extends Fragment {

        DatabaseReference databaseReference;
        ValueEventListener eventListener;
        RecyclerView recyclerView;
        List<DataClass2> dataList;
        MyAdapter2 adapter;
        SearchView searchView;
        FloatingActionButton fab;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_tarea1, container, false);

            recyclerView = view.findViewById(R.id.recyclerView);
            fab = view.findViewById(R.id.fab);
            searchView = view.findViewById(R.id.search);
            searchView.clearFocus();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 1);
            recyclerView.setLayoutManager(gridLayoutManager);

            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            dataList = new ArrayList<>();
            adapter = new MyAdapter2(requireActivity(), dataList);
            recyclerView.setAdapter(adapter);

            databaseReference = FirebaseDatabase.getInstance().getReference("lista2");
            dialog.show();

            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataList.clear();
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        DataClass2 dataClass2 = itemSnapshot.getValue(DataClass2.class);

                        dataList.add(dataClass2);
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
                    return true;
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(requireActivity(), UploadActivity2.class);
                    startActivity(intent);
                }
            });

            return view;
        }

        public void searchList(String text) {
            ArrayList<DataClass2> searchList = new ArrayList<>();
            for (DataClass2 dataClass2 : dataList) {
                if (dataClass2.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataClass2);
                }
            }
            adapter.searchDataList(searchList);
        }
    }