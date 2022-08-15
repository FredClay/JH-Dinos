package com.qa.may.selenium;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "classpath:dino-schema.sql",
		"classpath:dino-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class SeleniumTesting {

	private RemoteWebDriver driver;
	private WebDriverWait wait;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setup() {
		this.driver = new EdgeDriver();
		this.driver.manage().window().maximize();

		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	@Test
	void makeDinoTest() throws Exception {
		this.driver.get("http://localhost:" + port + "/");

		HomePage homePage = PageFactory.initElements(this.driver, HomePage.class);

		String expectedName = "Freddo";
		Integer anyAge = 24;
		String anySpecies = "T-Rex";
		homePage.createDino(expectedName, anyAge, anySpecies);

		String result = homePage.getSecondDino(this.wait);

		assertEquals(expectedName, result);

	}

	@AfterEach
	void tearDown() {
		this.driver.close();
	}

}
