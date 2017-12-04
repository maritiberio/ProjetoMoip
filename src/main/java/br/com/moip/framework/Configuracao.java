package br.com.moip.framework;

import org.openqa.selenium.WebDriver;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.authentication.Authentication;
import br.com.moip.authentication.BasicAuth;

public class Configuracao {

	static WebDriver driver;

	public Configuracao(WebDriver driver) {
		this.driver = driver;
	}

	public Authentication configurarAutenticacao(String token, String key) {
		
		Authentication auth = new BasicAuth(token, key);
		return auth;
	
	}
	
	public Client configurarAmbiente(String token, String key) {
		
		Authentication auth = configurarAutenticacao(token, key);
		Client client = new Client(Client.SANDBOX, auth);
		return client;
		
	}
	
	public API instanciarAPI(Client client) {
		
		API api = new API(client);
		return api;
	}
	

}
