package pekan5; 
import java.util.ArrayList;

public class Rekening { 
    private String nomorRekening;
    private String namaPemilik;
    protected double saldo;
    private String pin;
    
    protected ArrayList<Transaksi> riwayatTransaksi;
    
    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal; 
        
        if (pinAwal.length() == 6) { 
            this.pin = pinAwal; 
        } else { 
            System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
            this.pin = "123456"; 
        } 
        this.riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat.");
    }

    public String getNomorRekening() {return nomorRekening;} 
    public String getNamaPemilik() {return namaPemilik;}
    
    public boolean otentikasi(String inputPin) { 
        return this.pin.equals(inputPin);
    } 
    
    public void setorTunai(double nominal) {
        saldo += nominal;
        String id = "TR" + (riwayatTransaksi.size() + 1);
        Transaksi transaksi = new Transaksi(id, "Setor", nominal);
        riwayatTransaksi.add(transaksi);
        System.out.println("Setor tunai berhasil.");
        System.out.println("Saldo sekarang: Rp" + saldo);
    }  

    
    public void transfer(Rekening tujuan, double nominal, String pinInput) {
        if (!otentikasi(pinInput)) {
            System.out.println("Akses ditolak: PIN yang Anda masukkan salah!");
            return;
        }
        
        boolean statusTarik = this.tarikTunai(nominal);
        
        if (statusTarik) {
            tujuan.setorTunai(nominal);
            System.out.println("Transfer sebesar Rp" + nominal + " ke rekening " + tujuan.getNomorRekening() + " berhasil.");
        } else {
            System.out.println("Transfer gagal.");
        }
    }
    
    public void cekInformasi() {
        System.out.println("\n=== INFORMASI REKENING ===");
        System.out.println("No Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik: " + namaPemilik);
        System.out.println("Saldo       : Rp" + saldo);

        System.out.println("\n=== RIWAYAT TRANSAKSI ===");

        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            for (Transaksi transaksi : riwayatTransaksi) {
                transaksi.cetakDetail();
            }
        }
    }
    
    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
            return false; 
        } else if (nominal > saldo) { 
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda Rp" + saldo);
            return false;
        } else {
            saldo -= nominal;
            String idTrx = "TRX-T-" + System.currentTimeMillis();
            riwayatTransaksi.add(new Transaksi(idTrx, "Debit", nominal));
            System.out.println("Tarik tunai Rp" + nominal + " berhasil. Sisa saldo: Rp" + saldo);
            return true;
        } 
    }
    
    public void cetakMutasi() {
        System.out.println("\n=== CETAK MUTASI REKENING ===");
        System.out.println("No Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik: " + namaPemilik);
        System.out.println("----------------------------------------");
        
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada mutasi transaksi.");
        } else {
            for (Transaksi transaksi : riwayatTransaksi) {
                transaksi.cetakDetail();
            }
        }
        System.out.println("----------------------------------------");
        System.out.println("Saldo Saat Ini: Rp" + saldo);
    }
}