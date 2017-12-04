package test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.framework.ClienteController;
import br.com.moip.framework.Configuracao;
import br.com.moip.model.ClienteModel;
import util.geradorCPF;

/**
 * Cenário: Criar cliente e adicionar um cartão via API.
 * 
 * Autor: Mariana Marques
 * 
 * Data de Criação: 03/12/2017
 * 
 * Pré-requisitos: Cliente não deve existir na base de dados. Para isso, usamos
 * um gerador e validador de CPF.
 * 
 * Resultado esperado: Cliente cadastrado com sucesso; Cartão de crédito adicionado
 * com sucesso.
 * 
 * 
 */

public class CN001 {

	static WebDriver driver;
	static Configuracao config;
	static ClienteController clienteController;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		config = new Configuracao(driver);
		clienteController = new ClienteController(driver);

	}

	@Test
	public void test() {
		Client client;
		API api;

		client = config.configurarAmbiente("SXL2COV4PGLGHCHRK5FNW3LZRU8WYXSD",
				"GCQNCR15QHHC8OLXC0VLFYAMALKGTSBIZ7LNHZHV");
		api = config.instanciarAPI(client);

		String cpfGerador = geradorCPF.geraCPF();
		Assert.assertFalse("Erro ao gerar CPF", !geradorCPF.validaCPF(cpfGerador));

		ClienteModel c = new ClienteModel();
		c.setFullname("Ana Carolina Rodrigues Marques");
		c.setEmail("aninhagatinha@gmail.com");
		c.setCpf(cpfGerador);
		c.setAreaCode("11");
		c.setNumber("39444681");
		c.setStreet("Rua Pitinga");
		c.setStreetNumber("12");
		c.setComplement("");
		c.setCity("São Paulo");
		c.setState("SP");
		c.setDistrict("Vila Prudente");
		c.setCountry("BRA");
		c.setZipCode("03287-010");

		clienteController.criarCliente(api, c);
		clienteController.consultarCliente(api, c.getId());
		clienteController.adicionarCartao(api, c);

	}

}
