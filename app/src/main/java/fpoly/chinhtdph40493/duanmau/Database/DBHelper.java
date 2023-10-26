package fpoly.chinhtdph40493.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "ThuVien.db", null, 7);
    }

    String tb_thanhVien = "CREATE TABLE ThanhVien(MATV INTEGER PRIMARY KEY AUTOINCREMENT, HOTEN TEXT NOT NULL,NAMSINH TEXT NOT NULL,soTaiKhoan INTEGER NOT NULL)";
    String createTableThuThu = "CREATE TABLE ThuThu (maTT TEXT PRIMARY KEY,hoTen TEXT NOT NULL,matKhau TEXT NOT NULL)";

    String createTableTheLoai = "CREATE TABLE TheLoai(maLoai INTEGER PRIMARY KEY AUTOINCREMENT,tenLoai TEXT NOT NULL)";
    String createTableSach = "CREATE TABLE Sach (maSach INTEGER PRIMARY KEY AUTOINCREMENT,tenSach TEXT NOT NULL," +
            "giaSach INTEGER NOT NULL, maLoai INTEGER REFERENCES TheLoai(maLoai),soLuong INTEGER NOT NULL)";
    String createTablePhieuMuon = "CREATE TABLE PhieuMuon(" +
             "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
            "maTT TEXT REFERENCES ThuThu(maTT), " +
            "maTV INTEGER REFERENCES ThanhVien(maTV), " +
            "maSach INTEGER REFERENCES Sach(maSach), " +
            "tienThue INTEGER NOT NULL," +
            "ngay DATE NOT NULL," +
            "traSach INTEGER NOT NULL,"+
            "gio STRING NOT NULL)";

    String insertintoThuThu = "INSERT INTO ThuThu(maTT,hoTen,matKhau) VALUES('admin','admin',123)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb_thanhVien);
        db.execSQL(createTableThuThu);
        db.execSQL(createTableTheLoai);
        db.execSQL(createTableSach);
        db.execSQL(createTablePhieuMuon);
        db.execSQL(insertintoThuThu);
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
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
