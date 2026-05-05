package io.github.gavintan1234.repository;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {
	
	JsonNode root;
	HashMap<String, Integer> cardCounts = new HashMap<>();

	public JsonParser(String filename) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		root = mapper.readTree(new File(filename));
		parseCardCounts();
		System.out.println("Run won: " + root.get("win"));
	}
	
	public void parseCardCounts() {
		for (JsonNode player : root.get("players")) {
			
			JsonNode deck = player.get("deck");
			if (deck == null) continue;
			
			for (JsonNode card : deck) {
				String cardId = card.get("id").asText();
				cardCounts.putIfAbsent(cardId, 0);
				cardCounts.put(cardId, cardCounts.get(cardId) + 1);
			}
		}
	}
	
	public HashMap<String, Integer> getCardCounts() {
		return cardCounts;
	}

	public static void main(String[] args) throws JsonProcessingException, IOException {
		JsonParser parser = new JsonParser("history/1772952202.run");
		System.out.println(parser.getCardCounts());
	}
}
