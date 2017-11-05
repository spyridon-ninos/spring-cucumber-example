package com.ninos.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninos.App;
import com.ninos.events.model.Event;
import com.ninos.events.model.EventsResponse;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@ContextConfiguration(
		classes = App.class,
		loader = SpringBootContextLoader.class
)
@WebAppConfiguration
public class EventsAT {

	@Inject
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	private String stringResponse;

	private final ObjectMapper mapper;

	public EventsAT() {
		mapper = new ObjectMapper();
	}

	@Given("^the application is running$")
	public void theAppIsRunning() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		// nothing
	}

	@When("^I do a GET request to /eventsService$")
	public void getRequest() throws Exception {
		stringResponse = mockMvc.perform(get("/eventsService"))
		                        .andReturn()
		                        .getResponse()
		                        .getContentAsString();
	}

	@Then("^I get the available events$")
	public void getEvents() throws IOException {
		EventsResponse actualResponse = mapper.readValue(stringResponse, EventsResponse.class);
		EventsResponse expectedResponse = createEventsResponse(10);

		assertEquals(expectedResponse, actualResponse);
	}

	private EventsResponse createEventsResponse(long howManyEvents) {

		List<Event> eventList = LongStream.range(0, howManyEvents).mapToObj(i -> {
			String home = "Home_" + i;
			String away = "Away_" + i;
			return new Event(i, home, away);
		}).collect(Collectors.toList());

		EventsResponse response = new EventsResponse();
		response.setResponseBody(eventList);

		return response;
	}
}
