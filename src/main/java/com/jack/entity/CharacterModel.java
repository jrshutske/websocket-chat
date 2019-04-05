package com.jack.entity;

import lombok.Getter;
import lombok.Setter;

public class CharacterModel {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String realm;

    @Getter
    @Setter
    private String battlegroup;

    @Getter
    @Setter
    private Integer classnumber;

    @Getter
    @Setter
    private Integer race;

    @Getter
    @Setter
    private Integer level;

    @Getter
    @Setter
    private Integer achievementpoints;

    @Getter
    @Setter
    private String thumbnail;

    @Getter
    @Setter
    private Integer totalhorablekills;
}
