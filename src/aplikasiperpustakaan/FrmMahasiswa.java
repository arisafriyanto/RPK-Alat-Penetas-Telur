package aplikasiperpustakaan;

import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class FrmMahasiswa extends javax.swing.JFrame {

    public DefaultTableModel model;
    String nim, nama, jenis_kelamin, kelas, prodi, id;
    
    public FrmMahasiswa() {
        initComponents();
        model = new DefaultTableModel();
        tblMahasiswa.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nim");
        model.addColumn("Nama");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Kelas");
        model.addColumn("Prodi");
        
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
            Statement st = (Statement) Koneksi.koneksiDB().createStatement();
            String sql = "select * from mahasiswa";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("id");
                obj[1] = rs.getString("nim");
                obj[2] = rs.getString("nama");
                obj[3] = rs.getString("jenis_kelamin");
                obj[4] = rs.getString("kelas");
                obj[5] = rs.getString("Prodi");
                
                model.addRow(obj);
            }
            
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void isianKosong() {
        txtId.setText("");
        txtNim.setText("");
        txtNamaMahasiswa.setText("");
        cbJenisKelamin.setSelectedItem("Laki - Laki");
        txtKelas.setText("");
        cbProdi.setSelectedItem("D3 Teknik Informatika");
        
        txtNim.requestFocus();
    }
    
    public void nonactive() {
        txtNim.setEnabled(false);
        txtNamaMahasiswa.setEnabled(false);
        cbJenisKelamin.setEnabled(false);
        txtKelas.setEnabled(false);
        cbProdi.setEnabled(false);
    }
    
    public void active(){
        txtNim.setEnabled(true);
        txtNamaMahasiswa.setEnabled(true);
        cbJenisKelamin.setEnabled(true);
        txtKelas.setEnabled(true);
        cbProdi.setEnabled(true);
    }
    
    public void loadData() {
        id            = txtId.getText();
        nim           = txtNim.getText();
        nama          = txtNamaMahasiswa.getText();
        jenis_kelamin = (String)cbJenisKelamin.getSelectedItem();
        kelas         = txtKelas.getText();
        prodi         = (String)cbProdi.getSelectedItem();
    }
    
    public void selectData() {
        int i = tblMahasiswa.getSelectedRow();
        if(i==-1) {
            return;
        }
        
        txtId.setText(""+model.getValueAt(i, 0));
        txtNim.setText(""+model.getValueAt(i, 1));
        txtNamaMahasiswa.setText(""+model.getValueAt(i, 2));
        cbJenisKelamin.setSelectedItem(""+model.getValueAt(i, 3));
        txtKelas.setText(""+model.getValueAt(i, 4));
        cbProdi.setSelectedItem(""+model.getValueAt(i, 5));
        
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
    
    public void tblMahasiswaClick() {
        
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
            if(txtNim.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nim tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtNim.requestFocus();
            }else if(txtNamaMahasiswa.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nama mahasiswa tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtNamaMahasiswa.requestFocus();
            }else if(cbJenisKelamin.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Jenis kelamin tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                cbJenisKelamin.requestFocus();
            }else if(txtKelas.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Kelas tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtKelas.requestFocus();
            }else if(cbProdi.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Prodi tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                cbProdi.requestFocus();
            }else{
                Statement stat = (Statement)Koneksi.koneksiDB().createStatement();
                String sql = "insert into mahasiswa (nim, nama, jenis_kelamin, kelas, prodi) "
                            + "values ('"+nim+"','"+nama+"','"+jenis_kelamin+"','"+kelas+"','"+prodi+"')";
                PreparedStatement p = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
                p.executeUpdate();

                getData();
                clearForm();
                JOptionPane.showMessageDialog(null, "Data mahasiswa berhasil ditambahkan");

                btnSimpanEnabled();
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    public void editData() {
        loadData();
        try {
            if(txtNim.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nim tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtNim.requestFocus();
            }else if(txtNamaMahasiswa.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nama mahasiswa tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtNamaMahasiswa.requestFocus();
            }else if(cbJenisKelamin.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Jenis kelamin tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                cbJenisKelamin.requestFocus();
            }else if(txtKelas.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Kelas tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                txtKelas.requestFocus();
            }else if(cbProdi.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Prodi tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                cbProdi.requestFocus();
            }else{
                Statement st = (Statement)Koneksi.koneksiDB().createStatement();
                String sql = "update mahasiswa set "
                        + "nim='"+nim+"', nama='"+nama+"', jenis_kelamin='"+jenis_kelamin+"', kelas='"+kelas+"', prodi='"+prodi+"'"
                        + "where id='"+id+"' ";
                PreparedStatement ps = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
                ps.executeUpdate();

                getData();
                clearForm();
                JOptionPane.showMessageDialog(null, "Data mahasiswa berhasil diedit");
                
                btnEditHapusEnabled();
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    public void hapusData() {
        loadData();
        
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin akan menghapus data "+nama+"?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        
        if(pesan == JOptionPane.OK_OPTION) {
            
            try {
                Statement state = (Statement)Koneksi.koneksiDB().createStatement();
                String sql = "delete from mahasiswa where id = '"+id+"'";
                PreparedStatement pst = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
                pst.executeUpdate();
                
                getData();
                clearForm();
                JOptionPane.showMessageDialog(null, "Data mahasiswa berhasil dihapus"); 
                
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNim = new javax.swing.JTextField();
        txtNamaMahasiswa = new javax.swing.JTextField();
        txtKelas = new javax.swing.JTextField();
        cbProdi = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        cbJenisKelamin = new javax.swing.JComboBox<>();
        txtId = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMahasiswa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setFont(new java.awt.Font("Calisto MT", 1, 24)); // NOI18N
        jLabel1.setText("Form Mahasiswa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel2.setText("Nim");

        jLabel3.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel3.setText("Nama Mahasiswa");

        jLabel4.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel4.setText("Jenis Kelamin");

        jLabel5.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel5.setText("Kelas");

        jLabel6.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel6.setText("Prodi");

        txtNim.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtNamaMahasiswa.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtKelas.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        cbProdi.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cbProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "D3 Teknik Informatika", "D3 Komputerisasi Akuntansi", "S1 Teknik Informatika", "S1 Sistem Informasi" }));

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

        cbJenisKelamin.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cbJenisKelamin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki - Laki", "Perempuan" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKelas)
                    .addComponent(cbProdi, 0, 179, Short.MAX_VALUE)
                    .addComponent(txtNamaMahasiswa)
                    .addComponent(txtNim)
                    .addComponent(cbJenisKelamin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(89, 89, 89))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNamaMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblMahasiswa.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        tblMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMahasiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMahasiswa);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void tblMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMahasiswaMouseClicked
        selectData();
        tblMahasiswaClick();
    }//GEN-LAST:event_tblMahasiswaMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editData();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMahasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbJenisKelamin;
    private javax.swing.JComboBox<String> cbProdi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMahasiswa;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKelas;
    private javax.swing.JTextField txtNamaMahasiswa;
    private javax.swing.JTextField txtNim;
    // End of variables declaration//GEN-END:variables
}
