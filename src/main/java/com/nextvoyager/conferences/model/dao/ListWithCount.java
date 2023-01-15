package com.nextvoyager.conferences.model.dao;

import lombok.Data;

import java.util.List;

@Data
public class ListWithCount<T> {
    private Integer count;
    private List<T> list;
}
