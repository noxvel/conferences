package com.nextvoyager.conferences.model.dao;

import lombok.Data;

import java.util.List;

/**
 * A data class that contains a list of a selected items and the count of all items in the database.
 *
 * @author Stanislav Bozhevskyi
 */
@Data
public class ListWithCount<T> {
    private Integer count;
    private List<T> list;
}
