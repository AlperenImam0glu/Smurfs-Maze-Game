public abstract  class Dusman extends Karakter {

    private int dusmanID;
    private String dusmanAdi, dusmanTür;

    public Dusman(int ıd, String ad, String tur, int xLokasyon, int ylokasyon, int dusmanID, String dusmanAdi, String dusmanTür) {
        super(ıd, ad, tur, xLokasyon, ylokasyon);
        this.dusmanID = dusmanID;
        this.dusmanAdi = dusmanAdi;
        this.dusmanTür = dusmanTür;
    }

    public int getDusmanID() {
        return dusmanID;
    }

    public void setDusmanID(int dusmanID) {
        this.dusmanID = dusmanID;
    }

    public String getDusmanAdi() {
        return dusmanAdi;
    }

    public void setDusmanAdi(String dusmanAdi) {
        this.dusmanAdi = dusmanAdi;
    }

    public String getDusmanTür() {
        return dusmanTür;
    }

    public void setDusmanTür(String dusmanTür) {
        this.dusmanTür = dusmanTür;
    }
}
