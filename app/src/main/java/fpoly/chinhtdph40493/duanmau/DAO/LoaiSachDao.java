package fpoly.chinhtdph40493.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.LoaiSach;

public class LoaiSachDao {
    DBHelper dbHelper;
    Context context;

    public LoaiSachDao(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }
    public ArrayList<LoaiSach> getData(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            }
        }
        return list;
    }
    public LoaiSach getID(int id){
        String sql = "SELECT * FROM TheLoai WHERE maLoai = ?";
        ArrayList<LoaiSach> list = getData(sql, String.valueOf(id));
        return list.get(0);
    }
    public ArrayList<LoaiSach> getAll(){
        String sql = "SELECT * FROM TheLoai";
        return getData(sql);
    }

    public boolean insertTL(LoaiSach loaiSach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        long kq = database.insert("TheLoai", null, values);
        loaiSach.setMaLoai((int) kq);
        return kq != -1;
    }

    public boolean deleteTL(int id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long kq = database.delete("TheLoai", "maLoai = ?",new String[]{String.valueOf(id)});
        return kq != -1;
    }
    public boolean updateTL(LoaiSach loaiSach){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        long kq = database.update("TheLoai",values,"maLoai = ?",new String[]{String.valueOf(loaiSach.getMaLoai())});
        return kq != -1;
    }
}
