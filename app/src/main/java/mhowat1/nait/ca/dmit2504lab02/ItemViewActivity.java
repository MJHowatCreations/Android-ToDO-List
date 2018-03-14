package mhowat1.nait.ca.dmit2504lab02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
    }

    //5.	There will be a view that allows the addition of a new item. Each item will contain a description, association with a title, created date (as string if desired) and completed flag.
    //6.	There will be a view that displays all active items (within a list) and allows them to be selected for editing (update, delete and archive)

    //7.	Archived items will be deleted from the local database and stored on the remote server. Each archived item will contain a username, password, content, list title, created date and completed flag (0 for false, 1 for true).  All will be posted as Strings.  The username and password will be unique on the remote server.  Creating a new username and/or password will effectively create a new account on the remote server.  The keys for the post parameters will be “LIST_TITLE, CONTENT, COMPLETED_FLAG, ALIAS, PASSWORD and CREATED_DATE”.

    //8.	There will be a view that displays all of the archived items.  Use a query string with the following format: http://www.youcode.ca/Lab02Get.jsp?ALIAS=username&PASSWORD=password.  The data will be returned as four strings per item in the order of “Posted Date, List Title, Content, and the Completed Flag as a 1 or 0.

    //9.	Implement preferences to store your username, password, two colors (radio list) and a font size. Be sure to provide a default values.

    //10.	Implement an interface and menu to provide an intuitive user experience.

}

