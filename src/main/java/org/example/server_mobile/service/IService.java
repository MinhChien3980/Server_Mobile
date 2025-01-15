package org.example.server_mobile.service;

import java.util.List;

public interface IService<REQUEST, RESPONSE> {
    RESPONSE create(REQUEST request);

    RESPONSE update(REQUEST request);

    RESPONSE findById(Long id);

    void delete(Long id);

    List<RESPONSE> findAll();

}
