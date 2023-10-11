package fpoly.chinhtdph40493.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import fpoly.chinhtdph40493.duanmau.fragment.AddTVFragment;
import fpoly.chinhtdph40493.duanmau.fragment.DoanhThuFragment;
import fpoly.chinhtdph40493.duanmau.fragment.DoiMKFragment;
import fpoly.chinhtdph40493.duanmau.fragment.LoaiSachFragment;
import fpoly.chinhtdph40493.duanmau.fragment.PhieuMuonFragment;
import fpoly.chinhtdph40493.duanmau.fragment.SachFragment;
import fpoly.chinhtdph40493.duanmau.fragment.ThanhVienFragment;
import fpoly.chinhtdph40493.duanmau.fragment.Top10Fragment;
import fpoly.chinhtdph40493.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView view = findViewById(R.id.navigation_view);
        // set toolbar thay cho ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View header = view.getHeaderView(0);
        TextView tv_user = header.findViewById(R.id.tvTen);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        tv_user.setText("Welcome "+user);
        if (!user.equalsIgnoreCase("admin")){
            view.getMenu().findItem(R.id.nv_addUser).setVisible(false);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open_name,R.string.close_name);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new PhieuMuonFragment()).commit();
        getSupportActionBar().setTitle("Quản Lý Phiếu Mượn");
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = item.getItemId();
                if (position == R.id.nv_quanLyPhieuMuon){
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new PhieuMuonFragment()).commit();
                    getSupportActionBar().setTitle("Quản Lý Phiếu Mượn");
                    drawerLayout.close();
                }else if (position == R.id.nv_quanLyLS){
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new LoaiSachFragment()).commit();
                    getSupportActionBar().setTitle("Quản Lý Loại Sách");
                    drawerLayout.close();
                } else if (position == R.id.nv_quanLyS) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new SachFragment()).commit();
                    getSupportActionBar().setTitle("Quản Lý Sách");
                    drawerLayout.close();
                } else if (position == R.id.nv_quanLyTV) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new ThanhVienFragment()).commit();
                    getSupportActionBar().setTitle("Quản Lý Thành Viên");
                    drawerLayout.close();
                } else if(position == R.id.nv_doiMK){
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new DoiMKFragment()).commit();
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                    drawerLayout.close();
                } else if (position == R.id.nv_top10) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new Top10Fragment()).commit();
                    getSupportActionBar().setTitle("Top 10 Sách mượn nhiều nhất");
                    drawerLayout.close();
                } else if (position == R.id.nv_doanhThu) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new DoanhThuFragment()).commit();
                    getSupportActionBar().setTitle("Doanh Thu");
                    drawerLayout.close();
                } else if (position == R.id.nv_addUser) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new AddTVFragment()).commit();
                    getSupportActionBar().setTitle("Thêm Thành Viên");
                    drawerLayout.close();
                } else if (position == R.id.nv_dangXuat) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                return true;
            }
        });
    }
}