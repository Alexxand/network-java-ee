package com.github.alexxand.db;

import com.github.alexxand.model.Manager;

import java.util.List;

public interface ContactsDAO {

    List<Manager> getContacts(int id);

    List<Manager> getGroup(int id);

    List<Manager> getBosses(int id);

    List<Manager> getCompanyContacts(int id);

    List<Manager> getRequests(int id);

    boolean addContact(Manager manager,int promoterId);

    boolean confirm(int promoterId, int receiverId);

    boolean delete(int promoterId, int receiverId);
}
