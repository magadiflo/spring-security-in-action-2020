package com.magadiflo.book.security.app.entity;

/**
 * RESPONSABILIDAD: Esta clase puede representar un Entity (mismo que tendrá su correspondiente tabla en la BD)
 * o una clase que nos permita representar usuarios provenientes de un Servicio Web.
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private String authority; // Por el momento solo trabajaremos con un authoriy por usuario

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", authority='").append(authority).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
