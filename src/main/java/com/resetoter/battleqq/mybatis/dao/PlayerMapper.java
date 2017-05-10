package com.resetoter.battleqq.mybatis.dao;

import com.resetoter.battleqq.mybatis.model.Player;
import com.resetoter.battleqq.mybatis.model.PlayerKey;

public interface PlayerMapper {
    int deleteByPrimaryKey(PlayerKey key);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(PlayerKey key);

    Player selectByName(PlayerKey key);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
}