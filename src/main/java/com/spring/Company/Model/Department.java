package com.spring.Company.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.profile.Fetch;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
