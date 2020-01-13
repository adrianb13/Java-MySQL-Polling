package com.polling.payload;

import javax.validation.constraints.NotNull;

public class VoteRequest {
	@NotNull
	private Long choiceId;
	
	public Long getChoice() {
		return choiceId;
	}
	
	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}
}
