package com.sistemadequeimadas.model;

import com.sistemadequeimadas.enums.TipoPerfilUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "tb_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email deve ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false)
    private String senha;

    @Size(max = 20, message = "Telefone não pode exceder 20 caracteres")
    private String telefone;

    @NotNull(message = "Tipo de perfil não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPerfilUsuario tipoPerfil;

    @ManyToMany
    @JoinTable(
            name = "tb_usuario_area_monitoramento",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "area_monitoramento_id")
    )
    private Set<AreaMonitoramento> areasAtuacao;

    public Usuario(String nome, String email, String senha, String telefone, TipoPerfilUsuario tipoPerfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.tipoPerfil = tipoPerfil;
    }

    // Getters e setters manuais para compatibilidade com o padrão JavaBean
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoPerfilUsuario getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(TipoPerfilUsuario tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Set<AreaMonitoramento> getAreasAtuacao() {
        return areasAtuacao;
    }

    public void setAreasAtuacao(Set<AreaMonitoramento> areasAtuacao) {
        this.areasAtuacao = areasAtuacao;
    }
}
