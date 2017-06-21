package model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import database.Queries;

/**
 * Created by Carolin on 21.04.2017.
 */
public class TodoItem implements Serializable{

    //region Attribute

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("done")
    private boolean done; //1=is done - 0=is not done

    @SerializedName("favourite")
    private boolean favourite;  //1=is favourite - 0=is not favourite

    @SerializedName("expiry")
    private long expiry;

    @SerializedName("contacts")
    private String contacts;

    //endregion



    public TodoItem() {
    }

//region Getter und Setter


   public long getExpiry() {
       return expiry;
   }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDone() {
        return done;
    }

    public void setIsDone(boolean isDone) {
        this.done = isDone;
    }

    public boolean getIsFavourite() {
        return favourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.favourite = isFavourite;
    }

    //endregion




    public void setAllDataFromCursor(Cursor cursor) {

        if(cursor != null) {

            id = cursor.getLong(cursor.getColumnIndexOrThrow(Queries.COLUMN_ID));
            name = cursor.getString(cursor.getColumnIndexOrThrow(Queries.COLUMN_NAME));
            description = cursor.getString(cursor.getColumnIndexOrThrow(Queries.COLUMN_DESCRIPTION));
            expiry = cursor.getLong(cursor.getColumnIndexOrThrow(Queries.COLUMN_EXPIRY));
            String done = cursor.getString(cursor.getColumnIndexOrThrow(Queries.COLUMN_ISDONE));
            contacts = cursor.getString(cursor.getColumnIndexOrThrow(Queries.TABLE_TODO_CONTACTS));


            if(done.equals(Boolean.TRUE.toString())){
                this.done = true;
            }else{
                this.done = false;
            }

            String favourite = cursor.getString(cursor.getColumnIndexOrThrow(Queries.COLUMN_ISFAVOURITE));
            if(favourite.equals(Boolean.TRUE.toString())){
                this.favourite = true;
            }else{
                this.favourite = false;
            }
        }
    }


    public ContentValues createContentValues(){

        ContentValues todoValues = new ContentValues();

        if(id > -1){
            todoValues.put(Queries.COLUMN_ID, id);
        }
        todoValues.put(Queries.COLUMN_NAME, name);
        todoValues.put(Queries.COLUMN_DESCRIPTION, description);
        todoValues.put(Queries.COLUMN_ISFAVOURITE, String.valueOf(favourite));
        todoValues.put(Queries.COLUMN_ISDONE, String.valueOf(done));
        todoValues.put(Queries.COLUMN_EXPIRY, expiry);
        todoValues.put(Queries.TABLE_TODO_CONTACTS, contacts);


        Log.i("TotodItem","Id: " +  todoValues.get(Queries.COLUMN_ID));
        Log.i("TotodItem","Name: " +  todoValues.get(Queries.COLUMN_NAME));
        Log.i("TotodItem","Description: " +  todoValues.get(Queries.COLUMN_DESCRIPTION));
        Log.i("TotodItem","Expiry: " +  todoValues.get(Queries.COLUMN_EXPIRY));
        Log.i("TotodItem","Done: " +  todoValues.get(Queries.COLUMN_ISDONE));
        Log.i("TotodItem","Favourite: " +  todoValues.get(Queries.COLUMN_ISFAVOURITE));
        Log.i("TotodItem","Contacts: " +  todoValues.get(Queries.TABLE_TODO_CONTACTS));

        return todoValues;
    }


}
