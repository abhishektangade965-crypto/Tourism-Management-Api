package com.example.controller;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.response.ApiResponse;
import com.example.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token
        String jwt = tokenProvider.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        AuthResponse authResponse = new AuthResponse(jwt, userDetails.getUsername(), roles);

        return ResponseEntity.ok(new ApiResponse(true, "User authenticated successfully", authResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Username is already taken!", null));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Email is already in use!", null));
        }

        // Creating user's account
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "User registered successfully", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Not authenticated", null));
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Mask password before sending
        user.setPassword(null);

        return ResponseEntity.ok(new ApiResponse(true, "Current user retrieved successfully", user));
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${app.oauth2.google.client-id:}")
    private String googleClientId;

    @Value("${app.oauth2.github.client-id:}")
    private String githubClientId;

    @Value("${app.oauth2.github.client-secret:}")
    private String githubClientSecret;

    @PostMapping("/google")
    public ResponseEntity<ApiResponse> googleLogin(@RequestBody Map<String, String> request) {
        String accessToken = request.get("accessToken");
        if (accessToken == null || accessToken.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Access token is required", null));
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
            Map<String, Object> profile = response.getBody();
            
            if (profile == null || !profile.containsKey("email")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Failed to retrieve Google profile", null));
            }

            String email = (String) profile.get("email");
            String name = (String) profile.get("name");
            String sub = (String) profile.get("sub");
            
            // Check if user already exists
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                // Register on the fly
                user = new User();
                String baseUsername = email.split("@")[0];
                String username = baseUsername;
                int suffix = 1;
                while (userRepository.existsByUsername(username)) {
                    username = baseUsername + suffix;
                    suffix++;
                }
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("google_oauth_" + sub + "_secure_pass"));
                user.setRoles(Collections.singleton(Role.ROLE_USER));
                userRepository.save(user);
            }

            // Authenticate programmatically
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toSet());

            AuthResponse authResponse = new AuthResponse(jwt, userDetails.getUsername(), roles);
            return ResponseEntity.ok(new ApiResponse(true, "Google authentication successful", authResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Google login failed: " + e.getMessage(), null));
        }
    }

    @PostMapping("/github")
    public ResponseEntity<ApiResponse> githubLogin(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        if (code == null || code.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Authorization code is required", null));
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            
            // 1. Exchange code for access token
            String tokenUrl = "https://github.com/login/oauth/access_token";
            Map<String, String> params = new HashMap<>();
            params.put("client_id", githubClientId);
            params.put("client_secret", githubClientSecret);
            params.put("code", code);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, entity, Map.class);
            Map<String, Object> tokenBody = tokenResponse.getBody();
            
            if (tokenBody == null || !tokenBody.containsKey("access_token")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Failed to exchange GitHub authorization code", null));
            }

            String accessToken = (String) tokenBody.get("access_token");

            // 2. Fetch user profile
            String userUrl = "https://api.github.com/user";
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setBearerAuth(accessToken);
            HttpEntity<String> userEntity = new HttpEntity<>(userHeaders);
            
            ResponseEntity<Map> userResponse = restTemplate.exchange(userUrl, HttpMethod.GET, userEntity, Map.class);
            Map<String, Object> githubProfile = userResponse.getBody();

            if (githubProfile == null || !githubProfile.containsKey("id")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Failed to retrieve GitHub profile", null));
            }

            String id = String.valueOf(githubProfile.get("id"));
            String login = (String) githubProfile.get("login");
            String email = (String) githubProfile.get("email");
            
            if (email == null) {
                email = login + "@github.com";
            }

            // Check if user already exists
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                // Register on the fly
                user = new User();
                String username = login;
                int suffix = 1;
                while (userRepository.existsByUsername(username)) {
                    username = login + suffix;
                    suffix++;
                }
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("github_oauth_" + id + "_secure_pass"));
                user.setRoles(Collections.singleton(Role.ROLE_USER));
                userRepository.save(user);
            }

            // Authenticate programmatically
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toSet());

            AuthResponse authResponse = new AuthResponse(jwt, userDetails.getUsername(), roles);
            return ResponseEntity.ok(new ApiResponse(true, "GitHub authentication successful", authResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "GitHub login failed: " + e.getMessage(), null));
        }
    }
}
