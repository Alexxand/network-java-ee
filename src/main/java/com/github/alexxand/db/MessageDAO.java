package com.github.alexxand.db;

import com.github.alexxand.model.Manager;
import com.github.alexxand.model.Message;

import java.util.List;

public interface MessageDAO {
    /**
     * Get other managers with which the given manager has messages.
     * @param id the current manager id
     * @return list of managers sorted by last message date
     */
    List<Manager> getManagers(int id);

    List<Message> getMessages(int idSender, int idReceiver);

    boolean addMessage(Message message);


}
