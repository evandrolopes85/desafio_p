package dev.evandro.picpay.repositories;

import org.springframework.data.repository.ListCrudRepository;

import dev.evandro.picpay.model.Wallet;

public interface WalletRepository extends ListCrudRepository<Wallet, Integer>{

}
