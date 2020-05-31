package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.CardCreationDTO;
import com.omar.time.dto.CardDTO;
import com.omar.time.model.Card;
import com.omar.time.service.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	@GetMapping("/stack/{stackId}")
	public Page<CardDTO> getAll(@PathVariable long stackId, Pageable pagable) {
		return cardService.getAll(stackId, pagable);
	}
	
	@GetMapping("/{stackId}/{id}")
	public CardDTO get(@PathVariable long stackId, @PathVariable long id) {
		return cardService.get(stackId, id);
	}
	
	@PostMapping("/{stackId}")
	public Card create(@RequestBody CardCreationDTO cardCreationDTO, @PathVariable long stackId) {
		return cardService.create(cardCreationDTO, stackId);
	}
	
	@PutMapping("/{id}")
	public Card update(@RequestBody CardCreationDTO cardCreationDTO, @PathVariable long id) {
		return cardService.update(cardCreationDTO, id);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable long id) {
		return cardService.delete(id);
	}
	
}
