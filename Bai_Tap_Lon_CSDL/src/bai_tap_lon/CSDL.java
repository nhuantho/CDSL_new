/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bai_tap_lon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nhuan's computer
 */


public class CSDL {
    private String UserID;
    public CSDL(){
        
    }
    //gọi 1 cơ sở dữ liệu mysql
    public static Connection jdbcConnection(){
        String url="jdbc:mysql://localhost:3306/quanlisuckhoe";
        String user="root";
        String password = "nt01247005688N";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    //in dữ liệu lấy từ mysql
    public static void statement(){
        try{
            Statement sta=jdbcConnection().createStatement();
            String select="SELECT * FROM user";
            ResultSet re=sta.executeQuery(select);
            while (re.next()) {                
                String useeid=re.getString("UserID");
                String hodem=re.getString("HoDem");
                String ten=re.getString("Ten");
                String date=re.getString("NgaySinh");
                String diachi=re.getString("DiaChi");
                String sdt=re.getString("SDT");
                System.out.println(useeid+" "+hodem+" "+ten+" "+date+" "+diachi+" "+sdt);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        
    }
    //kiểm tra tài khoản
    public static boolean statement_kiemtrataikhoan(String taikhoan){
        try {
            Statement sta=jdbcConnection().createStatement();
            String where="SELECT UserID FROM taikhoan";
            ResultSet re=sta.executeQuery(where);
            while (re.next()) {                
                String tk=re.getString("UserID");
                if(tk.equalsIgnoreCase(taikhoan)==true) return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    //kiểm tra mật khẩu
    public static boolean statement_kiemtramatkhau(String matkhau){
        try {
            Statement sta=jdbcConnection().createStatement();
            String where="SELECT MatKhau FROM taikhoan";
            ResultSet re=sta.executeQuery(where);
            while (re.next()) {                
                String mk=re.getString("MatKhau");
                if(mk.equalsIgnoreCase(matkhau)==true) return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    //đưa dữ liệu về bảng user của cơ sở dữ liệu mysql
    public static boolean insert_into_user(String id, String hodem, String ten, String ngaysinh, String diachi, String sdt){
        try {
            if(id.equalsIgnoreCase("")==true|| hodem.equalsIgnoreCase("")==true|| ten.equalsIgnoreCase("")==true|| ngaysinh.equalsIgnoreCase("")==true|| diachi.equalsIgnoreCase("")==true|| sdt.equalsIgnoreCase("")==true) 
                return false;
            String insert="insert into user values(?,?,?,?,?,?)";
            PreparedStatement ps=jdbcConnection().prepareStatement(insert);
            ps.setString(1, id);
            ps.setString(2, hodem);
            ps.setString(3, ten);
            ps.setString(4, ngaysinh);
            ps.setString(5, diachi);
            ps.setString(6, sdt);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //đưa dữ liệu về bảng admin của cơ sở dữ liệu mysql
    public static boolean insert_into_admin(String id, String hoten, String SDT, String email){
        try {
            if(id.equalsIgnoreCase("")==true|| hoten.equalsIgnoreCase("")==true|| SDT.equalsIgnoreCase("")==true|| email.equalsIgnoreCase("")==true) 
                return false;
            String insert="insert into admin values(?,?,?,?)";
            PreparedStatement ps=jdbcConnection().prepareStatement(insert);
            ps.setString(1, id);
            ps.setString(2, hoten);
            ps.setString(3, SDT);
            ps.setString(4, email);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //đưa dữ liệu về bảng tài khoản của mysql
     public static boolean insert_into_taikhoan(String id, String matkhau){
        try {
            if(id.equalsIgnoreCase("")==true|| matkhau.equalsIgnoreCase("")==true) 
                return false;
            String insert="insert into taikhoan values(?,?)";
            PreparedStatement ps=jdbcConnection().prepareStatement(insert);
            ps.setString(1, id);
            ps.setString(2, matkhau);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //nhap thong tin hang ngay vao co so du lieu by tanhdz
    public static boolean insert_into_nhap_thong_tin_hang_ngay(String UserId,String day,String CanNang,String ChieuCao,String BMI,String TheTrang){
        try{
            if(ChieuCao.equalsIgnoreCase("")||CanNang.equalsIgnoreCase("")) return false;
            String insert="insert into nhapthongtinvaloikhuyen values(?,?,?,?,?,?)";
            PreparedStatement ps=jdbcConnection().prepareStatement(insert);
            ps.setString(1, UserId);
            ps.setString(2, day);
            Double chieucao=Double.valueOf(ChieuCao);//format sang by tanhdz
            Double cannang=Double.valueOf(CanNang);
            Double bmi=Double.valueOf(BMI);
            ps.setDouble(3, chieucao);
            ps.setDouble(4, cannang);
            ps.setDouble(5, bmi);
            ps.setString(6, TheTrang);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
            //cap nhat xong by tanhdz
        } catch(SQLException e){
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    // Đọc ID từ file
    public static String ReadIDFromFile() throws FileNotFoundException, IOException {
        String s="";
        FileReader fr;
        fr = new FileReader("src\\bai_tap_lon\\luuID.txt");
        int i;
        while((i=fr.read())!=-1){
            s+= (char)i;
        }
        return s;
    }
    
   // Ghi ID vào File
    public static void WriteIDToFile(String id) throws IOException {
        FileWriter fw;
        fw = new FileWriter("src\\bai_tap_lon\\luuID.txt");
        fw.write(id);
        fw.close();
    }
    
    
    // cập nhật trung tâm
    public static boolean insert_into_trungtam(String IDtrungtam,String TenTrungTam,String Tinh,String DiaChiCuThe,String sdt){
        try{
            if(IDtrungtam.equalsIgnoreCase("")||TenTrungTam.equalsIgnoreCase("")||Tinh.equalsIgnoreCase("")||DiaChiCuThe.equalsIgnoreCase("")||sdt.equalsIgnoreCase(""))
                    return false;
            String insert="insert into trungtam values(?,?,?,?,?)";
            PreparedStatement ps=jdbcConnection().prepareStatement(insert);
            ps.setString(1, IDtrungtam);
            ps.setString(2, TenTrungTam);
            ps.setString(3, Tinh);
            ps.setString(4, DiaChiCuThe);
            ps.setString(5, sdt);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
        }
        catch(SQLException e){
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    //cap nhat them admin nào nhập trung tâm nào
    public static boolean insert_into_capnhattrungtam(String IDadmin,String IDTrungTam,String date){
        try{
            String insert="insert into capnhattrungtam values(?,?,?)";
            PreparedStatement ps=jdbcConnection().prepareStatement(insert);
            ps.setString(1, IDadmin);
            ps.setString(2, IDTrungTam);
            ps.setString(3, date);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
        }
        catch(SQLException e){
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    //update loi khuyen
    public static boolean update_loi_khuyen(String TheTrang,String TapLuyen,String DinhDuong){
        try{
            if(TapLuyen.equalsIgnoreCase("")||DinhDuong.equalsIgnoreCase(""))
                    return false;
            String update="UPDATE loikhuyen "
                    + "SET TapLuyen=?"
                    + ", DinhDuong=?"
                    + "WHERE TheTrang=?";
            PreparedStatement ps=jdbcConnection().prepareStatement(update);
            ps.setString(1, TapLuyen);
            ps.setString(2, DinhDuong);
            ps.setString(3, TheTrang);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
        }
        catch(SQLException e){
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public static boolean insert_into_cap_nhat_loi_khuyen(String IDadmin,String thetrang,String date){
        try{
            String insert="insert into capnhatloikhuyen values(?,?,?)";
            PreparedStatement ps=jdbcConnection().prepareStatement(insert);
            ps.setString(1, IDadmin);
            ps.setString(2, thetrang);
            ps.setString(3, date);
            int n=ps.executeUpdate();
            if(n!=0)return true;
            return false;
        }
        catch(SQLException e){
            Logger.getLogger(CSDL.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
