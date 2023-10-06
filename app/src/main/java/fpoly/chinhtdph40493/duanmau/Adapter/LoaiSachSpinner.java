package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Model.LoaiSach;
import fpoly.chinhtdph40493.myapplication.R;

public class LoaiSachSpinner extends BaseAdapter {
    ArrayList<LoaiSach> list;
    Context context;
    TextView maLoai,ten;

    public LoaiSachSpinner(ArrayList<LoaiSach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spiner_ls,parent,false);
        maLoai = view.findViewById(R.id.tv_maLoai);
        ten = view.findViewById(R.id.tv_ten);
        LoaiSach loaiSach = list.get(position);
        maLoai.setText(loaiSach.getMaLoai()+"");
        ten.setText(loaiSach.getTenLoai());
        return view;
    }
}
