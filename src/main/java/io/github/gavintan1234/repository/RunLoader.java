package io.github.gavintan1234.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.gavintan1234.dto.Run;

public class RunLoader {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static List<Run> loadRuns(String zipPath) throws IOException {
		
		List<Run> runList = new ArrayList<>();
		
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
			
			ZipEntry entry;

			// Iterate through all run files
            while ((entry = zis.getNextEntry()) != null) {
            	
            	// Skip files that do not have .run extension
                if (!entry.getName().endsWith(".run")) {
                    continue;
                }
                
                JsonParser parser = mapper.getFactory().createParser(zis);
                JsonNode root = mapper.readTree(parser);
                
                // Create Run objects
                try {
                	Run run = new Run(root);
                	runList.add(run);
                } catch (JsonProcessingException e) {
                	System.out.println("Error processing file: " + entry.getName());
                }

            }
            
		}
		
		return runList;
	}
	
	public static void main(String[] args) throws IOException {
		List<Run> runList = RunLoader.loadRuns("history.zip");
		for (Run run : runList) {
			System.out.println("Character: " + run.getCharacter());
			System.out.println(run.getFinalDeck());
		}
	}
}
