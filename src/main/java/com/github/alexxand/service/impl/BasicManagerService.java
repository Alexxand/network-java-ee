package com.github.alexxand.service.impl;

import com.github.alexxand.db.ManagerDAO;
import com.github.alexxand.model.BasicManagerInf;
import com.github.alexxand.model.ManagerInf;
import com.github.alexxand.model.Photo;
import com.github.alexxand.service.ManagerService;
import com.github.alexxand.service.SecurityService;
import com.google.inject.Inject;

public class BasicManagerService implements ManagerService {
    private final SecurityService securityService;
    private final ManagerDAO managerDAO;

    @Inject
    public BasicManagerService(SecurityService securityService, ManagerDAO managerDAO){
        this.securityService = securityService;
        this.managerDAO = managerDAO;
    }

    @Override
    public ManagerInf stash(BasicManagerInf baseInf) {
        String email = baseInf.getEmail();
        String passwordHash = securityService.encrypt(baseInf.getPassword());
        String fullName = baseInf.getName() + " " + baseInf.getLastName();

        if(managerDAO.checkEmail(email))
            return null;

        ManagerInf managerInf = new ManagerInf();
        managerInf.setEmail(email);
        managerInf.setPasswordHash(passwordHash);
        managerInf.setFullName(fullName);
        return managerInf;
    }

    @Override
    public boolean add(ManagerInf managerInf, Photo photo) {
        if (managerDAO.checkEmail(managerInf.getEmail()))
            return false;
        managerDAO.add(managerInf,photo);
        return true;
    }
}
