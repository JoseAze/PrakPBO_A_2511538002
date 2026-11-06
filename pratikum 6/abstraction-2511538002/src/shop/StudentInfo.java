package shop;
/**
 * Identity and personal parameters for the take-home assignment (Tugas).
 * 1. Write your NIM and name.
 * 2. Run: java -cp out tools.NimParams <your NIM>
 * 3. Copy the three printed values below.
 */
public final class StudentInfo {
    public static final String NIM = "2511538002";   
    public static final String NAME = "José Moniz de Araújo";  

    public static final int CARD_ADMIN_PERCENT = 2;     // Sesuaikan jika ada ketentuan angka dari tools
    public static final long TRANSFER_ADMIN_FEE = 2500; // Sesuaikan jika ada ketentuan angka dari tools
    public static final int SMS_MAX_LENGTH = 160;       // Sesuaikan jika ada ketentuan angka dari tools

    private StudentInfo() {
        // constants only; no objects
    }
}