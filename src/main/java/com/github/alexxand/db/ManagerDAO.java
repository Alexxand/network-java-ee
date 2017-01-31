package com.github.alexxand.db;

import com.github.alexxand.model.Manager;

import java.io.InputStream;

public interface ManagerDAO {
    /**
     * Add the manager if it's possible.
     *
     * @param manager The manager to be added (field id is just ignored)
     * @return false if such email already exists in the data source and true in another case
     */
    boolean add(Manager manager);

    /**
     * Get a manager if it's possible
     *
     * @param email        unique e'mail of the manager
     * @param passwordHash hash of a password after using scrypt
     * @return Manager if there's a manager with such e'mail and passwordCash and null otherwise.
     */
    Manager get(String email, String passwordHash);

    InputStream getPhoto(int managerId);

    //TODO подумать, как можно удобно произвести изменение любого количества параметров одной функцией
    boolean update(Manager manager);
}
