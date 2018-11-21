package okayyildirim.com.callcalenderorganizermvc.Model;

public class ListItem {

    private int ID;
    private String Name;
    private String date;
    private String endDate;
    private int notifyBeginDate;
    private int notifyEndDate;


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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ListItem(int ID, String name, String date, String endDate, int notifyBeginDate, int notifyEndDate) {
        this.ID = ID;
        Name = name;
        this.date = date;
        this.endDate = endDate;
        this.notifyBeginDate = notifyBeginDate;
        this.notifyEndDate = notifyEndDate;
    }

    public int getNotifyBeginDate() {
        return notifyBeginDate;
    }

    public void setNotifyBeginDate(int notifyBeginDate) {
        this.notifyBeginDate = notifyBeginDate;
    }

    public int getNotifyEndDate() {
        return notifyEndDate;
    }

    public void setNotifyEndDate(int notifyEndDate) {
        this.notifyEndDate = notifyEndDate;
    }
}

