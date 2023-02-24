package ucsd.edu.cse110.lab5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Application;
import android.content.Context;

import androidx.room.Delete;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TodoDatabaseTest {
    private TodoListItemDao dao;
    private  TodoDatabase db;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.todoListItemDao();
    }

    @After
    public void closeDb() throws Exception{
        db.close();
    }

    @Test
    public void testInsert(){
        TodoListItem item1 = new TodoListItem("pizza time", false, 0);
        TodoListItem item2 = new TodoListItem("Spiderman", false, 1);

        long id1 = dao.insert(item1);
        long id2 = dao.insert(item2);

        assertNotEquals(id1,id2);
    }

    @Test
    public void testGet(){
        TodoListItem insertedItem = new TodoListItem("pizza time",false,0);
        long id = dao.insert(insertedItem);

        TodoListItem item = dao.get(id);
        assertEquals(id,item.id);
        assertEquals(insertedItem.text,item.text);
        assertEquals(insertedItem.completed,item.completed);
        assertEquals(insertedItem.order,item.order);
    }
    @Test
    public void testUpdate(){
        TodoListItem item = new TodoListItem("pizza time",false,0);
        long id = dao.insert(item);

        item = dao.get(id);
        item.text = "Spiderman";
        int itemsUpdated = dao.update(item);
        assertEquals(itemsUpdated,1);

        item=dao.get(id);
        assertNotNull(item);
        assertEquals("Spiderman",item.text);
    }

    @Test
    public void testDelete(){
        TodoListItem item = new TodoListItem("pizza time",false,0);
        long id = dao.insert(item);

        item = dao.get(id);
        int itemsDeleted = dao.delete(item);
        assertEquals(itemsDeleted,1);
        assertNull(dao.get(id));
    }
}
