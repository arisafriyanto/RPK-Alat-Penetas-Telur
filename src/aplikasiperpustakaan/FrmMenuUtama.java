package aplikasiperpustakaan;

public class FrmMenuUtama extends javax.swing.JFrame {

    public FrmMenuUtama() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnLogin = new javax.swing.JMenuItem();
        mnLogout = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnFormBuku = new javax.swing.JMenuItem();
        mnFormMahasiswa = new javax.swing.JMenuItem();
        mnFormAdmin = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Calisto MT", 1, 36)); // NOI18N
        jLabel2.setText("Aplikasi Perpustakaan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jLabel2)
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jLabel2)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        jMenu1.setText("System");

        mnLogin.setText("Login");
        mnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLoginActionPerformed(evt);
            }
        });
        jMenu1.add(mnLogin);

        mnLogout.setText("Logout");
        jMenu1.add(mnLogout);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Master");

        mnFormBuku.setText("Form Buku");
        mnFormBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFormBukuActionPerformed(evt);
            }
        });
        jMenu2.add(mnFormBuku);

        mnFormMahasiswa.setText("Form Mahasiswa");
        mnFormMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFormMahasiswaActionPerformed(evt);
            }
        });
        jMenu2.add(mnFormMahasiswa);

        mnFormAdmin.setText("Form Admin");
        mnFormAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFormAdminActionPerformed(evt);
            }
        });
        jMenu2.add(mnFormAdmin);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Transaksi");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Report");
        jMenuBar1.add(jMenu4);

        jMenu5.setText("Exit");
        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(728, 558));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLoginActionPerformed
        new FrmLogin().show();
        this.dispose();
    }//GEN-LAST:event_mnLoginActionPerformed

    private void mnFormBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFormBukuActionPerformed
        new FrmBuku().show();
        this.dispose();
    }//GEN-LAST:event_mnFormBukuActionPerformed

    private void mnFormMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFormMahasiswaActionPerformed
        new FrmMahasiswa().show();
        this.dispose();
    }//GEN-LAST:event_mnFormMahasiswaActionPerformed

    private void mnFormAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFormAdminActionPerformed
        new FrmAdmin().show();
        this.dispose();
    }//GEN-LAST:event_mnFormAdminActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem mnFormAdmin;
    private javax.swing.JMenuItem mnFormBuku;
    private javax.swing.JMenuItem mnFormMahasiswa;
    private javax.swing.JMenuItem mnLogin;
    private javax.swing.JMenuItem mnLogout;
    // End of variables declaration//GEN-END:variables
}
