package com.github.alexxand.service;

import com.github.alexxand.model.BasicManagerInf;
import com.github.alexxand.model.ManagerInf;
import com.github.alexxand.model.Photo;

public interface ManagerService {
    /**
     * Stash basic information in ManagerInf.
     * @param basicInf basic information obtained from a manager.
     * @return ManagerInf if there aren't a manager with such e'mail in a database and null otherwise.
     */
    ManagerInf stash(BasicManagerInf basicInf);
    /**
     * Add manager in a database
     * @param managerInf full information obtained from a manager.
     * @param photo photo obtained from a manager.
     * @return true if a try of adding this manager was successful and
     * false if there are already a manager with such e'mail
     */
    boolean add(ManagerInf managerInf, Photo photo);
}
