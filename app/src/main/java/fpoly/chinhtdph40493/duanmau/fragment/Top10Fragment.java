package fpoly.chinhtdph40493.duanmau.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Adapter.Top10Adapter;
import fpoly.chinhtdph40493.duanmau.DAO.ThongKeDao;
import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.Top;
import fpoly.chinhtdph40493.myapplication.R;

public class Top10Fragment extends Fragment {
    ThongKeDao dao;
    ArrayList<Sach> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new ThongKeDao(getContext());
        list = dao.top10();
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        Top10Adapter adapter = new Top10Adapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}