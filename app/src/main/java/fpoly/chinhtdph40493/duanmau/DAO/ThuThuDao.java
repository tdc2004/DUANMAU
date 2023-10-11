package fpoly.chinhtdph40493.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.Sach;
import fpoly.chinhtdph40493.duanmau.Model.ThuThu;

public class ThuThuDao {
    DBHelper dbHelper;
    Context context;

    public ThuThuDao(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }

    public boolean insertTT(ThuThu thuThu) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.getMaTT());
        values.put("hoTen", thuThu.getHoTen());
        values.put("matKhau", thuThu.getMatKhau());
        long kq = database.insert("ThuThu", null, values);
        return kq != 0;
    }

    public ArrayList<ThuThu> getData(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                list.add(new ThuThu(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
        return list;
    }

    public ThuThu getID(String id) {
        String sql = "SELECT * FROM ThuThu WHERE hoTen = ?";
        ArrayList<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    public ArrayList<ThuThu> getAll() {
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }

    public boolean checkLogin(String user, String pass) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?", new String[]{user, pass});
        return cursor.getCount() > 0;
    }

    public boolean checkUser(String user) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE maTT = ?", new String[]{user});
        return cursor.getCount() > 0;
    }

    public boolean updatePass(String user, String pass, String newPass) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE maTT = ? AND matKhau = ?", new String[]{user, pass});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("matKhau", newPass);
            long kq = database.update("ThuThu", values, "maTT = ?", new String[]{user});
            if (kq != 0) {
                return true;
            }
            return false;
        }
        return false;
    }
}
