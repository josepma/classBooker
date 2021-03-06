/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.classbooker.entity;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author josepma
 * 
 */
@Entity
@Table(name="BUILDING")
public class Building {


    
    @Id
    @Column (name="NAME")
    private String name;

    
    @OneToMany (mappedBy="building", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<Room> rooms;
    
    public Building() {
        this.rooms=new ArrayList<Room>();
        this.name="";
    }
    
    public Building (String name){
        this.rooms=new ArrayList<Room>();
        this.name = name; 
    }
    
    public String getBuildingName(){
        return name;
    }
    
    public void setBuildingName(String name){
        this.name=name;
    }    
    
    /**
     *
     * @return
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     *
     * @param rooms
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public void setRoom(Room room){
        this.rooms.add(room);
    }
    
    
    @Override
    public String toString(){
        return "Building{name="+name + "rooms="+rooms+"}";       
    }
    
    @Override
    public boolean equals(Object obj){
        return (obj instanceof Building) && 
                ((Building) obj).name.equals(this.name);          
    }
    

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
