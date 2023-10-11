package fpoly.chinhtdph40493.duanmau.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import fpoly.chinhtdph40493.duanmau.Adapter.PhieuMuonAdapter;
import fpoly.chinhtdph40493.duanmau.Adapter.PhieuMuonAdapter;
import fpoly.chinhtdph40493.duanmau.Adapter.SachSpinner;
import fpoly.chinhtdph40493.duanmau.Adapter.ThanhVienSpinner;
import fpoly.chinhtdph40493.duanmau.DAO.PhieuMuonDao;
import fpoly.chinhtdph40493.duanmau.DAO.SachDao;
import fpoly.chinhtdph40493.duanmau.DAO.ThanhVienDao;
import fpoly.chinhtdph40493.duanmau.DAO.ThuThuDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.PhieuMuon;
import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.ThanhVien;
import fpoly.chinhtdph40493.duanmau.Model.ThuThu;
import fpoly.chinhtdph40493.myapplication.R;


public class PhieuMuonFragment extends Fragment {
    FloatingActionButton btn_add;
    PhieuMuonDao dao;
    ArrayList<PhieuMuon> list = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<ThanhVien> thanhViens = new ArrayList<>();
    ArrayList<Sach> saches = new ArrayList<>();
    ThanhVienDao thanhVienDao;
    SachDao sachDao;
    int maThanhVien, maSach;
    Spinner sp_sach, sp_thanhVien;
    CheckBox chk_trangThai;
    int position;
    PhieuMuonAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_add = view.findViewById(R.id.btn_add);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        dao = new PhieuMuonDao(new DBHelper(getContext()), getContext());
        list = dao.getAll();
        adapter = new PhieuMuonAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });
    }
    private void showDialogAdd(){
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_pm_add_update, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        thanhVienDao = new ThanhVienDao(new DBHelper(getContext()), getContext());
        thanhViens = thanhVienDao.getAll();
        ThanhVienSpinner adapterTV = new ThanhVienSpinner(thanhViens, getContext());
        sp_thanhVien = view1.findViewById(R.id.sp_thanhVien);
        sp_thanhVien.setAdapter(adapterTV);
        sp_thanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = thanhViens.get(position).getMaTV();
                Toast.makeText(getContext(), "Chọn: " + thanhViens.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sachDao = new SachDao(new DBHelper(getContext()), getContext());
        saches = sachDao.getAll();
        SachSpinner sachAdapter = new SachSpinner(saches, getContext());
        sp_sach = view1.findViewById(R.id.sp_tenSach);
        sp_sach.setAdapter(sachAdapter);
        sp_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = saches.get(position).getMaSach();
                Toast.makeText(getContext(), "Chọn: " + saches.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn_them = view1.findViewById(R.id.btn_pm);
        btn_them.setText("ADD");
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Sach sach = sachDao.getID(maSach);
                    Date date = new Date();
                    String ngay = sdf.format(date);
                    PhieuMuon phieuMuon = new PhieuMuon();
                    int trangThai;
                    chk_trangThai = view1.findViewById(R.id.chk_trangThai);
                    if (chk_trangThai.isChecked()){
                        trangThai = 0;
                    }else {
                        trangThai=1;
                    }
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("File_User", Context.MODE_PRIVATE);
                    String maTT = sharedPreferences.getString("USERNAME","");
                    PhieuMuon phieuMuon1 = new PhieuMuon(getId(), maTT, maThanhVien, maSach, sach.getGiaThue(), ngay, trangThai);
                    boolean check = dao.insertPM(phieuMuon1);
                    if (check) {
                        list.add(phieuMuon1);
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
            }
        });
        Button btn_huy = view1.findViewById(R.id.btn_huy);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}