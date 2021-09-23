package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.StockInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestStockInfoService implements StockInfoService {

    private String apiKey = "c55kruaad3icdhg123f0";
    private String BASE_URL = "https://finnhub.io/api/v1/";
    private Map<String, LocalTime> retrieveTimeMap = new HashMap<>();
    private Map<String, StockInfo> stockInfoMap = new HashMap<>();
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<StockInfo> getTopStocks() {
        //TODO
        List<StockInfo> topTen = new ArrayList<>();
        topTen.add(getStockInfo("FB"));
        topTen.add(getStockInfo("TSLA"));
        topTen.add(getStockInfo("AAPL"));
        topTen.add(getStockInfo("AMZN"));
        topTen.add(getStockInfo("MSFT"));
        topTen.add(getStockInfo("NVDA"));
        topTen.add(getStockInfo("FDX"));
        topTen.add(getStockInfo("MRNA"));
        topTen.add(getStockInfo("ADBE"));
        topTen.add(getStockInfo("AMD"));
        return topTen;
    }

    @Override
    public StockInfo getStockInfo(String stockSymbol) {
        LocalTime retrievedTime = retrieveTimeMap.get(stockSymbol);
        if (retrievedTime != null) {
            if (retrievedTime.until(LocalTime.now(), ChronoUnit.MINUTES) <=10) {
                return stockInfoMap.get(stockSymbol);
            }
        }
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        String url = BASE_URL + "/quote?symbol=" + stockSymbol + "&token=" + apiKey;
        StockInfo stockInfo = null;
        String price = "";
        String percentChange = "";
        String companyName = "";
        String logoURL = "";
        ResponseEntity<String> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(result.getBody());
            JsonNode root = jsonNode.path("data");
            price = jsonNode.path("c").asText();
            percentChange = jsonNode.path("dp").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        url = BASE_URL + "/stock/profile2?symbol=" + stockSymbol + "&token=" + apiKey;
        result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        System.out.println(result);
        try {
            JsonNode jsonNode = objectMapper.readTree(result.getBody());
            JsonNode root = jsonNode.path("data");
            companyName = jsonNode.path("name").asText();
            logoURL = jsonNode.path("logo").asText();
        } catch (JsonProcessingException e) {e.printStackTrace();}

        stockInfo = new StockInfo(stockSymbol, companyName, new BigDecimal(price), logoURL, new BigDecimal(percentChange));
        retrieveTimeMap.put(stockInfo.getStockSymbol(), LocalTime.now());
        stockInfoMap.put(stockSymbol, stockInfo);
        return stockInfo;
    }

}
