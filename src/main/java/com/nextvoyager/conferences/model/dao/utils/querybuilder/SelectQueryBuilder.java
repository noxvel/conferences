package com.nextvoyager.conferences.model.dao.utils.querybuilder;

import java.util.StringJoiner;

/**
 * Utility class to help create SQL queries
 *
 * @author Stanislav Bozhevskyi
 */
public class SelectQueryBuilder {

    protected final StringBuilder stringBuilder;

    private byte filterCount = 0;

    public SelectQueryBuilder(String baseQuery){
        this.stringBuilder = new StringBuilder();
        this.stringBuilder.append(baseQuery);
    }

    public SelectQueryBuilder setFilter(String filter) {
        if (filter == null) return this;

        setFilterKeyWord();

        this.stringBuilder.append(filter);
        this.filterCount++;
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
        this.filterCount++;
        return this;
    }

    private void setFilterKeyWord() {
        if (this.filterCount > 0) {
            this.stringBuilder.append("AND ");
        } else {
            this.stringBuilder.append("WHERE ");
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

    public SelectQueryBuilder setLimit() {
        this.stringBuilder.append("LIMIT ?, ? ");
        return this;
    }

    public String build() {
        return this.stringBuilder.toString();
    }
}
