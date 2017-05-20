package cgellner.mytodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import model.Todo;

/**
 * Created by Carolin on 23.04.2017.
 */
public class TodolistArrayAdapter extends ArrayAdapter<Todo> {

    private  TextView todoName;
    private TextView todoDeadline;
    private CheckBox todoIsDone;
    private RatingBar todoIsFavourite;

    private View itemView;

    public TodolistArrayAdapter(Context context, ArrayList<Todo> todos) {

        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {

        this.itemView = itemView;

        Todo todo = getItem(position);

        itemView = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);


        //Bestandteile der View
        todoName = (TextView)itemView.findViewById(R.id.todo_textview_name);
        todoDeadline = (TextView)itemView.findViewById(R.id.todo_textview_deadline);
        todoIsDone = (CheckBox)itemView.findViewById(R.id.todo_checkbox_isdone);

        //Werte zuweisen
        todoName.setText(todo.getName());
        todoDeadline.setText("Fälligkeit: " + todo.getDeadlineDate().toString() + " " + todo.getDeadlineTime().toString());

        boolean isdone = false;
        if(todo.getIsDone() == 1){

            isdone = true;
        }

        todoIsDone.setChecked(isdone);

        setListener();

        return itemView;
    }


    private void setListener(){



        todoIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        todoIsFavourite.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if(todoIsFavourite.getRating() == 1){

                    todoIsFavourite.setRating(0);

                }else{

                    todoIsFavourite.setRating(1);
                }

            }
        });
    }
}
