package com.sg.m2l9.data;

import com.sg.m2l9.models.ToDo;
import java.util.List;

public interface ToDoDao {

    /**
     * Add a to do item
     *
     * @param todo {ToDo} a properly formed to do list item
     * @return {Todo} the successfully added to do obj
     */
    ToDo add(ToDo todo);

    /**
     * Get all to do items
     *
     * @return {List} all to do obj's
     */
    List<ToDo> getAll();

    /**
     * Read a to do item by its id number
     *
     * @param id {int} id number of a existing to do item
     * @return {ToDo} the obj corresponding to that key
     */
    ToDo findById(int id);

    /**
     * Update a to do item
     *
     * @param todo {ToDo} the edited to do obj to be put into db
     * @return {boolean} true if item exists and is updated
     */
    boolean update(ToDo todo);

    /**
     * Delete a to do item
     *
     * @param id {int} id number of a existing to do item
     * @return {boolean} true if item exists and is deleted
     */
    boolean deleteById(int id);

}
