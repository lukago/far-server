package com.far.server.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = {
    FixedAssetRegisterServerApplication.class,
    TestConfig.class
})
@TestPropertySource("classpath:application-test.yml")
@ActiveProfiles("test")
class FixedAssetRegisterServerApplicationIT {

	@Test
	void contextLoads() {
	}

}
