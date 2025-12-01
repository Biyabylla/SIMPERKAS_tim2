/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelas;
import java.sql.*;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


/**
 *
 * @author Nabila
 */
public class transaksiPerkakas extends koneksi{
    private String idPeminjaman;
    private String namaSantri;
    private int nomorKamar;
    private String namaBarang;
    private int jumlahPinjam;
    private Date tanggalPinjam;
    private Date tanggalKembali;

    public void setIdPeminjaman(String id) {
        this.idPeminjaman = id;
    }

    public void setNamaSantri(String nama) {
        this.namaSantri = nama;
    }

    public void setNomorKamar(int kamar) {
        this.nomorKamar = kamar;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public void setJumlahPinjam(int jumlah) {
        this.jumlahPinjam = jumlah;
    }

    public void setTanggalPinjam(Date tgl) {
        this.tanggalPinjam = tgl;
    }

    public void setTanggalKembali(Date tgl) {
        this.tanggalKembali = tgl;
    }

    public boolean Tambah() {
        try (Connection conn = connect()) {
            String qSantri = "SELECT id_santri FROM santri WHERE nama_santri=? AND nomor_kamar=?";
            Integer idSantri = null;
            try (PreparedStatement ps = conn.prepareStatement(qSantri)) {
                ps.setString(1, namaSantri);
                ps.setInt(2, nomorKamar);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idSantri = rs.getInt("id_santri");
                    }
                }
            }

            if (idSantri == null) {
                String insertSantri = "INSERT INTO santri (nama_santri, nomor_kamar) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertSantri, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, namaSantri);
                    ps.setInt(2, nomorKamar);
                    ps.executeUpdate();
                    try (ResultSet gk = ps.getGeneratedKeys()) {
                        if (gk.next()) {
                            idSantri = gk.getInt(1);
                        }
                    }
                }
            }

