package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Model.ThanhVien;
import fpoly.chinhtdph40493.myapplication.R;

public class ThanhVienSpinner extends BaseAdapter {
    ArrayList<ThanhVien> list;
    Context context;
    TextView tv_ma,tv_ten;

    public ThanhVienSpinner(ArrayList<ThanhVien> list, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_tv,parent,false);
        tv_ma = view.findViewById(R.id.tv_maTV);
        tv_ten = view.findViewById(R.id.tv_tenTV);
        ThanhVien thanhVien = list.get(position);
        tv_ten.setText(thanhVien.getHoTen());
        tv_ma.setText(thanhVien.getMaTV()+"");
        return view;
    }
}
