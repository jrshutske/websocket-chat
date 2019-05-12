package com.jack.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Character model.
 */
public class CharacterModel {

    @Getter
    @Setter
    private Integer id;

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
    private String classname;

    @Getter
    @Setter
    private Integer racenumber;

    @Getter
    @Setter
    private String racename;

    @Getter
    @Setter
    private String faction;

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
    private Integer totalhonorablekills;
}
