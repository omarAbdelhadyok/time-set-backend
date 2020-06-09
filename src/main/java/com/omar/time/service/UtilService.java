package com.omar.time.service;

import com.omar.time.model.Card;
import com.omar.time.model.Comment;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
import com.omar.time.model.Task;
import com.omar.time.security.UserPrincipal;

public class UtilService {

	public static void handleUnathorized(Project project, UserPrincipal userPrincipal) {
    	boolean isAuthor = false;
		for(int i = 0; i < project.getAuthors().size(); i++) {
			 if(project.getAuthors().get(i).getId() == userPrincipal.getId()) {
				 isAuthor = true;
			 }
		}
		if((project.getCreatedBy() != userPrincipal.getId()) && !isAuthor) {
			throw new RuntimeException("You are not either owner or auditor for this project");
		}
    }
	
	public static Stack getStackFromProject(Project project, long stackId) {
		Stack stack = null;
		for(Stack stackResult : project.getStacks()) {
			boolean found = stackResult.getId() == stackId;
			if(found) {
				stack = stackResult;
				break;
			}
		}
		if(stack == null) {
			throw new RuntimeException("Stack with id of " + stackId + " was not found");
		}
		return stack;
	}
	
	public static Card getCardFromStack(Stack stack, long cardId) {
		Card card = null;
		for(Card cardResult : stack.getCards()) {
			boolean found = cardResult.getId() == cardId;
			if(found) {
				card = cardResult;
				break;
			}
		}
		if(card == null) {
			throw new RuntimeException("Card with id of " + cardId + " was not found");
		}
		return card;
	}
	
	public static Task getTaskFromCard(Card card, long taskId) {
		Task task = null;
		for(Task taskResult : card.getTasks()) {
			boolean found = taskResult.getId() == taskId;
			if(found) {
				task = taskResult;
				break;
			}
		}
		if(task == null) {
			throw new RuntimeException("Task with id of " + taskId + " was not found");
		}
		return task;
	}
	
	public static Comment getCommentFromCard(Card card, long commentId) {
		Comment comment = null;
		for(Comment commentResult : card.getComments()) {
			boolean found = commentResult.getId() == commentId;
			if(found) {
				comment = commentResult;
				break;
			}
		}
		if(comment == null) {
			throw new RuntimeException("Task with id of " + commentId + " was not found");
		}
		return comment;
	}
	
}