package com.company.media.data;

import com.company.media.model.Role;
import com.company.media.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ADMIN_ROLE","USER_ROLE");
        createDefaultRolesIfNotExists(defaultRoles);
    }

    private void createDefaultRolesIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role->roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }

}
