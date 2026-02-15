package com.foodlink.service;

import com.foodlink.domain.entity.Creator;
import com.foodlink.domain.repository.CreatorRepository;
import com.foodlink.dto.request.CreatorRegistrationRequest;
import com.foodlink.dto.request.LoginRequest;
import com.foodlink.dto.response.AuthResponse;
import com.foodlink.dto.response.CreatorResponse;
import com.foodlink.exception.ResourceAlreadyExistsException;
import com.foodlink.mapper.CreatorMapper;
import com.foodlink.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final CreatorRepository creatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CreatorMapper creatorMapper;

    public AuthService(CreatorRepository creatorRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, CreatorMapper creatorMapper) {
        this.creatorRepository = creatorRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.creatorMapper = creatorMapper;
    }

    @Transactional
    public AuthResponse register(CreatorRegistrationRequest request) {
        // Check if email already exists
        if (creatorRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        // Check if username already exists
        if (creatorRepository.existsBySlug(request.getSlug())) {
            throw new ResourceAlreadyExistsException("Slug already taken");
        }

        // Check if Instagram handle already exists (if provided)
        if (request.getInstagramHandle() != null && 
            creatorRepository.existsByInstagramHandle(request.getInstagramHandle())) {
            throw new ResourceAlreadyExistsException("Instagram handle already registered");
        }

        // Create creator
        Creator creator = Creator.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .username(generateUsername(request.getDisplayName()))
                .displayName(request.getDisplayName())
                .slug(request.getSlug().toLowerCase())
                .instagramHandle(normalizeInstagramHandle(request.getInstagramHandle()))
                .bio(request.getBio())
                .subscriptionTier(Creator.SubscriptionTier.FREE)
                .status(Creator.CreatorStatus.ACTIVE)
                .build();

        creator = creatorRepository.save(creator);

        // Authenticate and generate token
        String token = tokenProvider.generateTokenFromUserId(creator.getId());

        CreatorResponse creatorResponse = creatorMapper.toResponse(creator);

        return AuthResponse.builder()
                .token(token)
                .expiresIn(tokenProvider.getExpirationMs())
                .creator(creatorResponse)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        // Update last login
        Creator creator = creatorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Creator not found"));
        creator.setLastLoginAt(LocalDateTime.now());
        creatorRepository.save(creator);

        CreatorResponse creatorResponse = creatorMapper.toResponse(creator);

        return AuthResponse.builder()
                .token(token)
                .expiresIn(tokenProvider.getExpirationMs())
                .creator(creatorResponse)
                .build();
    }

    private String generateUsername(String displayName) {
        String baseUsername = displayName.toLowerCase()
                .replaceAll("[^a-z0-9]", "")
                .substring(0, Math.min(displayName.length()-1, 20));
        
        String username = baseUsername;
        int counter = 1;
        
        while (creatorRepository.existsByUsername(username)) {
            username = baseUsername + counter++;
        }
        
        return username;
    }

    private String normalizeInstagramHandle(String handle) {
        if (handle == null || handle.isBlank()) {
            return null;
        }
        return handle.startsWith("@") ? handle.substring(1) : handle;
    }
}
