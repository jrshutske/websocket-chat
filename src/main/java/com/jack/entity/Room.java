package com.jack.entity;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "roomName")
    private String roomName;

    @ManyToOne
    @JoinColumn(name="creator", referencedColumnName="id", nullable=false)
    private User creator;

    public Room() {}
}