package pekan3;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static Scanner input = new Scanner(System.in);
static ArrayList<Rekening> daftarRekening = new ArrayList<>();
    static double bacaAngka(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    static int bacaInt(String prompt) {
        return (int) bacaAngka(prompt);
    }

    static String bacaPin(String prompt) {
        while (true) {
            System.out.print(prompt);
            String pin = input.nextLine().trim();
            if (pin.matches("\\d{6}")) return pin;
            System.out.println("PIN harus tepat 6 digit angka!");
        }
    }

    public static void main(String[] args) {
        Rekening akunAktif = null;
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
            System.out.println("7. simulasi akhir bulan(kusus Rekening Tabungan)");
            System.out.println("0. Keluar");

            int pilihan = bacaInt("Pilih menu: ");

            // Menu 2-6 butuh rekening aktif
            if (pilihan >= 2 && pilihan <= 6 && akunAktif == null) {
                System.out.println("Error: Anda belum membuka rekening!");
                continue;
            }

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();

                    System.out.print("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();

                    String pin = bacaPin("Masukkan PIN (6 digit): ");

                    double saldo;
                    while ((saldo = bacaAngka("Masukkan Saldo Awal (min. 50.000): ")) < 50000) {
                        System.out.println("Nominal saldo awal harus minimal 50.000!");
                    }

                    int produk;
                    do {
                        produk = bacaInt("Pilih Produk:\n"+" 1. Tabungan Umum | 2. Giro Bisnis: ");
                        if (produk != 1 && produk != 2) {
                            System.out.println("Pilihan produk tidak valid!");
                        }
                    } while (produk != 1 && produk != 2);

                    if (produk == 1) {
                        double bunga = bacaAngka("Masukkan suku bunga (%): ");
                        akunAktif = new RekeningTabungan(no, nama, saldo, pin, bunga);
                    } else {
                        double overdraft = bacaAngka("Masukkan batas overdraft: ");
                        akunAktif = new RekeningGiro(no, nama, saldo, pin, overdraft);
                    }
                    break;

                case 2:
                    akunAktif.setorTunai(bacaAngka("Masukkan nominal setor: "));
                    break;

                case 3:
                    double tarik = bacaAngka("Masukkan nominal tarik (minimal 10 ribu): ");
                    String pinTarik = bacaPin("Masukkan PIN: ");
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
                    String pinMutasi = bacaPin("Masukkan PIN: ");
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
                        System.out.println("Fitur bunga hanya untuk rekening Tabungan!");
                    }
                    break; 

case 7:
    if (akunAktif instanceof RekeningTabungan) {
        RekeningTabungan tabungan = (RekeningTabungan) akunAktif;
        tabungan.tambahBungaAkhirBulan();
    } else {
        System.out.println("Gagal: Fitur bunga akhir bulan berlaku untuk Rekening Tabungan.");
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