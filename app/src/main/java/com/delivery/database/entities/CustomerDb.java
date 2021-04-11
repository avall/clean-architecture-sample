package com.delivery.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Entity(name = "customer")
@EqualsAndHashCode(callSuper = true, of = {"name", "email", "address", "password"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "customer")
@ToString(callSuper = true, of = {"name", "email", "address"})
@SuperBuilder
public class CustomerDb extends BaseDbEntity {
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String password;
}
