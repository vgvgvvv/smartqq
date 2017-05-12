package com.resetoter.battleqq.mybatis.dao;

import com.resetoter.battleqq.mybatis.model.PlayerInfo;
import com.resetoter.battleqq.mybatis.model.PlayerInfoKey;

public interface PlayerInfoMapper {
    int deleteByPrimaryKey(PlayerInfoKey key);

    int insert(PlayerInfo record);

    int insertSelective(PlayerInfo record);

    PlayerInfo selectByPrimaryKey(PlayerInfoKey key);

    PlayerInfo selectByName(PlayerInfoKey key);

    int updateByPrimaryKeySelective(PlayerInfo record);

    int updateByPrimaryKey(PlayerInfo record);
}