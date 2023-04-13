package com.example.antifraudsystem;

import com.example.antifraudsystem.component.LuhnAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AntiFraudSystemApplicationTests {

	LuhnAlgorithm luhnAlgorithm = new LuhnAlgorithm();

	@Test
	void contextLoads() {

		luhnAlgorithm.validate("4000008449433403");
		luhnAlgorithm.validate("4000008449433402");
	}

}
