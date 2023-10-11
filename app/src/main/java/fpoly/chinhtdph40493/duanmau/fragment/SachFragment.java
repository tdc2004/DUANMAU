package fpoly.chinhtdph40493.duanmau.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Adapter.LoaiSachSpinner;
import fpoly.chinhtdph40493.duanmau.Adapter.SachAdapter;
import fpoly.chinhtdph40493.duanmau.DAO.LoaiSachDao;
import fpoly.chinhtdph40493.duanmau.DAO.SachDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.LoaiSach;
import fpoly.chinhtdph40493.myapplication.R;

public class SachFragment extends Fragment {
    SachDao dao;
    ArrayList<Sach> list = new ArrayList<>();

    TextInputEditText edt_ten, edt_giaThue;
    Spinner sp_loaiSach;
    Button btn_ad, btn_huy;
    int maLoaiSach;
    ArrayList<LoaiSach> list1 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btn_add = view.findViewById(R.id.btn_addSach);
        dao = new SachDao(new DBHelper(getContext()), getContext());
        list = dao.getAll();
        SachAdapter adapter = new SachAdapter(list, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_sach_add_update, null, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                edt_ten = view1.findViewById(R.id.edt_tenSach);
                edt_giaThue = view1.findViewById(R.id.edt_giaThue);
                sp_loaiSach = view1.findViewById(R.id.sp_loaiSach);
                btn_ad = view1.findViewById(R.id.btn_sach);
                btn_ad.setText("ADD");
                btn_huy = view1.findViewById(R.id.btn_huy);
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                LoaiSachDao sachDao = new LoaiSachDao(new DBHelper(getContext()),getContext());
                list1 = sachDao.getAll();
                LoaiSachSpinner adapter1 = new LoaiSachSpinner(list1, getContext());
                sp_loaiSach.setAdapter(adapter1);
                sp_loaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLoaiSach = list1.get(position).getMaLoai();
                        Toast.makeText(getContext(), "Chọn: " + list1.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btn_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (validate()){
                           String ten = edt_ten.getText().toString();
                           int gia = Integer.parseInt(edt_giaThue.getText().toString());
                           Sach sach = new Sach(getId(), ten, gia, maLoaiSach);
                           dao = new SachDao(new DBHelper(getContext()), getContext());
                           boolean check = dao.insertSach(sach);
                           if (check){
                               list.add(sach);
                               adapter.notifyDataSetChanged();
                               alertDialog.dismiss();
                               Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                           }else{
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
        if (edt_ten.getText().toString().isEmpty() || edt_giaThue.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        int gia;
        try {
            gia = Integer.parseInt(edt_giaThue.getText().toString());
        }catch (Exception e){
            Toast.makeText(getContext(), "Giá phải là số", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}