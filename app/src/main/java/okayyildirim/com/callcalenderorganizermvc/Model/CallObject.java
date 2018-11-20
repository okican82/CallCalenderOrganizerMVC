package okayyildirim.com.callcalenderorganizermvc.Model;

import java.util.Date;

public class CallObject
{
    private String callNumber;
    private int callState;
    private Date callDate;

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public CallObject(String callNumber, int callState, Date callDate) {

        this.callNumber = callNumber;
        this.callState = callState;
        this.callDate = callDate;
    }

    public int getCallState() {
        return callState;
    }

    public void setCallState(int callState) {
        this.callState = callState;
    }

    public String getCallNumber() {

        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }
}
