/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;

import Models.ThongKeDT;
import Models.ThongKeKH;
import Models.ThongKeSP;
import Repositories.RP_ThongKe;
import Repositories.RP_ThongKeDT;
import Repositories.RP_ThongKeKH;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Dang
 */
public class ThongKeView extends javax.swing.JPanel {
    ArrayList<ThongKeSP> ds = new ArrayList<>();
    RP_ThongKe TKRePo = new RP_ThongKe();
     ArrayList<ThongKeKH> ds1= new ArrayList<>();
    RP_ThongKeKH TKKHRePo = new RP_ThongKeKH();
    ArrayList<ThongKeDT> ds2= new ArrayList<>();
    RP_ThongKeDT TKDTRePo = new RP_ThongKeDT();
    /**
     * Creates new form ThongKeView
     */
    public ThongKeView() {
        initComponents();
        loadToTable();
        loadToTable1();
        loadToTable2();
        
        jScrollPane1.getViewport().setBackground(Color.decode("#eed4d4"));
        jScrollPane2.getViewport().setBackground(Color.decode("#eed4d4"));
        jScrollPane3.getViewport().setBackground(Color.decode("#eed4d4"));
        
        TableUtils.setHeaderStyle(tblThongKeSP);
        TableUtils.setHeaderStyle(tblThongKeKH);
        TableUtils.setHeaderStyle(tblThongKeDT);
    }
    
     public void loadToTable(){
         ds = TKRePo.search();
         DefaultTableModel tblModel =(DefaultTableModel) tblThongKeSP.getModel();
        tblModel.setRowCount(0);
        for(ThongKeSP tk:ds){
            String tinhTrangHang = (tk.getSlConLai() > 0) ? "Còn hàng" : "Hết hàng";
            Object[] data ={tk.getTenSP(),tk.getTongSL(),tk.getSlConLai(),tk.getSlDaBan(),tinhTrangHang};
            tblModel.addRow(data);
        }
    }
     
      public void loadToTable1(){
         ds1 = TKKHRePo.search();
         DefaultTableModel tblModel =(DefaultTableModel) tblThongKeKH.getModel();
        tblModel.setRowCount(0);
        for(ThongKeKH tkkh:ds1){
            Object[] data ={tkkh.getTenKH(),tkkh.getSdt(),tkkh.getDiaChi(),tkkh.getTongHD()};
            tblModel.addRow(data);
        }
    }

      public void loadToTable2(){
         ds2 = TKDTRePo.search();
         DefaultTableModel tblModel =(DefaultTableModel) tblThongKeDT.getModel();
        tblModel.setRowCount(0);
        for(ThongKeDT tkdt:ds2){
            Object[] data ={tkdt.getNgayTao(),tkdt.getTongHD(),tkdt.getTongDoanhThu(),tkdt.getCaoNhat(),tkdt.getTrungBinh(),tkdt.getThapNhat()};
            tblModel.addRow(data);
        }
    }
      
       public class TableUtils {

    public static void setHeaderStyle(JTable table) {
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Roboto", Font.PLAIN, 14));
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.decode("#89cff0"));   
        headerRenderer.setForeground(Color.WHITE);  

        for (int i = 0; i < tableHeader.getColumnModel().getColumnCount(); i++) {
            tableHeader.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKeSP = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKeKH = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongKeDT = new javax.swing.JTable();

        jTabbedPane1.setBackground(new java.awt.Color(242, 227, 227));
        jTabbedPane1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(242, 227, 227));

        tblThongKeSP.setFont(new java.awt.Font("Roboto", 0, 14));
        tblThongKeSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Sản Phẩm", "Tổng Số Lượng", "Số Lượng Còn Lại", "Số Lượng Đã Bán",
                "Tình Trạng Hàng"
            }
        ));
        tblThongKeSP.setRowHeight(25);
        tblThongKeSP.setShowGrid(true);
        tblThongKeSP.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tblThongKeSP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kho Hàng", jPanel1);

        jPanel2.setBackground(new java.awt.Color(242, 227, 227));

        tblThongKeKH.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblThongKeKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ", "Tổng Hóa Đơn"
            }
        ));
        tblThongKeKH.setRowHeight(25);
        tblThongKeKH.setShowGrid(true);
        tblThongKeKH.setShowVerticalLines(false);
        jScrollPane2.setViewportView(tblThongKeKH);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Khách Hàng Thân Thiết", jPanel2);

        jPanel3.setBackground(new java.awt.Color(242, 227, 227));

        tblThongKeDT.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblThongKeDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ngày", "Tổng Hóa Đơn", "Tổng Doanh Thu", "Thu Nhập Cao Nhất", "Thu Nhập Trung Bình",
                "Thu Nhập Thấp Nhất"
            }
        ));
        tblThongKeDT.setRowHeight(25);
        tblThongKeDT.setShowGrid(true);
        tblThongKeDT.setShowVerticalLines(false);
        jScrollPane3.setViewportView(tblThongKeDT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Doanh Thu", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblThongKeDT;
    private javax.swing.JTable tblThongKeKH;
    private javax.swing.JTable tblThongKeSP;
    // End of variables declaration//GEN-END:variables
}
