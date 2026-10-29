import java.util.Scanner;

public class kompro {

    static final String[] KODE_WILAYAH = { "A", "B", "C", "D" };
    static final int[][] MATRIKS_JARAK = {
            { 0, 4, 0, 9 },
            { 4, 0, 6, 0 },
            { 0, 6, 0, 3 },
            { 9, 0, 3, 0 }
    };
    static final int TARIF_PER_KM_PER_KG = 3000;
    static final int BERAT_MIN = 1;
    static final int BERAT_MAKS = 20;

    static int cariIndeksWilayah(String[] daftarKode, String kode) {
        String cari = kode.trim();
        for (int i = 0; i < daftarKode.length; i++) {
            if (daftarKode[i].equalsIgnoreCase(cari)) {
                return i;
            }
        }
        return -1;
    }

    static int cariJarak(int[][] matriks, int indeksAsal, int indeksTujuan) {
        return matriks[indeksAsal][indeksTujuan];
    }

    static int hitungOngkir(int jarak, int berat) {
        return jarak * berat * TARIF_PER_KM_PER_KG;
    }

    static String formatRupiah(int nilai) {
        String angka = String.valueOf(nilai);
        StringBuilder sb = new StringBuilder();
        int hitung = 0;
        for (int i = angka.length() - 1; i >= 0; i--) {
            sb.append(angka.charAt(i));
            hitung++;
            if (hitung % 3 == 0 && i != 0) {
                sb.append('.');
            }
        }
        return "Rp" + sb.reverse().toString();
    }

    static int mintaWilayah(Scanner sc, String label) {
        while (true) {
            System.out.print("Masukkan kode wilayah " + label + " (A-D): ");
            String input = sc.nextLine();
            int indeks = cariIndeksWilayah(KODE_WILAYAH, input);
            if (indeks == -1) {
                System.out.println("Wilayah tidak valid.");
            } else {
                return indeks;
            }
        }
    }

    static int mintaTujuan(Scanner sc, int indeksAsal) {
        while (true) {
            int indeksTujuan = mintaWilayah(sc, "tujuan");
            if (indeksTujuan == indeksAsal) {
                System.out.println("Asal dan tujuan harus berbeda.");
            } else {
                return indeksTujuan;
            }
        }
    }

    static int mintaBerat(Scanner sc) {
        while (true) {
            System.out.print("Masukkan berat paket (" + BERAT_MIN + "-" + BERAT_MAKS + " kg): ");
            String input = sc.nextLine().trim();
            try {
                int berat = Integer.parseInt(input);
                if (berat >= BERAT_MIN && berat <= BERAT_MAKS) {
                    return berat;
                }
                System.out.println("Berat tidak valid. Harus bilangan bulat " + BERAT_MIN + "-" + BERAT_MAKS + " kg.");
            } catch (NumberFormatException e) {
                System.out.println("Berat tidak valid. Harus bilangan bulat " + BERAT_MIN + "-" + BERAT_MAKS + " kg.");
            }
        }
    }

    static boolean tanyaUlang(Scanner sc) {
        while (true) {
            System.out.print("Cek pengiriman lagi? (1 = ya, 0 = keluar): ");
            String input = sc.nextLine().trim();
            if (input.equals("1")) {
                return true;
            } else if (input.equals("0")) {
                return false;
            }
            System.out.println("Pilihan tidak valid. Masukkan 1 atau 0.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean ulang;

        System.out.println("=== Perhitungan Ongkir Antarwilayah ===");

        do {
            System.out.println();
            int indeksAsal = mintaWilayah(sc, "asal");
            int indeksTujuan = mintaTujuan(sc, indeksAsal);

            int jarak = cariJarak(MATRIKS_JARAK, indeksAsal, indeksTujuan);

            if (jarak == 0) {
                System.out.println("Rute langsung tidak tersedia.");
            } else {
                int berat = mintaBerat(sc);
                int ongkir = hitungOngkir(jarak, berat);

                System.out.println();
                System.out.println("--- Rincian Pengiriman ---");
                System.out.println("Wilayah asal   : " + KODE_WILAYAH[indeksAsal]);
                System.out.println("Wilayah tujuan : " + KODE_WILAYAH[indeksTujuan]);
                System.out.println("Jarak          : " + jarak + " km");
                System.out.println("Berat          : " + berat + " kg");
                System.out.println("Ongkir         : " + formatRupiah(ongkir));
            }

            System.out.println();
            ulang = tanyaUlang(sc);
        } while (ulang);

        System.out.println("Terima kasih.");
        sc.close();
    }
}