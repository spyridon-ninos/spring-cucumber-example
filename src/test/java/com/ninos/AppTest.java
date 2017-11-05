package com.ninos;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources",
		glue = "com.ninos",
		plugin = {
				"pretty",
				"html:target/cucumber-reports"
		}
)
public class AppTest {
}
