package com.jack.entity;

import org.hibernate.annotations.GenericGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Getter
    @Setter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter
    @Setter
    @Column(name = "roomName")
    private String roomName;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="creator", referencedColumnName="id", nullable=false)
    private User creator;

    public Room() {}
}