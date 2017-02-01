package com.github.alexxand.db;

import com.github.alexxand.model.Event;
import com.github.alexxand.model.Manager;
import com.github.alexxand.utils.exceptions.DAOException;

import java.util.List;

public interface EventDAO {
    /**
     * Returns a list of all events which the manager with the given id create or in which they participate.
     * If there are no such events then returned list will be empty.
     * @param managerId the id of the manager to find events.
     * @return list of events or empty list if this manager doesn't participate in any events.
     * @throws DAOException if an error occurs during execution
     */
    List<Event> getEvents(int managerId) throws DAOException;

    void addEvent(Event event) throws DAOException;

    boolean checkIfCreator(int eventId, int managerId);

    boolean checkIfParticipant(int eventId, int managerId);

    boolean delete(int eventId) throws DAOException;

    boolean decline(int eventId) throws DAOException;
}
