package comparators;

import java.util.Comparator;

import model.ToDoItem;

public class ToDoNameComparator implements Comparator<ToDoItem> {
    @Override
    public int compare(ToDoItem o1, ToDoItem o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
