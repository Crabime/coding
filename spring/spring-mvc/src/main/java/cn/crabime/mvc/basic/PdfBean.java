package cn.crabime.mvc.basic;

public class PdfBean {

    private String name;

    private String character;

    public PdfBean() {
    }

    public PdfBean(String name, String character) {
        this.name = name;
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
