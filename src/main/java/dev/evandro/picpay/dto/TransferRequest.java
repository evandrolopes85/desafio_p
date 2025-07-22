package dev.evandro.picpay.dto;

public record TransferRequest(
		Double value, 
		Integer payer,
		Integer payee
		) {

}
