package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import fpoly.chinhtdph40493.duanmau.DAO.SachDao;
import fpoly.chinhtdph40493.duanmau.DAO.LoaiSachDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.LoaiSach;
import fpoly.chinhtdph40493.myapplication.R;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    ArrayList<Sach> list;
    Context context;
    LoaiSachDao loaiSachDao;
    SachDao sachDao;
    EditText edt_tenSach,edt_giaThue;
    Spinner sp_loaiSach;
    ArrayList<LoaiSach> list1 = new ArrayList<>();

    int maLoaiSach;
    public SachAdapter(ArrayList<Sach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sach,parent,false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = list.get(position);
        loaiSachDao = new LoaiSachDao(new DBHelper(context),context);
        holder.tv_ten.setText("Tên Sách: "+sach.getTenSach());
        holder.tv_ma.setText("Mã Sách: "+sach.getMaSach());
        holder.tv_giaThue.setText("Giá thuê: "+sach.getGiaThue());
        LoaiSach loaiSach = loaiSachDao.getID(sach.getMaLoai());
        holder.tv_loaiSach.setText("Tên Loại Sách: "+loaiSach.getTenLoai());
        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.create();
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
                builder.setIcon(R.drawable.baseline_warning_24);
                sachDao = new SachDao(new DBHelper(context),context);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(holder.getAdapterPosition()).getMaSach();
                        boolean check = sachDao.deleteSach(id);
                        if (check){
                            list.remove(position);
                            notifyItemRemoved(holder.getAdapterPosition());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.item_sach_add_update, null, false);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                TextView tv_title = view1.findViewById(R.id.tv_title);
                tv_title.setText("UPDATE");
                edt_tenSach = view1.findViewById(R.id.edt_tenSach);
                edt_giaThue = view1.findViewById(R.id.edt_giaThue);
                sp_loaiSach = view1.findViewById(R.id.sp_loaiSach);
                edt_tenSach.setText(sach.getTenSach());
                edt_giaThue.setText(sach.getGiaThue()+"");
                list1 = loaiSachDao.getAll();
                LoaiSachSpinner adapter = new LoaiSachSpinner(list1, context);
                sp_loaiSach.setAdapter(adapter);
                sp_loaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLoaiSach = list1.get(position).getMaLoai();
                        Toast.makeText(context, "Chọn: " + list1.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                for (int i = 0; i < list1.size(); i++) {
                    if (list1.get(i).getMaLoai() == sach.getMaLoai()) {
                        // Nếu tìm thấy, đặt vị trí của Spinner để hiển thị giá trị đó
                        sp_loaiSach.setSelection(i);
                        break; // Bạn có thể dừng vòng lặp ngay sau khi tìm thấy giá trị tương ứng
                    }
                }
                Button btn_sua ,btn_huy;
                btn_sua = view1.findViewById(R.id.btn_sach);
                btn_sua.setText("UPDATE");
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate()){
                            String ten = edt_tenSach.getText().toString();
                            int gia = Integer.parseInt(edt_giaThue.getText().toString());
                            Sach sach1 = new Sach(sach.getMaSach(), ten, gia, maLoaiSach);
                            sachDao = new SachDao(new DBHelper(context),context);
                            boolean check = sachDao.updateSach(sach1);
                            if (check){
                                list.set(position,sach1);
                                adapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                return true;
            }
        });
    }

    private boolean validate() {
        return true;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SachViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ma,tv_ten,tv_giaThue,tv_loaiSach;
        ImageView btn_xoa;
        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ma = itemView.findViewById(R.id.tv_maSach);
            tv_ten = itemView.findViewById(R.id.tv_tenSach);
            tv_giaThue = itemView.findViewById(R.id.tv_giaThue);
            tv_loaiSach = itemView.findViewById(R.id.tv_loaiSach);
            btn_xoa = itemView.findViewById(R.id.btn_xoa);
        }
    }
}
