package fpoly.chinhtdph40493.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.Sach;

public class SachDao {
    DBHelper dbHelper;
    Context context;

    public SachDao(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }
    public ArrayList<Sach> getData(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Sach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
            }
        }
        return list;
    }
    public Sach getID(int id){
        String sql = "SELECT * FROM Sach WHERE maSach = ?";
        ArrayList<Sach> list = getData(sql, String.valueOf(id));
        return list.get(0);
    }
    public ArrayList<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }
    public boolean insertSach(Sach sach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("maLoai", sach.getMaLoai());
        values.put("giaSach", sach.getGiaThue());
        long kq = database.insert("Sach", null, values);
        sach.setMaSach((int) kq);
        return kq != -1;
    }

    public boolean deleteSach(int id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long kq = database.delete("Sach", "maSach = ?", new String[]{String.valueOf(id)});
        return kq != -1;
    }

    public boolean updateSach(Sach sach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSach", sach.getMaSach());
        values.put("tenSach", sach.getTenSach());
        values.put("maLoai", sach.getMaLoai());
        values.put("giaSach", sach.getGiaThue());
        long kq = database.update("Sach", values, "maSach = ?", new String[]{String.valueOf(sach.getMaSach())});
        return kq != -1;
    }
}
