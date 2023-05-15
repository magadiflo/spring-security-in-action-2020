package com.magadiflo.book.security.app.security;

import com.magadiflo.book.security.app.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * RESPONSABILIDAD: Clase que representa la implementación de un usuario
 * reconocido por Spring Security dentro de su Arquitectura.
 * <p>
 * Esta clase representa un usuario con el que Spring Security trabajará dentro de
 * su Arquitectura, ya que implementa la interfaz UserDetails. Observamos que
 * esta clase que implementa el UserDetails usa la clase Entity User para obtener los
 * datos que dicha entidad trae desde la base de datos (o de un servicio web),
 * pero es esta implementación (SecurityUser) la clase que Spring Security reconocerá
 * como un usuario de Spring, para manejar sus roles, permisos, autenticación, etc.
 */
public class SecurityUser implements UserDetails {
    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.user.getAuthority()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
