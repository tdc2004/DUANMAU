package fpoly.chinhtdph40493.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.chinhtdph40493.duanmau.Database.DBHelper;
import fpoly.chinhtdph40493.duanmau.Model.PhieuMuon;
import fpoly.chinhtdph40493.duanmau.Model.Sach;

public class PhieuMuonDao {
    DBHelper dbHelper;
    Context context;

    public PhieuMuonDao(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }
    public ArrayList<PhieuMuon> getData(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),cursor.getString(5), cursor.getInt(6),cursor.getString(7) ));
            }
        }
        return list;
    }
    public ArrayList<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }
    public PhieuMuon getID(int id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM = ?";
        ArrayList<PhieuMuon> list = getData(sql, String.valueOf(id));
        return list.get(0);
    }

    public boolean insertPM(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngay", phieuMuon.getNgay());
        values.put("traSach", phieuMuon.getTraSach());
        values.put("gio",phieuMuon.getGio());
        long kq = database.insert("PhieuMuon", null, values);
        phieuMuon.setMaPM((int) kq);
        return kq != -1;
    }

    public boolean deletePM(int id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long kq = database.delete("PhieuMuon", "maPM = ?", new String[]{String.valueOf(id)});
        return kq != -1;
    }

    public boolean updatePM(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maPM", phieuMuon.getMaPM());
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngay", phieuMuon.getNgay());
        values.put("traSach", phieuMuon.getTraSach());
        values.put("gio",phieuMuon.getGio());
        long kq = database.update("PhieuMuon", values, "maPM = ?", new String[]{String.valueOf(phieuMuon.getMaPM())});
        return kq != -1;
    }
}
