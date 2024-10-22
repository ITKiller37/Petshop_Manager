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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

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
        addSearchListenerKho();
        addSearchListenerKhachHang();
        addSearchListenerDoanhThu();
        initDoanhThu();
        
        jScrollPane1.getViewport().setBackground(Color.decode("#eed4d4"));
        jScrollPane2.getViewport().setBackground(Color.decode("#eed4d4"));
        jScrollPane3.getViewport().setBackground(Color.decode("#eed4d4"));
        
        TableUtils.setHeaderStyle(tblThongKeSP);
        TableUtils.setHeaderStyle(tblThongKeKH);
        TableUtils.setHeaderStyle(tblThongKeDT);
        
        cboDoanhThu.addActionListener(e -> loadDoanhThu());
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
       private void addSearchListenerKho() {
        txtSearchKho.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filterTableKho();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterTableKho();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterTableKho();
        }
    });
}
       
       private void addSearchListenerKhachHang() {
        txtSearchKhachHang.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filterTableKhachHang();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterTableKhachHang();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterTableKhachHang();
        }
    });
}
       
         private void addSearchListenerDoanhThu() {
        txtSearchDoanhThu.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filterTableDoanhThu();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterTableDoanhThu();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterTableDoanhThu();
        }
    });
}
       
       private void filterTableKho() {
    String searchTextKho = txtSearchKho.getText().trim().toLowerCase();
    DefaultTableModel model = (DefaultTableModel) tblThongKeSP.getModel();
    
    // Chỉ cần tạo TableRowSorter một lần và sử dụng lại
    TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tblThongKeSP.getRowSorter();
    if (sorter == null) {
        sorter = new TableRowSorter<>(model);
        tblThongKeSP.setRowSorter(sorter);
    }

    try {
        if (searchTextKho.length() == 0) {
            // Nếu ô tìm kiếm rỗng, hiển thị tất cả các hàng
            sorter.setRowFilter(null);
        } else {
           RowFilter<DefaultTableModel, Integer> customFilter = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String tenSP = entry.getStringValue(0).toLowerCase(); // Ten SP
                    
                    // Kiểm tra xem searchText có tồn tại trong bất kỳ cột nào không
                    return tenSP.contains(searchTextKho);
                }
            };

            sorter.setRowFilter(customFilter);
        }
    } catch (Exception e) {
        e.printStackTrace(); // In ra thông tin lỗi để xử lý
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi lọc bảng: " + e.getMessage());
    }
}
       
       private void filterTableKhachHang() {
    String searchTextKhachHang = txtSearchKhachHang.getText().trim().toLowerCase();
    DefaultTableModel model = (DefaultTableModel) tblThongKeKH.getModel();
    
    // Chỉ cần tạo TableRowSorter một lần và sử dụng lại
    TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tblThongKeKH.getRowSorter();
    if (sorter == null) {
        sorter = new TableRowSorter<>(model);
        tblThongKeKH.setRowSorter(sorter);
    }

    try {
        if (searchTextKhachHang.length() == 0) {
            // Nếu ô tìm kiếm rỗng, hiển thị tất cả các hàng
            sorter.setRowFilter(null);
        } else {
           RowFilter<DefaultTableModel, Integer> customFilter = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String tenKH = entry.getStringValue(0).toLowerCase(); //Ten
                    String sdt = entry.getStringValue(1).toLowerCase(); //sdt
                    String diaChi = entry.getStringValue(2).toLowerCase(); //Dia chi
                    
                    // Kiểm tra xem searchText có tồn tại trong bất kỳ cột nào không
                    return tenKH.contains(searchTextKhachHang) || sdt.contains(searchTextKhachHang) || diaChi.contains(searchTextKhachHang);
                }
            };

            sorter.setRowFilter(customFilter);
        }
    } catch (Exception e) {
        e.printStackTrace(); // In ra thông tin lỗi để xử lý
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi lọc bảng: " + e.getMessage());
    }
}
       
         private void filterTableDoanhThu() {
    String searchTextDoanhThu = txtSearchDoanhThu.getText().trim().toLowerCase();
    DefaultTableModel model = (DefaultTableModel) tblThongKeDT.getModel();
    
    // Chỉ cần tạo TableRowSorter một lần và sử dụng lại
    TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tblThongKeDT.getRowSorter();
    if (sorter == null) {
        sorter = new TableRowSorter<>(model);
        tblThongKeDT.setRowSorter(sorter);
    }

    try {
        if (searchTextDoanhThu.length() == 0) {
            // Nếu ô tìm kiếm rỗng, hiển thị tất cả các hàng
            sorter.setRowFilter(null);      
        } else {
           RowFilter<DefaultTableModel, Integer> customFilter = new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String ngayMua = entry.getStringValue(0).toLowerCase(); //Ngay                    
                    
                    // Kiểm tra xem searchText có tồn tại trong bất kỳ cột nào không
                    return ngayMua.contains(searchTextDoanhThu); 
                }
            };

            sorter.setRowFilter(customFilter);
        }
    } catch (Exception e) {
        e.printStackTrace(); // In ra thông tin lỗi để xử lý
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi lọc bảng: " + e.getMessage());
    }
}
         
       public void initDoanhThu(){
           String[] data = {"Tất cả","Tháng","Năm"};
           DefaultComboBoxModel cboModel = new DefaultComboBoxModel(data);
           cboDoanhThu.setModel(cboModel);
       }
       
        private void loadDoanhThu() {
        String selectedOption = (String) cboDoanhThu.getSelectedItem();
        DefaultTableModel tblModel = (DefaultTableModel) tblThongKeDT.getModel();
        tblModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        try {
            if ("Tất cả".equals(selectedOption)) {
                // Gọi phương thức search() để load tất cả dữ liệu
                ds2 = TKDTRePo.search();
            } else if ("Năm".equals(selectedOption)) {
                // Lấy năm từ người dùng, có thể là một JTextField hoặc JComboBox
                int year = Integer.parseInt(JOptionPane.showInputDialog("Nhập năm:")); // Hiển thị dialog nhập năm
                ds2 = TKDTRePo.getDoanhThuByYear(year); // Gọi phương thức getDoanhThuByYear
            } else if ("Tháng".equals(selectedOption)) {
                // Lấy tháng và năm từ người dùng
                int month = Integer.parseInt(JOptionPane.showInputDialog("Nhập tháng:")); // Hiển thị dialog nhập tháng
                int year = Integer.parseInt(JOptionPane.showInputDialog("Nhập năm:")); // Hiển thị dialog nhập năm
                ds2 = TKDTRePo.getDoanhThuByMonth(month, year); // Gọi phương thức getDoanhThuByMonth
            }

            // Cập nhật bảng
            for (ThongKeDT tkdt : ds2) {
                Object[] data = {tkdt.getNgayTao(), tkdt.getTongHD(), tkdt.getTongDoanhThu(), tkdt.getCaoNhat(), tkdt.getTrungBinh(), tkdt.getThapNhat()};
                tblModel.addRow(data);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi tải dữ liệu doanh thu: " + ex.getMessage());
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
        txtSearchKho = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKeKH = new javax.swing.JTable();
        txtSearchKhachHang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThongKeDT = new javax.swing.JTable();
        cboDoanhThu = new javax.swing.JComboBox<>();
        txtSearchDoanhThu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

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

        txtSearchKho.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchKho, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtSearchKho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtSearchKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearchKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        cboDoanhThu.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cboDoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(cboDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
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
    private javax.swing.JComboBox<String> cboDoanhThu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JTextField txtSearchDoanhThu;
    private javax.swing.JTextField txtSearchKhachHang;
    private javax.swing.JTextField txtSearchKho;
    // End of variables declaration//GEN-END:variables
}
