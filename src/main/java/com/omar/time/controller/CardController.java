package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.card.CardByIdDTO;
import com.omar.time.dto.card.CardDTO;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {

	private CardService cardService;
	
	
	@Autowired
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}
	
	@GetMapping("/{cardId}")
	public CardByIdDTO get(@CurrentUser UserPrincipal userPrincipal, @PathVariable long cardId) {
		return cardService.get(userPrincipal, cardId);
	}
	
	@PostMapping("/{stackId}")
	public CardDTO create(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Create.class) @RequestBody CardDTO cardDTO,
			@PathVariable long stackId) {
		return cardService.create(userPrincipal, cardDTO, stackId);
	}
	
	@PutMapping
	public CardDTO update(@CurrentUser UserPrincipal userPrincipal, 
			@Validated(Update.class) @RequestBody CardDTO cardDTO) {
		return cardService.update(userPrincipal, cardDTO);
	}
	
	@DeleteMapping("/{projectId}/{stackId}/{cardId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long cardId) {
		return cardService.delete(userPrincipal, cardId);
	}
	
}
