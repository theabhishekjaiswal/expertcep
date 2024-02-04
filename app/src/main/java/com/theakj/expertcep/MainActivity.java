package com.theakj.expertcep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<DataClass> dataList;
    MyAdapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore fs;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fs=FirebaseFirestore.getInstance();
          recyclerView = findViewById(R.id.recyclerView);
//        searchView = findViewById(R.id.search);
//        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);
//        databaseReference = FirebaseDatabase.getInstance().getReference("Crop Problem Details");
        dialog.show();
        loadData();
//        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dataList.clear();
//                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
//                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
//                    dataClass.setKey(itemSnapshot.getKey());
//                    dataList.add(dataClass);
//                }
//                adapter.notifyDataSetChanged();
//                dialog.dismiss();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                dialog.dismiss();
//            }
//        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchList(newText);
//                return true;
//            }
//        });
    }

    private void loadData() {
        fs.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dataList.clear();
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                            dataList.clear();
//                            switch (dc.getType()) {
//                                case ADDED:
//                                case MODIFIED:
//                                case REMOVED:
//                                    dataList.add(dc.getDocument().toObject(DataClass.class));
//                                    break;
//                            }
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                dataList.add(dc.getDocument().toObject(DataClass.class));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
    }
//    public void searchList(String text){
//        ArrayList<DataClass> searchList = new ArrayList<>();
//        for (DataClass dataClass: dataList){
//            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
//                searchList.add(dataClass);
//            }
//        }
//        adapter.searchDataList(searchList);
//    }
}