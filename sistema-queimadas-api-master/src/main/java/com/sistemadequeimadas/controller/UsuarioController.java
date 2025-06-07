package com.sistemadequeimadas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadequeimadas.model.Usuario;
import com.sistemadequeimadas.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários do sistema")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Operation(summary = "Criar novo usuário", description = "Cadastra um novo usuário no sistema.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos") })
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
		Usuario savedUsuario = usuarioService.save(usuario);
		return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
	}

	@Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados.")
	@ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuarios() {
		List<Usuario> usuarios = usuarioService.findAll();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

	@Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
		return usuarioService.findById(id).map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioDetails) {
		Usuario updatedUsuario = usuarioService.update(id, usuarioDetails); // Exceções tratadas pelo
																			// GlobalExceptionHandler
		return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
	}

	@Operation(summary = "Excluir usuário", description = "Remove um usuário do sistema pelo ID.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable Long id) {
		usuarioService.deleteById(id); // Exceções tratadas pelo GlobalExceptionHandler
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
