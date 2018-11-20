package okayyildirim.com.callcalenderorganizermvc;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Date;

import okayyildirim.com.callcalenderorganizermvc.Utiliy.Util;


public class DateTests {

    @Test
    public void dateIsBetween()
    {
        try {


            Assert.assertEquals(true, Util.dateIsBetween(
                    Util.convertStringToDate("20-11-2018"),
                    Util.convertStringToDate("19-11-2018"),
                    Util.convertStringToDate("25-11-2018")
                    ));

            Assert.assertEquals(false, Util.dateIsBetween(
                    Util.convertStringToDate("20-12-2019"),
                    Util.convertStringToDate("19-11-2018"),
                    Util.convertStringToDate("25-11-2018")
            ));

            Assert.assertEquals(false, Util.dateIsBetween(
                    Util.convertStringToDate("20-11-2018"),
                    Util.convertStringToDate("22-11-2018"),
                    Util.convertStringToDate("25-11-2018")
            ));

            Assert.assertEquals(true, Util.dateIsBetween(
                    Util.convertStringToDate("17-1-2019"),
                    Util.convertStringToDate("19-11-2018"),
                    Util.convertStringToDate("25-11-2019")
            ));


        }
        catch (Exception e)
        {
            System.out.print(e);

        }

    }

}
