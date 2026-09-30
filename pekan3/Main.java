package pekan3;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Rekening akunAktif = null;
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("6. cetak mustrasi");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu: ");
			
			int pilihan = input.nextInt();
			input.nextLine();
			
			switch (pilihan) {
				case 1:
					System.out.print("Masukkan No Rekening: ");
					String no = input.nextLine();
					System.out.print("Masukkan Nama Pemilik: ");
					String nama = input.nextLine();
					
					System.out.println("Masukan PIN (6 digit): ");
					String pin = input.nextLine();
					
				
					double saldo = 0;
					while (true) {
						System.out.print("Masukkan Saldo Awal (min. 50.000): ");
						saldo = input.nextDouble();
						
						if (saldo >= 50000) {
							break;
						} else {
							System.out.println("Nominal saldo awal harus minimal 50.000!");
						}
					}
					
					akunAktif = new Rekening(no, nama, saldo, pin);
					break;
				
				case 2:
					if (akunAktif == null) {
						System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening");
					} else {
						System.out.print("Masukkan nominal setor: ");
						double setor = input.nextDouble();
						akunAktif.setorTunai(setor);
					}
					break;
				
				case 3: // Fitur Tarik Tunai
					System.out.println("Masukan nominal Yang harus ditarik(minimal 10 ribu):");
					double tarik = input.nextDouble();
					input.nextLine();
					System.out.println("Masukan PIN");
					String pintarik= input.nextLine();
					if(akunAktif.otentikasi(pintarik)) {  
						akunAktif.tarikTunai(tarik);
					} else { 
						System.out.println("Akses ditolak : PIN yang anda masukkan salah!");
						
					} 
					break;
					
				case 4: // Fitur Cek Informasi Rekening
					if (akunAktif == null) {
						System.out.println("Error: Anda belum membuka rekening!");
					} else {
						akunAktif.cekInformasi();
					}
					break; 
					
				case 6:

				    String pinmutasi = input.nextLine();

				    if(akunAktif.otentikasi(pinmutasi)) { 
				        akunAktif.cetakMutasi();
				    }else {
				        System.out.println("Akses ditolak : PIN yang anda masukkan salah!");
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