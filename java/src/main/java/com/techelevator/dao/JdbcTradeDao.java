package com.techelevator.dao;

import com.techelevator.model.Player;
import com.techelevator.model.Stock;
import com.techelevator.model.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

@Component
public class JdbcTradeDao implements TradeDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTradeDao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    public void buyTrade(Trade trade, Player player) {
        String sql = "INSERT INTO trades (stock_id, shares_traded, buy_or_sell, price, entered_in, date, time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        if (trade.getBuy_or_sell().toLowerCase() == "buy") {
            if (player.getAvailableFunds().compareTo(trade.getPrice()) == 1 || player.getAvailableFunds().compareTo(trade.getPrice()) == 0) {
                jdbcTemplate.update(sql, trade.getId(), trade.getStock_id(), trade.getShares_traded(),
                        trade.getBuy_or_sell(), trade.getPrice(), trade.getEntered_in(), trade.getDate(), trade.getTime());
            }
        }
    }

    public void sellTrade(Trade trade, Stock stock) {
        String sql = "INSERT INTO trades (stock_id, shares_traded, buy_or_sell, price, entered_in, date, time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        if (trade.getBuy_or_sell().toLowerCase() == "sell") {
            if (trade.getShares_traded() >= stock.getTotal_shares()) {
                jdbcTemplate.update(sql, trade.getId(), trade.getStock_id(), trade.getShares_traded(),
                        trade.getBuy_or_sell(), trade.getPrice(), trade.getEntered_in(), trade.getDate(), trade.getTime());
            }
        }
    }

    public boolean createTrade(Trade trade){
        String sql = "INSERT INTO trades (stock_id, shares_traded, buy_or_sell, price, entered_in, date, time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";


        return true;
    }

    private Trade mapResultToTrade(SqlRowSet result) {
        int id = result.getInt("id");
        int stock_id = result.getInt("stock_id");
        double shares_traded = result.getDouble("shares_traded");
        String buy_or_sell = result.getString("buy_or_sell");
        BigDecimal price = result.getBigDecimal("price");
        String entered_in = result.getString("entered_in");
        LocalDate date = result.getDate("date").toLocalDate();
        LocalTime time = result.getTime("time").toLocalTime();

        Trade trade = new Trade(stock_id, shares_traded, buy_or_sell, price, entered_in, date, time);

        return trade;

    }

}
