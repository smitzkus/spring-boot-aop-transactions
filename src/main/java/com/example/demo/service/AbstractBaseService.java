/**
 * Projekt: HB-Kautionen
 * Copyright (c): 2013-2024 iSYS Software GmbH
 */
package com.example.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mitzkus
 *
 */
@Service
@Transactional
public class AbstractBaseService {

    @PersistenceContext
    protected EntityManager entityManager;

}
