package com.jack.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import java.util.*;
import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {

  @Id
  @Getter
  @Setter
  @Column(name="id")
  @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
  @GenericGenerator(name = "native", strategy = "native")
  private int id;

  @Getter
  @Setter
  @NaturalId
  @Column(name = "username")
  private String username;

  @Getter
  @Setter
  @Column(name = "password")
  private String password;

  @Getter
  @Setter
  @Column(name = "contact")
  private String contact;

  @Getter
  @Setter
  @Column(name = "firstname")
  private String firstname;

  @Getter
  @Setter
  @Column(name = "lastname")
  private String lastname;

  @Getter
  @Setter
  @OneToMany(mappedBy="creator", fetch=FetchType.EAGER)
  private Set<Character> characters;

  public User() {}
}