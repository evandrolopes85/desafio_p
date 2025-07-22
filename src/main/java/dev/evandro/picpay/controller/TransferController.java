package dev.evandro.picpay.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.evandro.picpay.dto.TransferRequest;
import dev.evandro.picpay.service.transfer.ITransferService;

@RestController
public class TransferController {
	
	public ITransferService service;
	
	public TransferController(ITransferService service) {
		this.service = service;
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<Boolean> transfer(@RequestBody TransferRequest transferRequest) {
		System.out.println(transferRequest);
		Boolean response = service.transfer(transferRequest);
		
		if(Objects.nonNull(response)) {
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.status(400).build();
	}
}
