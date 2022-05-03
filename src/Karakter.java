public abstract class Karakter {
    private  int ıd;
    private  String ad , tur;
    private  int xLokasyon, Ylokasyon;

    public Karakter(int ıd, String ad, String tur, int xLokasyon, int ylokasyon) {
        this.ıd = ıd;
        this.ad = ad;
        this.tur = tur;
        this.xLokasyon = xLokasyon;
        Ylokasyon = ylokasyon;
    }

    public void enKısaYol(){

    }

    public int getId() {
        return ıd;
    }

    public void setId(int ıd) {
        this.ıd = ıd;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public int getxLokasyon() {
        return xLokasyon;
    }

    public void setxLokasyon(int xLokasyon) {
        this.xLokasyon = xLokasyon;
    }

    public int getYlokasyon() {
        return Ylokasyon;
    }

    public void setYlokasyon(int ylokasyon) {
        Ylokasyon = ylokasyon;
    }
}
