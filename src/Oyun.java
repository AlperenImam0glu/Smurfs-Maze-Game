import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Gargamel g = new Gargamel(1,"Gargamel","düşman",0,0,1,"GARGAMEL","Gargamel");
// Azman A = new Azman(2,"Azman","düşman",0,0,2,"Azman","Azman");
// GozlukluSirin gS = new GozlukluSirin(3,"Sirin","GozlukluSirin",0,0,3,3,0,"Gozluklu Sirin");
// TembelSirin gS = new  TembelSirin(4,"Sirin","GozlukluSirin",0,0,4,4,0,"Tembel Sirin");
// Puan p = new Puan(0,"Puan","Puan",0,0,0,0,0,"Puan Sınıfı");
//Lokasyon l = new Lokasyon(0,0);

public class Oyun {
    static  String[] dusmanlartxt;
    public Oyun(char[][] dusmanlar, int[][] harita) throws InterruptedException {

        dusmanlartxt = new String[10];
        dusmanlartxt =dusmanKapi( charToString(dusmanlar));

        JFrame secenek =new JFrame("ŞİRİNLER LABİRENT");
        secenek.setSize(600,600);
        ImageIcon back= new ImageIcon("arkaplan.jpg");

        JLabel label = new JLabel("",back,JLabel.CENTER);
        label.setBounds(0,0,600,600);

        JLabel yazi= new JLabel("KARAKTER SEÇİN");
        yazi.setFont(new Font("Serif", Font.BOLD, 40));
        yazi.setBounds(100,50,365,45);

        Icon tembelsirin = new ImageIcon("/images/tembelsirin1.png");
        Icon gozluklusirin = new ImageIcon("/images/gozluklusirin1.png");

        JLabel yazi2= new JLabel(" GÖZLÜKLÜ SİRİN");
        yazi2.setFont(new Font("Serif", Font.BOLD, 18));
        yazi2.setBounds(93,430,170,50);
        yazi2.setVisible(true);

        JLabel yazi3= new JLabel(" TEMBEL SİRİN");
        yazi3.setFont(new Font("Serif", Font.BOLD, 19));
        yazi3.setBounds(325,430,150,50);
        yazi3.setVisible(true);

        yazi.setBackground(Color.white);
        yazi.setOpaque(true);

        yazi2.setBackground(Color.white);
        yazi2.setOpaque(true);

        yazi3.setBackground(Color.white);
        yazi3.setOpaque(true);

        JButton btn1 = new JButton();
        btn1.setIcon(gozluklusirin);
        btn1.setBounds(75,150,200,270);

        JButton btn2 = new JButton();
        btn2.setText("Tembel Şirin");
        btn2.setIcon(tembelsirin);
        btn2.setBounds(300,150,200,270);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Gozlüklü şrini seçildi");

                try {
                    Frame frame = new Frame(harita,dusmanlartxt,2);
                    secenek.setVisible(false);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Tembel şrini seçildi");

                try {
                    Frame frame = new Frame(harita,dusmanlartxt,1);
                    secenek.setVisible(false);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        secenek.add(btn1);
        secenek.add(btn2);
        secenek.add(yazi);
        secenek.add(yazi2);
        secenek.add(yazi3);
        secenek.add(label);
        secenek.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secenek.setBounds(450,125,600,600);
        secenek.setLayout(null);
        secenek.setVisible(true);


    }

    public String[] charToString(char[][] dusmanlar) {
        String[] cumleler = new String[10];

        for (int i = 0; i < 10; i++) {
            cumleler[i] = String.valueOf(dusmanlar[i]);
        }

        return cumleler;
    }

    public String[] dusmanKapi(String[] dusmanlartxt) {

        String[] kapilar = new String[10];
        int k = 0;

        for (int i = 0; i < 10; i++) {
            if (dusmanlartxt[i].contains("Gargamel")) {
                if (dusmanlartxt[i].contains("Kapi:A")) {
                    kapilar[k] = "GA";

                } else if (dusmanlartxt[i].contains("Kapi:B")) {
                    kapilar[k] = "GB";

                } else if (dusmanlartxt[i].contains("Kapi:C")) {
                    kapilar[k] = "GC";

                } else {
                    kapilar[k] = "GD";
                }
                k++;
            } else if (dusmanlartxt[i].contains("Azman")) {
                if (dusmanlartxt[i].contains("Kapi:A")) {
                    kapilar[k] = "AA";

                } else if (dusmanlartxt[i].contains("Kapi:B")) {
                    kapilar[k] = "AB";

                } else if (dusmanlartxt[i].contains("Kapi:C")) {
                    kapilar[k] = "AC";

                } else {
                    kapilar[k] = "AD";
                }
                k++;

            }

        }
        return kapilar;
    }

}
