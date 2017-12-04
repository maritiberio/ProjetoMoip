package test;

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

/**
 * Cenário: Criar pedido e gera boleto via API.
 * 
 * Autor: Mariana Marques
 * 
 * Data de Criação: 03/12/2017
 * 
 * Pré-requisitos: Cliente deve existir na base de dados.
 * 
 * Resultado esperado: Pedido criado com sucesso, status CREATED; Pagamento via
 * boleto realizado com sucesso, status WAITING
 * 
 * 
 */
public class CN002 {

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
		PedidoModel p = new PedidoModel();
		ClienteModel c = new ClienteModel();

		p.setProduto("Condicionador");
		p.setQuantidade(100);
		p.setDetalhe("Condicionador bom para o cabelo");
		p.setValorProduto(1500);
		c.setFullname("Matheus Marques Tibério");
		c.setEmail("math@gmail.com");
		c.setCpf("72449617159");
		c.setAreaCode("11");
		c.setNumber("26187766");
		c.setStreet("Rua Pitinga");
		c.setStreetNumber("12");
		c.setComplement("");
		c.setCity("São Paulo");
		c.setState("SP");
		c.setDistrict("Vila Prudente");
		c.setCountry("BRA");
		c.setZipCode("03287-010");
		
		pedidoController.criarPedido(api, p, c);
		pagamentoController.pagarComBoleto(api, p);

	}

}
