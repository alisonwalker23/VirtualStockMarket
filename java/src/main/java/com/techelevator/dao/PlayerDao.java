package com.techelevator.dao;

import com.techelevator.model.Player;

import java.util.List;

public interface PlayerDao {

    List<Player> getAllPlayers();

    List<Player> getPlayersByGame(int game_id);

    Player getPlayerById(int id);

    void createPlayer(Player player);

    void updatePlayer(Player player);

    void deletePlayer(int id);

}