package org.example.server_mobile.controller;

import org.example.server_mobile.dto.request.ApiResponse;

import java.util.List;

public interface IController<RESQUEST, RESPONSE> {
    ApiResponse<RESPONSE> create(RESQUEST request);

    ApiResponse<RESPONSE> findById(Long id);

    ApiResponse<List<RESPONSE>> findAll();

    ApiResponse<String> update(Long id, RESQUEST request);

    ApiResponse<String> delete(Long id);
}
