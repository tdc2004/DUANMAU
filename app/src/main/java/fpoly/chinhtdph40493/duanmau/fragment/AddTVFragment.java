package fpoly.chinhtdph40493.duanmau.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.chinhtdph40493.duanmau.DAO.ThuThuDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.ThuThu;
import fpoly.chinhtdph40493.myapplication.R;


public class AddTVFragment extends Fragment {
    TextInputEditText edt_tk,edt_mk,edt_ten;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_t_v, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_tk = view.findViewById(R.id.edt_tenDN);
        edt_ten = view.findViewById(R.id.edt_hoTen_add);
        edt_mk = view.findViewById(R.id.edtPassword_Add);
        Button button_add = view.findViewById(R.id.btnSave_addTT);
        Button button_huy = view.findViewById(R.id.btnCancle_addTT);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String tk = edt_tk.getText().toString();
                    String mk = edt_mk.getText().toString();
                    String hoTen = edt_ten.getText().toString();
                    ThuThu thuThu = new ThuThu(tk,hoTen,mk);
                    ThuThuDao thuThuDao = new ThuThuDao(new DBHelper(getContext()),getContext());
                    boolean check = thuThuDao.insertTT(thuThu);
                    if (check){
                        Toast.makeText(getContext(), "Thêm Thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        button_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_ten.setText("");
                edt_mk.setText("");
                edt_tk.setText("");
            }
        });
    }

    private boolean validate() {
        if (edt_tk.getText().toString().equals("") || edt_mk.getText().toString().equals("") || edt_ten.getText().toString().equals("")){
            Toast.makeText(getContext(), "Không được bỏ trống!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        ThuThuDao thuThuDao = new ThuThuDao(new DBHelper(getContext()),getContext());
        if (thuThuDao.checkUser(edt_tk.getText().toString())) {
            Toast.makeText(getContext(), "Tài Khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}