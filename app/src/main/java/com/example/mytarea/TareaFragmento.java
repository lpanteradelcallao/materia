package com.example.mytarea;
import androidx.appcompat.widget.SearchView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;

public class TareaFragmento extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<DataClass> dataList;
    private int[] imageList;
    private String[] titleList;
    private String[] descList;
    private int[] detailImageList;
    private SearchView searchView;
    private ArrayList<DataClass> searchList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea1, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        imageList = new int[]{
                R.drawable.ic_list,
                R.drawable.ic_checkbox,
                R.drawable.ic_image,
                R.drawable.ic_toggle,
                R.drawable.ic_date,
                R.drawable.ic_rating,
                R.drawable.ic_time,
                R.drawable.ic_text,
                R.drawable.ic_edit,
                R.drawable.ic_camera
        };

        titleList = new String[]{
                "ListView",
                "CheckBox",
                "ImageView",
                "Toggle Switch",
                "Date Picker",
                "Rating Bar",
                "Time Picker",
                "TextView",
                "EditText",
                "Camera"
        };

        descList = new String[]{
                getString(R.string.listview),
                getString(R.string.checkbox),
                getString(R.string.imageview),
                getString(R.string.toggle),
                getString(R.string.date),
                getString(R.string.rating),
                getString(R.string.time),
                getString(R.string.textview),
                getString(R.string.edit),
                getString(R.string.camera)
        };

        detailImageList = new int[]{
                R.drawable.list_detail,
                R.drawable.check_detail,
                R.drawable.image_detail,
                R.drawable.toggle_detail,
                R.drawable.date_detail,
                R.drawable.rating_detail,
                R.drawable.time_detail,
                R.drawable.text_detail,
                R.drawable.edit_detail,
                R.drawable.camera_detail
        };

        dataList = new ArrayList<>();
        searchList = new ArrayList<>();
        getData();

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList.clear();
                String searchText = newText.toLowerCase(Locale.getDefault());
                if (!searchText.isEmpty()) {
                    for (DataClass data : dataList) {
                        if (data.getDataTitle().toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(data);
                        }
                    }
                    if (recyclerView.getAdapter() != null) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    searchList.clear();
                    searchList.addAll(dataList);
                    if (recyclerView.getAdapter() != null) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
                return false;
            }
        });

        AdapterClass myAdapter = new AdapterClass(searchList);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClick(new AdapterClass.OnItemClickListener() {
            @Override
            public void onItemClick(DataClass item) {
                Intent intent = new Intent(requireContext(), DetailActivity.class);
                intent.putExtra("android", item);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getData() {
        for (int i = 0; i < imageList.length; i++) {
            DataClass dataClass = new DataClass(imageList[i], titleList[i], descList[i], detailImageList[i]);
            dataList.add(dataClass);
        }
        searchList.addAll(dataList);
        recyclerView.setAdapter(new AdapterClass(searchList));
    }
}
