package cgellner.mytodo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import database.Queries;
import database.SqliteDatabase;
import model.Todo;

public class MainActivity extends Activity {


    private ListView listviewTodos;
    private SqliteDatabase database;
    private TodoCursorAdapter todoCursorAdapter;

    private MenuItem itemNew;
    private MenuItem itemSort;
    private MenuItem itemSortMode1;
    private MenuItem itemSortMode2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        database = new SqliteDatabase(this);
        database.open();

        createListviewTodos();

        initToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        createToolbarButtons(menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void createToolbarButtons(Menu menu){

        itemNew = menu.add(Menu.NONE,
                R.id.action_new_todo,
                1, "Neu");
        itemNew.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemNew.setIcon(R.drawable.ic_add_white_24dp);

        itemNew.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                showFormForNewTodo();
                return false;
            }
        });

        itemSort = menu.add(Menu.NONE,
                R.id.action_sort_todolist,
                2, "Sortieren");
        itemSort.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemSort.setIcon(R.drawable.ic_sort_white_24dp);


    }


    private void createListviewTodos(){

        try {

            Cursor todoCursor = database.getDatabase().rawQuery("SELECT  * FROM " + Queries.TABLE_TODOS + " ORDER BY " + Queries.COLUMN_ISDONE + "," + Queries.COLUMN_DEADLINE_DATE + "," + Queries.COLUMN_DEADLINE_TIME + " ASC", null);
            listviewTodos = (ListView) findViewById(R.id.listview_todolist);
            todoCursorAdapter = new TodoCursorAdapter(this, todoCursor);
            todoCursorAdapter.setMainActivity(this);
            listviewTodos.setAdapter(todoCursorAdapter);



            // Switch to new cursor and update contents of ListView
            // todoAdapter.changeCursor(newCursor);

        }catch (Exception ex){

            Log.d(this.getClass().getName(), ex.getMessage());
        }
    }


    private void showFormForNewTodo(){

        Intent intent = new Intent(getBaseContext(), ActivityTodoDetail.class);
        intent.putExtra(String.valueOf(R.string.view_mode), R.integer.VIEW_MODE_NEW);
        startActivityForResult(intent, R.integer.DETAIL_ACTIVITY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

          if (requestCode == R.integer.DETAIL_ACTIVITY) {

              if (resultCode == R.integer.SAVE_TODO) {

                  //Daten auslesen die vom Benutzer eingegeben wurden
                  Todo newTodo = new Todo();
                  newTodo.setDataFromIntentExtras(data.getExtras());

                  //Daten speichern in der DB
                  database.open();
                  long id = database.createTodo(newTodo);

                  if(id > 0){

                      Toast.makeText(getApplicationContext(), "TODO gespeichert!", Toast.LENGTH_SHORT).show();

                      Cursor cur = database.getDatabase().rawQuery("SELECT  * FROM " + Queries.TABLE_TODOS, null);
                      todoCursorAdapter.changeCursor(cur);

                  }else{

                      Toast.makeText(getApplicationContext(), "TODO konnte nicht gespeichert werden.", Toast.LENGTH_SHORT).show();
                  }

              }else if(resultCode == R.integer.DELETE_TODO){

                    long todoId = data.getLongExtra(Queries.COLUMN_ID, 0);
                    database.open();
                    boolean deleted = database.deleteTodoItem(todoId);

                  if(deleted == true){

                      Toast.makeText(getApplicationContext(), "TODO wurde gelöscht.", Toast.LENGTH_SHORT).show();
                      Cursor cur = database.getDatabase().rawQuery("SELECT  * FROM " + Queries.TABLE_TODOS, null);
                      todoCursorAdapter.changeCursor(cur);

                  }else{

                      Toast.makeText(getApplicationContext(), "Fehler beim Löschen aufgetreten.", Toast.LENGTH_SHORT).show();
                  }
              }else if(requestCode == R.integer.UPDATE_TODO){

                  Todo newTodoData = new Todo();
                  newTodoData.setDataFromIntentExtras(data.getExtras());

                  database.open();
                  boolean updated = database.updateTodoItem(newTodoData);

                  if(updated == true){

                      Toast.makeText(getApplicationContext(), "Daten geändert", Toast.LENGTH_SHORT).show();
                      Cursor cur = database.getDatabase().rawQuery("SELECT  * FROM " + Queries.TABLE_TODOS, null);
                      todoCursorAdapter.changeCursor(cur);

                  }else{

                      Toast.makeText(getApplicationContext(), "Daten nicht geändert", Toast.LENGTH_SHORT).show();
                  }
              }
          }
    }


    private void initToolbar(){

        Toolbar toolbar = (android.widget.Toolbar)findViewById(R.id.todo_main_toolbar);
        toolbar.setTitle("MyTODO");
        setActionBar(toolbar);
    }


}
