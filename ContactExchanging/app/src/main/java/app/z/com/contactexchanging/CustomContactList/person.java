package app.z.com.contactexchanging.CustomContactList;

public class person {

    private String name;
    private int ava;

    public person(String name,
                           int ava) {
        this.name = name;
        this.ava = ava;
    }

    public String getName() {
        return this.name;
    }


    public int getAva() {
        return this.ava;
    }
}