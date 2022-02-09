package mx.com.truper.springboot.practica5.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mx.com.truper.springboot.practica5.entities.User;
import mx.com.truper.springboot.practica5.repositories.UserRepository;
import mx.com.truper.springboot.practica5.services.api.IUserService;

// define service, imeplementa IUserService
@Service
@AllArgsConstructor
public class UserService implements IUserService {

	private UserRepository userRepository;

	@Override
	public Page<User> findPaginated(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public void saveOrUpdate(User user) {
		userRepository.save(user);
	}

	@Override
	public User searchById(long id) {
		return userRepository.findById(id)
							 .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id) );
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

}