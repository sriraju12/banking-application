package com.hcl.capstone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.capstone.controller.CustomerController;
import com.hcl.capstone.customexception.NoSuchIdPresentInDBException;
import com.hcl.capstone.entity.Account;
import com.hcl.capstone.entity.Customer;
import com.hcl.capstone.repository.CustomerRepository;
import com.hcl.capstone.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@MockBean
	CustomerService customerService;
	@MockBean
	CustomerRepository customerRepository;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MockMvc mockMvc;

	private Customer customer;
	private List<Account> accounts;

	@BeforeEach
	void setup() {
		accounts = new ArrayList<Account>();
		accounts.add(new Account(1, 1234567890, "USD", "savings", 100.0));
		accounts.add(new Account(2, 456874789, "USD", "current", 200.0));
		customer = new Customer(1, "raju", accounts);

	}

	@Test
	public void shouldCreateCustomer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/customer").content(objectMapper.writeValueAsString(customer))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
		when(customerService.create(customer)).thenReturn(customer);
		assertEquals(customer, customerService.create(customer));

		
	}

	@Test
	public void shouldGetAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<>();
		customers.add(customer);
		when(customerService.getAll()).thenReturn(customers);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customer").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(customers.size()));
	}

	@Test
	public void shouldDeleteCustomerById() throws Exception {
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
	
		doNothing().when(customerRepository).deleteById(anyInt());
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/{id}", 1)).andExpect(status().isAccepted());
	}

	@Test
	public void shouldNotDeleteCustomerById() throws Exception {
		int id = 1;
		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
		// doNothing().when(customerRepository).deleteById(anyInt());
//		doThrow(new NoSuchIdPresentInDBException("id" + id + " is not present in database")).when(customerRepository)
//				.deleteById(id);
		assertThrows(NoSuchIdPresentInDBException.class,() -> {
			customerRepository.deleteById(4);
		});
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/{id}", 4));

	}

	@Test
	public void shouldUpdateCustomer() throws Exception {
		int id = 2;
		Customer updateCustomer = new Customer(1, "sriraju", accounts);
		when(customerRepository.findById(id)).thenReturn(Optional.of(updateCustomer));
		when(customerService.update(any(Customer.class))).thenReturn(updateCustomer);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/customer/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateCustomer))).andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$.custId").value(updateCustomer.getCustId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.custName").value(updateCustomer.getCustName()))
				.andDo(print());
	}

//	@Test
//	public void shouldUpdateCustomerNotFound() throws Exception {
//		int id = 1;
//		Customer updateCustomer = new Customer(1, "srira", accounts);
//		//when(customerRepository.findById(id)).thenReturn(Optional.empty());
//		// when(customerService.update(any(Customer.class))).thenThrow(NoSuchIdPresentInDBException.class);
//		doThrow(NoSuchIdPresentInDBException.class).when(customerRepository).findById(id);
//
//		mockMvc.perform(MockMvcRequestBuilders.put("/api/customer/{id}", id).contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(updateCustomer)));
//
//	}

}
