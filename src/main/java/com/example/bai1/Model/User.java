    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.example.bai1.Model;

    import jakarta.persistence.CascadeType;
    import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.JoinTable;
    import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
    import jakarta.persistence.Table;
import java.util.List;
    import java.util.Set;
    import lombok.Data;

    /**
     *
     * @author Admin
     */
    @Entity
    @Data
    @Table(name = "user")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String name;
        private String username;
        private String email;
        private String password;
        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(
                name="users_roles",
                joinColumns = @JoinColumn(name = "userId",referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name ="role_id",referencedColumnName = "id" )
        )
        
        private Set<Role>roles;
        @OneToMany(fetch = FetchType.EAGER)
        private List<LogoutToken>tokens;
    }
