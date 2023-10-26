package fpoly.chinhtdph40493.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.ThanhVien;

public class ThanhVienDao {
    DBHelper dbHelper;
    Context context;

    public ThanhVienDao(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }

    public ArrayList<ThanhVien> getData(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getInt(3)));
            }
        }
        return list;
    }
    public ThanhVien getID(int id){
        String sql = "SELECT * FROM ThanhVien WHERE MATV = ?";
        ArrayList<ThanhVien> list = getData(sql, String.valueOf(id));
        return list.get(0);
    }
    public ArrayList<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }
    public boolean insertTV(ThanhVien thanhVien){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HOTEN",thanhVien.getHoTen());
        values.put("NAMSINH",thanhVien.getNamSinh());
        values.put(("soTaiKhoan"),thanhVien.getStk());
        long kq = database.insert("ThanhVien",null,values);
        thanhVien.setMaTV((int) kq);
        return kq != -1;
    }
    public boolean deleteTV(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long kq = database.delete("ThanhVien","MATV = ?",new String[]{String.valueOf(id)});
        return kq != -1;
    }
    public boolean updateTV(ThanhVien thanhVien){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HOTEN",thanhVien.getHoTen());
        values.put("NAMSINH",thanhVien.getNamSinh());
        values.put(("soTaiKhoan"),thanhVien.getStk());
        long kq = database.update("ThanhVien",values,"MATV = ?",new String[]{String.valueOf(thanhVien.getMaTV())});
        return kq != -1;
    }
}
