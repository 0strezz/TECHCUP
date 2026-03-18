package main.java.edu.eci.dosw.tech_cup.controller;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
