package okayyildirim.com.callcalenderorganizermvc.Model;

public class Person {

    private String phoneNumber;
    private String personName;



    public Person(String phoneNumber, String personName) {
        this.phoneNumber = phoneNumber;
        this.personName = personName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
