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
        return setFilter(filter, false);
    }

    public SelectQueryBuilder setFilter(String filter, boolean isAdditional) {
        if (filter == null) return this;

        setFilterKeyWord(isAdditional);

        this.stringBuilder.append(filter);
        return this;
    }

    public SelectQueryBuilder addOrFilter(String filter) {

        this.stringBuilder.append("OR ");
        this.stringBuilder.append(filter);
        return this;
    }

    public SelectQueryBuilder setInFilter(String filter, int filterCount) {
        return setInFilter(filter, filterCount, false);
    }

    public SelectQueryBuilder setInFilter(String filter, int filterCount, boolean isAdditional) {
        setFilterKeyWord(isAdditional);

        this.stringBuilder.append(filter);
        StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
        for (int i = 0; i < filterCount; i++) {
            stringJoiner.add("?");
        }
        this.stringBuilder.append(stringJoiner.toString());
        return this;
    }

    private void setFilterKeyWord(boolean isAdditional) {
        if (isAdditional) {
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

    public SelectQueryBuilder setLimit(String limit) {
        this.stringBuilder.append(limit);
        return this;
    }

    public String build() {
        return this.stringBuilder.toString();
    }
}
