package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.card.CardByIdDTO;
import com.omar.time.dto.card.CardDTO;
import com.omar.time.dto.card.CreateCardDTO;
import com.omar.time.dto.card.UpdateCardDTO;
import com.omar.time.model.Card;
import com.omar.time.model.Stack;
import com.omar.time.repository.CardRepository;
import com.omar.time.repository.StackRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class CardService {

	private CardRepository cardRepository;
	private StackRepository stackRepository;

	@Autowired
	public CardService(CardRepository cardRepository, StackRepository stackRepository) {
		this.cardRepository = cardRepository;
		this.stackRepository = stackRepository;
	}
	
	public CardByIdDTO get(UserPrincipal userPrincipal, long id) {
		Card card = cardRepository.findCard(userPrincipal.getId(), id).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.card.notFound")
		);
		
		return ObjectMapperUtils.map(card, CardByIdDTO.class);
	}
	
	@Transactional
	public CardDTO create(UserPrincipal userPrincipal, CreateCardDTO createCardDTO, long stackId) {		
		Stack stack = stackRepository.findStack(userPrincipal.getId(), stackId).orElseThrow(() ->
			new EntityNotFoundException("errors.app.stack.notFound")
		);
		
		Card card = ObjectMapperUtils.map(createCardDTO, Card.class);
		card.setStack(stack);
		card = cardRepository.save(card);
		
		return ObjectMapperUtils.map(card, CardDTO.class);
    }
	
	@Transactional
	public CardDTO update(UserPrincipal userPrincipal, UpdateCardDTO updateCardDTO) {
		Card card = cardRepository.findCard(userPrincipal.getId(), updateCardDTO.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.card.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(updateCardDTO, card);
		card = cardRepository.save(card);
		
		return ObjectMapperUtils.map(card, CardDTO.class);
    }
	
	@Transactional
	public boolean delete(UserPrincipal userPrincipal, long cardId) {
		cardRepository.deleteById(cardId);
		
		return true;
    }

}
