package br.com.wallet.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.wallet.dto.CustomerDTO;
import br.com.wallet.exception.BusinessException;
import br.com.wallet.model.Account;
import br.com.wallet.model.Customer;
import br.com.wallet.repository.CustomerRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Customer user = customerRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public CustomerDTO save(Customer user) {
		
		if(customerRepository.findByUsername(user.getUsername()) != null) {
			throw new BusinessException("", "username", user.getUsername(), "User already exists!");
		}		
		Customer newUser = new Customer();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		Account account = new Account();
		account.setAmount(0f);
		newUser.setAccount(account);
		Customer returnedCustomer =  customerRepository.save(newUser);
		return new CustomerDTO(returnedCustomer.getUsername(), returnedCustomer.getAccount().getId());		
		
	}
}