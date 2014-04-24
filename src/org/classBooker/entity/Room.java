/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
/**
 *
 * @author josepma
 */
@Entity
@Table(name = "ROOM")

public class Room {
    @Id
    @Column(name = "ID")
    private String id;
    @ManyToOne
    @JoinColumn(name = "BUILDING_NAME", referencedColumnName = "NAME")
    private Building building;
    @OneToMany (mappedBy="room", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<Reservation> reservations;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Room() {
        this.id = UUID.randomUUID().toString(); 
    }

    public Building getBuilding() {
        return building;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    
    @Override
    public String toString() {
        String result="Room id "+ id+ "building "+building.toString()+"reservations "+reservations;
        return result; 
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Room) && ((Room) obj).id.equals(this.id)
                && ((((Room) obj).building == null && this.building == null)
                || ((Room) obj).building.equals(this.building));
    }
    
}
