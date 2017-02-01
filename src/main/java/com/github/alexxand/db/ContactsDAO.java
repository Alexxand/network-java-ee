package com.github.alexxand.db;

import com.github.alexxand.model.ManagerContact;
import com.github.alexxand.model.ManagerContact.Type;

import java.util.List;

public interface ContactsDAO {

    /**
     * Return all contacts of the manager with the given id without requests.
     * @param id id of the manager
     * @return all contacts without requests
     */
    List<ManagerContact> getContacts(int id);

    List<ManagerContact> getGroup(int id);

    List<ManagerContact> getBosses(int id);

    List<ManagerContact> getCompanyContacts(int id);

    List<ManagerContact> getOutgoingRequests(int id);

    List<ManagerContact> getIncomingRequests(int id);

    boolean addContact(int promoterId, int receiverId, Type type);

    boolean confirm(int promoterId, int receiverId);

    boolean delete(int promoterId, int receiverId);
}
