package com.dns.pactconsumer;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

@Provider("ConsumerProvider")
@PactFolder("pacts")
@ExtendWith(PactConsumerTestExt.class)
public class ProductConsumer2PactTest {

	@Pact(consumer = "ConsumerProvider", provider = "Provider")
	public V4Pact getProduct1(PactDslWithProvider builder) {
		return builder.given("No Idea").uponReceiving("No Idea").method("GET").path("/products/1").willRespondWith()
				.status(200).body(newJsonBody(object -> {
					object.integerType("id", 1);
					object.stringType("name", "Name1");
					object.integerType("price", 10);
				}).build()).toPact(V4Pact.class);
	}
	
//	@Configuration
//	static class PactTestConfiguration {
//
//		@Bean
//		@Primary
//		RestTemplate testProductRestTemplate(MockServer mockServer) {
//			return new RestTemplateBuilder().rootUri(mockServer.getUrl()).build();
//		}
//
//	}



	private static ConfigurableWebApplicationContext application;

	@BeforeAll
	public static void start() {
		application = (ConfigurableWebApplicationContext) SpringApplication.run(PactConsumer2Application.class);
	}

	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	void pactVerificationTestTemplate(PactVerificationContext context) {
		context.verifyInteraction();
	}

	@BeforeEach
	void setUp(PactVerificationContext context) {
		context.setTarget(new HttpTestTarget("localhost", 8080));
	}

	@State("Product with id 10 exist in consumer-provider")
	void toProductWithIdTenExistsInConsumerProviderState() {

	}

}
