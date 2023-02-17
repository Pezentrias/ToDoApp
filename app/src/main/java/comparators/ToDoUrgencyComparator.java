package comparators;

import java.util.Comparator;

import model.ToDoItem;

public class ToDoUrgencyComparator implements Comparator<ToDoItem> {
    @Override
    public int compare(ToDoItem o1, ToDoItem o2) {
     return    Integer.parseInt(o1.getUrgency())-Integer.parseInt(o2.getUrgency());
    }
}
