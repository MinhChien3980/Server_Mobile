package org.example.server_mobile.service;

import org.example.server_mobile.dto.request.PermissionRequest;
import org.example.server_mobile.dto.response.PermissionResponse;
import org.example.server_mobile.entity.Permission;
import org.example.server_mobile.mapper.PermissionMapper;
import org.example.server_mobile.repository.PermissionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissonService {
    PermissionRepo permissionRepo;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest resquest) {
        Permission permission = permissionMapper.toPermission(resquest);
        permission = permissionRepo.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepo.findAll();
        return permissions.stream().map(permission -> permissionMapper.toPermissionResponse(permission)).toList();
    }

    public void delete(String permission) {
        permissionRepo.deleteById(permission);
    }
}
