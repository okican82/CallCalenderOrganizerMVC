package okayyildirim.com.callcalenderorganizermvc.Model;

public class ListPhoneBookItem
{
    private Person person;
    private int status;
    private String dateText;
    private int listID;

    public ListPhoneBookItem(Person person, int status, String dateText, int listID) {

        this.person = person;
        this.status = status;
        this.dateText = dateText;
        this.listID = listID;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
}
