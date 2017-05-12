package com.resetoter.battleqq.mybatis.model;

public class PlayerInfoKey {
    private Integer id;

    private String playername;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername == null ? null : playername.trim();
    }
}