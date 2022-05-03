import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Frame extends JFrame implements KeyListener {

    List<Integer> path = new ArrayList<Integer>();
    List<Integer> gargamelYol = new ArrayList<Integer>();

    int kapi1[];
    int kapi2[];
    int renkKontrol[] = {9, 11};
    int karakterGucu = 0;
    int sirinID;
    int karakterLokasyon[] = {5, 6};
    int gargamelLokasyon[] = {0, 0};
    int azmanLokasyon[] = {0, 0};
    int ba = 0, ia = 0, ca = 0, ja = 0, mantarGorulmeSuresi = 0, uretilmeSuresi = 3; // mantar için
    int ba2 = 0, ia2 = 0, ca2 = 0, ja2 = 0, altinGorunmeSuresi = 0, uretilmeSuresi2 = 3; // altın için
    int karakterx = 5, karaktery = 6, skor = 20, mantarx = 0, mantary = 0;
    int dusmanSayisi=0;
    ImageIcon icon, icon2, icon3, icon4, icon5, icon6, icon7, icon8, icon9, icon10;
    /*icon = ERKEK şirin // oyun bitince win  icon2 = Şirine
    icon3 = mantar       icon4 = beyaz     icon5 = altin*/

    boolean renkKontrol2 = false, dusmanTuru = true;

    Dijkstra dijkstra;
    Objeler objeler[];

    Mantar mantarNesnesi;
    Altın altınNesnesi;

    JLabel karakterLabel, puan, mantarLabel;
    JLabel altinlabels[];
    JLabel[][] labels;

    int[][] maze;
    int[][] altin;

    boolean gargamelvar=false;
    boolean azmanvar=false;

    public Frame(int[][] maze, String[] dusmanlartxt, int sirinId) throws InterruptedException {
        this.maze = maze;
        this.setTitle("ŞİRİNLER LABİRENT");
        this.addKeyListener(this);

        //1 = gözlüklü 2= tembelsirin.

        sirinID = sirinId;
        labels = new JLabel[11][13];
        altinlabels = new JLabel[5];

        JButton btn1 = new JButton();
        btn1.setText("Gözlüklü Şirin");
        btn1.setBounds(75, 150, 200, 200);

        JButton btn2 = new JButton();
        btn2.setText("Tembel Şirin");
        btn2.setBounds(300, 150, 200, 200);

        maze[0][3] = 0;
        maze[0][10] = 0;
        maze[5][0] = 0;
        maze[10][3] = 0;

        ImageIcon back = new ImageIcon("arkaplan1.jpg");
        JLabel arka = new JLabel("", back, JLabel.CENTER);
        arka.setBounds(0, 0, 610, 555);

        icon = new ImageIcon("/images/sirin.png");
        icon2 = new ImageIcon("/images/sirine.png");
        icon3 = new ImageIcon("/images/mantar.png");
        icon4 = new ImageIcon("/images/beyaz.png");
        icon6 = new ImageIcon("/images/gözlüklüsirin.png");
        icon7 = new ImageIcon("/images/azman.png");
        icon8 = new ImageIcon("/images/gargamel.png");
        icon9 = new ImageIcon("/images/dusmanYolu.png");

        karakterLabel = new JLabel();
        karakterLabel.setIcon(icon);
        karakterLabel.setBounds(280, 240, 40, 40);
        karakterLabel.setVisible(true);

        puan = new JLabel();
        puan.setBounds(250, 0, 150, 50);
        puan.setFont(new Font("Serif", Font.BOLD, 20));
        puan.setText("SKOR : " + String.valueOf(skor));
        puan.setVisible(true);
        this.add(puan);

        mantarLabel = new JLabel();
        mantarLabel.setIcon(icon3);
        mantarLabel.setVisible(false);
        mantarLabel.setBounds(0, 0, 40, 40);
        this.add(mantarLabel);

        for (int i = 0; i <10 ; i++) {
            if(dusmanlartxt[i]!=null){
                dusmanSayisi++;
            }
        }
        System.out.println("DusmanSayisi ="+dusmanSayisi);



        if(dusmanSayisi==1){
            gargamelvar = dusmanBul(dusmanlartxt,1);
            azmanvar = dusmanBul(dusmanlartxt,2);
        }

        if (gargamelvar){
            System.out.println("GARGAMEL VAR");
        }
        if(azmanvar){
            System.out.println("AZMAN VAR");
        }



        for (int i = 0; i < 5; i++) {
            JLabel altin = new JLabel();
            altin.setBounds(0, 0, 40, 40);
            altin.setVisible(false);
            altinlabels[i] = altin;
            this.add(altin);
        }

        if (sirinID == 1) {
            TembelSirin tembelSirin = new TembelSirin(4, "Sirin", "tembelSirin", 0, 0, 4, 4, 0, "Tembel Sirin", 0);
            karakterGucu = tembelSirin.getGuc();
        } else if (sirinID == 2) {
            GozlukluSirin gozlukluSirin = new GozlukluSirin(3, "Sirin", "GozlukluSirin", 0, 0, 3, 3, 0, "Gozluklu Sirin", 5);
            karakterGucu = gozlukluSirin.getGuc();
            karakterLabel.setIcon(icon6);
        }

        Gargamel gargamel = new Gargamel(1, "Gargamel", "düşman", 0, 0, 1, "GARGAMEL", "Gargamel");
        Azman azman = new Azman(2, "Azman", "düşman", 0, 0, 2, "Azman", "Azman");

        this.add(karakterLabel);

        int pikselBoyutu = 40;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 13; j++) {
                JLabel l2 = new JLabel();
                labels[i][j] = l2;
                l2.setBackground(Color.white);
                l2.setOpaque(true);
                l2.setBorder(BorderFactory.createLineBorder(Color.black));
                l2.setBounds(pikselBoyutu * j + 40, pikselBoyutu * i + 40, 40, 40);
                this.add(l2);
            }
        }


        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 12; j++) {
                if (maze[i][j] == 0) {
                    labels[i][j].setBackground(Color.gray.darker());
                }
                if (i == 0 && maze[i][j] == 1 || i == 5 && j == 0) {
                    labels[i][j].setBackground(Color.PINK);

                }
                if (i == 7 && j == 12) {
                    labels[i][j].setIcon(icon2);
                }
            }
        }

        labels[10][3].setBackground(Color.PINK);
        labels[0][3].setBackground(Color.PINK);
        labels[0][10].setBackground(Color.PINK);
        labels[5][0].setBackground(Color.PINK);

        altin = new int[5][2];
        for (int j = 0; j < 5; j++) {
            altin[j][0] = 0;
            altin[j][1] = 0;
        }

        kapi1 = new int[2];
        kapi1[0] = 0;
        kapi1[1] = 0;

        kapi2 = new int[2];
        kapi2[0] = 0;
        kapi2[1] = 0;


        kapi1 = kapiBul(dusmanlartxt, 1);

        gargamel.setxLokasyon(kapi1[0]);
        gargamel.setYlokasyon(kapi1[1]);

        gargamelLokasyon[0] = kapi1[0];
        gargamelLokasyon[1] = kapi1[1];
        labels[gargamel.getxLokasyon()][gargamel.getYlokasyon()].setIcon(icon8);

        dijkstra = new Dijkstra();
        List<Vertex> dugumler;

        if(gargamelvar && dusmanSayisi==1) {
            dugumler = dijkstra.Dijkstra(gargamelLokasyon, karakterLokasyon);
            System.out.println("gargagamel");

            for (int i = 0; i < dugumler.size(); i++) {
                int x1 = dugumler.get(i).getVertexX();
                int y1 = dugumler.get(i).getVertexY();
                if (i == 0) {
                    labels[x1][y1].setBackground(Color.black);
                } else {
                    labels[x1][y1].setBackground(Color.RED);
                }
            }
        }


        kapi2 = kapiBul(dusmanlartxt, 2);
        azman.setxLokasyon(kapi2[0]);
        azman.setYlokasyon(kapi2[1]);

        azmanLokasyon[0] = kapi2[0];
        azmanLokasyon[1] = kapi2[1];
        labels[azman.getxLokasyon()][azman.getYlokasyon()].setIcon(icon7);


        if(azmanvar && dusmanSayisi==1) {
            dugumler = dijkstra.Dijkstra(azmanLokasyon, karakterLokasyon);

            System.out.println("azmanvar");
            for (int i = 0; i < dugumler.size(); i++) {
                int x1 = dugumler.get(i).getVertexX();
                int y1 = dugumler.get(i).getVertexY();
                if (i == 0) {
                    labels[x1][y1].setBackground(Color.orange);
                } else {
                    labels[x1][y1].setBackground(Color.yellow);
                }
            }
        }

        if(dusmanSayisi==2) {
            dugumler = dijkstra.Dijkstra(gargamelLokasyon, karakterLokasyon);

            for (int i = 0; i < dugumler.size(); i++) {
                int x1 = dugumler.get(i).getVertexX();
                int y1 = dugumler.get(i).getVertexY();
                if (i == 0) {
                    labels[x1][y1].setBackground(Color.black);
                } else {
                    labels[x1][y1].setBackground(Color.RED);
                }
            }
        }


        zamanSayaci(mantarLabel, altinlabels); // objelerin üretildiği yer


        this.add(arka);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(450, 125, 610, 555);
        this.setLayout(null);
        this.setVisible(true);


    }

    public int[] kapiBul(String[] dusmanlartxt, int gelen) {
        int kapilar[] = new int[2];
        kapilar[0] = 0;
        kapilar[1] = 0;

        for (int i = 0; i < 10; i++) {
            if (gelen == 1) {
                if (dusmanlartxt[i] != null) {
                    if (dusmanlartxt[i].contains("GA")) {
                        kapilar[0] = 0;
                        kapilar[1] = 3;

                    } else if (dusmanlartxt[i].contains("GB")) {
                        kapilar[0] = 0;
                        kapilar[1] = 10;

                    } else if (dusmanlartxt[i].contains("GC")) {
                        kapilar[0] = 5;
                        kapilar[1] = 0;

                    } else if (dusmanlartxt[i].contains("GD")) {
                        kapilar[0] = 10;
                        kapilar[1] = 3;

                    }
                }
            } else if (gelen == 2) {
                if (dusmanlartxt[i] != null) {
                    if (dusmanlartxt[i].contains("AA")) {
                        kapilar[0] = 0;
                        kapilar[1] = 3;

                    } else if (dusmanlartxt[i].contains("AB")) {
                        kapilar[0] = 0;
                        kapilar[1] = 10;

                    } else if (dusmanlartxt[i].contains("AC")) {
                        kapilar[0] = 5;
                        kapilar[1] = 0;

                    } else if (dusmanlartxt[i].contains("AD")) {
                        kapilar[0] = 10;
                        kapilar[1] = 3;

                    }
                }

            }

        }
        return kapilar;
    }


    public boolean dusmanBul(String[] dusmanlartxt, int gelen) {

        for (int i = 0; i < 10; i++) {
            if (gelen == 1) {
                if (dusmanlartxt[i] != null) {
                    if (dusmanlartxt[i].contains("GA")) {
                        return true;

                    } else if (dusmanlartxt[i].contains("GB")) {
                        return true;

                    } else if (dusmanlartxt[i].contains("GC")) {
                        return true;
                    } else if (dusmanlartxt[i].contains("GD")) {
                        return true;

                    }
                }
            } else if (gelen == 2) {
                if (dusmanlartxt[i] != null) {
                    if (dusmanlartxt[i].contains("AA")) {
                        return true;

                    } else if (dusmanlartxt[i].contains("AB")) {
                        return true;

                    } else if (dusmanlartxt[i].contains("AC")) {
                        return true;

                    } else if (dusmanlartxt[i].contains("AD")) {
                        return true;

                    }
                }
            }
        }

        return false;
    }



    public void zamanSayaci(JLabel mantarLabel, JLabel altinlabels[]) {

        Random a2 = new Random();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ca = a2.nextInt(4) + 3;
                if (ja < uretilmeSuresi) {
                    System.out.println("ÜRETİM BEKLENİYOR " + (uretilmeSuresi - ja));
                    ja++;

                } else {
                    if (ia == 0) {
                        ba = a2.nextInt(20);
                        System.out.println("MANTAR ÜRETİLDİ ");

                        try {
                            mantar(mantarLabel);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ia++;
                    } else {
                        System.out.print(mantarGorulmeSuresi + " ");
                        mantarGorulmeSuresi++;
                        if (mantarGorulmeSuresi >= 7) {
                            System.out.println();
                            System.out.println("MANTAR YOK OLDU");
                            mantarLabel.setVisible(false);
                            if (mantarx != 0) {
                                labels[mantarx][mantary].setIcon(null);
                            }
                            mantarx = 0;
                            mantary = 0;
                            ia = 0;
                            mantarGorulmeSuresi = 0;
                            uretilmeSuresi = a2.nextInt(20);
                            ja = 0;
                        }

                    }
                }
            }
        }, 0, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second) bu alıntıdır.


        Random a3 = new Random();


        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                ca2 = a3.nextInt(4) + 3;
                if (ja2 < uretilmeSuresi2) {
                    System.out.println("ÜRETİM BEKLENİYOR " + (uretilmeSuresi2 - ja2));
                    ja2++;

                } else {
                    if (ia2 == 0) {
                        ba2 = a2.nextInt(20);
                        altinUret();
                        ia2++;
                    } else {
                        System.out.print(altinGorunmeSuresi + " ");
                        altinGorunmeSuresi++;
                        if (altinGorunmeSuresi >= 5) {
                            System.out.println();
                            System.out.println("ALTINLAR YOK OLDU");

                            for (int j = 0; j < 5; j++) {

                                altinlabels[j].setVisible(false);
                                altin[j][0] = 0;
                                altin[j][1] = 0;

                            }

                            ia2 = 0;
                            altinGorunmeSuresi = 0;
                            uretilmeSuresi2 = a2.nextInt(10);
                            ja2 = 0;
                        }

                    }
                }
            }
        }, 0, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second)

    }

    public void mantar(JLabel mantarLabel) throws InterruptedException {
        Random random = new Random();
        mantarNesnesi = new Mantar();

        mantarx = 0;
        mantary = 0;
        while (true) {
            mantarx = random.nextInt(11);
            mantary = random.nextInt(13);

            if (maze[mantarx][mantary] == 1 && mantarx != karakterx && mantarx > 0 && mantarx < 10 && mantary > 0 && mantary < 10) {
                break;
            }
        }

        mantarLabel.setLocation(40 + (mantary * 40), 40 + (mantarx * 40));
        mantarLabel.setVisible(true);
        icon3 = new ImageIcon("/images/mantar.png");

    }

    public void altinUret() {
        Random random = new Random();
        int ik = 0;
        altınNesnesi = new Altın(5);
        int altinx = 0, altiny = 0;
        while (true) {
            altinx = random.nextInt(11);
            altiny = random.nextInt(13);

            if (ik > 4) {
                break;
            } else if (maze[altinx][altiny] == 1 && altinx != karakterx) {
                if (altinx > 0 && altinx < 10 && altiny > 0 && altiny < 10) {
                    altin[ik][0] = altinx;
                    altin[ik][1] = altiny;
                    ik++;
                }
            }

        }
        icon5 = new ImageIcon("/images/altın.png");
        for (int j = 0; j < 5; j++) {
            altinlabels[j].setIcon(icon5);
            altinlabels[j].setBounds(45 + (altin[j][1] * 40), 40 + (altin[j][0] * 40), 40, 40);
            altinlabels[j].setVisible(true);
        }
    }


    public void dusmanHareket() {


        if (dusmanSayisi == 2) {
            if (dusmanTuru) {
                List<Vertex> dugumler1;
                List<Vertex> dugumler2;
                dugumler1 = dijkstra.Dijkstra(gargamelLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler1.size(); i++) {
                    int x1 = dugumler1.get(i).getVertexX();
                    int y1 = dugumler1.get(i).getVertexY();
                    labels[x1][y1].setBackground(Color.white);
                }

                gargamelLokasyon[0] = dugumler1.get(2).getVertexX();
                gargamelLokasyon[1] = dugumler1.get(2).getVertexY();

                karakterLokasyon[0] = karakterx;
                karakterLokasyon[1] = karaktery;
                dugumler2 = dijkstra.Dijkstra(gargamelLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler2.size(); i++) {
                    int x1 = dugumler2.get(i).getVertexX();
                    int y1 = dugumler2.get(i).getVertexY();
                    if (i == 0) {
                        labels[x1][y1].setBackground(Color.black);
                    } else {

                        labels[x1][y1].setBackground(Color.RED);
                    }
                }

            } else {
                List<Vertex> dugumler3;
                List<Vertex> dugumler4;
                dugumler3 = dijkstra.Dijkstra(azmanLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler3.size(); i++) {
                    int x1 = dugumler3.get(i).getVertexX();
                    int y1 = dugumler3.get(i).getVertexY();
                    labels[x1][y1].setBackground(Color.white);
                }

                azmanLokasyon[0] = dugumler3.get(1).getVertexX();
                azmanLokasyon[1] = dugumler3.get(1).getVertexY();


                karakterLokasyon[0] = karakterx;
                karakterLokasyon[1] = karaktery;
                dugumler4 = dijkstra.Dijkstra(azmanLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler4.size(); i++) {
                    int x1 = dugumler4.get(i).getVertexX();
                    int y1 = dugumler4.get(i).getVertexY();
                    if (i == 0) {
                        labels[x1][y1].setBackground(Color.orange);
                    } else {

                        labels[x1][y1].setBackground(Color.yellow);
                    }
                }
            }
        }


        if (dusmanSayisi == 1) {
            if (gargamelvar) {
                List<Vertex> dugumler1;
                List<Vertex> dugumler2;
                System.out.println("|||"+gargamelLokasyon[0]+" "+gargamelLokasyon[1]);
                System.out.println("|||"+karakterLokasyon[0]+" "+karakterLokasyon[1]);
                dugumler1 = dijkstra.Dijkstra(gargamelLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler1.size(); i++) {
                    int x1 = dugumler1.get(i).getVertexX();
                    int y1 = dugumler1.get(i).getVertexY();
                    labels[x1][y1].setBackground(Color.white);
                }
                System.out.println(dugumler1);

                gargamelLokasyon[0] = dugumler1.get(2).getVertexX();
                gargamelLokasyon[1] = dugumler1.get(2).getVertexY();

                karakterLokasyon[0] = karakterx;
                karakterLokasyon[1] = karaktery;
                dugumler2 = dijkstra.Dijkstra(gargamelLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler2.size(); i++) {
                    int x1 = dugumler2.get(i).getVertexX();
                    int y1 = dugumler2.get(i).getVertexY();
                    if (i == 0) {
                        labels[x1][y1].setBackground(Color.black);
                    } else {

                        labels[x1][y1].setBackground(Color.RED);
                    }
                }
                dusmanTuru=true;
            } else if(azmanvar) {
                List<Vertex> dugumler3;
                List<Vertex> dugumler4;
                dugumler3 = dijkstra.Dijkstra(azmanLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler3.size(); i++) {
                    int x1 = dugumler3.get(i).getVertexX();
                    int y1 = dugumler3.get(i).getVertexY();
                    labels[x1][y1].setBackground(Color.white);
                }

                azmanLokasyon[0] = dugumler3.get(1).getVertexX();
                azmanLokasyon[1] = dugumler3.get(1).getVertexY();


                karakterLokasyon[0] = karakterx;
                karakterLokasyon[1] = karaktery;
                dugumler4 = dijkstra.Dijkstra(azmanLokasyon, karakterLokasyon);

                for (int i = 0; i < dugumler4.size(); i++) {
                    int x1 = dugumler4.get(i).getVertexX();
                    int y1 = dugumler4.get(i).getVertexY();
                    if (i == 0) {
                        labels[x1][y1].setBackground(Color.orange);
                    } else {

                        labels[x1][y1].setBackground(Color.yellow);
                    }
                }
                dusmanTuru=false;
            }
        }


    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (labels[renkKontrol[0]][renkKontrol[1]].getBackground() == Color.BLACK && renkKontrol2 == true) {
            labels[renkKontrol[0]][renkKontrol[1]].setBackground(Color.white);

            renkKontrol2 = false;
        }
        switch (e.getKeyCode()) {
            case 37:
                if (sirinID == 1) {
                    if (maze[karakterx][karaktery - 1] == 1) {
                        karakterLabel.setLocation(karakterLabel.getX() - 40, karakterLabel.getY());
                        karaktery = karaktery - 1;
                        dusmanHareket();
                    }
                } else if (sirinID == 2) {
                    if (maze[karakterx][karaktery - 1] == 1 && maze[karakterx][karaktery - 2] == 1) {
                        karakterLabel.setLocation(karakterLabel.getX() - 80, karakterLabel.getY());
                        karaktery = karaktery - 2;
                        dusmanHareket();
                    }
                }

                break;
            case 38:
                if (sirinID == 1) {
                    if (maze[karakterx - 1][karaktery] == 1) {
                        karakterLabel.setLocation(karakterLabel.getX(), karakterLabel.getY() - 40);
                        karakterx = karakterx - 1;
                        dusmanHareket();
                    }
                } else if (sirinID == 2) {
                    if (maze[karakterx - 1][karaktery] == 1 && maze[karakterx - 2][karaktery] == 1) {
                        karakterLabel.setLocation(karakterLabel.getX(), karakterLabel.getY() - 80);
                        karakterx = karakterx - 2;
                        dusmanHareket();
                    }
                }

                break;
            case 39:
                if (sirinID == 1) {
                    if (maze[karakterx][karaktery + 1] == 1 || maze[karakterx][karaktery + 1] == 3 && karaktery <= 11) {
                        karakterLabel.setLocation(karakterLabel.getX() + 40, karakterLabel.getY());
                        karaktery = karaktery + 1;
                        dusmanHareket();
                    }
                } else if (sirinID == 2) {
                    if (maze[karakterx][karaktery + 1] == 1 && maze[karakterx][karaktery + 2] == 1) {
                        karakterLabel.setLocation(karakterLabel.getX() + 80, karakterLabel.getY());
                        karaktery = karaktery + 2;
                        dusmanHareket();
                    }
                }

                break;
            case 40:
                if (sirinID == 1) {
                    if (maze[karakterx + 1][karaktery] == 1) {
                        karakterLabel.setLocation(karakterLabel.getX(), karakterLabel.getY() + 40);
                        karakterx = karakterx + 1;
                        dusmanHareket();
                    }
                } else if (sirinID == 2)
                    if (maze[karakterx + 1][karaktery] == 1 && maze[karakterx + 2][karaktery] == 1) {
                        karakterLabel.setLocation(karakterLabel.getX(), karakterLabel.getY() + 80);
                        karakterx = karakterx + 2;
                        dusmanHareket();
                    }
                break;
        }

        if (karakterx == 7 && karaktery - 1 == 11) {
            System.out.println("TEBRİKLER");

            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 13; j++) {
                    labels[i][j].setVisible(false);
                }
            }

            for (int i = 0; i < 5; i++) {
                altinlabels[i].setVisible(false);
            }
            mantarLabel.setVisible(false);

            uretilmeSuresi = 5000;
            uretilmeSuresi2 = 5000;

            icon = new ImageIcon("/images/win.png");
            karakterLabel.setIcon(icon);
            puan.setBounds(150, 50, 500, 50);
            puan.setFont(new Font("Serif", Font.BOLD, 60));
            this.karakterLabel.setBounds(90, 140, 418, 300);
        }

        if (dusmanTuru) {
            if (karakterx == gargamelLokasyon[0] + 1 && karaktery == gargamelLokasyon[1] ||
                    karakterx == gargamelLokasyon[0] && karaktery == gargamelLokasyon[1] ||
                    karakterx == gargamelLokasyon[0] + 1 && karaktery == gargamelLokasyon[1] + 1 ||
                    karakterx == gargamelLokasyon[0] + 1 && karaktery == gargamelLokasyon[1] - 1 ||
                    karakterx == gargamelLokasyon[0] && karaktery == gargamelLokasyon[1] + 1 ||
                    karakterx == gargamelLokasyon[0] && karaktery == gargamelLokasyon[1] - 1 ||
                    karakterx == gargamelLokasyon[0] - 1 && karaktery == gargamelLokasyon[1] ||
                    karakterx == gargamelLokasyon[0] - 1 && karaktery == gargamelLokasyon[1] + 1 ||
                    karakterx == gargamelLokasyon[0] - 1 && karaktery == gargamelLokasyon[1] - 1) {

                renkKontrol[0] = gargamelLokasyon[0];
                renkKontrol[1] = gargamelLokasyon[1];

                if (labels[renkKontrol[0]][renkKontrol[1]].getBackground() == Color.BLACK) {
                    labels[renkKontrol[0]][renkKontrol[1]].setBackground(Color.white);
                }
                labels[karakterx][karaktery].setBackground(Color.BLACK);

                renkKontrol2 = true;

                if (sirinID == 1) {
                    skor -= 15;

                } else {
                    skor -= 15;
                    skor += karakterGucu;
                }
                puan.setText("SKOR : " + String.valueOf(skor));
                gargamelLokasyon[0] = kapi1[0];
                gargamelLokasyon[1] = kapi1[1];
                for (int i = 0; i < 11; i++) {
                    for (int j = 0; j < 13; j++) {
                        if (labels[i][j].getBackground() == Color.RED) {
                            labels[i][j].setBackground(Color.white);
                        }
                    }
                }
                dusmanTuru = false;
            }
        }

        if (karakterx == azmanLokasyon[0] && karaktery == azmanLokasyon[1] || karakterx == azmanLokasyon[0] - 1 && karaktery == azmanLokasyon[1] || karakterx == azmanLokasyon[0] && karaktery == azmanLokasyon[1] - 1) {
            labels[azmanLokasyon[0]][azmanLokasyon[1]].setBackground(Color.white);

            if (sirinID == 1) {
                skor -= 5;
            } else {
                skor -= 5;
                skor += +karakterGucu - 3;
            }
            System.out.println("PUAN ==" + skor);
            puan.setText("SKOR : " + String.valueOf(skor));
            azmanLokasyon[0] = kapi2[0];
            azmanLokasyon[1] = kapi2[1];

            dusmanTuru = true;
        }

        if (skor <= 0) {
            System.out.println("TEBRİKLER");

            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 13; j++) {
                    labels[i][j].setVisible(false);

                }
            }

            for (int i = 0; i < 5; i++) {
                altinlabels[i].setVisible(false);
            }
            mantarLabel.setVisible(false);

            uretilmeSuresi = 5000;
            uretilmeSuresi2 = 5000;
            icon10 = new ImageIcon("/images/gameover.png");

            karakterLabel.setIcon(icon10);
            karakterLabel.setBounds(75, 150, 400, 400);
            puan.setBounds(160, 50, 500, 50);
            puan.setFont(new Font("Serif", Font.BOLD, 60));
            this.karakterLabel.setBounds(110, 130, 418, 300);
        }

        if (karakterx == mantarx && karaktery == mantary) {
            skor += 50;
            System.out.println("PUAN ==" + skor);
            puan.setText("SKOR : " + String.valueOf(skor));
            mantarLabel.setVisible(false);
            mantarGorulmeSuresi = 7;
            mantarx = 0;
            mantary = 0;
        }

        for (int j = 0; j < 5; j++) {

            if (karakterx == altin[j][0] && karaktery == altin[j][1]) {
                skor += 5;
                puan.setText("SKOR : " + String.valueOf(skor));
                altinlabels[j].setVisible(false);
                altinlabels[j].setBounds(0, 0, 40, 40);
                altin[j][0] = 0;
                altin[j][1] = 0;
            }
        }

        labels[10][3].setBackground(Color.PINK);
        labels[0][3].setBackground(Color.PINK);
        labels[0][10].setBackground(Color.PINK);
        labels[5][0].setBackground(Color.PINK);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("tuşa basıldı: " + e.getKeyCode());
    }
}
