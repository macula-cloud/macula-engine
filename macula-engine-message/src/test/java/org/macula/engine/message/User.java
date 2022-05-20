package org.macula.engine.message;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	String name;
	String password;
	int age;
	Bank bank;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class Bank {
		String cardNo;
		BigDecimal balance;
	}

}