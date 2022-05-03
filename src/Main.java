import javax.swing.*;
import java.io.*;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println();

        String[] okunan = new String[20];

        File dosya = new File("harita.txt");
        if (!dosya.exists()) {
            dosya.createNewFile();
        }

        FileReader fReader = new FileReader(dosya);
        String line;
        BufferedReader bReader = new BufferedReader(fReader);

        int j = 0;

        while ((line = bReader.readLine()) != null) {
            okunan[j] = line;
            j++;
        }

        System.out.println("==========DOSYADAN OKUNANLAR=========");
        char[][] chars2 = new char[50][50];
        chars2 = diziyeAktar(okunan);

        System.out.println("===========KARAKTERLER===============");
        char[][] dusmanlar = new char[10][30];
        dusmanlar = dusmanlariAyir(chars2);

        int k = 0;
        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 30; n++) {
                if (dusmanlar[m][n] != '$')
                    System.out.print(dusmanlar[m][n]);
                k = n;
            }
            if (dusmanlar[m][k] == '$') {
                System.out.println();
            }
        }

        System.out.println("===========HARİTA====================");
        int[][] harita;
        harita = haritaAyir(chars2);
        for (int m = 0; m < 11; m++) {
            for (int n = 0; n < 13; n++) {
                System.out.print(harita[m][n]);
            }
            System.out.println("");
        }

        System.out.println("=====================================");
        Oyun o = new Oyun(dusmanlar, harita);

        bReader.close();
    }


    public static char[][] diziyeAktar(String[] okunan) {

        char[] chars1 = new char[50];
        char[][] chars2 = new char[50][50]; // dosyadaki tüm verileri tutuyor.

        for (int i = 0; i < 50; i++) {
            chars1[i] = '$';
        }

        for (int m = 0; m < 50; m++) {
            for (int n = 0; n < 50; n++) {
                chars2[m][n] = '$';
            }
        }

        int k = 0;

        while (okunan[k] != null) {
            okunan[k].getChars(0, okunan[k].length(), chars1, 0);

            for (int n = 0; n < okunan[k].length(); n++) {
                chars2[k][n] = chars1[n];
            }
            k++;
        }
        return chars2;
    }

    public static char[][] dusmanlariAyir(char[][] okunan) {
        char[][] dusmanlar = new char[10][30];

        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 30; n++) {
                if (okunan[m][0] != '1' && okunan[m][0] != '0' && okunan[m][0] != '\t') {
                    dusmanlar[m][n] = okunan[m][n];
                } else {
                    dusmanlar[m][n] = '$';
                    break;
                }
            }
        }

        return dusmanlar;
    }

    public static int[][] haritaAyir(char[][] okunan) {
        char[][] harita = new char[11][13];
        int[][] harita2 = new int[11][13];

        int i = 0, j = 0;
        for (int m = 0; m < 50; m++) {
            for (int n = 0; n < 50; n++) {
                if (okunan[m][n] == '0' || okunan[m][n] == '1') {
                    if (okunan[m][n] != '$' || okunan[m][n] != '\t') {
                        harita[i][j] = okunan[m][n];
                        j++;
                    }
                }
            }
            if (j == 13) {
                i++;
            }
            j = 0;
        }

        for (int k = 0; k < 11; k++) {
            for (int l = 0; l < 13; l++) {
                int x = harita[k][l] - '0';
                harita2[k][l] = x;
            }
        }
        return harita2;
    }

}
