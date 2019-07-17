package propertysource;

public class IronWorker {

    private String name;

    public IronWorker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void introduce() {
        System.out.println("my name is " + name);
    }
}
