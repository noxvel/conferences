package com.nextvoyager.conferences.dao.report;

import com.nextvoyager.conferences.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.Report;
import lombok.Data;

import java.util.List;

public interface ReportDAO {

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the report from the database matching the given ID, otherwise null.
     * @param id The ID of the report to be returned.
     * @return The report from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public Report find(Integer id) throws DAOException;

    /**
     * Returns a list of all reports from the database ordered by report ID. The list is never null and
     * is empty when the database does not contain any report.
     * @return A list of all reports from the database ordered by report ID.
     * @throws DAOException If something fails at database level.
     */
    public List<Report> list(Integer eventID) throws DAOException;

    ListWithCountResult listWithPagination(Integer eventID, Integer page, Integer limit) throws DAOException;

    /**
     * Create the given report in the database. The report ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given report.
     * @param report The report to be created in the database.
     * @throws IllegalArgumentException If the report ID is not null.
     * @throws DAOException If something fails at database level.
     */
    public void create(Report report) throws IllegalArgumentException, DAOException;

    /**
     * Update the given report in the database. The report ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param report The report to be updated in the database.
     * @throws IllegalArgumentException If the report ID is null.
     * @throws DAOException If something fails at database level.
     */
    public void update(Report report) throws IllegalArgumentException, DAOException;

    /**
     * Delete the given report from the database. After deleting, the DAO will set the ID of the given
     * report to null.
     * @param report The report to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    public void delete(Report report) throws DAOException;

    @Data
    public static class ListWithCountResult{
        private Integer count;
        private List<Report> list;
    }
}
