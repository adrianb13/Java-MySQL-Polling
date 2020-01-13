package com.polling.entity;

public class ChoiceVoteCount {
 private Long choiceId;
 private Long voteCount;
 
 public ChoiceVoteCount(Long choiceId, Long voteCount) {
	 this.choiceId = choiceId;
	 this.voteCount = voteCount;
 }
 
 public Long getChoiceId() {
	 return choiceId;
 }
 
 public void setChoice(Long choiceId) {
	 this.choiceId = choiceId;
 }
 
 public Long getVoteCount() {
	 return voteCount;
 }
 
 public void setVoteCount(Long voteCount) {
	 this.voteCount = voteCount;
 }
}
