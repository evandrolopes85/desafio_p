package dev.evandro.picpay.service.transfer;

import java.util.Optional;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import dev.evandro.picpay.dto.AuthorizeTransfer;
import dev.evandro.picpay.dto.TransferRequest;
import dev.evandro.picpay.exception.AuthorizeException;
import dev.evandro.picpay.model.TypeUser;
import dev.evandro.picpay.model.User;
import dev.evandro.picpay.model.Wallet;
import dev.evandro.picpay.repositories.UserRepository;
import dev.evandro.picpay.repositories.WalletRepository;

@Service
@Transactional
public class TransferServiceImpl implements ITransferService {
	
	private UserRepository userRepository;
	private final WalletRepository walletRepository;

	public TransferServiceImpl(UserRepository userRepository, WalletRepository walletRepository) {
		super();
		this.userRepository = userRepository;
		this.walletRepository = walletRepository;
	}
	
	public RestClient start() {
		RestClient restClient = RestClient.builder().baseUrl("https://util.devi.tools/api/v2").build();
		return restClient;
	}


	@Override
	public Boolean transfer(TransferRequest transferRequest) {
		
		Optional<User> userOptional = this.userRepository.findById(transferRequest.payer());
		
		if(userOptional.isPresent() && (userOptional.get().getTypeUser() == TypeUser.STOREOWNER)) {
			return false;
		}
		User user = userOptional.get();
		
		if(user.getWallet().getBalance() >= transferRequest.value()) {
			
			try {
				AuthorizeTransfer authorizeTransfer = getAuthorizer();
				if(authorizeTransfer.data().authorization()) {
					double newBalance = user.getWallet().getBalance() - transferRequest.value();
					Wallet wallet = createWaller(user, newBalance);
					walletRepository.save(wallet);
					return true;
				}
			}catch(AuthorizeException ex) {
				ex.printStackTrace();
			}
			
			return false;
		}else {
			System.out.println("Saldo Insulficiente");
			return false;
		}
	}
	
	public Wallet createWaller(User user, double balance) {
		Wallet wallet = new Wallet();
		wallet.setWalletId(user.getWallet().getWalletId());
		wallet.setUser(user);
		wallet.setBalance(balance);
		
		return wallet;
	}
	
	public AuthorizeTransfer getAuthorizer() {
		RestClient restClient = start();
		
		ResponseEntity<AuthorizeTransfer> authorizeTransfer = restClient.get().uri("/authorize")
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
					throw new AuthorizeException("message: Unauthorized");
				})
				.toEntity(AuthorizeTransfer.class);
		
		System.out.println(authorizeTransfer);
		return authorizeTransfer.getBody();
	}
}
