package com.resetoter.battleqq.mybatis.dao;

import com.resetoter.battleqq.mybatis.model.PlayerInfo;

public interface PlayerInfoMapper {
    int deleteByPrimaryKey(String playername);

    int insert(PlayerInfo record);

    int insertSelective(PlayerInfo record);

    PlayerInfo selectByPrimaryKey(String playername);

    int updateByPrimaryKeySelective(PlayerInfo record);

    int updateByPrimaryKey(PlayerInfo record);
}