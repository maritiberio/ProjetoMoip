package test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.moip.API;
import br.com.moip.Client;
import br.com.moip.framework.ClienteController;
import br.com.moip.framework.Configuracao;
import br.com.moip.framework.PagamentoController;
import br.com.moip.framework.PedidoController;
import br.com.moip.model.ClienteModel;
import br.com.moip.model.PedidoModel;
import util.geradorCPF;

/**
 * Cenário: Cliente novo cria pedido e gera boleto para pagamento via API.
 * 
 * Autor: Mariana Marques
 * 
 * Data de Criação: 03/12/2017
 * 
 * Pré-requisitos: Cliente não deve existir na base de dados. Para isso, usamos
 * um gerador e validador de CPF.
 * 
 * Resultado esperado: Cliente cadastrado com sucesso; Pedido criado com
 * sucesso, status CREATED; Pagamento via boleto realizado com sucesso, status
 * WAITING
 * 
 * 
 */
public class CN003 {

	static WebDriver driver;
	static Configuracao config;
	static PedidoController pedidoController;
	static PagamentoController pagamentoController;
	static ClienteController clienteController;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		config = new Configuracao(driver);
		pedidoController = new PedidoController(driver);
		pagamentoController = new PagamentoController(driver);
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
		c.setFullname("Mario Henrique");
		c.setEmail("math@gmail.com");
		c.setCpf(cpfGerador);
		c.setAreaCode("11");
		c.setNumber("26185512");
		c.setStreet("Rua João Pessoa");
		c.setStreetNumber("12");
		c.setComplement("");
		c.setCity("São Paulo");
		c.setState("SP");
		c.setDistrict("Vila Lucia");
		c.setCountry("BRA");
		c.setZipCode("03287-010");

		PedidoModel p = new PedidoModel();

		p.setProduto("Camiseta");
		p.setQuantidade(10);
		p.setDetalhe("Camiseta amarela");
		p.setValorProduto(1500);

		clienteController.criarCliente(api, c);
		clienteController.consultarCliente(api, c.getId());
		pedidoController.criarPedido(api, p, c);
		pagamentoController.pagarComBoleto(api, p);
	}

}
