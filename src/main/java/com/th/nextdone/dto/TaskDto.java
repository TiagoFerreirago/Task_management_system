package com.th.nextdone.dto;

import java.time.LocalDate;

public record TaskDto(Long id,String title,
		String description,
		boolean completed,
		LocalDate dataCreation) {

}
