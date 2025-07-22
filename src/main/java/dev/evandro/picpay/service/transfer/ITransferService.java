package dev.evandro.picpay.service.transfer;

import dev.evandro.picpay.dto.TransferRequest;

public interface ITransferService {

	public Boolean transfer(TransferRequest transferRequest);
}
