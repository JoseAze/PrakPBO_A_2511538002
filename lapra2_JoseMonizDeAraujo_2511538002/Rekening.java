
package pekan2;

import java.util.ArrayList;
import java.util.Locale;

public class Rekening {

    String nomorRekening;
    String namaPemilik;
    double saldo;

    ArrayList<Transaksi> riwayatTransaksi;

    public Rekening(String nomor, String nama, double saldoAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;

        this.riwayatTransaksi = new ArrayList<>();

        System.out.println(
            "Rekening atas nama " + namaPemilik
            + " berhasil dibuat dengan saldo Rp"
            + String.format(Locale.US, "%,.2f", saldo)
        );
    }

    public void tarikTunai(double nominal) {
        if (nominal < 10000) {
            System.out.println(
                "Transaksi Gagal: Minimal nominal penarikan Rp10.000"
            );
        } else if (nominal > saldo) {
            System.out.printf(
                Locale.US,
                "Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp%,.2f%n",
                saldo
            );
        } else {

            saldo -= nominal;

            String idTrx = "TRX-T-" + System.currentTimeMillis();

            Transaksi trxBaru =
                new Transaksi(idTrx, "Debit", nominal);

            riwayatTransaksi.add(trxBaru);

            System.out.printf(
                Locale.US,
                "Tarik tunai Rp%,.2f berhasil. Saldo Anda: Rp%,.2f%n",
                nominal,
                saldo
            );
        }
    }

    public void cekInformasi() {

        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);

        System.out.printf(
            Locale.US,
            "Saldo Akhir  : Rp%,.2f%n",
            saldo
        );

        System.out.println("--------------------");
    }

	public void setorTunai(double setor) {
		// TODO Auto-generated method stub
		
	}
}
