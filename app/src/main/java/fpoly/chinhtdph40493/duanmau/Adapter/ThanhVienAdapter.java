package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.DAO.ThanhVienDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.ThanhVien;
import fpoly.chinhtdph40493.myapplication.R;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.TVHolder> {
    ArrayList<ThanhVien> list;
    Context context;
    ThanhVienDao dao;
    TextInputEditText edt_ten, edt_namSinh,edt_stk;

    public ThanhVienAdapter(ArrayList<ThanhVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv, parent, false);
        return new TVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        holder.tv_namSinh.setText("Năm Sinh: " + thanhVien.getNamSinh());
        holder.tv_maTV.setText("Mã Thành Viên: " + thanhVien.getMaTV());
        holder.tv_name.setText("Họ Tên: " + thanhVien.getHoTen());
        holder.tv_STK.setText("STK: "+thanhVien.getStk());
        if (thanhVien.getStk() % 5 == 0){
            holder.tv_STK.setTextColor(Color.RED);
            holder.tv_STK.setTextSize(25);
        }
        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.create();
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
                builder.setIcon(R.drawable.baseline_warning_24);
                dao = new ThanhVienDao(new DBHelper(context), context);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(holder.getAdapterPosition()).getMaTV();
                        try {
                            boolean check = dao.deleteTV(id);
                            if (check) {
                                list.remove(position);
                                notifyItemRemoved(holder.getAdapterPosition());
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
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
                View view1 = LayoutInflater.from(context).inflate(R.layout.item_tv_add_update, null, false);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                dao = new ThanhVienDao(new DBHelper(context), context);
                edt_ten = view1.findViewById(R.id.edt_hoTen);
                edt_namSinh = view1.findViewById(R.id.edt_namSinh);
                edt_stk = view1.findViewById(R.id.edt_stk);
                edt_ten.setText(thanhVien.getHoTen());
                edt_namSinh.setText(thanhVien.getNamSinh());
                edt_stk.setText(thanhVien.getStk()+"");
                TextView textView = view1.findViewById(R.id.tv_title);
                textView.setText("Sửa Thành Viên");
                Button button = view1.findViewById(R.id.btn_tv);
                button.setText("Update");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hoTen = edt_ten.getText().toString();
                        String namSinh = edt_namSinh.getText().toString();
                        int stk = Integer.parseInt(edt_stk.getText().toString());
                        ThanhVien thanhVien1 = new ThanhVien(thanhVien.getMaTV(), hoTen, namSinh,stk);
                        boolean check = dao.updateTV(thanhVien1);
                        if (check) {
                            list.set(position, thanhVien1);
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                view1.findViewById(R.id.btn_huy).setOnClickListener(new View.OnClickListener() {
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

    public class TVHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_namSinh, tv_maTV,tv_STK;
        ImageView btn_xoa;

        public TVHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_hoTen);
            tv_maTV = itemView.findViewById(R.id.tv_maTV);
            tv_namSinh = itemView.findViewById(R.id.tv_namSinh);
            btn_xoa = itemView.findViewById(R.id.btn_xoa);
            tv_STK = itemView.findViewById(R.id.tv_STK);
        }
    }
}
