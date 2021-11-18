package br.com.wallet.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	
	public Account() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private Float amount;

	public Float getAmount() {
		return amount;
	}
	
	public Integer getId() {
		return id;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public boolean deposit(Float amount) throws Exception {
		try {
			setAmount(getAmount() + amount);
			return true;
		} catch(Exception e) {
			throw new Exception("Something wrong with deposit " + e.getMessage());
		}		
	}
	
	public boolean withdraw(Float amount) throws Exception {
		try {
			setAmount(getAmount() - amount);
			return true;
		} catch(Exception e) {
			throw new Exception("Something wrong with withdraw " + e.getMessage());
		}
	}

	public boolean payment(Float amount) throws Exception {
		try {
			setAmount(getAmount() - amount);
			return true;
		} catch(Exception e) {
			throw new Exception("Something wrong with payment " + e.getMessage());
		}
	}
	
	public Account transfer(Account toAccount, Float amount) throws Exception {
		try {
			setAmount(getAmount() - amount);
			toAccount.setAmount(toAccount.getAmount() + amount);
			return toAccount;
		} catch(Exception e) {
			throw new Exception("Something wrong with transfer " + e.getMessage());
		}
	}

}