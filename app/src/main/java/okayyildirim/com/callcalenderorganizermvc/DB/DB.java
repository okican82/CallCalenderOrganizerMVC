package okayyildirim.com.callcalenderorganizermvc.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okayyildirim.com.callcalenderorganizermvc.Model.ListItem;
import okayyildirim.com.callcalenderorganizermvc.Model.ListPhoneBookItem;
import okayyildirim.com.callcalenderorganizermvc.Model.Person;
import okayyildirim.com.callcalenderorganizermvc.Utiliy.Util;

public class DB extends SQLiteOpenHelper {

    private final static int DatabaseVersiyon = 1;
    private static DB INSTANCE = null;
    private final static String DatabaseName = "CallList";
    private Context context;

    public static DB getInstance(Context contect)
    {
        if (INSTANCE == null) {
            INSTANCE = new DB(contect.getApplicationContext());
        }
        return INSTANCE;
    }

    public DB(Context context)
    {
        super(context, DatabaseName, null, DatabaseVersiyon);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Call_List = "CREATE TABLE LISTS ("
                + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " NAME TEXT,"
                + " DATE TEXT, "
                + " END_DATE TEXT, "
                + " NOTIFY_BEGIN_DATE INTEGER, "
                + " NOTIFY_END_DATE INTEGER "
                + ")";
        db.execSQL(Call_List);

        String List_Phonebook = "CREATE TABLE LIST_PHONEBOOK ("
                + " LIST_ID INTEGER,"
                + " PHONE_NUMBER TEXT, "
                + " STATUS INTEGER, "
                + " DATE TEXT "
                + ")";
        db.execSQL(List_Phonebook);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    /**
     * @return
     */
    public ArrayList<ListItem> getCallLists()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "";
        selectQuery = "SELECT * FROM LISTS";
        selectQuery += " order by 1 desc";

        Cursor cursor = db.rawQuery(selectQuery,null);
        ArrayList<ListItem> ListItems = new ArrayList<ListItem>();

        if(cursor.moveToFirst())
        {
            do
            {
                ListItems.add(new ListItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5)));

            }while (cursor.moveToNext());
        }

        db.close();

        return ListItems;

    }

    /**
     * @param listId
     * @return
     */
    public String getListNameByID(int listId)
    {
        String listName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "";
        selectQuery = "SELECT * FROM LISTS";
        selectQuery += " Where ID =" + String.valueOf(listId) + "";
        selectQuery += " order by 1 desc";

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            listName = cursor.getString(cursor.getColumnIndex("NAME"));
        }

        db.close();

        return listName;
    }

    /**
     * @param listId
     * @return
     */
    public ListItem getListItemByID(int listId)
    {
        ListItem item;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "";
        selectQuery = "SELECT * FROM LISTS";
        selectQuery += " Where ID =" + String.valueOf(listId) + "";
        selectQuery += " order by 1 desc";

        Cursor cursor = db.rawQuery(selectQuery,null);


        if(cursor.moveToFirst())
        {
            item = new ListItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
        }
        else
        {
            item = null;
        }

        db.close();

        return item;
    }

    public int getListCountById(int ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "";
        selectQuery = " SELECT * FROM LIST_PHONEBOOK ";
        selectQuery += " WHERE LIST_ID = " + String.valueOf(ID);
        selectQuery += " order by 1 desc ";

        Cursor cursor = db.rawQuery(selectQuery,null);

        int itemCount =  cursor.getCount();

        db.close();

        return itemCount;

    }

    public void addNewListItem(String name,String beginDate,String endDate,int notifyBeginDate,int notifyEndDate)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("NAME", name);
        values.put("DATE", beginDate);
        values.put("END_DATE",endDate);
        values.put("NOTIFY_BEGIN_DATE",notifyBeginDate);
        values.put("NOTIFY_END_DATE",notifyEndDate);

        db.insert("LISTS", null, values);

        db.close();
    }

    public void updateListItem(int listID,String beginDate,String endDate,int notifyBeginDate,int notifyEndDate)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("DATE", beginDate);
        values.put("END_DATE",endDate);
        values.put("NOTIFY_BEGIN_DATE", notifyBeginDate);
        values.put("NOTIFY_END_DATE",notifyEndDate);

        db.update("LISTS", values, "ID=" + listID, null);

        db.close();
        db.close();
    }

    public void addToCallList(String contactPhoneNumber, int callListID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("LIST_ID", callListID);
        values.put("PHONE_NUMBER", contactPhoneNumber);
        values.put("STATUS", 0);
        values.put("DATE", "");

        db.insert("LIST_PHONEBOOK", null, values);

        db.close();
    }

    /**
     * @param listID
     */
    public void removeListFromDB(int listID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("LISTS","ID=" + String.valueOf(listID) ,null);
        db.close();
    }

    public ArrayList<ListPhoneBookItem> getPersonListByListID(int listID)
    {
        ArrayList<ListPhoneBookItem> personArrayList = new ArrayList<ListPhoneBookItem>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "";
        selectQuery = " SELECT * FROM LIST_PHONEBOOK ";
        selectQuery += " WHERE LIST_ID = " + String.valueOf(listID);
        selectQuery += " order by 1 desc";

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do
            {
                Person person = Util.getContactName(context,String.valueOf(cursor.getString(cursor.getColumnIndex("PHONE_NUMBER"))));

                personArrayList.add(new ListPhoneBookItem(person,cursor.getInt(cursor.getColumnIndex("STATUS")),cursor.getString(cursor.getColumnIndex("DATE")),listID));

            }while (cursor.moveToNext());
        }

        db.close();

        return personArrayList;

    }

    public void deletePersonFromAList(int listID,String phoneNumber)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "DELETE FROM LIST_PHONEBOOK"
                + " WHERE LIST_ID = " + String.valueOf(listID)
                + " AND PHONE_NUMBER = \"" + String.valueOf(phoneNumber) + "\""
                ;
        db.execSQL(strSQL);

        db.close();
    }

    public void updateStatusListCall(int listId,int index, int status)
    {
        String phoneNumber = getPersonListByListID(listId).get(index).getPerson().getPhoneNumber();
        ListItem item = getListItemByID(listId);

        try {
            Date beginDate = Util.convertStringToDate(item.getDate());
            Date endDate = Util.convertStringToDate(item.getEndDate());

            boolean dateIsBetween = Util.dateIsBetween(Calendar.getInstance().getTime(),beginDate,endDate);

            if(dateIsBetween) {
                SQLiteDatabase db = this.getWritableDatabase();

                String strSQL = "UPDATE LIST_PHONEBOOK SET STATUS = " + String.valueOf(status)
                        + " WHERE LIST_ID = " + String.valueOf(listId)
                        + " AND PHONE_NUMBER = \"" + String.valueOf(phoneNumber) + "\"";
                db.execSQL(strSQL);

                db.close();
            }

        }
        catch (Exception e)
        {

        }






    }


}
