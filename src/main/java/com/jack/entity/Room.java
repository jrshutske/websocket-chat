package com.jack.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;


    @Column(name = "roomName")
    private String roomName;

    @ManyToOne
    @JoinColumn(name="creator", referencedColumnName="id", nullable=false)
    private User creator;

    public Room() {
    }

    public Room(User creator, String roomName) {
        this.roomName = roomName;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() { return creator; }

    public void setCreator(User creator) { this.creator = creator; }

    public String getRoomName() { return roomName; }

    public void setRoomName(String roomName) { this.roomName = roomName; }

}