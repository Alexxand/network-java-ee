package com.github.alexxand.db;

import com.github.alexxand.utils.exceptions.DAOException;
import com.github.alexxand.model.Manager;

import java.io.InputStream;

public interface ManagerDAO {
    /**
     * Check if manager with such email doesn't yet exist.
     * @param email email to check
     * @return true if a manager with the email doesn't yet exist and false otherwise
     * @throws DAOException if an error occurs
     */
    boolean checkEmail(String email) throws DAOException;

    /**
     * Add the manager if it's possible.
     *
     * @param manager The manager to be added (field id is just ignored)
     * @return false if such email already exists in the data source and true in another case
     * @throws DAOException if another error occurs
     */
    boolean add(Manager manager) throws DAOException;

    /**
     * Check if such pair (email, passwordHash) actually exists
     * @param email email to check
     * @param passwordHash passwordHash to check
     * @return true if such pair exists and false otherwise
     * @throws DAOException if an error occurs
     */
    boolean checkEmailAndPassword(String email, String passwordHash) throws DAOException;

    /**
     * Get a manager if it's possible
     *
     * @param email unique e'mail of the manager
     * @return Manager if there's a manager with such e'mail and null otherwise
     * @throws DAOException if another error occurs
     */
    Manager get(String email) throws DAOException;

    InputStream getPhoto(int managerId) throws DAOException;

    //TODO подумать, как можно удобно произвести изменение любого количества параметров одной функцией
    boolean update(Manager manager) throws DAOException;
}
