package com.sistemadequeimadas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistemadequeimadas.config.JwtProvider;
import com.sistemadequeimadas.dto.TokenResponse;
import com.sistemadequeimadas.exception.ResourceNotFoundException;
import com.sistemadequeimadas.model.Usuario;
import com.sistemadequeimadas.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenResponse autenticar(String user, String senha) {
        Usuario admin = usuarioRepository.findByNome(user)
                .orElseThrow(() -> new EntityNotFoundException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(senha, admin.getSenha())) {
            throw new EntityNotFoundException("Usuário ou senha inválidos");
        }

        String accessToken = jwtProvider.generateAccessToken(admin);

        return new TokenResponse(accessToken);
    }

    public Usuario save(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario update(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioDetails.getNome());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setTelefone(usuarioDetails.getTelefone());
            usuario.setTipoPerfil(usuarioDetails.getTipoPerfil());
            if (usuarioDetails.getSenha() != null && !usuarioDetails.getSenha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(usuarioDetails.getSenha()));
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }

    public void deleteById(Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getNome())
                .password(usuario.getSenha())
                .build();
    }
}
