package aplikasiperpustakaan;

import java.sql.SQLException;
import java.sql.ResultSet;
import aplikasiperpustakaan.Koneksi;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import com.mysql.jdbc.Statement;
import javax.swing.table.DefaultTableModel;

public class FrmAdmin extends javax.swing.JFrame {

    public DefaultTableModel model;
    String username, password, nama_lengkap, id;
    
    public FrmAdmin() {
        initComponents();
        model = new DefaultTableModel();
        tblAdmin.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nama Lengkap");
        model.addColumn("Username");
        model.addColumn("Password");
        
        getData();
        nonactive();
        
        txtId.setVisible(false);
        btnEdit.setEnabled(false);
        btnHapus.setEnabled(false);
        btnSimpan.setEnabled(false);
    }
    
    public void getData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try {
            int no = 1;
            Statement st = (Statement) Koneksi.koneksiDB().createStatement();
            String sql = "select * from admin";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                Object[] obj = new Object[4];
                obj[0] = rs.getString("id");
                obj[1] = rs.getString("nama_lengkap");
                obj[2] = rs.getString("username");
                obj[3] = rs.getString("password");
                
                model.addRow(obj);
            }
            
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void isianKosong() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtNamaLengkap.setText("");
        txtId.setText("");
        
        txtUsername.requestFocus();
    }
    
    public void nonactive() {
        txtUsername.setEnabled(false);
        txtPassword.setEnabled(false);
        txtNamaLengkap.setEnabled(false);
    }
    
    public void active(){
        txtUsername.setEnabled(true);
        txtPassword.setEnabled(true);
        txtNamaLengkap.setEnabled(true);
    }
    
    public void loadData() {
        username     = txtUsername.getText();
        password     = txtPassword.getText();
        nama_lengkap = txtNamaLengkap.getText();
        id           = txtId.getText();
    }
    
    public void selectData() {
        int i = tblAdmin.getSelectedRow();
        if(i==-1) {
            return;
        }
        
        txtId.setText(""+model.getValueAt(i, 0));
        txtUsername.setText(""+model.getValueAt(i, 2));
        txtPassword.setText(""+model.getValueAt(i, 3));
        txtNamaLengkap.setText(""+model.getValueAt(i, 1));
        
        active();
    }
    
    // Start logic button
    
    public void btnTambahEnabled() {
        btnTambah.setEnabled(false);
        btnEdit.setEnabled(false);
        btnHapus.setEnabled(false);
        
        btnSimpan.setEnabled(true);
    }
    
    public void btnSimpanEnabled() {
        btnSimpan.setEnabled(false);
        btnTambah.setEnabled(true);
    }
    
    public void tblAdminClick() {
        
        btnEdit.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(true);
        btnSimpan.setEnabled(false);
    }
    
    public void btnEditHapusEnabled() {
        
        btnEdit.setEnabled(false);
        btnHapus.setEnabled(false);
        btnTambah.setEnabled(true);
    }
    
    public void clearForm() {
        nonactive();
        isianKosong();
    }
    
    // End logic button
    
    
    
    public void saveData() {
        loadData();
        try {
            if(txtUsername.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Username tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtUsername.requestFocus();
            }else if(txtPassword.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtPassword.requestFocus();
            }else if(txtNamaLengkap.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nama lengkap tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtNamaLengkap.requestFocus();
            }else{
                Statement stat = (Statement)Koneksi.koneksiDB().createStatement();
                String sql = "insert into admin (username, password, nama_lengkap) "
                            + "values ('"+username+"','"+password+"','"+nama_lengkap+"')";
                PreparedStatement p = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
                p.executeUpdate();

                getData();
                clearForm();
                JOptionPane.showMessageDialog(null, "Data admin berhasil ditambahkan");
                
                btnSimpanEnabled();
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void editData() {
        loadData();
        try {
            if(txtUsername.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Username tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtUsername.requestFocus();
            }else if(txtPassword.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtPassword.requestFocus();
            }else if(txtNamaLengkap.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nama lengkap tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtNamaLengkap.requestFocus();
            }else{
                Statement st = (Statement)Koneksi.koneksiDB().createStatement();
                String sql = "update admin set username='"+username+"', password='"+password+"',"
                            + " nama_lengkap='"+nama_lengkap+"' where id='"+id+"' ";
                PreparedStatement ps = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
                ps.executeUpdate();

                getData();
                clearForm();
                JOptionPane.showMessageDialog(null, "Data admin berhasil diedit");
                
                btnEditHapusEnabled();
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void hapusData() {
        loadData();
        
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin akan menghapus data "+nama_lengkap+"?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        
        if(pesan == JOptionPane.OK_OPTION) {
            
            try {
                Statement state = (Statement)Koneksi.koneksiDB().createStatement();
                String sql = "delete from admin where id = '"+id+"'";
                PreparedStatement pst = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
                pst.executeUpdate();
                
                getData();
                clearForm();
                JOptionPane.showMessageDialog(null, "Data admin berhasil dihapus"); 
                
                btnEditHapusEnabled();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, error.getMessage());
            }
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtNamaLengkap = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAdmin = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setFont(new java.awt.Font("Calisto MT", 1, 24)); // NOI18N
        jLabel1.setText("Form Admin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(279, 279, 279))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel2.setText("Username");

        jLabel3.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel3.setText("Password");

        jLabel4.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel4.setText("Nama Lengkap");

        txtUsername.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtPassword.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtNamaLengkap.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        btnTambah.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnSimpan.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(txtNamaLengkap)
                    .addComponent(txtUsername))
                .addGap(87, 87, 87)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNamaLengkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblAdmin.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        tblAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAdminMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAdmin);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(728, 558));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        new FrmMenuUtama().show();
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        isianKosong();
        active();
        btnTambahEnabled();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        saveData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAdminMouseClicked
        selectData();
        tblAdminClick();
    }//GEN-LAST:event_tblAdminMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editData();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAdmin;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNamaLengkap;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
