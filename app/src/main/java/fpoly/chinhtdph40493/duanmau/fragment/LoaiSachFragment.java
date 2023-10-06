package fpoly.chinhtdph40493.duanmau.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Adapter.LoaiSachAdapter;
import fpoly.chinhtdph40493.duanmau.DAO.LoaiSachDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.LoaiSach;
import fpoly.chinhtdph40493.myapplication.R;

public class LoaiSachFragment extends Fragment {
    LoaiSachDao dao;
    ArrayList<LoaiSach> list = new ArrayList<>();
    FloatingActionButton button;
    Button button_add,btn_huy;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        dao = new LoaiSachDao(new DBHelper(getContext()),getContext());
        list = dao.getAll();
        LoaiSachAdapter adapter = new LoaiSachAdapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        button = view.findViewById(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_loai_sach_add_update,null,false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view1);
                TextView title = view1.findViewById(R.id.tv_title);
                TextInputEditText edt_tenLoai = view1.findViewById(R.id.edt_tenLoaiSach);
                title.setText("Thêm Loại Sách");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                button_add = view1.findViewById(R.id.btn_loaiSach);
                button_add.setText("ADD");
                btn_huy = view1.findViewById(R.id.btn_huy);
                button_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate()){
                            String tenLoai = edt_tenLoai.getText().toString();
                            LoaiSach loaiSach = new LoaiSach(getId(),tenLoai);
                            boolean check = dao.insertTL(loaiSach);
                            if (check){
                                list.add(loaiSach);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });


    }

    private boolean validate() {
        return true;
    }
}