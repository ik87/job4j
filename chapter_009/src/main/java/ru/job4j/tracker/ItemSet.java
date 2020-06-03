package ru.job4j.tracker;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "item")
public class ItemSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Column(name = "name")
    private String name;
    //@Column(name = "description")
    private String description;
    //@Column(name = "created")
    private Timestamp created;

    public ItemSet() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
