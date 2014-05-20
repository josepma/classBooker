/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classBooker.entity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
/**
 *
 * @author josepma
 */
@Entity
@Table(name = "ROOM")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ROOM_TYPE",
        discriminatorType=DiscriminatorType.STRING,length=5)
public abstract class Room {
    
    
    @Id
    @GeneratedValue      
    @Column(name = "ROOMID")    
    private long roomId;
    
    private String number;    
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "BUILDING", referencedColumnName = "NAME")
    private Building building;
    @OneToMany (mappedBy="room", fetch=FetchType.EAGER)
    private List<Reservation> reservations;

    public Room() {
        this.capacity = 0;
        this.building = null;
        this.reservations=new ArrayList<>();
        
    }
    
    public Room( Building building, String number, int capacity) {
        this.number=number;
        this.building = building;
        this.capacity = capacity;
        this.reservations=new ArrayList<>();
       
    }

    public int getCapacity() {
        return capacity;
    }
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

  

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

   
    
    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
    
    public Building getBuilding() {
        return building;
    }

    public void setReservation(Reservation r){
        this.reservations.add(r);
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
        String result="Room{Type: "+this.getClass().getName()+ "Room id: "+ roomId + 
                      "Number: "+ number +"building: " +building.getBuildingName()+"}";
        return result; 
    }

    @Override
    public boolean equals(Object obj) {
        
        return (obj instanceof Room) && ((Room)obj).roomId==(this.roomId);
                
    }

    @Override
    public int hashCode() {
        return super.hashCode(); 
    }
    
}
