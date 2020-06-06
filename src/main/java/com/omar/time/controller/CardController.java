package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.CardByIdDTO;
import com.omar.time.dto.CardCreationDTO;
import com.omar.time.model.Card;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	
	@GetMapping("/{projectId}/{stackId}/{cardId}")
	public CardByIdDTO get(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId) {
		return cardService.get(userPrincipal, projectId, stackId, cardId);
	}
	
	@PostMapping("/{projectId}/{stackId}")
	public Card create(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody CardCreationDTO cardCreationDTO,
			@PathVariable long projectId,
			@PathVariable long stackId) {
		return cardService.create(userPrincipal, cardCreationDTO, projectId, stackId);
	}
	
	@PutMapping("/{projectId}/{stackId}/{cardId}")
	public Card update(@CurrentUser UserPrincipal userPrincipal, 
			@RequestBody CardCreationDTO cardCreationDTO, 
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId) {
		return cardService.update(userPrincipal, cardCreationDTO, projectId, stackId, cardId);
	}
	
	@DeleteMapping("/{projectId}/{stackId}/{cardId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId) {
		return cardService.delete(userPrincipal, projectId, stackId, cardId);
	}
	
}
