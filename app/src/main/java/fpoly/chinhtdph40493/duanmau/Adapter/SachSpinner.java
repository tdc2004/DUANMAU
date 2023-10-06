package fpoly.chinhtdph40493.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.myapplication.R;

public class SachSpinner extends BaseAdapter {
    ArrayList<Sach> list;
    Context context;
    TextView tv_maSach,tv_tenSach;

    public SachSpinner(ArrayList<Sach> list, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.spiner_sach,parent,false);
        tv_maSach = view.findViewById(R.id.tv_maSach);
        tv_tenSach = view.findViewById(R.id.tv_tenSach);
        Sach sach = list.get(position);
        tv_tenSach.setText(sach.getTenSach());
        tv_maSach.setText(sach.getMaSach()+"");
        return view;
    }
}
