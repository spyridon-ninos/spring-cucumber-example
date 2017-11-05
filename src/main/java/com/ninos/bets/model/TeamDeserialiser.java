package com.ninos.bets.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class TeamDeserialiser extends JsonDeserializer<Team> {
	@Override
	public Team deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		String team = jsonParser.getText().toLowerCase();

		if (Arrays.asList("home", "away").contains(team) == false) {
			throw new IllegalArgumentException("Invalid team provided: " + team);
		}

		return "home".equals(team) ? Team.HOME : Team.AWAY;
	}
}
