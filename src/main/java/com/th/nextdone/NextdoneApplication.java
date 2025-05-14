package com.th.nextdone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.th.nextdone.email.EmailService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class NextdoneApplication {

	@Autowired
	private EmailService emailService;
	
	public static void main(String[] args) {
		SpringApplication.run(NextdoneApplication.class, args);
	}
	

    @PostConstruct
    public void testEmail() {
        emailService.sendEmail("seu-email-de-teste@gmail.com", "Teste", "Isso é só um teste.");
        
    }
}
