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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Adapter.ThanhVienAdapter;
import fpoly.chinhtdph40493.duanmau.DAO.ThanhVienDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.ThanhVien;
import fpoly.chinhtdph40493.myapplication.R;

public class ThanhVienFragment extends Fragment {
    ThanhVienDao dao;
    ArrayList<ThanhVien> list = new ArrayList<>();
    TextInputEditText edt_ten, edt_namSinh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rclView);
        FloatingActionButton button_add = view.findViewById(R.id.btn_add);
        dao = new ThanhVienDao(new DBHelper(getContext()), getContext());
        list = dao.getAll();
        ThanhVienAdapter adapter = new ThanhVienAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_tv_add_update, null, false);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                edt_ten = view1.findViewById(R.id.edt_hoTen);
                edt_namSinh = view1.findViewById(R.id.edt_namSinh);
                TextView textView = view1.findViewById(R.id.tv_title);
                textView.setText("Thêm Thành Viên");
                Button button = view1.findViewById(R.id.btn_tv);
                Button btn_huy = view1.findViewById(R.id.btn_huy);
                button.setText("Add");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate()) {
                            String hoTen = edt_ten.getText().toString();
                            String namSinh = edt_namSinh.getText().toString();
                            ThanhVien thanhVien = new ThanhVien(getId(), hoTen, namSinh);
                            boolean check = dao.insertTV(thanhVien);
                            if (check) {
                                list.add(thanhVien);
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
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