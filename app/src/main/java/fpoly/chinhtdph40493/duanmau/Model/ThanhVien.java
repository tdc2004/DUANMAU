package fpoly.chinhtdph40493.duanmau.Model;

public class ThanhVien {
    private int maTV;
    private String hoTen;
    private String namSinh;
    private int stk;

    public ThanhVien(int maTV, String hoTen, String namSinh,int stk) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.stk = stk;
    }

    public int getStk() {
        return stk;
    }

    public void setStk(int stk) {
        this.stk = stk;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
