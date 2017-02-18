package com.github.alexxand.service.impl;

import com.github.alexxand.db.ManagerDAO;
import com.github.alexxand.model.Credentials;
import com.github.alexxand.service.LoginService;
import com.github.alexxand.service.SecurityService;
import com.google.inject.Inject;

public class BasicLoginService implements LoginService {
    private final SecurityService securityService;
    private final ManagerDAO managerDAO;

    @Inject
    public BasicLoginService(SecurityService securityService, ManagerDAO managerDAO){
        this.securityService = securityService;
        this.managerDAO = managerDAO;
    }

    @Override
    public boolean check(Credentials credentials) {
        String passwordHash = managerDAO.getPasswordHash(credentials.getEmail());
        return securityService.check(credentials.getPassword(),passwordHash);
    }

    @Override
    public int getId(Credentials credentials) {
        return 0;
    }
}
