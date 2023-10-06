package fpoly.chinhtdph40493.duanmau.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.chinhtdph40493.duanmau.DAO.ThuThuDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.ThuThu;
import fpoly.chinhtdph40493.myapplication.R;

public class DoiMKFragment extends Fragment {
    ThuThuDao dao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doi_m_k, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText edt_old = view.findViewById(R.id.edt_old_password);
        TextInputEditText edt_new = view.findViewById(R.id.edt_forget_password);
        TextInputEditText edt_reNew = view.findViewById(R.id.edt_refresh_password);
        Button btn_save = view.findViewById(R.id.btnSubmit);
        Button btn_cancel = view.findViewById(R.id.btnCancel);
        dao = new ThuThuDao(new DBHelper(getContext()),getContext());
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_old.setText("");
                edt_new.setText("");
                edt_reNew.setText("");
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences spf = getContext().getSharedPreferences("File_User", Context.MODE_PRIVATE);
                String user = spf.getString("USERNAME","");
                String passold = spf.getString("PASSWORD","");

                if (edt_new.getText().toString().equals("") || edt_old.getText().toString().equals("") || edt_reNew.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Không đc bỏ trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!passold.equals(edt_old.getText().toString())){
                    Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!edt_new.getText().toString().equals(edt_reNew.getText().toString())){
                    Toast.makeText(getContext(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                String pass = edt_reNew.getText().toString();
                boolean check = dao.updatePass(user,passold,pass);
                if (check){
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("PASSWORD", pass);
                    editor.apply();
                }else{
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();

                    Log.d("TAG", "Old Password: " + passold);
                    Log.d("TAG", "New Password: " + pass);
                }
            }
        });
    }
}