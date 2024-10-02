/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;

import Models.HoaDon;
import Models.HoaDonChiTiet;
import Repositories.RP_KhachHang;
import Repositories.RP_LoaiDichVu;
import Repositories.RP_NhanVien;
import Repositories.RP_SanPham;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Models.KhachHang;
import Models.Pair;
import Print.ReportManager;
import Print.model.FieldReportPayment;
import Print.model.ParameterReportPayment;
import Repositories.RP_DichVu;
import Repositories.RP_HoaDon;
import Repositories.RP_LoaiSanPham;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Dang
 */
public class HoaDonView extends javax.swing.JPanel {
    RP_NhanVien NVRepo = new RP_NhanVien();
    RP_LoaiDichVu LDVRepo = new RP_LoaiDichVu();
    RP_SanPham SPRepo = new RP_SanPham();
    RP_KhachHang KHRepo = new RP_KhachHang();
    RP_DichVu DVRepo = new RP_DichVu();
    RP_HoaDon HDRepo = new RP_HoaDon();
    RP_LoaiSanPham LSPRepo = new RP_LoaiSanPham();
    ArrayList<HoaDon> ds = new ArrayList<>();
    private Map<String, Integer> tenNVMap = new HashMap<>();
    /**
     * Creates new form HoaDonView
     */
    public HoaDonView() {
        initComponents();
        fillCboTenNV();
        fillCboLdv();
        fillCboLsp();
        addListeners();
        setNgayTao();
        loadToTable();
        
        jScrollPane3.getViewport().setBackground(Color.decode("#eed4d4"));
        jScrollPane4.getViewport().setBackground(Color.decode("#eed4d4"));
        jScrollPane5.getViewport().setBackground(Color.decode("#eed4d4"));
        TableUtils.setHeaderStyle(tblThanhToan);
        TableUtils.setHeaderStyle(tblHoaDon);
        TableUtils.setHeaderStyle(tblHoaDonChiTiet);
        
        lstLdv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addItemToTable("DV",lstLdv.getSelectedValue());
            }
            
        });
        
        lstSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addItemToTable("SP",lstSanPham.getSelectedValue());
            }
            
        });
        
        cboLdv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLstLdv();
            }
        });
        
        cboLsp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLstLsp();
            }
        });
        
        tblHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblHoaDon.getSelectedRow();
                if (selectedRow != -1) {
                    int maHD = (int) tblHoaDon.getValueAt(selectedRow, 0); // Giả sử cột 0 chứa mã hóa đơn
                    displayChiTietHoaDon(maHD);
                }
            }
            
        });
         
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    
               LocalDateTime now = LocalDateTime.now();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
               String formattedDateTime = now.format(formatter);   
               DateTime.setText(formattedDateTime);
            }
        });
        timer.start();;
        //btnXoa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedRow();
            }
        });
    }
    private void addItemToTable(String loai, String tenHoatDong) {
         DefaultTableModel model = (DefaultTableModel) tblThanhToan.getModel();
         
         // Lấy mã tương ứng từ map
         Map<String, String> map = (loai.equals("DV")) ? 
        (Map<String, String>) lstLdv.getClientProperty("dichVuMap") :
        (Map<String, String>) lstSanPham.getClientProperty("sanPhamMap");
         
          String maHoatDong = map.get(tenHoatDong);
          
        if (maHoatDong == null) {
           return; // Nếu không tìm thấy mã, thoát
        }
        
         // Tìm kiếm dòng có mã hoạt động trùng
        boolean found = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingLoai = (String) model.getValueAt(i, 0);
            String existingMaHoatDong = (String) model.getValueAt(i, 1);
            
            if (existingLoai.equals(loai) && existingMaHoatDong.equals(maHoatDong)) {
                // Tăng số lượng nếu mục đã tồn tại
                int currentSoLuong = (int) model.getValueAt(i, 2);
                model.setValueAt(currentSoLuong + 1, i, 2);
                found = true;
                break;
            }
        }
        
        if (!found) {
            // Nếu chưa có trong bảng, thêm dòng mới
            float gia = (loai.equals("DV")) ? DVRepo.getGia(maHoatDong) : SPRepo.getGia(maHoatDong);
            model.addRow(new Object[]{loai, maHoatDong, 1, gia});
        }
        updateTotal();
    }
    
    
    private void fillCboTenNV() {
        List<Pair<Integer, String>> danhSachNhanVien = NVRepo.getDanhSachNhanVien();
        cboTenNV.removeAllItems(); // Xóa tất cả item cũ (nếu có)
        tenNVMap.clear();
        
        for (Pair<Integer, String> nhanVien : danhSachNhanVien) {
        Integer maNV = nhanVien.getKey(); // Mã nhân viên dưới dạng Integer
        String tenNV = nhanVien.getValue(); // Tên nhân viên
        cboTenNV.addItem(tenNV); // Thêm tên vào combobox
        tenNVMap.put(tenNV, maNV); // Lưu tên và mã vào bản đồ
    }
    }
    
    private void setNgayTao() {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // hoặc "dd/MM/yyyy"
    txtNgayTao.setText(today.format(formatter));
}
    
    private void fillCboLdv() {
    List<String> danhSachLoaiDichVu = LDVRepo.getDanhSachLoaiDichVu();
    cboLdv.removeAllItems();
    for (String loaiDichVu : danhSachLoaiDichVu) {
        cboLdv.addItem(loaiDichVu);
    }
}
    
    private void updateLstLdv() {
    String loaiDichVu = (String) cboLdv.getSelectedItem();
    List<Pair<String, String>> danhSachDichVu = LDVRepo.getDanhSachDichVuTheoLoai(loaiDichVu);;
    
    DefaultListModel<String> listModel = new DefaultListModel<>();
    Map<String, String> dichVuMap = new HashMap<>();
    
    for (Pair<String, String> service : danhSachDichVu) {
        dichVuMap.put(service.getValue(), service.getKey()); // Lưu tên là key, mã là value
        listModel.addElement(service.getValue()); // Thêm tên vào JList
    }
    
    
    lstLdv.setModel(listModel);
    lstLdv.putClientProperty("dichVuMap", dichVuMap);
}
    
    private void fillCboLsp() {
        List<String> danhSachLoaiSanPham = LSPRepo.getDanhSachLoaiSanPham();
        cboLsp.removeAllItems(); // Xóa tất cả item cũ (nếu có)
        for (String loaiSanPham : danhSachLoaiSanPham) {
           cboLsp.addItem(loaiSanPham); // Thêm loại sản phẩm vào combobox
        }
}
    
    private void updateLstLsp() {
     String loaiSanPham = (String) cboLsp.getSelectedItem(); // Lấy loại sản phẩm được chọn
     List<Pair<String, String>> danhSachSanPham = LSPRepo.getDanhSachSanPhamTheoLoai(loaiSanPham); // Lấy sản phẩm theo loại

     DefaultListModel<String> listModel = new DefaultListModel<>();
     Map<String, String> sanPhamMap = new HashMap<>();

     for (Pair<String, String> product : danhSachSanPham) {
        sanPhamMap.put(product.getValue(), product.getKey()); // Lưu tên là key, mã là value
        listModel.addElement(product.getValue()); // Thêm tên vào JList
     }

     lstSanPham.setModel(listModel); // Cập nhật JList với các sản phẩm tương ứng
     lstSanPham.putClientProperty("sanPhamMap", sanPhamMap); // Lưu map để truy xuất sau
}

    
    private void addListeners() {
        txtMaKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateKhachHangInfo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateKhachHangInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateKhachHangInfo();
            }
        });
    }

    private void updateKhachHangInfo() {
        String maKHText = txtMaKH.getText().trim();
        if (maKHText.isEmpty()) {
            // Nếu txtMaKH rỗng, xóa thông tin ở các ô khác
            txtTenKH.setText("");
            txtSdt.setText("");
            return;
        }

        try {
            int maKH = Integer.parseInt(maKHText);
            KhachHang kh = KHRepo.getKhachHangByMaKH(maKH);
            if (kh != null) {
                // Cập nhật thông tin khách hàng vào các ô text
                txtTenKH.setText(kh.getTenKH());
                txtSdt.setText(kh.getSdt());
            } else {
                // Nếu không tìm thấy khách hàng, xóa thông tin
                txtTenKH.setText("");
                txtSdt.setText("");
            }
        } catch (NumberFormatException e) {
            // Xử lý trường hợp mã khách hàng không phải là số hợp lệ
            txtTenKH.setText("");
            txtSdt.setText("");
        }
    }

    private void updateTotal() {
        DefaultTableModel model = (DefaultTableModel) tblThanhToan.getModel();
        double total = 0.0;

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());
                double price = Double.parseDouble(model.getValueAt(i, 3).toString());
                total += quantity * price;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        txtTongTien.setText(String.format("%.2f", total));
    }
    
    private void addTableListeners() {
        tblThanhToan.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateTotal();
            }
        });
    }
    
      private void removeSelectedRow() {
        int selectedRow = tblThanhToan.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) tblThanhToan.getModel();
            model.removeRow(selectedRow);
            updateTotal(); // Cập nhật tổng tiền sau khi xóa hàng
        }
    }
      
      public HoaDon getFormData(){
          String tenKH = txtTenKH.getText().trim();
          String tenNV = (String) cboTenNV.getSelectedItem();
          String ngayTao = txtNgayTao.getText().trim();
          float tongTien = Float.parseFloat(txtTongTien.getText().trim());
          String trangThai = "Chưa Thanh Toán";
          
          return new HoaDon(0, tenKH, tenNV, tongTien, ngayTao,trangThai);
      }
      
       public void loadToTable(){
        ds = HDRepo.search();
        DefaultTableModel tblModel =(DefaultTableModel) tblHoaDon.getModel();
        tblModel.setRowCount(0);
        for(HoaDon hd:ds){
            Object[] data ={hd.getMaHD(),hd.getTenNV(),hd.getTenKH(),hd.getTongTien(),hd.getNgayTao(),hd.getTrangThai()};
            tblModel.addRow(data);
        }
    }
       
       private int getMaNV() {
          String tenNV = (String) cboTenNV.getSelectedItem();
          Integer maNV = tenNVMap.get(tenNV); 

          if (maNV != null) {
             return maNV; 
         }
    return -1; 
    }
       
       private int getSelectedMaHD() {
         int selectedRow = tblHoaDon.getSelectedRow(); // Lấy chỉ số hàng được chọn
         if (selectedRow != -1) { // Kiểm tra nếu có hàng được chọn
         DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        
        return (int) model.getValueAt(selectedRow, 0); 
    }
        return -1;
}
       
       private void displayChiTietHoaDon(int maHD) {
         DefaultTableModel tblModel = (DefaultTableModel) tblHoaDonChiTiet.getModel();
         tblModel.setRowCount(0); // Xóa tất cả các hàng hiện có

         List<HoaDonChiTiet> chiTietList = HDRepo.getChiTietHoaDonByMaHD(maHD);
         for (HoaDonChiTiet chiTiet : chiTietList) {
             
             float thanhTien = chiTiet.getSoLuong() * chiTiet.getGia();
                
            String ten = chiTiet.getMaSP() != null ? SPRepo.getTenSanPham(chiTiet.getMaSP()) : 
                     (chiTiet.getMaDV() != null ? DVRepo.getTenDichVu(chiTiet.getMaDV()) : "");
            
           
        
        tblModel.addRow(new Object[]{chiTiet.getMaHDCT(), ten, chiTiet.getSoLuong(), chiTiet.getGia(), thanhTien});
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
    // Các phương thức khác và khai báo biến của lớp


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        DateTime = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboTenNV = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboLdv = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstLdv = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstSanPham = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThanhToan = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        btnAddHD = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        cboLsp = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnThanhToan = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();

        setBackground(new java.awt.Color(242, 227, 227));

        jTabbedPane4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(242, 227, 227));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("Đăng Ký Hóa Đơn");

        DateTime.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel2.setText("Nhân Viên:");

        cboTenNV.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cboTenNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setText("Ngày Tạo:");

        txtNgayTao.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setText("Mã Khách Hàng:");

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("Tên Khách Hàng:");

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("SDT:");

        txtMaKH.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        txtTenKH.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        txtSdt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel7.setText("Loại Dịch Vụ:");

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel8.setText("Loại Sản Phẩm:");

        cboLdv.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cboLdv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lstLdv.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(lstLdv);

        lstSanPham.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(lstSanPham);

        tblThanhToan.setFont(new java.awt.Font("Roboto", 0, 14));
        tblThanhToan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Loại", "Mã Hoạt Động", "Số Lượng", "Giá"
            }
        ));
        tblThanhToan.setRowHeight(25);
        tblThanhToan.setShowGrid(true);
        tblThanhToan.setShowVerticalLines(false);
        jScrollPane3.setViewportView(tblThanhToan);

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setText("Tổng Tiền:");

        txtTongTien.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        btnAddHD.setBackground(new java.awt.Color(255, 204, 204));
        btnAddHD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnAddHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-30.png"))); // NOI18N
        btnAddHD.setText("Tạo Hóa Đơn");
        btnAddHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHDActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 204, 204));
        btnXoa.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-30.png"))); // NOI18N
        btnXoa.setText("Xóa");

        btnNew.setBackground(new java.awt.Color(255, 204, 204));
        btnNew.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-refresh-30.png"))); // NOI18N
        btnNew.setText("Làm Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        cboLsp.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cboLsp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(46, 46, 46)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(54, 54, 54))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(cboLdv, 0, 189, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(cboLsp, 0, 181, Short.MAX_VALUE))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddHD)
                .addGap(38, 38, 38)
                .addComponent(btnXoa)
                .addGap(38, 38, 38)
                .addComponent(btnNew)
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddHD)
                            .addComponent(btnXoa)
                            .addComponent(btnNew))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboLdv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboLsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane4.addTab("Thanh Toán", jPanel1);

        jPanel2.setBackground(new java.awt.Color(242, 227, 227));

        tblHoaDon.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Tên Nhân Viên", "Tên Khách Hàng", "Tổng Tiền", "Ngày Tạo","Trạng Thái"
            }
        ));
        tblHoaDon.setRowHeight(25);
        tblHoaDon.setShowGrid(true);
        tblHoaDon.setShowVerticalLines(false);
        jScrollPane4.setViewportView(tblHoaDon);

        btnThanhToan.setBackground(new java.awt.Color(255, 204, 204));
        btnThanhToan.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-pay-30.png"))); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        tblHoaDonChiTiet.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HDCT", "Tên SP và DV", "Số Lượng", "Giá", "Thành Tiền"
            }
        ));
        tblHoaDonChiTiet.setRowHeight(25);
        tblHoaDonChiTiet.setShowGrid(true);
        tblHoaDonChiTiet.setShowVerticalLines(false);
        jScrollPane5.setViewportView(tblHoaDonChiTiet);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addGap(37, 37, 37))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(btnThanhToan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnThanhToan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        jTabbedPane4.addTab("Hóa Đơn", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 972, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHDActionPerformed
            HoaDon hd = this.getFormData();
            int maKH = Integer.parseInt(txtMaKH.getText());
            int maNV = getMaNV();
            int maHD = HDRepo.create(hd, maKH, maNV);
            
            DefaultTableModel thanhToanModel = (DefaultTableModel) tblThanhToan.getModel();
           for (int i = 0; i < thanhToanModel.getRowCount(); i++) {
               String loai = (String) thanhToanModel.getValueAt(i, 0);
               String maHoatDong = (String) thanhToanModel.getValueAt(i, 1);
               int soLuong = (int) thanhToanModel.getValueAt(i, 2);
               float gia = (float) thanhToanModel.getValueAt(i, 3);
                            
               
               // Lưu vào bảng hóa đơn chi tiết
            if (loai.equals("SP")) {
              // Nếu là sản phẩm, mã dịch vụ là null
              HDRepo.addChiTietHoaDon(maHD, maHoatDong, null, soLuong, gia);
            } else if (loai.equals("DV")) {
            // Nếu là dịch vụ, mã sản phẩm là null
              HDRepo.addChiTietHoaDon(maHD, null, maHoatDong, soLuong, gia);
            }
            
            }
            JOptionPane.showMessageDialog(this, "Tạo Thành Công");
            loadToTable();
    }//GEN-LAST:event_btnAddHDActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSdt.setText("");
        lstLdv.clearSelection();
        
        DefaultTableModel model = (DefaultTableModel) tblThanhToan.getModel();
        model.setRowCount(0);
        
        txtTongTien.setText("0.00");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        int selectedRow = tblHoaDon.getSelectedRow(); 
        if (selectedRow >= 0) {
        int maHD = (int) tblHoaDon.getValueAt(selectedRow, 0); 
        try {
            HDRepo.updateTrangThai(maHD, "Đã Thanh Toán"); // Cập nhật trạng thái
            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!");
            loadToTable(); // Tải lại bảng để xem trạng thái mới
            
             String staff = tblHoaDon.getValueAt(selectedRow, 1).toString(); 
            String customer = tblHoaDon.getValueAt(selectedRow, 2).toString(); 
            
            // Lấy thông tin khách hàng từ tên
            RP_KhachHang khachHangRepo = new RP_KhachHang();
            KhachHang customer1 = khachHangRepo.getCustomerByName(customer);
            String phone = (customer != null) ? customer1.getSdt() : "Không tìm thấy số điện thoại"; 
           
            double totalAmount = Double.parseDouble(tblHoaDon.getValueAt(selectedRow, 3).toString()); 

            List<FieldReportPayment> fields = new ArrayList<>();
            for (int i = 0; i < tblHoaDonChiTiet.getRowCount(); i++) {
                String productName = tblHoaDonChiTiet.getValueAt(i, 1).toString(); 
                int qty = Integer.parseInt(tblHoaDonChiTiet.getValueAt(i, 2).toString()); 
                double price = Double.parseDouble(tblHoaDonChiTiet.getValueAt(i, 3).toString()); 
                double total = Double.parseDouble(tblHoaDonChiTiet.getValueAt(i, 4).toString()); 
                fields.add(new FieldReportPayment(productName, qty, price, total));
            }
            
            int confirmPrint = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirmPrint == JOptionPane.YES_OPTION) {
                // Chuẩn bị dữ liệu để in hóa đơn
                ParameterReportPayment parameter = new ParameterReportPayment(staff, customer, phone, totalAmount, generateQrcode(), fields);

                // Gọi phương thức in báo cáo
                ReportManager.getInstance().compileReport();
                ReportManager.getInstance().printReportPayment(parameter);
            } else {
                JOptionPane.showMessageDialog(this, "Bạn đã chọn không in hóa đơn.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để thanh toán.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }
        
    }//GEN-LAST:event_btnThanhToanActionPerformed

     private InputStream generateQrcode() throws WriterException, IOException {
        NumberFormat nf = new DecimalFormat("0000000");
        Random ran = new Random();
        String invoice = nf.format(ran.nextInt(9999999) + 1);
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(invoice, BarcodeFormat.QR_CODE, 100, 100, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DateTime;
    private javax.swing.JButton btnAddHD;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLdv;
    private javax.swing.JComboBox<String> cboLsp;
    private javax.swing.JComboBox<String> cboTenNV;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JList<String> lstLdv;
    private javax.swing.JList<String> lstSanPham;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblThanhToan;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
