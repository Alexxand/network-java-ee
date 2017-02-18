package com.github.alexxand.service.impl;

import com.github.alexxand.model.ManagerInf;
import com.github.alexxand.service.SecurityService;
import com.google.inject.Inject;
import com.lambdaworks.crypto.SCryptUtil;

import javax.inject.Named;

public class SCryptSecurityService implements SecurityService{

    //CPU cost parameter
    private final int N;

    //Memory cost parameter
    private final int r;

    //Parallelization parameter
    private final int p;

    @Inject
    public SCryptSecurityService(@Named("scrypt.N") int N,
                                 @Named("scrypt.r") int r,
                                 @Named("scrypt.p") int p){
        this.N = N;
        this.r = r;
        this.p = p;
    }

    @Override
    public String encrypt(String password) {
        return SCryptUtil.scrypt(password,N,r,p);
    }

    @Override
    public boolean check(String password, String passwordHash) {
        return SCryptUtil.check(password, passwordHash);
    }
}
