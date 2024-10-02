/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;

import Models.LoaiSanPham;
import Models.SanPham;
import Repositories.RP_LoaiSanPham;
import Repositories.RP_SanPham;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Dang
 */
public class SanPhamView extends javax.swing.JPanel {
    RP_SanPham SPRepo = new RP_SanPham();
    RP_LoaiSanPham LSPRepo = new RP_LoaiSanPham();
    ArrayList<SanPham> ds = new ArrayList<>();
    ArrayList<LoaiSanPham> ds1 = new ArrayList<>();
    /**
     * Creates new form SanPhamView
     */
    public SanPhamView() {
        initComponents();
        ds = SPRepo.search("");
        loadToTable(ds);
        loadToTable1();
        
        jScrollPane1.getViewport().setBackground(Color.decode("#eed4d4"));
        TableUtils.setHeaderStyle(tblLsp);
        TableUtils.setHeaderStyle(tblSanPham);
        
        txtSearchSP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }
            
             public void search() {
            String keyWord = txtSearchSP.getText();  
            loadToTable(SPRepo.search(keyWord)); 
            }
        });
    }

     public SanPham getFormData(){
        String maSP = txtMaSP.getText();
        String maLsp = txtMaLsp.getText();
        String tenSP = txtTenSP.getText();
        float gia = Float.parseFloat(txtGia.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        String ghiChu = txtGhiChu.getText();
        return new SanPham(maSP, maLsp, tenSP, gia, soLuong, ghiChu);
    }
     
     public LoaiSanPham getFormData1(){
        String maLSP = txtMaLSP.getText();
        String tenLSP = txtTenLSP.getText();
        
        return new LoaiSanPham(maLSP, tenLSP);
    }
     
      public void loadToTable(ArrayList<SanPham> ds){
         DefaultTableModel tblModel =(DefaultTableModel) tblSanPham.getModel();
        tblModel.setRowCount(0);
        for(SanPham sp:ds){
            Object[] data ={sp.getMaLSP(),sp.getMaSP(),sp.getTenSP(),sp.getGia(),sp.getSoLuong(),sp.getGhiChu()};
            tblModel.addRow(data);
        }
    }
      
      public void loadToTable1(){
          ds1 = LSPRepo.search();
         DefaultTableModel tblModel =(DefaultTableModel) tblLsp.getModel();
        tblModel.setRowCount(0);
        for(LoaiSanPham lsp:ds1){
            Object[] data ={lsp.getMaLSP(),lsp.getTenLSP()};
            tblModel.addRow(data);
        }
    }
      
       public void hienThi(int index){
        txtMaLsp.setText(tblSanPham.getValueAt(index, 0).toString());
        txtMaSP.setText(tblSanPham.getValueAt(index, 1).toString());
        txtTenSP.setText(tblSanPham.getValueAt(index, 2).toString());
        txtGia.setText(tblSanPham.getValueAt(index, 3).toString());
        txtSoLuong.setText(tblSanPham.getValueAt(index, 4).toString());
        txtGhiChu.setText(tblSanPham.getValueAt(index, 5).toString());
    }
       
         public void hienThi1(int index){
        txtMaLSP.setText(tblLsp.getValueAt(index, 0).toString());
        txtTenLSP.setText(tblLsp.getValueAt(index, 1).toString());      
    }
       
        public void newTable(){
             txtMaLsp.setText("");
             txtMaSP.setText("");
             txtTenSP.setText("");
             txtGia.setText("");
             txtSoLuong.setText("");
             txtGhiChu.setText("");
         }
        
        public void newTable1(){
             txtMaLSP.setText("");
             txtTenLSP.setText("");            
         }
        
        
        public void hienThiDialog(boolean isSua){
    if(isSua) {
        btnAdd.setVisible(false); // Ẩn nút Thêm
        btnUpdate.setVisible(true); // Hiện nút Sửa
        txtMaLsp.setEditable(false);
        txtMaSP.setEditable(false);
    } else {
        btnAdd.setVisible(true);  // Hiện nút Thêm
        btnUpdate.setVisible(false);  // Ẩn nút Sửa
        txtMaLsp.setEditable(true);
        txtMaSP.setEditable(true);
    }
   }
        
        public boolean check(String ma){
        for(int i=0;i<ds.size();i++){
            if(ds.get(i).getMaSP().equalsIgnoreCase(ma)){
                return true;
            }
        }
        return false;
    }
        
        public boolean check1(String ma){
        for(int i=0;i<ds1.size();i++){
            if(ds1.get(i).getMaLSP().equalsIgnoreCase(ma)){
                return true;
            }
        }
        return false;
    }
        
    public class TableUtils {

    public static void setHeaderStyle(JTable table) {
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Roboto", Font.PLAIN, 14));
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.decode("#89cff0"));   
        headerRenderer.setForeground(Color.WHITE);  

        // Áp dụng renderer cho tất cả các cột
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

        SanPhamDialog = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        txtMaLsp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSearchSP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLsp = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtMaLSP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTenLSP = new javax.swing.JTextField();
        btnAddLSP = new javax.swing.JButton();
        btnUpdateLSP = new javax.swing.JButton();
        btnDeleteLSP = new javax.swing.JButton();

        SanPhamDialog.setMinimumSize(new java.awt.Dimension(400, 550));

        jLabel3.setText("Mã Loại Sản Phẩm:");

        jLabel4.setText("Tên Sản Phẩm:");

        jLabel5.setText("Giá:");

        jLabel6.setText("Số Lượng:");

        jLabel7.setText("Ghi Chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        btnAdd.setText("Xác Nhận");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập Nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnExit.setText("Hủy");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel10.setText("Mã Sản Phẩm:");

        javax.swing.GroupLayout SanPhamDialogLayout = new javax.swing.GroupLayout(SanPhamDialog.getContentPane());
        SanPhamDialog.getContentPane().setLayout(SanPhamDialogLayout);
        SanPhamDialogLayout.setHorizontalGroup(
            SanPhamDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPhamDialogLayout.createSequentialGroup()
                .addGroup(SanPhamDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnAdd)
                        .addGap(37, 37, 37)
                        .addComponent(btnUpdate)
                        .addGap(44, 44, 44)
                        .addComponent(btnExit))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SanPhamDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtMaLsp, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SanPhamDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        SanPhamDialogLayout.setVerticalGroup(
            SanPhamDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPhamDialogLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaLsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(9, 9, 9)
                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(SanPhamDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnExit))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(242, 227, 227));

        tblSanPham.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã LSP","Mã SP", "Tên SP", "Giá", "Số Lượng", "Ghi Chú"
            }
        ));
        tblSanPham.setRowHeight(25);
        tblSanPham.setShowGrid(true);
        tblSanPham.setShowVerticalLines(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnThem.setBackground(new java.awt.Color(255, 204, 204));
        btnThem.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-30.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 204, 204));
        btnSua.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-update-30.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 204, 204));
        btnDelete.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-30.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("Quản Lý Sản Phẩm");

        txtSearchSP.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-30.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Loại Sản Phẩm"));
        jPanel1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        tblLsp.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblLsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Loại SP", "Tên Loại SP"
            }
        ));
        tblLsp.setRowHeight(25);
        tblLsp.setShowGrid(true);
        tblLsp.setShowVerticalLines(false);
        tblLsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLspMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLsp);

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel8.setText("Mã Loại Sản Phẩm:");

        txtMaLSP.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setText("Tên Loại Sản Phẩm:");

        txtTenLSP.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        btnAddLSP.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnAddLSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-30.png"))); // NOI18N
        btnAddLSP.setText("Thêm");
        btnAddLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLSPActionPerformed(evt);
            }
        });

        btnUpdateLSP.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnUpdateLSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-update-30.png"))); // NOI18N
        btnUpdateLSP.setText("Sửa");
        btnUpdateLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateLSPActionPerformed(evt);
            }
        });

        btnDeleteLSP.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnDeleteLSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-30.png"))); // NOI18N
        btnDeleteLSP.setText("Xóa");
        btnDeleteLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteLSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTenLSP, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                        .addComponent(txtMaLSP))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddLSP)
                        .addGap(44, 44, 44)
                        .addComponent(btnUpdateLSP)
                        .addGap(47, 47, 47)
                        .addComponent(btnDeleteLSP)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddLSP)
                    .addComponent(btnUpdateLSP)
                    .addComponent(btnDeleteLSP))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtSearchSP, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addGap(67, 67, 67)
                        .addComponent(btnSua)
                        .addGap(67, 67, 67)
                        .addComponent(btnDelete)
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtSearchSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnSua)
                            .addComponent(btnDelete))
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(52, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
          if(txtMaLsp.getText().equals("") || txtMaSP.getText().equals("") || txtTenSP.getText().equals("") || txtGia.getText().equals("")
                 ||txtSoLuong.getText().equals("") || txtGhiChu.getText().equals("") ){
            JOptionPane.showMessageDialog(this, "Mời Bạn Nhập Đầy Đủ Thông Tin");
        }else if(check(txtMaSP.getText())==true){
            JOptionPane.showMessageDialog(this, "Mã Sản Phẩm Đã Tồn Tại");
        }else{
            SanPham sp = this.getFormData();
            SPRepo.creat(sp);
            JOptionPane.showMessageDialog(this, "Thêm Thành Công");
            loadToTable(SPRepo.search(""));
            SanPhamDialog.dispose();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        SanPham sp = this.getFormData();
        SPRepo.update(sp);
        JOptionPane.showMessageDialog(this, "Sửa Thành Công");
        loadToTable(SPRepo.search(""));
        SanPhamDialog.dispose();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        SanPhamDialog.setVisible(false);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        newTable();
        hienThiDialog(false); 
        SanPhamDialog.setLocationRelativeTo(null);
        SanPhamDialog.setVisible(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int index = tblSanPham.getSelectedRow();
        if (index == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa");
        return;
        } 
        
        hienThiDialog(true);
        SanPhamDialog.setLocationRelativeTo(null);
        SanPhamDialog.setVisible(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        SanPham sp = this.getFormData();
        SPRepo.delete(sp.getMaSP());
        JOptionPane.showMessageDialog(this, "Xóa Thành Công");
        loadToTable(SPRepo.search(""));
        hienThi(0);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int index = tblSanPham.getSelectedRow();
        hienThi(index);
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnAddLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLSPActionPerformed
       if(txtMaLSP.getText().equals("") || txtTenLSP.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mời Bạn Nhập Đầy Đủ Thông Tin");
        }else if(check1(txtMaLSP.getText())==true){
            JOptionPane.showMessageDialog(this, "Mã Loại Sản Phẩm Đã Tồn Tại");
        }else{
            LoaiSanPham lsp = this.getFormData1();
            LSPRepo.creat(lsp);
            JOptionPane.showMessageDialog(this, "Thêm Thành Công");
            loadToTable1();
            newTable1();
        }
    }//GEN-LAST:event_btnAddLSPActionPerformed

    private void btnUpdateLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateLSPActionPerformed
       LoaiSanPham lsp = this.getFormData1();
        LSPRepo.update(lsp);
        JOptionPane.showMessageDialog(this, "Sửa Thành Công");
        loadToTable1();
        newTable1();
    }//GEN-LAST:event_btnUpdateLSPActionPerformed

    private void btnDeleteLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteLSPActionPerformed
        LoaiSanPham lsp = this.getFormData1();
        LSPRepo.delete(lsp.getMaLSP());
        JOptionPane.showMessageDialog(this, "Xóa Thành Công");
        loadToTable1();
        hienThi(0);
    }//GEN-LAST:event_btnDeleteLSPActionPerformed

    private void tblLspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLspMouseClicked
        int index = tblLsp.getSelectedRow();
        hienThi1(index);
    }//GEN-LAST:event_tblLspMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog SanPhamDialog;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddLSP;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteLSP;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateLSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblLsp;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaLSP;
    private javax.swing.JTextField txtMaLsp;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSearchSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenLSP;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
