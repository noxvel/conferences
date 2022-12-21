package com.nextvoyager.conferences.model.dao.utils.querybuilder;

public class SelectQueryBuilder {

    protected final StringBuilder stringBuilder;

    public SelectQueryBuilder(String baseQuery){
        this.stringBuilder = new StringBuilder();
        this.stringBuilder.append(baseQuery);
    }

    public SelectQueryBuilder setFilter(String filter) {
        if (this.stringBuilder.indexOf("WHERE") == -1) {
            this.stringBuilder.append("WHERE ");
        } else {
            this.stringBuilder.append("AND ");
        }
        this.stringBuilder.append(filter);
        return this;
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
