package com.unip.biometria.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.unip.biometria.model.entities.enums.AcessLevel;
@Entity
@Table(name = "usuarios")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcessLevel nivelAcesso = AcessLevel.USER;

    @Column(name = "posicao_digital")
    private Integer posicaoDigital; // Posição no ESP32
    
    public Users() {}

    public Users(String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.nivelAcesso = AcessLevel.USER;
    }

    // Getters e Setters
    public long getId() {
        return id;
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

    public AcessLevel getNivelAcesso() {
        return nivelAcesso;
    }

    public boolean podeAlterarOuDeletar(Users outro) {
        return this.nivelAcesso.ordinal() >= outro.nivelAcesso.ordinal();
    }
}
