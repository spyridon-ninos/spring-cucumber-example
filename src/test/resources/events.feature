Feature: Retrieve all available events
  As a non-logged in user
  I want to retrieve all available events
  So that I can place bets on them

    Scenario: Get all events
        Given the application is running
        When I do a GET request to /eventsService
        Then I get the available events