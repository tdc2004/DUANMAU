package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.DAO.LoaiSachDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.LoaiSach;
import fpoly.chinhtdph40493.myapplication.R;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    ArrayList<LoaiSach> list;
    Context context;
    LoaiSachDao dao;

    public LoaiSachAdapter(ArrayList<LoaiSach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loai_sach, parent, false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        holder.tv_tenLoai.setText("Tên loại sách: " + loaiSach.getTenLoai()+"");
        holder.tv_maLoai.setText("Mã loại sách: " + loaiSach.getMaLoai());
        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.create();
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
                dao = new LoaiSachDao(new DBHelper(context), context);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(holder.getAdapterPosition()).getMaLoai();
                        try {
                            boolean check = dao.deleteTL(id);
                            if (check) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyItemRemoved(position);
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
                View view = LayoutInflater.from(context).inflate(R.layout.item_loai_sach_add_update,null,false);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_title.setText("UPDATE");
                TextInputEditText edt_tenLS = view.findViewById(R.id.edt_tenLoaiSach);
                edt_tenLS.setText(loaiSach.getTenLoai());
                Button btn_update,btn_huy;
                btn_update = view.findViewById(R.id.btn_loaiSach);
                btn_update.setText("UPDATE");
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenLS = edt_tenLS.getText().toString();
                        LoaiSach loaiSach1 = new LoaiSach(loaiSach.getMaLoai(), tenLS);
                        dao = new LoaiSachDao(new DBHelper(context),context);
                        boolean check = dao.updateTL(loaiSach1);
                        if (check){
                            list.set(position,loaiSach1);
                            alertDialog.dismiss();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btn_huy = view.findViewById(R.id.btn_huy);
                btn_huy.setOnClickListener(new View.OnClickListener() {
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

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView tv_maLoai, tv_tenLoai;
        ImageView btn_xoa;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maLoai = itemView.findViewById(R.id.tv_maLoaiSach);
            tv_tenLoai = itemView.findViewById(R.id.tv_tenLoai);
            btn_xoa = itemView.findViewById(R.id.btn_xoa);

        }
    }
}
