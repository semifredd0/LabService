package com.uniba.di.dfmdevelop.labservice.model;

import com.uniba.di.dfmdevelop.labservice.model.cittadino.Cittadino;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

/*
    Questa classe, assieme a CustomUserDetailsService, viene
    utilizzata per la configurazione nella classe SecurityConfig.
 */
@Data
@Entity(name = "utente_generico")
public class UtenteGenerico implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "indirizzo_email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "ruolo",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String role;

    // Disattivo finchè non conferma la registrazione
    @Column(
            name = "enabled",
            nullable = false
    )
    private Boolean enabled = false;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "utenteGenerico"
    )
    private Laboratorio laboratorio;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "utenteGenerico"
    )
    private Cittadino cittadino;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(mappedBy = "utenteGenerico", cascade = CascadeType.ALL)
    private Collection<Prenotazione> listaPrenotazioni = new ArrayList<>();

    public UtenteGenerico() {
    }

    public UtenteGenerico(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + this.role);
        authorities.add(auth);
        return authorities;
    }

    /*
        Il ruolo per ogni utente è unico, quindi
        basta controllare che il ruolo passato al
        metodo sia uguale a quello impostato per l'utente.
     */
    public boolean hasRole(String roleName) {
        return this.role.equals(roleName);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.enabled;
    }
}
