package com.foodlink.security;

import com.foodlink.domain.entity.Creator;
import com.foodlink.domain.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CreatorRepository creatorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Creator creator = creatorRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Creator not found with email: " + email)
                );

        return UserPrincipal.create(creator);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Creator creator = creatorRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Creator not found with id: " + id)
                );

        return UserPrincipal.create(creator);
    }
}
