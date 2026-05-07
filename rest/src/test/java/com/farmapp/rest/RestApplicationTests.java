package com.farmapp.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.farmapp.rest.repository.LitterRepository;
import com.farmapp.rest.repository.SowRepository;

@SpringBootTest
class RestApplicationTests {

	@Autowired
	SowRepository sowRepository;
	
	@Autowired
	LitterRepository litterRepository;
	
	@Autowired
	@Test
	void contextLoads() {
	}

}
