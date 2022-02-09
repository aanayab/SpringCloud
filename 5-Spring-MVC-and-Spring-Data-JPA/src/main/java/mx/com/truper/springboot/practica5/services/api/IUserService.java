package mx.com.truper.springboot.practica5.services.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.com.truper.springboot.practica5.entities.User;

public interface IUserService {

	Page<User> findPaginated(Pageable pageable);

	void saveOrUpdate(User user);

	User searchById(long id);

	void delete(User user);
}
