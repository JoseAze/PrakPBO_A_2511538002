package pekan3; 
import java.util.ArrayList;

public class Rekening { 
	private String nomorRekening;
	private String namaPemilik;
	private double saldo;
	private String pin;
	
	private ArrayList<Transaksi> riwayatTransaksi;
	
	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal; 
		
		if (pinAwal.length() == 6 ) { 
			this.pin = pinAwal; 
		} else { 
			System.out.println("peringatan: PIN harus 6 digit! menggunakan PIN default 123456");
			this.pin ="123456"; 
		} 
		this.riwayatTransaksi = new ArrayList<> ();
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat.");
		
			
		}
	public String getNomorRekening() { return nomorRekening;} 
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
	
	public void tarikTunai(double nominal) {
	    if (nominal > saldo) {
	        System.out.println("Saldo tidak mencukupi!");
	    } else {
	        saldo -= nominal;

	        String id = "TR" + (riwayatTransaksi.size() + 1);

	        Transaksi transaksi = new Transaksi(id, "Tarik", nominal);

	        riwayatTransaksi.add(transaksi);

	        System.out.println("Tarik tunai berhasil.");
	        System.out.println("Saldo sekarang: Rp" + saldo);
	    } 
	    
	    
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void cetakMutasi() {
		// TODO Auto-generated method stub
		
	}

}
