package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.Top;
import fpoly.chinhtdph40493.myapplication.R;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.Top10ViewHolder> {
    ArrayList<Sach> list;
    Context context;

    public Top10Adapter(ArrayList<Sach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Top10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top,parent,false);
        return new Top10ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10ViewHolder holder, int position) {
        Sach sach = list.get(position);
        holder.tv_tenSach.setText("Tên Sách: "+sach.getTenSach());
        holder.tv_soLuong.setText("Số Lượng: "+sach.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Top10ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenSach,tv_soLuong;
        public Top10ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenSach = itemView.findViewById(R.id.tv_tenSach);
            tv_soLuong = itemView.findViewById(R.id.tv_soLuong);
        }
    }
}
