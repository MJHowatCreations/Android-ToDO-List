package mhowat1.nait.ca.dmit2504lab02;

/**
 * Created by Matthew on 2018-03-14.
 */

public class ToDoList {
    int listID;
    String listName;
    public ToDoList(int listID, String listName)
    {
        this.listID = listID;
        this.listName = listName;
    }

    public int getListID() {
        return listID;
    }

    public String getListName() {
        return listName;
    }
}
