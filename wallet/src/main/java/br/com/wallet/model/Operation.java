package br.com.wallet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import br.com.wallet.enums.OperationType;

@Entity
@Table(name = "operation")
public class Operation {
	
	public Operation() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "amount")
	private Float amount;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "desription")
	private String description;
	
	@Column(name = "moment")
	private Date moment;
	
	@Column
	@JoinColumn(name="account_id")
	private Integer accountId;

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}	

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	public boolean buildEvent(Integer accountId, Float amount, OperationType type) throws Exception {
		try {
			setAccountId(accountId);
			setAmount(amount);
			setType(type.name());
			setMoment(new Date());
			switch(type) {
			case DEPOSIT: 
				setDescription("Deposit into account: " + accountId + ", amount: " + amount + ", at: " + getMoment()); break;
			case PAYMENT:
				setDescription("Debit for payment from account: " + accountId + ", amount: " + amount + ", at: " + getMoment()); break;
			case TRANSFER:
				setDescription("Debit for Transfer from account: " + accountId + ", amount: " + amount + ", at: " + getMoment()); break;
			case WITHDRAW:
				setDescription("Withdraw from account: " + accountId + ", amount: " + amount + ", at: " + getMoment()); break;
			}
			return true;
		} catch(Exception e) {
			throw new Exception("Something wrong when build timeline event: " + e.getMessage());
		}

	}

}
