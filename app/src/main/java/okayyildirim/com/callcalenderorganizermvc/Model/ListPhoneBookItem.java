package okayyildirim.com.callcalenderorganizermvc.Model;

public class ListPhoneBookItem
{
    private int listID;
    private int personID;

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public ListPhoneBookItem(int listID, int personID) {

        this.listID = listID;
        this.personID = personID;
    }
}
