public abstract class Oyuncu extends Karakter {
    private  int oyuncuID,oyuncuTur;
    private int skor = 20 ;
    private int guc;
    private  String oyuncuAdi;

    public Oyuncu(int ıd, String ad, String tur, int xLokasyon, int ylokasyon, int oyuncuID, int oyuncuTur, int skor, String oyuncuAdi,int guc ) {
        super(ıd, ad, tur, xLokasyon, ylokasyon);
        this.oyuncuID = oyuncuID;
        this.oyuncuTur = oyuncuTur;
        this.skor = skor;
        this.oyuncuAdi = oyuncuAdi;
        this.guc=guc;

    }

    public void puaniGoster(){
        System.out.println(this.skor);
    }

    //============================================================================================================================
    public int getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(int oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public int getOyuncuTur() {
        return oyuncuTur;
    }

    public void setOyuncuTur(int oyuncuTur) {
        this.oyuncuTur = oyuncuTur;
    }

    public int getSkor() {
        return skor;
    }

    public void setSkor(int skor) {
        this.skor = skor;
    }

    public String getOyuncuAdi() {
        return oyuncuAdi;
    }

    public void setOyuncuAdi(String oyuncuAdi) {
        this.oyuncuAdi = oyuncuAdi;
    }

    public int getGuc() {
        return guc;
    }

    public void setGuc(int guc) {
        this.guc = guc;
    }



}
