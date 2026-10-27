package pekan5;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Rekening> DaftarRekening = new ArrayList<Rekening>();
    private static Rekening akunAktif = null;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("=== SISTEM PERBANKAN MINI ===");

        while (isRunning) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Cetak Mutasi");
            System.out.println("6. Tambah Bunga Akhir Bulan (Tabungan)");
            System.out.println("7. Ganti / Pilih Rekening Aktif");
            System.out.println("8. Transfer ke Rekening Lain");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            
            int pilihan = input.nextInt();
            input.nextLine(); 

            
            if (((pilihan >= 2 && pilihan <= 6) || pilihan == 8) && akunAktif == null) {
                System.out.println("Error: Anda belum membuka atau memilih rekening!");
                continue;
            }

            switch (pilihan) {
                case 1:
                    System.out.println("Masukkan No Rekening: ");
                    String no = input.nextLine();
                    System.out.println("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();
                    System.out.println("Masukkan PIN Awal (6 digit): ");
                    String pin = input.nextLine();
                    System.out.println("Masukkan Saldo Awal: ");
                    double saldo = input.nextDouble();
                    System.out.println("Pilih Produk:\n"+
                                        "1. Tabungan Umum \n"+
                                        "2. Giro Bisnis ");
                    int pilihanJenisTabungan = input.nextInt();
                    if(pilihanJenisTabungan == 1) {
                        System.out.print("Masukkan Nominal Suku Bunga: ");
                        int sukuBunga = input.nextInt();
                        akunAktif = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
                        DaftarRekening.add(akunAktif);
                    }
                    else if(pilihanJenisTabungan == 2) {
                        System.out.print("Masukkan Nominal Batas Overdraft: ");
                        int batasOverdraft = input.nextInt();
                        akunAktif = new RekeningGiro(no, nama, saldo, pin, batasOverdraft);
                        DaftarRekening.add(akunAktif);
                    }
                    else {
                        System.out.println("Pilihan Tidak Sah!");
                    }
                    break;

                case 2:
                    System.out.print("Masukkan nominal setor: ");
                    double setor = input.nextDouble();
                    input.nextLine();
                    akunAktif.setorTunai(setor);
                    break;

                case 3:
                    System.out.print("Masukkan nominal tarik: ");
                    double tarik = input.nextDouble();
                    input.nextLine();
                    
                    System.out.print("Masukkan PIN: ");
                    String pinTarik = input.nextLine();
                    
                    if (akunAktif.otentikasi(pinTarik)) {
                        akunAktif.tarikTunai(tarik);
                    } else {
                        System.out.println("Akses ditolak: PIN yang Anda masukkan salah!");
                    }
                    break;

                case 4:
                    akunAktif.cekInformasi();
                    break;

                case 5:
                    System.out.print("Masukkan PIN: ");
                    String pinMutasi = input.nextLine();
                    if (akunAktif.otentikasi(pinMutasi)) {
                        akunAktif.cetakMutasi();
                    } else {
                        System.out.println("Akses ditolak: PIN yang Anda masukkan salah!");
                    }
                    break;

                case 6:
                    if (akunAktif instanceof RekeningTabungan) {
                        ((RekeningTabungan) akunAktif).tambahBungaAkhirBulan();
                    } else {
                        System.out.println("Gagal: Fitur bunga akhir bulan hanya untuk Rekening Tabungan.");
                    }
                    break;  

                case 7:
                    System.out.print("Masukkan No Rekening yang ingin diaktifkan: ");
                    String cariNo = input.nextLine();
                    boolean ditemukan = false;
                    for (Rekening r : DaftarRekening) {
                        if (r.getNomorRekening().equals(cariNo)) {
                            akunAktif = r;
                            ditemukan = true;
                            System.out.println("Berhasil beralih ke rekening milik: " + r.getNamaPemilik());
                            break;
                        }
                    }
                    if (!ditemukan) {
                        System.out.println("Rekening dengan nomor tersebut tidak ditemukan.");
                    }
                    break;

                case 8:
                    System.out.print("Masukkan No Rekening Tujuan: ");
                    String noTujuan = input.nextLine();
                    
                    Rekening rekeningTujuan = null;
                    for (Rekening r : DaftarRekening) {
                        if (r.getNomorRekening().equals(noTujuan)) {
                            rekeningTujuan = r;
                            break;
                        }
                    }

                    if (rekeningTujuan == null) {
                        System.out.println("Gagal: Rekening tujuan tidak ditemukan di dalam sistem!");
                    } else if (rekeningTujuan.getNomorRekening().equals(akunAktif.getNomorRekening())) {
                        System.out.println("Gagal: Tidak dapat mentransfer ke rekening sendiri!");
                    } else {
                        System.out.print("Masukkan nominal transfer: ");
                        double nominalTransfer = input.nextDouble();
                        input.nextLine();
                        
                        System.out.print("Masukkan PIN Anda: ");
                        String pinTransfer = input.nextLine();
                        
                        akunAktif.transfer(rekeningTujuan, nominalTransfer, pinTransfer);
                    }
                    break;

                case 0:
                    isRunning = false;
                    System.out.println("Sistem ditutup. Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
        input.close();
    }
}