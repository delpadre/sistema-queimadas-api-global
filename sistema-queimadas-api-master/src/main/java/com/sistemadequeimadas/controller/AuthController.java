package com.sistemadequeimadas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadequeimadas.dto.LoginDTO;
import com.sistemadequeimadas.dto.TokenResponse;
import com.sistemadequeimadas.model.Usuario;
import com.sistemadequeimadas.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Admin - Autenticação", description = "Endpoints de login e cadastro")
public class AuthController {

	private final UsuarioService usuarioService;

	@Operation(summary = "Autenticar usuário (Login)", description = "Realiza autenticação de um usuário e retorna o token JWT", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais do usuário", required = true, content = @Content(schema = @Schema(implementation = LoginDTO.class), examples = @ExampleObject(name = "Credenciais de Login", value = """
			    {
			        "nome": "joao.silva",
			        "senha": "Senha@123"
			    }
			"""))))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
			@ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida") })
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginDTO usuario) {
		return ResponseEntity.ok(this.usuarioService.autenticar(usuario.nome(), usuario.senha()));
	}

	@Operation(summary = "Cadastrar novo usuário", description = "Cadastra um novo usuário no sistema", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo usuário", required = true, content = @Content(schema = @Schema(implementation = Usuario.class), examples = @ExampleObject(name = "Exemplo de cadastro", value = """
			    {
			        "nome": "joao.silva",
			        "senha": "Senha@123"
			    }
			"""))))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso", content = @Content(schema = @Schema(implementation = Usuario.class))),
			@ApiResponse(responseCode = "400", description = "Requisição inválida") })
	@PostMapping("/register")
	public ResponseEntity<Usuario> register(@Valid @RequestBody Usuario usuario) {
		return ResponseEntity.ok(this.usuarioService.save(usuario));
	}
}
