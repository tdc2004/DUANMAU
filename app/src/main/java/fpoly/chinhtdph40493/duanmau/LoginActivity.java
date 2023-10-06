package fpoly.chinhtdph40493.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.chinhtdph40493.duanmau.DAO.ThuThuDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    ThuThuDao dao;
    TextInputEditText edtUser, edtPass;
    CheckBox chkRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser = findViewById(R.id.userName);
        edtPass = findViewById(R.id.passWord);
        Button btn_login = findViewById(R.id.btnLogin);
        Button btn_cancel = findViewById(R.id.btnHuy);
        chkRemember = findViewById(R.id.chkRemember);
        dao = new ThuThuDao(new DBHelper(this), this);

        // doc user, pass trong SharedPreferences
        SharedPreferences spf = getSharedPreferences("File_User", MODE_PRIVATE);
        edtUser.setText(spf.getString("USERNAME", ""));
        edtPass.setText(spf.getString("PASSWORD", ""));
        chkRemember.setChecked(spf.getBoolean("REMEMBER", false));

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUser.setText("");
                edtPass.setText("");
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }

    private void checkLogin() {
        String strUser = edtUser.getText().toString();
        String strPass = edtPass.getText().toString();
        if (strUser.equals("") && strPass.equals("")) {
            Toast.makeText(this, "Tài khoản và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dao.checkLogin(strUser, strPass) || (strUser.equals("admin") && strPass.equals("123"))) {
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            rememberUser(strUser,strPass,chkRemember.isChecked());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("user",strUser);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    private void rememberUser(String user, String pass, boolean status) {
        SharedPreferences spf = getSharedPreferences("File_User",MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        if (!status){
            // xóa tình trạng lưu trước đó
            editor.clear();
        }else{
            // lưu lại toàn bộ
            editor.putString("USERNAME",user);
            editor.putString("PASSWORD",pass);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }
}