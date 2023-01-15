package com.nextvoyager.conferences.model.dao.report;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import lombok.Data;

import java.util.List;

public interface ReportDAO {

    // Actions ------------------------------------------------------------------------------------

    /**
     * Create the given report in the database. The report ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given report.
     * @param report The report to be created in the database.
     * @throws IllegalArgumentException If the report ID is not null.
     * @throws DAOException If something fails at database level.
     */
    void create(Report report) throws IllegalArgumentException, DAOException;

    /**
     * Update the given report in the database. The report ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param report The report to be updated in the database.
     * @throws IllegalArgumentException If the report ID is null.
     * @throws DAOException If something fails at database level.
     */
    void update(Report report) throws IllegalArgumentException, DAOException;

    /**
     * Delete the given report from the database. After deleting, the DAO will set the ID of the given
     * report to null.
     * @param report The report to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    void delete(Report report) throws DAOException;
    /**
     * Returns the report from the database matching the given ID, otherwise null.
     * @param id The ID of the report to be returned.
     * @return The report from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */

    Report find(Integer id) throws DAOException;

    List<Report> list() throws DAOException;

    /**
     * Returns a list of all reports from the database ordered by report ID. The list is never null and
     * is empty when the database does not contain any report.
     * @return A list of all reports from the database ordered by report ID.
     * @throws DAOException If something fails at database level.
     */
    List<Report> list(Integer eventID) throws DAOException;

    ListWithCount<Report> listWithPagination(int page, int limit) throws DAOException;
    ListWithCount<Report> listWithPagination(int page, int limit, Report.Status status);
    ListWithCount<Report> listWithPagination(int page, int limit, User speaker) throws DAOException;
    ListWithCount<Report> listWithPagination(int page, int limit, User speaker, Report.Status status) throws DAOException;
    ListWithCount<Report> listWithPagination(int page, int limit, Integer eventID) throws DAOException;
    ListWithCount<Report> listWithPagination(int page, int limit,Integer eventID, Report.Status status) throws DAOException;
    ListWithCount<Report> listWithPagination(int page, int limit, Integer eventID, User speaker);
    ListWithCount<Report> listWithPagination(int page, int limit, Integer eventID, User speaker, Report.Status status);

}
