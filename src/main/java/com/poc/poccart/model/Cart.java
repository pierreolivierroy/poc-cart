package com.poc.poccart.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "ttl")
    private Integer timeToLive;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Column(name = "closed_date")
    private Time closedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem item) {
        if (item != null) {
            this.getItems().add(item);
            item.setCart(this);
        }
    }

    public void removeItem(CartItem item) {
        if (item != null) {
            this.getItems().remove(item);
        }
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Time getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Time closedDate) {
        this.closedDate = closedDate;
    }
}
