package aplikasiperpustakaan;

import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class FrmBuku extends javax.swing.JFrame {
    
    public DefaultTableModel model;
    String id, judul, pengarang, penerbit, isbn, lokasi;
    int tahun_terbit, jumlah_buku;

    public FrmBuku() {
        initComponents();
        model = new DefaultTableModel();
        tblBuku.setModel(model);
        model.addColumn("ID");
        model.addColumn("Judul");
        model.addColumn("Pengarang");
        model.addColumn("Penerbit");
        model.addColumn("Tahun Terbit");
        model.addColumn("ISBN");
        model.addColumn("Jumlah");
        model.addColumn("Lokasi");
        
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
            String sql = "select * from buku";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                Object[] obj = new Object[8];
                obj[0] = rs.getString("id");
                obj[1] = rs.getString("judul");
                obj[2] = rs.getString("pengarang");
                obj[3] = rs.getString("penerbit");
                obj[4] = rs.getString("tahun_terbit");
                obj[5] = rs.getString("isbn");
                obj[6] = rs.getString("jumlah_buku");
                obj[7] = rs.getString("lokasi");
                
                model.addRow(obj);
            }
            
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }    
    
    public void isianKosong() {
        txtId.setText("");
        txtJudul.setText("");
        txtPengarang.setText("");
        txtPenerbit.setText("");
        txtTahunTerbit.setText("");
        txtIsbn.setText("");
        txtJumlahBuku.setText("");
        cbLokasi.setSelectedItem("Rak 1");
        
        txtJudul.requestFocus();
    }
    
    public void nonactive() {
        txtJudul.setEnabled(false);
        txtPengarang.setEnabled(false);
        txtPenerbit.setEnabled(false);
        txtTahunTerbit.setEnabled(false);
        txtIsbn.setEnabled(false);
        txtJumlahBuku.setEnabled(false);
        cbLokasi.setEnabled(false);
    }
    
    public void active(){
        txtJudul.setEnabled(true);
        txtPengarang.setEnabled(true);
        txtPenerbit.setEnabled(true);
        txtTahunTerbit.setEnabled(true);
        txtIsbn.setEnabled(true);
        txtJumlahBuku.setEnabled(true);
        cbLokasi.setEnabled(true);
    }
    
    public void loadData() {
        id           = txtId.getText();
        judul        = txtJudul.getText();
        pengarang    = txtPengarang.getText();
        penerbit     = txtPenerbit.getText();
        tahun_terbit = Integer.parseInt(txtTahunTerbit.getText());
        isbn         = txtIsbn.getText();
        jumlah_buku  = Integer.parseInt(txtJumlahBuku.getText());
        lokasi       = (String)cbLokasi.getSelectedItem();
    }
    
    public void selectData() {
        int i = tblBuku.getSelectedRow();
        if(i==-1) {
            return;
        }
        
        txtId.setText(""+model.getValueAt(i, 0));
        txtJudul.setText(""+model.getValueAt(i, 1));
        txtPengarang.setText(""+model.getValueAt(i, 2));
        txtPenerbit.setText(""+model.getValueAt(i, 3));
        txtTahunTerbit.setText(""+model.getValueAt(i, 4));
        txtIsbn.setText(""+model.getValueAt(i, 5));
        txtJumlahBuku.setText(""+model.getValueAt(i, 6));
        cbLokasi.setSelectedItem(""+model.getValueAt(i, 7));
        
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
    
    public void tblBukuClick() {
        
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
            Statement stat = (Statement)Koneksi.koneksiDB().createStatement();
            String sql = "insert into buku (judul, pengarang, penerbit, tahun_terbit, isbn, jumlah_buku, lokasi) "
                        + "values ('"+judul+"','"+pengarang+"','"+penerbit+"','"+tahun_terbit+"','"+isbn+"','"+jumlah_buku+"','"+lokasi+"')";
            PreparedStatement p = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
            p.executeUpdate();
            
            getData();
            JOptionPane.showMessageDialog(null, "Data buku berhasil ditambahkan");
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void editData() {
        loadData();
        try {
            Statement st = (Statement)Koneksi.koneksiDB().createStatement();
            String sql = "update buku set "
                        + "judul='"+judul+"', pengarang='"+pengarang+"', penerbit='"+penerbit+"', tahun_terbit='"+tahun_terbit+"',"
                        + " isbn='"+isbn+"', jumlah_buku='"+jumlah_buku+"', lokasi='"+lokasi+"' where id='"+id+"' "; 
            PreparedStatement ps = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
            ps.executeUpdate();
            
            getData();
            JOptionPane.showMessageDialog(null, "Data buku berhasil diedit");
            selectData();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    public void hapusData() {
        loadData();
        
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin akan menghapus data "+judul+"?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        
        if(pesan == JOptionPane.OK_OPTION) {
            
            try {
                Statement state = (Statement)Koneksi.koneksiDB().createStatement();
                String sql = "delete from buku where id = '"+id+"'";
                PreparedStatement pst = (PreparedStatement)Koneksi.koneksiDB().prepareStatement(sql);
                pst.executeUpdate();
                
                getData();
                JOptionPane.showMessageDialog(null, "Data buku berhasil dihapus"); 
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtJudul = new javax.swing.JTextField();
        txtPengarang = new javax.swing.JTextField();
        txtPenerbit = new javax.swing.JTextField();
        txtIsbn = new javax.swing.JTextField();
        txtJumlahBuku = new javax.swing.JTextField();
        cbLokasi = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        txtTahunTerbit = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuku = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setFont(new java.awt.Font("Calisto MT", 1, 24)); // NOI18N
        jLabel1.setText("Form Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel2.setText("Judul Buku");

        jLabel3.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel3.setText("Pengarang");

        jLabel4.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel4.setText("Penerbit");

        jLabel5.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel5.setText("Tahun Terbit");

        jLabel6.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel6.setText("ISBN");

        jLabel7.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel7.setText("Jumlah");

        jLabel8.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        jLabel8.setText("Lokasi");

        txtJudul.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtPengarang.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtPenerbit.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtIsbn.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        txtJumlahBuku.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        cbLokasi.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cbLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rak 1", "Rak 2", "Rak 3", "Rak 4", "Rak 5" }));

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

        txtTahunTerbit.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

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
                    .addComponent(jLabel5))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPengarang, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                    .addComponent(txtJudul, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                    .addComponent(txtPenerbit, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                    .addComponent(txtTahunTerbit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(25, 25, 25))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJumlahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJumlahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cbLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTahunTerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblBuku.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        tblBuku.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBuku);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        clearForm();
        btnSimpanEnabled();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBukuMouseClicked
        selectData();
        tblBukuClick();
    }//GEN-LAST:event_tblBukuMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editData();
        clearForm();
        btnEditHapusEnabled();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
        clearForm();
        btnEditHapusEnabled();
    }//GEN-LAST:event_btnHapusActionPerformed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbLokasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBuku;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JTextField txtJumlahBuku;
    private javax.swing.JTextField txtPenerbit;
    private javax.swing.JTextField txtPengarang;
    private javax.swing.JTextField txtTahunTerbit;
    // End of variables declaration//GEN-END:variables
}
