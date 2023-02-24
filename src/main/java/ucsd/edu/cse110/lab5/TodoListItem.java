package ucsd.edu.cse110.lab5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NonNls;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "todo_list_items")
public class TodoListItem {
    @PrimaryKey(autoGenerate = true)
    public long id; //=0

    @NonNull
    public String text;
    public boolean completed;
    public int order;

    TodoListItem( String text, boolean completed, int order){
        this.text = text;
        this.completed=completed;
        this.order=order;
    }

    public static List<TodoListItem> loadJSON(Context context, String path){
        try{
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<TodoListItem>>(){}.getType();
            return gson.fromJson(reader,type);
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    @Override
    public String toString() {
        return "TodoListItem{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                ", order=" + order +
                '}';
    }

}