            String qBarang = "SELECT id_barang, jumlah FROM barang WHERE nama_barang=?";
            String idBarang = null;
            int stok = 0;
            try (PreparedStatement ps = conn.prepareStatement(qBarang)) {
                ps.setString(1, namaBarang);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idBarang = rs.getString("id_barang");
                        stok = rs.getInt("jumlah");
                    } else {
                        return false;
                    }
                }
            }

            if (stok < jumlahPinjam) {
                return false;
            }

            String idTransaksi = "TR" + (System.currentTimeMillis() % 100000);
            String insertTrx = "INSERT INTO transaksi (id_peminjaman, id_santri, id_barang, jumlah_pinjam, tanggal_pinjam) VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(insertTrx)) {
                ps.setString(1, idTransaksi);
                ps.setInt(2, idSantri);
                ps.setString(3, idBarang);
                ps.setInt(4, jumlahPinjam);
                ps.setDate(5, new java.sql.Date(tanggalPinjam.getTime()));
                ps.executeUpdate();
            }
            String updateStok = "UPDATE barang SET jumlah=jumlah-? WHERE id_barang=?";
            try (PreparedStatement ps = conn.prepareStatement(updateStok)) {
                ps.setInt(1, jumlahPinjam);
                ps.setString(2, idBarang);
                ps.executeUpdate();
            }
             String cekStok = "SELECT jumlah FROM barang WHERE id_barang=?";
        try (PreparedStatement ps = conn.prepareStatement(cekStok)) {
            ps.setString(1, idBarang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt("jumlah") == 0) {
                    String updateStatus = "UPDATE barang SET status='Tidak Tersedia' WHERE id_barang=?";
                    try (PreparedStatement ps2 = conn.prepareStatement(updateStatus)) {
                        ps2.setString(1, idBarang);
                        ps2.executeUpdate();
                    }
                }
            }
        }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 public boolean Hapus() {
    try (Connection conn = connect()) {
        String selectTrx = "SELECT id_barang, jumlah_pinjam, tanggal_kembali FROM transaksi WHERE id_peminjaman=?";
        String idBarang = null;
        int jumlah = 0;
        Date tglKembali = null;

        try (PreparedStatement ps = conn.prepareStatement(selectTrx)) {
            ps.setString(1, idPeminjaman);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idBarang = rs.getString("id_barang");
                    jumlah = rs.getInt("jumlah_pinjam");
                    tglKembali = rs.getDate("tanggal_kembali");
                } else {
                    return false; // transaksi tidak ditemukan
                }
            }
        }

        // Jika belum dikembalikan, jangan dihapus
        if (tglKembali == null) {
            JOptionPane.showMessageDialog(null, "Transaksi gagal dihapus! Barang belum dikembalikan.");
            return false;
        }

        // Hapus transaksi
        String deleteTrx = "DELETE FROM transaksi WHERE id_peminjaman=?";
        try (PreparedStatement ps = conn.prepareStatement(deleteTrx)) {
            ps.setString(1, idPeminjaman);
            ps.executeUpdate();
        }

        // Update stok barang
        String updateStok = "UPDATE barang SET jumlah=jumlah+? WHERE id_barang=?";
        try (PreparedStatement ps = conn.prepareStatement(updateStok)) {
            ps.setInt(1, jumlah);
            ps.setString(2, idBarang);
            ps.executeUpdate();
        }

        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean Kembalikan() {
        try (Connection conn = connect()) {
            String selectTrx = "SELECT tanggal_pinjam, id_barang, jumlah_pinjam FROM transaksi WHERE id_peminjaman=?";
            Date tglPinjam = null;
            String idBarang = null;
            int jumlah = 0;
            try (PreparedStatement ps = conn.prepareStatement(selectTrx)) {
                ps.setString(1, idPeminjaman);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        tglPinjam = rs.getDate("tanggal_pinjam");
                        idBarang = rs.getString("id_barang");
                        jumlah = rs.getInt("jumlah_pinjam");
                    } else {
                        return false;
                    }
                }
            }

            if (tanggalKembali.before(tglPinjam)) {
                return false;
            }

            String updateTrx = "UPDATE transaksi SET tanggal_kembali=? WHERE id_peminjaman=?";
            try (PreparedStatement ps = conn.prepareStatement(updateTrx)) {
                ps.setDate(1, new java.sql.Date(tanggalKembali.getTime()));
                ps.setString(2, idPeminjaman);
                ps.executeUpdate();
            }

            String updateStok = "UPDATE barang SET jumlah = jumlah + ?, status = CASE WHEN jumlah + ? > 0 THEN 'Tersedia' ELSE 'Tidak Tersedia' END WHERE id_barang = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateStok)) {
                ps.setInt(1, jumlah);
                ps.setInt(2, jumlah); // untuk CASE WHEN
                ps.setString(3, idBarang);
                ps.executeUpdate();
            }


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void tampilkanData(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT t.id_peminjaman, s.nama_santri, s.nomor_kamar, b.nama_barang, t.jumlah_pinjam, t.tanggal_pinjam, t.tanggal_kembali "
                + "FROM transaksi t "
                + "LEFT JOIN santri s ON t.id_santri=s.id_santri "
                + "LEFT JOIN barang b ON t.id_barang=b.id_barang "
                + "ORDER BY t.tanggal_pinjam DESC";

        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_peminjaman"),
                    rs.getString("nama_santri"),
                    rs.getInt("nomor_kamar"),
                    rs.getString("nama_barang"),
                    rs.getInt("jumlah_pinjam"),
                    rs.getDate("tanggal_pinjam"),
                    rs.getDate("tanggal_kembali")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== LOGIKA CETAK PDF =====
    public void cetakPDF(javax.swing.JTable tableTransaksi) {
        try {
            String userHome = System.getProperty("user.home");
            File defaultFile = new File(userHome + File.separator + "Downloads" + File.separator + "Laporan_SIMPERKAS.pdf");

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Simpan Laporan PDF");
            chooser.setSelectedFile(defaultFile);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("File PDF", "pdf");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);

            int userSelection = chooser.showSaveDialog(null);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileToSave = chooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".pdf");
            }

            Rectangle ukuranF4 = new Rectangle(210 * 2.83f, 330 * 2.83f);
            Document document = new Document(ukuranF4, 36, 36, 54, 36);
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            Font fontJudul = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph judul = new Paragraph("LAPORAN\nPEMINJAMAN DAN PENGEMBALIAN PERKAKAS\nPONDOK PESANTREN MOJOSARI", fontJudul);
            judul.setAlignment(Element.ALIGN_CENTER);
            judul.setSpacingBefore(20f);
            judul.setSpacingAfter(40f);
            document.add(judul);

            Font fontInfo = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC, BaseColor.GRAY);
            Paragraph info = new Paragraph("Dicetak pada: "
                    + new java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm:ss").format(new java.util.Date()),
                    fontInfo);
            info.setAlignment(Element.ALIGN_RIGHT);
            info.setSpacingAfter(10f);
            document.add(info);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 2, 2, 2, 2, 2});

            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            String[] header = {"No", "Nama Santri", "Nomor Kamar", "Nama Barang", "Jumlah Dipinjam", "Waktu Pinjam", "Waktu Kembali"};
            for (String h : header) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontHeader));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new BaseColor(135, 206, 250));
                cell.setPadding(8f);
                cell.setBorderColor(BaseColor.BLACK);
                table.addCell(cell);
            }

            DefaultTableModel model = (DefaultTableModel) tableTransaksi.getModel();
            Font fontIsi = new Font(Font.FontFamily.TIMES_ROMAN, 11);

            for (int i = 0; i < model.getRowCount(); i++) {
                boolean belumDikembalikan = false;
                Object tglKembali = model.getValueAt(i, 6);
                if (tglKembali == null || tglKembali.toString().isEmpty()) {
                    tglKembali = "Belum Dikembalikan";
                    belumDikembalikan = true;
                }

                PdfPCell cellNo = new PdfPCell(new Phrase(String.valueOf(i + 1), fontIsi));
                cellNo.setPadding(6f);
                if (belumDikembalikan) {
                    cellNo.setBackgroundColor(BaseColor.YELLOW);
                }
                table.addCell(cellNo);

                for (int j = 1; j <= 6; j++) {
                    Object value = (j == 6) ? tglKembali : model.getValueAt(i, j);
                    PdfPCell cell = new PdfPCell(new Phrase(value != null ? value.toString() : "", fontIsi));
                    cell.setPadding(6f);
                    if (belumDikembalikan) {
                        cell.setBackgroundColor(BaseColor.YELLOW);
                    }
                    table.addCell(cell);
                }
            }

            document.add(table);
            document.add(new Paragraph("\n\n"));
            Paragraph tandaTangan = new Paragraph("Mengetahui,\nSie Perlengkapan\n\n\n\n(_____________)", fontIsi);
            tandaTangan.setAlignment(Element.ALIGN_RIGHT);
            document.add(tandaTangan);

            document.close();
            JOptionPane.showMessageDialog(null, "✅ Laporan berhasil dibuat!\nLokasi: " + fileToSave.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Gagal membuat laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cariData(DefaultTableModel model, String keyword) {
        model.setRowCount(0);
        String sql = "SELECT t.id_peminjaman, s.nama_santri, s.nomor_kamar, "
                + "b.nama_barang, COALESCE(t.jumlah_pinjam,0) as jumlah_pinjam, "
                + "t.tanggal_pinjam, t.tanggal_kembali "
                + "FROM transaksi t "
                + "LEFT JOIN santri s ON t.id_santri = s.id_santri "
                + "LEFT JOIN barang b ON t.id_barang = b.id_barang "
                + "WHERE LOWER(s.nama_santri) LIKE ? "
                + "OR LOWER(CAST(s.nomor_kamar AS CHAR)) LIKE ? "
                + "OR LOWER(b.nama_barang) LIKE ?";

        try (Connection conn = koneksi.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String q = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, q);
            ps.setString(2, q);
            ps.setString(3, q);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("id_peminjaman"),
                        rs.getString("nama_santri"),
                        rs.getString("nomor_kamar"),
                        rs.getString("nama_barang"),
                        rs.getInt("jumlah_pinjam"),
                        rs.getString("tanggal_pinjam"),
                        rs.getString("tanggal_kembali")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mencari data: " + e.getMessage());
        }

    }

    public int resetData() {
        String sql = "DELETE FROM transaksi WHERE tanggal_kembali IS NOT NULL";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Terjadi kesalahan saat mereset data: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return -1;
        }
    }
}
