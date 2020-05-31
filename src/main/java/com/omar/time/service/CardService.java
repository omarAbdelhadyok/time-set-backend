package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.CardCreationDTO;
import com.omar.time.dto.CardDTO;
import com.omar.time.model.Card;
import com.omar.time.model.Stack;
import com.omar.time.repository.CardRepository;
import com.omar.time.repository.StackRepository;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private StackRepository stackRepository;
	
	
	public Page<CardDTO> getAll(long projectId, Pageable pageable) {
		Page<Card> page = cardRepository.findByStackId(projectId, pageable);
		return new PageImpl<CardDTO>(ObjectMapperUtils.mapAll(page.getContent(), CardDTO.class), pageable, page.getTotalElements());
	}

	
	public CardDTO get(long stackId, long id) {
		Optional<Card> result = cardRepository.findByIdAndStackId(id, stackId);
		
		Card card = null;
		
		if(result.isPresent()) {
			card = result.get();
		} else {
			throw new RuntimeException("Stack with id of " + id + " was not found");
		}

		return ObjectMapperUtils.map(card, CardDTO.class);
	}
	
	public Card create(CardCreationDTO cardCreateUpdateDTO, long stackId) {
		Optional<Stack> result = stackRepository.findById(stackId);
		
		Stack stack = null;
		
		if(result.isPresent()) {
			stack = result.get();
		} else {
			throw new RuntimeException("Stack with id of " + stackId + " was not found");
		}
		
		Card card = ObjectMapperUtils.map(cardCreateUpdateDTO, Card.class);
		
		card.setStack(stack);
        
		return cardRepository.save(card);
    }
	
	public Card update(CardCreationDTO cardUpdatingDTO, long id) {
		Optional<Card> result = cardRepository.findById(id);
		
		Card card = null;
		if(result.isPresent()) {
			card = ObjectMapperUtils.map(cardUpdatingDTO, Card.class);
			card.setId(id);	
		} else {
			throw new RuntimeException("Card with id of " + id + " was not found");
		}
		
        return cardRepository.save(card);
    }
		
	public boolean delete(long id) {
        Optional<Card> result = cardRepository.findById(id);
		
		if(result.isPresent()) {
            cardRepository.deleteById(id);
		} else {
			throw new RuntimeException("Card with id of " + id + " was not found");
        }
		
		return true;
    }

}
