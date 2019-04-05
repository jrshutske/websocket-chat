package com.jack.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "characters",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"realmname", "charactername","id"})})
public class Character {

    @Id
    @Getter
    @Setter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter
    @Setter
    @Column(name = "charactername")
    private String charactername;

    @Getter
    @Setter
    @Column(name = "realmname")
    private String realmname;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="creator", referencedColumnName="id", nullable=false)
    private User creator;

    public Character() {}
}