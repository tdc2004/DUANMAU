package fpoly.chinhtdph40493.duanmau.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.Top;

public class ThongKeDao {
    private SQLiteDatabase database;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDao(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getReadableDatabase();
    }

    public ArrayList<Sach> top10() {
        String getTop = "SELECT s.tenSach, COUNT(*) as soLuong\n" +
                "FROM PhieuMuon pm\n" +
                "INNER JOIN Sach s ON pm.maSach = s.maSach\n" +
                "GROUP BY s.tenSach\n" +
                "ORDER BY soLuong DESC\n" +
                "LIMIT 10;";
        ArrayList<Sach> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(getTop, null);
        if (cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new Sach( cursor.getString(0), cursor.getInt(1)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        return list;
    }

    public int doanhThu(String startDay, String endDay) {
        Cursor cursor = database.rawQuery("SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?", new String[]{startDay, endDay});
        ArrayList<Integer> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                try {
                    list.add(Integer.valueOf(cursor.getString(0)));
                } catch (Exception e) {
                    list.add(0);
                }
            }
            cursor.close();
        }
        database.close();
        return list.get(0);
    }
}
