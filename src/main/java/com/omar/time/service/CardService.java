package com.omar.time.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omar.time.dto.card.CardByIdDTO;
import com.omar.time.dto.card.CardDTO;
import com.omar.time.model.Card;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
import com.omar.time.repository.CardRepository;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	public CardByIdDTO get(UserPrincipal userPrincipal, long projectId, long stackId, long cardId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
		} else {
			throw new EntityNotFoundException("Project Not Found");
		}

		return ObjectMapperUtils.map(card, CardByIdDTO.class);
	}
	
	public Card create(UserPrincipal userPrincipal, CardDTO cardDTO, long projectId, long stackId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
		} else {
			throw new EntityNotFoundException("Project Not Found");
		}
		Card card = ObjectMapperUtils.map(cardDTO, Card.class);
		card.setStack(stack);
        
		return cardRepository.save(card);
    }
	
	public Card update(UserPrincipal userPrincipal, CardDTO cardDTO, long projectId, long stackId, long cardId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			UtilService.getCardFromStack(stack, cardId);
			card = ObjectMapperUtils.map(cardDTO, Card.class);
			card.setId(cardId);
			card.setStack(stack);
		} else {
			throw new EntityNotFoundException("Project Not Found");
		}
		
		return cardRepository.save(card);
    }
		
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId, long cardId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
			card.dismissStack();
			cardRepository.deleteById(cardId);
		} else {
			throw new EntityNotFoundException("Project Not Found");
        }
		
		return true;
    }

}
