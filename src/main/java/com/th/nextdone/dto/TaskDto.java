package com.th.nextdone.dto;

import java.time.LocalDate;

import com.th.nextdone.security.model.User;

public record TaskDto(Long id,String title,
		String description,
		boolean completed,
		LocalDate dataCreation,
		User user) {

}
