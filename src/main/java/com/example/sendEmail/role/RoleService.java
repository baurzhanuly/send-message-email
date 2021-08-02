package com.example.sendEmail.role;

import com.example.sendEmail.exceptions.ServiceException;
import com.example.sendEmail.utils.codes.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) throws ServiceException {
        if (role.getId() != null){
            throw ServiceException.builder()
                    .code(ErrorCode.ALREADY_EXISTS)
                    .message("role exists")
                    .build();
        }
        return roleRepository.save(role);
    }
}
