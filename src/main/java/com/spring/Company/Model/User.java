package com.spring.Company.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.Company.Enum.Level;
import com.spring.Company.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

    @Override
    public String getUsername() {
        return email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String title;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    private String phone;
    private Float salaryGross;

    @Enumerated(EnumType.STRING)
    private Level level;
    @Transient
    private Float salaryNet;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private User manager;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" +role.name()));
    }




    private Float calculateNetSalary(Float grossSalary) {

        if (grossSalary == null) {
            return 0.0f; // Return 0 if gross salary is not provided
        }

        // This line will now only run if grossSalary has a value
        return grossSalary * 0.8f;
    }

    public Float getSalaryNet() {
        return calculateNetSalary(salaryGross);
    }


}