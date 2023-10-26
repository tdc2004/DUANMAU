package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fpoly.chinhtdph40493.duanmau.DAO.LoaiSachDao;
import fpoly.chinhtdph40493.duanmau.DAO.PhieuMuonDao;
import fpoly.chinhtdph40493.duanmau.DAO.SachDao;
import fpoly.chinhtdph40493.duanmau.DAO.ThanhVienDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.PhieuMuon;
import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.ThanhVien;
import fpoly.chinhtdph40493.myapplication.R;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonHolder> {
    ArrayList<PhieuMuon> list;
    Context context;
    PhieuMuonDao dao;
    ThanhVienDao thanhVienDao;
    SachDao sachDao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int maThanhVien, maSach;
    ArrayList<ThanhVien> thanhViens;
    ArrayList<Sach> saches;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PhieuMuonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pm, parent, false);
        return new PhieuMuonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        dao = new PhieuMuonDao(new DBHelper(context), context);
        thanhVienDao = new ThanhVienDao(new DBHelper(context), context);
        sachDao = new SachDao(new DBHelper(context), context);
        holder.tv_maPhieu.setText("Mã phiếu mượn: " + phieuMuon.getMaPM());
        ThanhVien thanhVien = thanhVienDao.getID(phieuMuon.getMaTV());
        holder.tv_tenTV.setText("Tên thành viên: " + thanhVien.getHoTen());
        Sach sach = sachDao.getID(phieuMuon.getMaSach());
        holder.tv_tenSach.setText("Tên sách: " + sach.getTenSach());
        holder.tv_giaThue.setText("Giá thuê: " + sach.getGiaThue());
        holder.tv_date.setText("Ngày thuê: " + phieuMuon.getNgay());
        holder.tv_gioThue.setText("Giờ Thuê: "+phieuMuon.getGio());

        if (phieuMuon.getTraSach() == 0) {
            holder.tv_trangThai.setTextColor(Color.BLUE);
            holder.tv_trangThai.setText("Đã Trả Sách");
        } else {
            holder.tv_trangThai.setText("Chưa Trả Sách");
            holder.tv_trangThai.setTextColor(Color.RED);
        }

        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.create();
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(holder.getAdapterPosition()).getMaPM();
                        boolean check = dao.deletePM(id);
                        if (check) {
                            list.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.item_pm_add_update, null, false);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_title.setText("UPDATE");
                Spinner sp_tv, sp_tenSach;
                sp_tv = view.findViewById(R.id.sp_thanhVien);
                sp_tenSach = view.findViewById(R.id.sp_tenSach);
                CheckBox checkBox = view.findViewById(R.id.chk_trangThai);
                if (phieuMuon.getTraSach() == 0) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                thanhViens = new ArrayList<>();
                thanhViens = thanhVienDao.getAll();
                ThanhVienSpinner adapter = new ThanhVienSpinner(thanhViens, context);
                sp_tv.setAdapter(adapter);
                sp_tv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maThanhVien = thanhViens.get(position).getMaTV();
                        Toast.makeText(context, "Chọn: " + thanhViens.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                saches = new ArrayList<>();
                saches = sachDao.getAll();
                SachSpinner sachSpinner = new SachSpinner(saches, context);
                sp_tenSach.setAdapter(sachSpinner);
                sp_tenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maSach = saches.get(position).getMaSach();
                        Toast.makeText(context, "Chọn: " + saches.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                for (int i = 0; i < thanhViens.size(); i++) {
                    if (thanhViens.get(i).getMaTV() == phieuMuon.getMaTV()) {
                        sp_tv.setSelection(i);
                        break;
                    }
                }
                for (int i = 0; i < saches.size(); i++) {
                    if (saches.get(i).getMaSach() == phieuMuon.getMaSach()) {
                        sp_tenSach.setSelection(i);
                        break;
                    }
                }
                Button btn_update = view.findViewById(R.id.btn_pm);
                btn_update.setText("UPDATE");
                view.findViewById(R.id.btn_pm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date date = new Date();
                        String ngay = sdf.format(date);
                        int trangThai;
                        if (checkBox.isChecked()) {
                            trangThai = 0;
                        } else {
                            trangThai = 1;
                        }
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        String gio = hour+":"+minute;
                        PhieuMuon phieuMuon1 = new PhieuMuon(phieuMuon.getMaPM(), phieuMuon.getMaTT(), maThanhVien, maSach, phieuMuon.getTienThue(), ngay, trangThai,gio);
                        boolean check = dao.updatePM(phieuMuon1);
                        if (check) {
                            list.set(position, phieuMuon1);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                view.findViewById(R.id.btn_huy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhieuMuonHolder extends RecyclerView.ViewHolder {
        TextView tv_maPhieu, tv_tenTV, tv_tenSach, tv_giaThue, tv_trangThai, tv_date,tv_gioThue;
        ImageView btn_xoa;

        public PhieuMuonHolder(@NonNull View itemView) {
            super(itemView);
            tv_maPhieu = itemView.findViewById(R.id.tv_maPhieu);
            tv_tenTV = itemView.findViewById(R.id.tv_tenThanhVien);
            tv_tenSach = itemView.findViewById(R.id.tv_tenSach);
            tv_giaThue = itemView.findViewById(R.id.tv_giaThue);
            tv_trangThai = itemView.findViewById(R.id.tv_trangThai);
            tv_date = itemView.findViewById(R.id.tv_date);
            btn_xoa = itemView.findViewById(R.id.btn_xoa);
            tv_gioThue = itemView.findViewById(R.id.tv_gioThue);
        }
    }
}
