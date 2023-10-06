package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PhieuMuonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pm,parent,false);
        return new PhieuMuonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        dao = new PhieuMuonDao(new DBHelper(context),context);
        thanhVienDao = new ThanhVienDao(new DBHelper(context),context);
        sachDao = new SachDao(new DBHelper(context),context);
        holder.tv_maPhieu.setText("Mã phiếu mượn: "+phieuMuon.getMaPM());
        ThanhVien thanhVien = thanhVienDao.getID(phieuMuon.getMaTV());
        holder.tv_tenTV.setText("Tên thành viên: "+thanhVien.getHoTen());
        Sach sach = sachDao.getID(phieuMuon.getMaSach());
        holder.tv_tenSach.setText("Tên sách: "+sach.getTenSach());
        holder.tv_giaThue.setText("Giá thuê: "+sach.getGiaThue());
        holder.tv_date.setText("Ngày thuê: "+phieuMuon.getNgay());
        Log.d("PhieuMuonAdapter", "PhieuMuon ID: " + phieuMuon.getTraSach());
        Log.d("PhieuMuonAdapter", "ThanhVien ID: " + thanhVien.getHoTen());
        Log.d("PhieuMuonAdapter", "Sach ID: " + sach.getMaSach());
        if (phieuMuon.getTraSach() == 0){
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
                        if (check){
                            list.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel",null).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhieuMuonHolder extends RecyclerView.ViewHolder{
        TextView tv_maPhieu,tv_tenTV,tv_tenSach,tv_giaThue,tv_trangThai,tv_date;
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
        }
    }
}
