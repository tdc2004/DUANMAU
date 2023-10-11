package fpoly.chinhtdph40493.duanmau.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fpoly.chinhtdph40493.duanmau.DAO.ThongKeDao;
import fpoly.chinhtdph40493.duanmau.DAO.ThuThuDao;
import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.myapplication.R;

public class DoanhThuFragment extends Fragment {
    Button btn_starNgay,btn_endNgay,btn_doanhThu;
    EditText edt_starNgay,edt_endNgay;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    int mYear,mMonth,mDay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_starNgay = view.findViewById(R.id.btn_starDay);
        btn_endNgay = view.findViewById(R.id.btn_endDay);
        edt_starNgay = view.findViewById(R.id.edt_TuNgay);
        edt_endNgay = view.findViewById(R.id.edt_denNgay);
        btn_doanhThu = view.findViewById(R.id.btn_doanhThu);
        DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mMonth = month;
                mDay=dayOfMonth;
                mYear = year;
                GregorianCalendar calendar = new GregorianCalendar(mYear,mMonth,mDay);
                edt_starNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mMonth = month;
                mDay=dayOfMonth;
                mYear = year;
                GregorianCalendar calendar = new GregorianCalendar(mYear,mMonth,mDay);
                edt_endNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        btn_starNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear= calendar.get(Calendar.YEAR);
                mDay= calendar.get(Calendar.DAY_OF_MONTH);
                mMonth= calendar.get(Calendar.MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),0,mDateTuNgay,mYear,mMonth,mDay);
                dialog.show();
            }
        });
        btn_endNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear= calendar.get(Calendar.YEAR);
                mDay= calendar.get(Calendar.DAY_OF_MONTH);
                mMonth= calendar.get(Calendar.MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),0,mDateDenNgay,mYear,mMonth,mDay);
                dialog.show();
            }
        });
        btn_doanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    ThongKeDao dao = new ThongKeDao(getContext());
                    String tuNgay = edt_starNgay.getText().toString();
                    String denNgay = edt_endNgay.getText().toString();
                    TextView textView = view.findViewById(R.id.tv_doanhThu);
                    textView.setText("Doanh Thu"+ dao.doanhThu(tuNgay,denNgay)+" VND");
                }

            }
        });
    }

    private boolean validate() {
        if (edt_starNgay.getText().toString().equals("") ||edt_endNgay.getText().toString().equals("")){
            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}