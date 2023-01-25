package com.nextvoyager.conferences.model.dao.event;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

import java.util.List;

/**
 * Operations with database for Event entity
 *
 * @author Stanislav Bozhevskyi
 */
public interface EventDAO {

    /**
     * Returns the event from the database matching the given ID, otherwise null.
     * @param id The ID of the event to be returned.
     * @return The event from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    Event find(Integer id) throws DAOException;
    /**
     * Create the given event in the database. The event ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given event.
     * @param event The event to be created in the database.
     * @throws IllegalArgumentException If the event ID is not null.
     * @throws DAOException If something fails at database level.
     */
    void create(Event event) throws IllegalArgumentException, DAOException;

    /**
     * Update the given event in the database. The event ID must not be null, otherwise it will throw
     * IllegalArgumentException.
     * @param event The event to be updated in the database.
     * @throws IllegalArgumentException If the event ID is null.
     * @throws DAOException If something fails at database level.
     */
    void update(Event event) throws IllegalArgumentException, DAOException;

    /**
     * Delete the given event from the database. After deleting, the DAO will set the ID of the given
     * event to null.
     * @param event The event to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    void delete(Event event) throws DAOException;

    /**
     * Register the user to the event
     * @param event The event on which the user must be registered.
     * @param user The user to be registered to the event.
     * @param register Boolean value for register or unregister the user to the event.
     * @throws DAOException If something fails at database level.
     */
    void registerUser(Event event, User user, boolean register) throws DAOException;

    /**
     * Check if selected user was registered to the event.
     * @param event The event that checked for user registration.
     * @param user The user to be checked that he registered on the event.
     * @throws DAOException If something fails at database level.
     * @return The boolean value of checking result.
     */
    boolean isUserRegisterEvent(Event event, User user) throws DAOException;

    /**
     * Returns a list of all events from the database ordered by SortType and SortDirection.
     * And also filtered with TimeFilter parameter. The list is never null and
     * is empty when the database does not contain any event.
     * @param sortType The type of sorting to use in returned list of events.
     * @param sortDirection The direction of sorting to use in returned list of events.
     * @param timeFilter Filter returned list of events by its data and time parameter.
     * @return A list of all events from the database ordered by sortType and sortDirection.
     * @throws DAOException If something fails at database level.
     */
    List<Event> list(SortType sortType, SortDirection sortDirection, TimeFilter timeFilter) throws DAOException;

    /**
     * Returns a list of part of events from the database ordered by SortType and SortDirection.
     * And also filtered with TimeFilter parameter. The list is never null and
     * is empty when the database does not contain any event.
     * @param page Current page to calculate offset.
     * @param limit Number of elements in returner list of events.
     * @param sortType The type of sorting to use in returned list of events.
     * @param sortDirection The direction of sorting to use in returned list of events.
     * @param timeFilter Filter returned list of events by its data and time parameter.
     * @return A list with count of all events and list of selected events.
     * @throws DAOException If something fails at database level.
     */
    ListWithCount<Event> listWithPagination(Integer page, Integer limit, SortType sortType,
                                     SortDirection sortDirection, TimeFilter timeFilter) throws DAOException;

    ListWithCount<Event> listWithPaginationReportStatusFilter(int page, int limit, SortType sortType,
                                                             SortDirection sortDirection, TimeFilter timeFilter,
                                                             Report.Status status) throws DAOException;

    ListWithCount<Event> listWithPaginationSpeaker(int page, int limit, SortType sortType,
                                                  SortDirection sortDirection, TimeFilter timeFilter,
                                                  User speaker, Boolean participated) throws DAOException;

    ListWithCount<Event> listWithPaginationOrdinaryUser(int page, int limit, SortType sortType,
                                                       SortDirection sortDirection, TimeFilter timeFilter,
                                                       User ordinaryUser, Boolean participated) throws DAOException;


    enum SortType{
        Date,
        ReportsCount,
        ParticipantsCount
    }

    enum SortDirection {
        Descending,
        Ascending
    }

    enum TimeFilter{
        AllTime,
        Future,
        Past
    }

}
