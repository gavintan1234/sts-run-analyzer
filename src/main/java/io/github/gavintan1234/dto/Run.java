package io.github.gavintan1234.dto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Run {
	
	private boolean isWon;
	private String character;
	private List<String> finalDeck;
	private JsonNode root;

	public Run(String filename) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		this.root = mapper.readTree(new File(filename));
		this.isWon = root.get("win").booleanValue();
		this.character = root.get("players").get(0).get("character").toString();
	}
	
	public boolean isWon() {
		return this.isWon;
	}
	
	public String getCharacter() {
		return this.character;
	}
	
	public List<String> getFinalDeck() {
		
		// Lazy evaluation
		if (this.finalDeck == null) {
			this.finalDeck = new ArrayList<>();
			
			JsonNode deck = root.get("players").get(0).get("deck");
			
			for (JsonNode card : deck) {
				String cardId = card.get("id").asText();
				this.finalDeck.add(cardId);
			}
		}
		
		return this.finalDeck;
	}

}
