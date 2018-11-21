package okayyildirim.com.callcalenderorganizermvc.Utiliy;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okayyildirim.com.callcalenderorganizermvc.Model.Person;

public class Util
{

    /**
     * @param personArrayList
     * @param person
     * @return
     */
    public static Boolean arrayHasElement(ArrayList<Person> personArrayList, Person person)
    {
        boolean result = false;

        for(int i = 0;i<personArrayList.size();i++)
        {
            if(personArrayList.get(i).getPersonName().equals(person.getPhoneNumber()))
            {
                result = true;

                break;
            }
        }



        return result;
    }

    /**
     * @param date
     * @return
     */
    public static String convertDateToString(Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);

        return formattedDate;
    }

    public static Date convertStringToDate(String dateString)throws Exception
    {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);

        return date;
    }

    /**
     * @param context
     * @param phoneNumber
     * @return
     */
    public static Person getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;

        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));

        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        if(contactName == null) contactName = phoneNumber;


        return new Person(phoneNumber,contactName);
    }

    /**
     * @param name
     * @param context
     * @return
     */
    public static ArrayList<Person> searchFromPhoneBook(String name, Context context)
    {
        ArrayList<Person> personList = new ArrayList<Person>();


        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";

        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

        Cursor contactLookupCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

        try {
            while (contactLookupCursor.moveToNext()) {

                Person newPerson = new Person(contactLookupCursor.getString(0),contactLookupCursor.getString(1));
                personList.add(newPerson);

                arrayHasElement(personList,newPerson);
            }
        }
        finally {
            contactLookupCursor.close();
        }

        return personList;


    }

    /**
     * @param returnString
     * @param targetString
     * @param replacedString
     * @return
     */
    public static String removeSpaceFromString(String returnString,String targetString, String replacedString)
    {
        return returnString.replace(targetString, replacedString);
    }

    /**
     * @param date
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean dateIsBetween(Date date,Date beginDate,Date endDate)
    {
        boolean result = false;

        if(date.after(beginDate) && date.before(endDate)) result = true;

        return result;
    }





}
