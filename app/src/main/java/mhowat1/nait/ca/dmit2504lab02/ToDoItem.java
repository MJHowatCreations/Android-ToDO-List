package mhowat1.nait.ca.dmit2504lab02;

/**
 * Created by Matthew on 2018-03-12.
 */

public class ToDoItem {
    int itemID;
    int listID_FK;
    String name;
    String description;
    Boolean completed;

    public ToDoItem(int itemID, int listID_FK, String name, String description) {
        this.itemID = itemID;
        this.listID_FK = listID_FK;
        this.name = name;
        this.description = description;
        this.completed = false;
    }

    public int getItemID() {
        return itemID;
    }

    public int getListID_FK() {
        return listID_FK;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCompleted() {
        return completed;
    }
}