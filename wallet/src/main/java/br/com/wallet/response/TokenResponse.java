package br.com.wallet.response;

import java.io.Serializable;

public class TokenResponse implements Serializable {

	private static final long serialVersionUID = 3041541865838324669L;
	private final String jwttoken;
	
	public TokenResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	public String getToken() {
		return this.jwttoken;
	}
}
