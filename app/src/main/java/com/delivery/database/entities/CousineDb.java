package com.delivery.database.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "cousine")
@EqualsAndHashCode(callSuper = true, of = {"name"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "cousine")
@ToString(of = {"name"})
@SuperBuilder
public class CousineDb extends BaseDbEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "cousine",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Default
    private Set<StoreDb> stores = new HashSet<>();

    // TODO: test
    public void addStore(StoreDb store) {
        if (this.stores == null) {
            this.stores = new HashSet<>();
        }

        store.setCousine(this);
        this.stores.add(store);
    }

    // TODO: test
    public static CousineDb newInstance(String name) {
        return CousineDb.builder().name(name).build();
    }
}
