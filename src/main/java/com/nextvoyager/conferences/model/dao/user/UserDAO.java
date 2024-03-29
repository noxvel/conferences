package com.nextvoyager.conferences.model.dao.user;

import com.nextvoyager.conferences.model.dao.ListWithCount;
import com.nextvoyager.conferences.model.dao.exeption.DAOException;
import com.nextvoyager.conferences.model.entity.Event;
import com.nextvoyager.conferences.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Operations with database for User entity
 *
 * @author Stanislav Bozhevskyi
 */
public interface UserDAO {

    /**
     * Returns the user from the database matching the given ID, otherwise null.
     * @param id The ID of the user to be returned.
     * @return The user from the database matching the given ID, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    User find(Integer id) throws DAOException;

    /**
     * Returns the user from the database matching the given email and password, otherwise null.
     * @param email The email of the user to be returned.
     * @param password The password of the user to be returned.
     * @return The user from the database matching the given email and password, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    User find(String email, String password) throws DAOException;

    /**
     * Returns the user from the database matching the given email, otherwise null.
     * @param email The email of the user to be returned.
     * @return The user from the database matching the given email, otherwise null.
     * @throws DAOException If something fails at database level.
     */
    User find(String email);

    /**
     * Create the given user in the database. The user ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given user.
     * @param user The user to be created in the database.
     * @throws IllegalArgumentException If the user ID is not null.
     * @throws DAOException If something fails at database level.
     */
    void create(User user) throws IllegalArgumentException, DAOException;

    /**
     * Update the given user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param user The user to be updated in the database.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DAOException If something fails at database level.
     */
    void update(User user) throws IllegalArgumentException, DAOException;

    /**
     * Delete the given user from the database. After deleting, the DAO will set the ID of the given
     * user to null.
     * @param user The user to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    void delete(User user) throws DAOException;

    /**
     * Returns a list of part of users from the database. Number of page and limit size are use for pagination.
     * The list is never null and is empty when the database does not contain any user.
     * @return A list of users from the database.
     * @throws DAOException If something fails at database level.
     */
    ListWithCount<User> list(int page, int limit) throws DAOException;

    /**
     * Returns a list of all users from the database that have the user role. The list is never null and
     * is empty when the database does not contain any user.
     * @return A list of users from the database.
     * @throws DAOException If something fails at database level.
     */
    List<User> listWithOneRole(User.Role userRole) throws DAOException;

    /**
     * Returns a list of users that want to receive notifications about changes in the event.
     * The list is never null and is empty when the database does not contain any user.
     * @return A list of users from the database.
     * @throws DAOException If something fails at database level.
     */
    List<User> receiveEventNotificationsList(Event event);

    /**
     * Returns a list of users that are participants of the event.
     * The list is never null and is empty when the database does not contain any user.
     * @return A list of users from the database.
     * @throws DAOException If something fails at database level.
     */
    List<User> listOfEventParticipants(Event event);

    /**
     * Returns true if the given email address exist in the database.
     * @param email The email address which is to be checked in the database.
     * @return True if the given email address exist in the database.
     * @throws DAOException If something fails at database level.
     */
    boolean existEmail(String email) throws DAOException;

    /**
     * Change the password of the given user. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException.
     * @param user The user to change the password for.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DAOException If something fails at database level.
     */
    void changePassword(User user) throws DAOException;

    /**
     * Check if user password compare to his password in the database.
     * @param user The user which password is to be checked in the database.
     * @return True if the given user password equals to the password in the database.
     * @throws DAOException If something fails at database level.
     */
    boolean checkPassword(User user) throws DAOException;

    void createPasswordResetTokenForUser(User user, String token, LocalDateTime fiveMinutesLater);

    User.PasswordResetToken getPasswordResetToken(String tokenParam);
}
