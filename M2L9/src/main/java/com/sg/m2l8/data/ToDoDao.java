package com.sg.m2l8.data;

import com.sg.m2l8.models.ToDo;
import java.util.List;

public interface ToDoDao {

    ToDo add(ToDo todo);

    List<ToDo> getAll();

    ToDo findById(int id);

    boolean update(ToDo todo);

    boolean deleteById(int id);

}
