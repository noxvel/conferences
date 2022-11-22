package com.nextvoyager.conferences.dao.event;

import com.nextvoyager.conferences.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.Event;

import java.util.List;

public interface EventDAO {

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the event from the database matching the given ID, otherwise null.
     * @param id The ID of the event to be returned.
     * @return The event from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    public Event find(Integer id) throws DAOException;

    /**
     * Returns a list of all events from the database ordered by event ID. The list is never null and
     * is empty when the database does not contain any event.
     * @return A list of all events from the database ordered by event ID.
     * @throws DAOException If something fails at database level.
     */
    public List<Event> list() throws DAOException;

    /**
     * Create the given event in the database. The event ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given event.
     * @param event The event to be created in the database.
     * @throws IllegalArgumentException If the event ID is not null.
     * @throws DAOException If something fails at database level.
     */
    public void create(Event event) throws IllegalArgumentException, DAOException;

    /**
     * Update the given event in the database. The event ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param event The event to be updated in the database.
     * @throws IllegalArgumentException If the event ID is null.
     * @throws DAOException If something fails at database level.
     */
    public void update(Event event) throws IllegalArgumentException, DAOException;

    /**
     * Delete the given event from the database. After deleting, the DAO will set the ID of the given
     * event to null.
     * @param event The event to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    public void delete(Event event) throws DAOException;

}
