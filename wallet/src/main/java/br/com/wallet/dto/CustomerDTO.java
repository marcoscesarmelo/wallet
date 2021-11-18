package br.com.wallet.dto;

public class CustomerDTO {
	
	private String username;
	private Integer accountId;
	
	public CustomerDTO(String username, Integer accountId) {
		this.username = username;
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
}