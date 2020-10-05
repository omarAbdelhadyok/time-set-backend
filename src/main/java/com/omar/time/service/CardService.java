package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	private CardRepository cardRepository;
	private ProjectRepository projectRepository;

	@Autowired
	public CardService(CardRepository cardRepository, ProjectRepository projectRepository) {
		this.cardRepository = cardRepository;
		this.projectRepository = projectRepository;
	}
	
	
	public CardByIdDTO get(UserPrincipal userPrincipal, long projectId, long stackId, long cardId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		
		return ObjectMapperUtils.map(card, CardByIdDTO.class);
	}
	
	public Card create(UserPrincipal userPrincipal, CardDTO cardDTO, long projectId, long stackId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = ObjectMapperUtils.map(cardDTO, Card.class);
		card.setStack(stack);
        
		return cardRepository.save(card);
    }
	
	@Transactional
	public Card update(UserPrincipal userPrincipal, CardDTO cardDTO, long projectId, long stackId, long cardId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() ->
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		UtilService.getCardFromStack(stack, cardId);
		Card card = ObjectMapperUtils.map(cardDTO, Card.class);
		card.setId(cardId);
		card.setStack(stack);
		
		return cardRepository.save(card);
    }
		
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId, long cardId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
	
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		card.dismissStack();
		cardRepository.deleteById(cardId);
		
		return true;
    }

}
