package okayyildirim.com.callcalenderorganizermvc.Model;

public class ListItem {

    private int ID;
    private String Name;
    private String date;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ListItem(int ID, String name, String date) {

        this.ID = ID;
        Name = name;
        this.date = date;
    }
}

