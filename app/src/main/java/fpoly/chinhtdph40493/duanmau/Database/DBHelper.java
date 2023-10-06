package fpoly.chinhtdph40493.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "ThuVien.db", null, 1);
    }

    String tb_thanhVien = "CREATE TABLE ThanhVien(MATV INTEGER PRIMARY KEY AUTOINCREMENT, HOTEN TEXT NOT NULL,NAMSINH TEXT NOT NULL)";
    String createTableThuThu = "CREATE TABLE ThuThu (maTT TEXT PRIMARY KEY,hoTen TEXT NOT NULL,matKhau TEXT NOT NULL)";

    String createTableTheLoai = "CREATE TABLE TheLoai(maLoai INTEGER PRIMARY KEY AUTOINCREMENT,tenLoai TEXT NOT NULL)";
    String createTableSach = "CREATE TABLE Sach (maSach INTEGER PRIMARY KEY AUTOINCREMENT,tenSach TEXT NOT NULL," +
            "giaSach INTEGER NOT NULL, maLoai INTEGER REFERENCES TheLoai(maLoai))";
    String createTablePhieuMuon = "CREATE TABLE PhieuMuon(" +
            "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
            "maTT TEXT REFERENCES ThuThu(maTT), " +
            "maTV INTEGER REFERENCES ThanhVien(maTV), " +
            "maSach INTEGER REFERENCES Sach(maSach), " +
            "tienThue INTEGER NOT NULL," +
            "ngay DATE NOT NULL," +
            "traSach INTEGER NOT NULL)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb_thanhVien);
        db.execSQL(createTableThuThu);
        db.execSQL(createTableTheLoai);
        db.execSQL(createTableSach);
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS TheLoai");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);
        }
    }
}