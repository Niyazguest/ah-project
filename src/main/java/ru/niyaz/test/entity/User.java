package ru.niyaz.test.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 04.09.15.
 */

@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Integer role;

    @Column(name = "vk_page")
    private String vkPage;

    @Column(name = "current_place_id")
    private String currentPlaceId;

    @Column(name = "want")
    private Boolean want;

    @Column(name = "active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getWant() {
        return want;
    }

    public void setWant(Boolean want) {
        this.want = want;
    }

    public String getCurrentPlaceId() {
        return currentPlaceId;
    }

    public void setCurrentPlaceId(String currentPlaceId) {
        this.currentPlaceId = currentPlaceId;
    }

    public String getVkPage() {
        return vkPage;
    }

    public void setVkPage(String vkPage) {
        this.vkPage = vkPage;
    }

}
