package pekan1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
Rekening akunAktif = null;
boolean isRunning = true;

System.out.println("=== SISTEM PERBAIKAN MINI ===");

while (isRunning) {
System.out.println("\nMenu Utama:");
System.out.println("1. Buka Rekening Baru");
System.out.println("2. Setor Tunai");
System.out.println("3. Tarik Tunai");
System.out.println("4. cek Informasi Rekening");
System.out.println("0. Keluar");
System.out.println("Pilih Menu");
int pilihan = input.nextInt();
input.nextLine();
switch (pilihan) { 
case 1: 
	System.out.println("Masukan No Rekening");
	String no = input.nextLine();
	System.out.println("Masukkan Nama Pemilik: ");
	String nama = input.nextLine();
	System.out.println("Masukkan Saldo Awal: ");
	double saldo = input.nextDouble();
	
	akunAktif =new Rekening(no, nama, saldo);
	break;
	
case 2: 
	if (akunAktif == null) {
		System.out.println("Error: mohon maaf, anda belum memiliki nomor rekening!");
	} else {
		System.out.println("Masukan nominal setor: ");
		double setor = input.nextDouble(); 
		akunAktif.setorTunai(setor);
	}
	break;
	
case 3: 
	System.out.println("Fitur ini akan kerjakan sebagai Tugas Mandiri.");
	break;
case 4:
	if (akunAktif == null) {
		System.out.println("Error: Anda belum membuka rekening!");
	} else { 
		akunAktif.cekInformasi();
	} 
	break;
	
case 0 : 
	isRunning = false;
	System.out.println("sistem ditutup. terima Kasih!");
	break;
	default: 
		System.out.println("Pilihan tidak valid");
		
	}
	
} 
input.close();
}

	} 
