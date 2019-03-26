package com.jack.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
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
  @Column(name = "username")
  private String username;

  @Getter
  @Setter
  @Column(name = "password")
  private String password;

  @Getter
  @Setter
  @Column(name = "email")
  private String email;

  @Getter
  @Setter
  @Column(name = "firstname")
  private String firstName;

  @Getter
  @Setter
  @Column(name = "lastname")
  private String lastName;

  @Getter
  @Setter
  @OneToMany(mappedBy="creator")
  private Set<Room> rooms;

  public User() {}
}