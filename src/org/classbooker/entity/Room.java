/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.entity;
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

    /**
     *
     */
    public Room() {
        this.capacity = 0;
        this.building = null;
        this.reservations=new ArrayList<>();
        
    }
    
    /**
     * Contructor room
     * @param building
     * @param number
     * @param capacity
     */
    public Room( Building building, String number, int capacity) {
        this.number=number;
        this.building = building;
        this.capacity = capacity;
        this.reservations=new ArrayList<>();
       
    }

    /**
     * This method return an Integer, it is a room capacity.
     * @return Capacity
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * This method return an String, it is a room Number.
     * @return Number
     */
    public String getNumber() {
        return number;
    }

    /**
     *This method change String number room 
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     *This method change Integer capacity room 
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     *This method return log, this is a room id.
     * @return  room Id
     */
    public long getRoomId() {
        return roomId;
    }

    /**
     *This method change long id
     * @param roomId
     */
    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
    
    /**
     * The method return a Building Object, this is a building than the room belongs.
     * @return building
     */
    public Building getBuilding() {
        return building;
    }

    /**
     *This method add reservation at the list of reservations.
     * @param r     Reservation Object
     */
    public void setReservation(Reservation r){
        this.reservations.add(r);
    }

    /**
     *The method return a List<Reservation> object, this represent the reservations room's.
     * @return  List reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * The method change building room
     * @param building
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     *The method change the reservation list
     * @param reservations
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * The method return a String that describe a Room
     * @return   description
     */
    @Override
    public String toString() {
        String result="Room{Type: "+this.getClass().getName()+ "Room id: "+ roomId + 
                      "Number: "+ number +"building: " +building.getBuildingName()+"}";
        return result; 
    }

    /**
     *This method compare if two rooms are equals, return true if is equals and false if not equals
     *
     * @param obj Especific Room
     * @return  If the Rooms are equals
     */
    @Override
    public boolean equals(Object obj) {
        
        return (obj instanceof Room) && ((Room)obj).roomId==(this.roomId);
                
    }

    /**
     * This method return a integer hash Code about the class
     * @return hash Code
     */
    @Override
    public int hashCode() {
        return super.hashCode(); 
    }
    
}
