package mx.com.truper.springboot.practica5.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica5.entities.User;
import mx.com.truper.springboot.practica5.services.api.IUserService;
import mx.com.truper.springboot.practica5.services.impl.UserService;

@Slf4j
// define controller
@Controller
@RequiredArgsConstructor
public class UserController {

	// inyecta UserService por constructor
	private final IUserService userService;
	
	@Value("${page.size:5}")
	private Integer PAGE_SIZE;
	
	@GetMapping("/")// /?page=1&size=5 (acuerdate que la paginacion empieza en 0)
	public String index(Model model, 
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(PAGE_SIZE);
		
		log.info("retrieving page of Users for page: {} of size: {}", currentPage-1, pageSize);

		// Implementa
		Page<User> usersPage = userService.findPaginated(PageRequest.of(currentPage-1, pageSize));
		
		model.addAttribute("usersPage", usersPage);
		
		int totalPages = usersPage.getTotalPages();
		
		if(totalPages > 0 ) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
												 .boxed()
												 .collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		log.info("going to index view");
		
		return "index";
	}

	@ResponseBody
	@GetMapping("/all")
	public Page<User> all(Model model, 
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(PAGE_SIZE);
		
		log.info("retrieving page of Users for page: {} of size: {}", currentPage-1, pageSize);

		// Implementa
		Page<User> usersPage = userService.findPaginated(PageRequest.of(currentPage-1, pageSize));

		return usersPage;
	}

	@GetMapping("/add-user-form")
	public String showSignUpForm(@ModelAttribute User user) {
		
		log.info("going to add-user-form view");
		log.info("user: " + user);
		return "add-user-form";
	}

	@PostMapping("/add-user")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		
		// Implementa
		if(result.hasErrors()) {
			log.info("user has errors");
			return "add-user-form";
		}
		
		log.info("saving user into DB");
		
		userService.saveOrUpdate(user);

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		
		log.info("retrieving User with id: {}", id);

		// Implementa		
		User user = userService.searchById(id);
		
		if(user == null)
			return "redirect:/";
		
		model.addAttribute("user", user);
		
		log.info("going to update-user-form view");

		return "update-user-form";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
		
		// Implementa
		user.setId(id);
		
		if(result.hasErrors()) {
			log.info("user has errors, forwarding to update-user-form");
			
			return "update-user-form";
		}
		
		log.info("updating user into DB");
		
		userService.saveOrUpdate(user);

		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		
		// Implementa
		log.info("retrieving user id: {}", id);
		
		User user = userService.searchById(id);
		
		if(user != null) {
			log.info("deleting user id: {}", id);
			userService.delete(user);
		}
		
		log.info("redirecting to '/' path");

		return "redirect:/";
	}
}
