package com.delivery.database.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Entity(name = "store")
@EqualsAndHashCode(callSuper = true, of = {"name", "address"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "store")
@ToString(of = {"name", "address"})
@SuperBuilder
public class StoreDb extends BaseDbEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "cousine_id", nullable = false)
    private CousineDb cousine;

    @OneToMany(mappedBy = "store")
    @Default
    private Set<ProductDb> products = new HashSet<>();

}
