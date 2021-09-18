package com.techelevator.model;

import java.math.BigDecimal;

public class Player {
    private int id;
    private int user_id;
    private int game_id;
    private BigDecimal availableFunds;

    public Player(int id, int user_id, int game_id) {
        this.id = id;
        this.user_id = user_id;
        this.game_id = game_id;
        this.availableFunds = new BigDecimal(100000);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public BigDecimal getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(BigDecimal availableFunds) {
        this.availableFunds = availableFunds;
    }
}