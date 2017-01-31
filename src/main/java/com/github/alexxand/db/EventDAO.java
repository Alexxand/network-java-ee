package com.github.alexxand.db;

import com.github.alexxand.model.Event;
import com.github.alexxand.model.Manager;

import java.util.List;

public interface EventDAO {
    List<Event> getEvents(int id);

    boolean addEvent(Event event);

    boolean deleteEvent(int eventId, int managerId);

    boolean decline(int eventId, int managerId);
}
