package dev.evandro.picpay.repositories;

import org.springframework.data.repository.ListCrudRepository;

import dev.evandro.picpay.model.TypeUser;
import dev.evandro.picpay.model.User;

public interface UserRepository extends ListCrudRepository<User, Integer>{
	
	public User findByTypeUser(TypeUser typeUser);

}
