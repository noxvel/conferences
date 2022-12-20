package com.nextvoyager.conferences.model.dao;

public class QueryBuilder {
    private StringBuilder stringBuilder;

    public QueryBuilder(String baseQuery){
        this.stringBuilder = new StringBuilder();
        this.stringBuilder.append(baseQuery);
    }

    public QueryBuilder setFilter(String filter) {
        if (this.stringBuilder.indexOf("WHERE") == -1) {
            this.stringBuilder.append("WHERE ");
        } else {
            this.stringBuilder.append("AND ");
        }
        this.stringBuilder.append(filter);
        return this;
    }

    public QueryBuilder setOrder(String order) {
        this.stringBuilder.append(order);
        return this;
    }

    public QueryBuilder setLimit(String limit) {
        this.stringBuilder.append(limit);
        return this;
    }

    public String build() {
        return this.stringBuilder.toString();
    }
}
