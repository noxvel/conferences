package com.nextvoyager.conferences.model.dao.utils.querybuilder;

import com.nextvoyager.conferences.model.dao.utils.DAOUtil;

import java.util.StringJoiner;

public class SelectQueryBuilder {

    protected final StringBuilder stringBuilder;

    public SelectQueryBuilder(String baseQuery){
        this.stringBuilder = new StringBuilder();
        this.stringBuilder.append(baseQuery);
    }

    public SelectQueryBuilder setFilter(String filter) {
        if (filter == null) return this;

        setFilterKeyWord();

        this.stringBuilder.append(filter);
        return this;
    }

    public SelectQueryBuilder addOrFilter(String filter) {

        this.stringBuilder.append("OR ");
        this.stringBuilder.append(filter);
        return this;
    }

    public SelectQueryBuilder setInFilter(String filter, int filterCount) {
        setFilterKeyWord();

        this.stringBuilder.append(filter);
        StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
        for (int i = 0; i < filterCount; i++) {
            stringJoiner.add("?");
        }
        this.stringBuilder.append(stringJoiner.toString());
        return this;
    }

    private void setFilterKeyWord() {
        if (this.stringBuilder.indexOf("WHERE") == -1) {
            this.stringBuilder.append("WHERE ");
        } else {
            this.stringBuilder.append("AND ");
        }
    }

    public SelectQueryBuilder setSortType(String sortType) {
        this.stringBuilder.append(sortType);
        return this;
    }

    public SelectQueryBuilder setSortDirection(String sortDirection) {
        this.stringBuilder.append(sortDirection);
        return this;
    }

    public SelectQueryBuilder setLimit(String limit) {
        this.stringBuilder.append(limit);
        return this;
    }

    public String build() {
        return this.stringBuilder.toString();
    }
}
