/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;


import Repositories.RP_KhachHang;
import java.util.ArrayList;
import Models.KhachHang;
import Models.Pet;
import Repositories.RP_Pet;
import java.awt.Color;
import java.awt.Font;
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
public class KhachHangView extends javax.swing.JPanel {
    RP_KhachHang KHRepo = new RP_KhachHang();
    RP_Pet PTRepo = new RP_Pet();
    ArrayList<KhachHang> ds = new ArrayList<>();
    ArrayList<Pet> ds1 = new ArrayList<>();
    /**
     * Creates new form KhachHangView
     */
    public KhachHangView() {
        initComponents();
        ds = KHRepo.search("");
        loadToTable(ds);
        ds1 = PTRepo.search("");
        loadToTable1(ds1);
        
        jScrollPane1.getViewport().setBackground(Color.decode("#eed4d4"));
        jScrollPane2.getViewport().setBackground(Color.decode("#eed4d4"));
        TableUtils.setHeaderStyle(tblPet);
        TableUtils.setHeaderStyle(tblKhachHang);
        
        txtSearchKH.getDocument().addDocumentListener(new DocumentListener() {
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
            String keyWord = txtSearchKH.getText();  
            loadToTable(KHRepo.search(keyWord)); 
            }
        });
        
        txtSearchPet.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search1();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search1();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search1();
            }
            
            public void search1() {
            String keyWord = txtSearchPet.getText();  
            loadToTable1(PTRepo.search(keyWord)); 
            }
        });
    }
    
     public KhachHang getFormData(boolean isUpdate){
        int maKH = isUpdate ? Integer.parseInt(txtMaKH.getText()) : 0;
        String tenKH = txtTenKH.getText();
        String sdt = txtSdt.getText();
        String email = txtEmail.getText();
        String diaChi = txtDiaChi.getText();
        
        return new KhachHang(maKH, tenKH, sdt, email, diaChi);
    }
     
      public Pet getFormDataPet(boolean isUpdate){
        int maPet = isUpdate ? Integer.parseInt(txtMaPet.getText()) : 0;
        String tenPet = txtTenPet.getText();
        String loaiPet = txtLoaiPet.getText();
        String giongLoai = txtGiong.getText();
        int tuoi = Integer.parseInt(txtTuoi.getText());
        float canNang = Float.parseFloat(txtcanNang.getText());
        int trangThaiTiem = rdoDaTiem.isSelected() ? 1 : 0;       
        int maKH = Integer.parseInt(txtMaKHP.getText());
        
        return new Pet(maPet, tenPet, loaiPet, giongLoai, tuoi, canNang, trangThaiTiem, maKH);
    }
     
     public void loadToTable(ArrayList<KhachHang> ds){
         DefaultTableModel tblModel =(DefaultTableModel) tblKhachHang.getModel();
        tblModel.setRowCount(0);
        for(KhachHang kh:ds){
            Object[] data ={kh.getMaKH(),kh.getTenKH(),kh.getSdt(),kh.getEmail(),kh.getDiaChi()};
            tblModel.addRow(data);
        }
    }
     
     public void loadToTable1(ArrayList<Pet> ds1){
         DefaultTableModel tblModel =(DefaultTableModel) tblPet.getModel();
        tblModel.setRowCount(0);
        for(Pet pt:ds1){
            Object[] data ={pt.getMaKH(),pt.getMaPet(),pt.getTenPet(),pt.getLoaiPet(),pt.getGiongLoai(),pt.getTuoi(),pt.getCanNang(),
                pt.getTrangThaiTiemChung()==1 ?"Đã Tiêm Chủng":"Chưa Tiêm Chủng" };
            tblModel.addRow(data);
        }
    }
     
     public void hienThi(int index){
        txtMaKH.setText(tblKhachHang.getValueAt(index, 0).toString());
        txtTenKH.setText(tblKhachHang.getValueAt(index, 1).toString());
        txtSdt.setText(tblKhachHang.getValueAt(index, 2).toString());
        txtEmail.setText(tblKhachHang.getValueAt(index, 3).toString());
        txtDiaChi.setText(tblKhachHang.getValueAt(index, 4).toString());
    }
     
      public void hienThi1(int index){
        txtMaKHP.setText(tblPet.getValueAt(index, 0).toString());
        txtMaPet.setText(tblPet.getValueAt(index, 1).toString());
        txtTenPet.setText(tblPet.getValueAt(index, 2).toString());
        txtLoaiPet.setText(tblPet.getValueAt(index, 3).toString());
        txtGiong.setText(tblPet.getValueAt(index, 4).toString());
        txtTuoi.setText(tblPet.getValueAt(index, 5).toString());
        txtcanNang.setText(tblPet.getValueAt(index, 6).toString());
        if(tblPet.getValueAt(index, 7).toString().equalsIgnoreCase("Đã Tiêm Chủng")){
            rdoDaTiem.setSelected(true);
        }else{
            rdoChuaTiem.setSelected(true);
        }
    }
     
     public void newTable(){
             txtMaKH.setText("");
             txtTenKH.setText("");
             txtSdt.setText("");
             txtEmail.setText("");
             txtDiaChi.setText("");
         }
     
     public void newTable1(){
        txtMaKHP.setText("");
        txtMaPet.setText("");
        txtTenPet.setText("");
        txtLoaiPet.setText("");
        txtGiong.setText("");
        txtTuoi.setText("");
        txtcanNang.setText("");
        buttonGroup1.clearSelection();
         }
     
     public void hienThiDialog(boolean isSua){
    if(isSua) {
        btnAdd.setVisible(false); // Ẩn nút Thêm
        btnUpdate.setVisible(true); // Hiện nút Sửa
        txtMaKH.setEditable(false);
    } else {
        btnAdd.setVisible(true);  // Hiện nút Thêm
        btnUpdate.setVisible(false);  // Ẩn nút Sửa
        txtMaKH.setEditable(true);
    }
}
     
      public void hienThiDialog1(boolean isSua){
    if(isSua) {
        btnAddPet.setVisible(false); // Ẩn nút Thêm
        btnUpdatePet.setVisible(true); // Hiện nút Sửa
        txtMaKHP.setEditable(false);
        txtMaPet.setEditable(false);
    } else {
        btnAddPet.setVisible(true);  // Hiện nút Thêm
        btnUpdatePet.setVisible(false);  // Ẩn nút Sửa
        txtMaKHP.setEditable(true);
        txtMaPet.setEditable(true);
    }
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

        KhachHangDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        PetDialog = new javax.swing.JDialog();
        jLabel10 = new javax.swing.JLabel();
        txtMaPet = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMaKHP = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTenPet = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtLoaiPet = new javax.swing.JTextField();
        txtGiong = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTuoi = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtcanNang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        rdoDaTiem = new javax.swing.JRadioButton();
        rdoChuaTiem = new javax.swing.JRadioButton();
        btnAddPet = new javax.swing.JButton();
        btnUpdatePet = new javax.swing.JButton();
        btnExitPet = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtSearchKH = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPet = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSearchPet = new javax.swing.JTextField();
        btnThemPet = new javax.swing.JButton();
        btnSuaPet = new javax.swing.JButton();
        btnDeletePet = new javax.swing.JButton();

        KhachHangDialog.setMinimumSize(new java.awt.Dimension(350, 450));

        jLabel1.setText("Mã Khách Hàng:");

        jLabel2.setText("Tên Khách Hàng:");

        jLabel3.setText("SDT:");

        jLabel4.setText("Email:");

        jLabel5.setText("Địa Chỉ:");

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

        javax.swing.GroupLayout KhachHangDialogLayout = new javax.swing.GroupLayout(KhachHangDialog.getContentPane());
        KhachHangDialog.getContentPane().setLayout(KhachHangDialogLayout);
        KhachHangDialogLayout.setHorizontalGroup(
            KhachHangDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhachHangDialogLayout.createSequentialGroup()
                .addGroup(KhachHangDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(KhachHangDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(KhachHangDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKH)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKH)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSdt)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)))
                    .addGroup(KhachHangDialogLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnAdd)
                        .addGap(31, 31, 31)
                        .addComponent(btnUpdate)
                        .addGap(36, 36, 36)
                        .addComponent(btnExit)))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        KhachHangDialogLayout.setVerticalGroup(
            KhachHangDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KhachHangDialogLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(KhachHangDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnExit))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        PetDialog.setMinimumSize(new java.awt.Dimension(400, 480));
        PetDialog.setModal(true);

        jLabel10.setText("Mã Pet:");

        jLabel11.setText("Mã Khách Hàng:");

        jLabel12.setText("Tên Pet:");

        jLabel13.setText("Loài Pet:");

        jLabel14.setText("Giống Loài:");

        jLabel15.setText("Tuổi:");

        jLabel16.setText("Cân Nặng:");

        jLabel17.setText("Trạng Thái Tiêm Chủng:");

        buttonGroup1.add(rdoDaTiem);
        rdoDaTiem.setText("Đã Tiêm");

        buttonGroup1.add(rdoChuaTiem);
        rdoChuaTiem.setText("Chưa Tiêm");

        btnAddPet.setText("Xác Nhận");
        btnAddPet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPetActionPerformed(evt);
            }
        });

        btnUpdatePet.setText("Cập Nhật");
        btnUpdatePet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePetActionPerformed(evt);
            }
        });

        btnExitPet.setText("Hủy");
        btnExitPet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitPetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PetDialogLayout = new javax.swing.GroupLayout(PetDialog.getContentPane());
        PetDialog.getContentPane().setLayout(PetDialogLayout);
        PetDialogLayout.setHorizontalGroup(
            PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PetDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PetDialogLayout.createSequentialGroup()
                        .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaPet)
                            .addComponent(txtMaKHP)
                            .addComponent(txtTenPet)
                            .addComponent(txtLoaiPet)
                            .addComponent(txtGiong)
                            .addComponent(txtTuoi)
                            .addComponent(txtcanNang, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)))
                    .addGroup(PetDialogLayout.createSequentialGroup()
                        .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAddPet)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rdoDaTiem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdatePet))
                        .addGap(41, 41, 41)
                        .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoChuaTiem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExitPet))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        PetDialogLayout.setVerticalGroup(
            PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PetDialogLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMaPet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMaKHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTenPet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtLoaiPet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtGiong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtcanNang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(rdoDaTiem)
                    .addComponent(rdoChuaTiem))
                .addGap(43, 43, 43)
                .addGroup(PetDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPet)
                    .addComponent(btnUpdatePet)
                    .addComponent(btnExitPet))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(242, 227, 227));

        jTabbedPane1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(242, 227, 227));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel8.setText("Thông Tin Khách Hàng");

        tblKhachHang.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khách Hàng", "Tên Khách Hàng", "SDT", "Email", "Địa Chỉ"
            }
        ));
        tblKhachHang.setRowHeight(25);
        tblKhachHang.setShowGrid(true);
        tblKhachHang.setShowVerticalLines(false);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-30.png"))); // NOI18N

        txtSearchKH.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 480, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchKH, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnThem)
                .addGap(74, 74, 74)
                .addComponent(btnSua)
                .addGap(75, 75, 75)
                .addComponent(btnDelete)
                .addGap(37, 37, 37))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel8)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(txtSearchKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnDelete))
                .addGap(53, 53, 53))
        );

        jTabbedPane1.addTab("Khách Hàng", jPanel1);

        jPanel2.setBackground(new java.awt.Color(242, 227, 227));

        tblPet.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblPet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khách Hàng", "Mã Pet", "Tên Pet", "Loài Pet", "Giống Loài","Tuổi",
                "Cân Nặng(Kg)","Trạng Thái Tiêm Chủng"
            }
        ));
        tblPet.setRowHeight(25);
        tblPet.setShowGrid(true);
        tblPet.setShowVerticalLines(false);
        tblPet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPetMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPet);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setText("Thông Tin Thú Cưng");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-30.png"))); // NOI18N

        txtSearchPet.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        btnThemPet.setBackground(new java.awt.Color(255, 204, 204));
        btnThemPet.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnThemPet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-30.png"))); // NOI18N
        btnThemPet.setText("Thêm");
        btnThemPet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPetActionPerformed(evt);
            }
        });

        btnSuaPet.setBackground(new java.awt.Color(255, 204, 204));
        btnSuaPet.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnSuaPet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-update-30.png"))); // NOI18N
        btnSuaPet.setText("Sửa");
        btnSuaPet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPetActionPerformed(evt);
            }
        });

        btnDeletePet.setBackground(new java.awt.Color(255, 204, 204));
        btnDeletePet.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnDeletePet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-30.png"))); // NOI18N
        btnDeletePet.setText("Xóa");
        btnDeletePet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchPet, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemPet)
                .addGap(79, 79, 79)
                .addComponent(btnSuaPet)
                .addGap(71, 71, 71)
                .addComponent(btnDeletePet)
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearchPet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemPet)
                    .addComponent(btnSuaPet)
                    .addComponent(btnDeletePet))
                .addGap(34, 34, 34))
        );

        jTabbedPane1.addTab("Thú Cưng", jPanel2);

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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
         if(txtTenKH.getText().equals("") || txtSdt.getText().equals("") || txtEmail.getText().equals("") || txtDiaChi.getText().equals("")){
             JOptionPane.showMessageDialog(this, "Mời Bạn Nhập Đầy Đủ Thông Tin");        
        }else{
            KhachHang kh = this.getFormData(false);
            KHRepo.creat(kh);
            JOptionPane.showMessageDialog(this, "Thêm Thành Công");
            loadToTable(KHRepo.search(""));
           KhachHangDialog.dispose();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        KhachHang kh = this.getFormData(true);
        KHRepo.update(kh);
        JOptionPane.showMessageDialog(this, "Sửa Thành Công");
        loadToTable(KHRepo.search(""));
        KhachHangDialog.dispose();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        KhachHangDialog.setVisible(false);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnAddPetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPetActionPerformed
         if(txtTenPet.getText().equals("") || txtLoaiPet.getText().equals("") || txtGiong.getText().equals("") 
                 || txtTuoi.getText().equals("") || txtcanNang.getText().equals("") || txtMaKHP.getText().equals("")){
             JOptionPane.showMessageDialog(this, "Mời Bạn Nhập Đầy Đủ Thông Tin");        
        }else{
            Pet pt = this.getFormDataPet(false);
            PTRepo.creat(pt);
            JOptionPane.showMessageDialog(this, "Thêm Thành Công");
            loadToTable1(PTRepo.search(""));
           PetDialog.dispose();
        }
    }//GEN-LAST:event_btnAddPetActionPerformed

    private void btnUpdatePetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePetActionPerformed
        Pet pt = this.getFormDataPet(true);
        PTRepo.update(pt);
        JOptionPane.showMessageDialog(this, "Sửa Thành Công");
        loadToTable1(PTRepo.search(""));
        PetDialog.dispose();
    }//GEN-LAST:event_btnUpdatePetActionPerformed

    private void btnExitPetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitPetActionPerformed
        PetDialog.setVisible(false);
    }//GEN-LAST:event_btnExitPetActionPerformed

    private void btnThemPetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPetActionPerformed
        newTable1();
        hienThiDialog1(false); 
        PetDialog.setLocationRelativeTo(null);
        PetDialog.setVisible(true);
    }//GEN-LAST:event_btnThemPetActionPerformed

    private void btnSuaPetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPetActionPerformed
        int index = tblPet.getSelectedRow();
        if (index == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn Pet để sửa");
        return;
        } 
        
        hienThiDialog1(true);
        PetDialog.setLocationRelativeTo(null);
        PetDialog.setVisible(true);
    }//GEN-LAST:event_btnSuaPetActionPerformed

    private void btnDeletePetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePetActionPerformed
        Pet pt = this.getFormDataPet(true);
        PTRepo.delete(pt.getMaPet());
        JOptionPane.showMessageDialog(this, "Xóa Thành Công");
        loadToTable1(PTRepo.search(""));
        hienThi1(0);
    }//GEN-LAST:event_btnDeletePetActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        newTable();
        hienThiDialog(false); 
        KhachHangDialog.setLocationRelativeTo(null);
        KhachHangDialog.setVisible(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int index = tblKhachHang.getSelectedRow();
        if (index == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa");
        return;
        } 
        
        hienThiDialog(true);
        KhachHangDialog.setLocationRelativeTo(null);
        KhachHangDialog.setVisible(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        KhachHang kh = this.getFormData(true);
        KHRepo.delete(kh.getMaKH());
        JOptionPane.showMessageDialog(this, "Xóa Thành Công");
        loadToTable(KHRepo.search(""));
        hienThi(0);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        int index = tblKhachHang.getSelectedRow();
        hienThi(index);
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void tblPetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPetMouseClicked
        int index = tblPet.getSelectedRow();
        hienThi1(index);
    }//GEN-LAST:event_tblPetMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog KhachHangDialog;
    private javax.swing.JDialog PetDialog;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddPet;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeletePet;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnExitPet;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaPet;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemPet;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdatePet;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoChuaTiem;
    private javax.swing.JRadioButton rdoDaTiem;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblPet;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGiong;
    private javax.swing.JTextField txtLoaiPet;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaKHP;
    private javax.swing.JTextField txtMaPet;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSearchKH;
    private javax.swing.JTextField txtSearchPet;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenPet;
    private javax.swing.JTextField txtTuoi;
    private javax.swing.JTextField txtcanNang;
    // End of variables declaration//GEN-END:variables
}
