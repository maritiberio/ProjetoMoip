package br.com.moip.framework;

import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import br.com.moip.API;
import br.com.moip.exception.UnexpectedException;
import br.com.moip.exception.ValidationException;
import br.com.moip.model.ClienteModel;
import br.com.moip.model.PedidoModel;
import br.com.moip.request.ApiDateRequest;
import br.com.moip.request.CustomerRequest;
import br.com.moip.request.OrderRequest;
import br.com.moip.request.PhoneRequest;
import br.com.moip.request.ShippingAddressRequest;
import br.com.moip.request.TaxDocumentRequest;
import br.com.moip.resource.Order;
import br.com.moip.response.OrderListResponse;

public class PedidoController {

	static WebDriver driver;

	public PedidoController(WebDriver driver) {
		this.driver = driver;
	}

	public void criarPedido(API api, PedidoModel p, ClienteModel c) {
		try {
			Order createdOrder = api.order()
					.create(new OrderRequest().ownId("order_own_id")
							.addItem(p.getProduto(), p.getQuantidade(), p.getDetalhe(), p.getValorProduto())
							.customer(new CustomerRequest().ownId("customer_own_id").fullname(c.getFullname())
									.email(c.getEmail()).birthdate(new ApiDateRequest().date(new Date()))
									.taxDocument(TaxDocumentRequest.cpf(c.getCpf()))
									.phone(new PhoneRequest().setAreaCode(c.getAreaCode()).setNumber(c.getNumber()))
									.shippingAddressRequest(new ShippingAddressRequest().street(c.getStreet())
											.streetNumber(c.getStreetNumber()).complement(c.getComplement())
											.city(c.getCity()).state(c.getState()).district(c.getDistrict())
											.country(c.getCountry()).zipCode(c.getZipCode()))));
			
			p.setId(createdOrder.getId());
			System.out.println("Pedido " + createdOrder.getId() + " status " + createdOrder.getStatus());
			Assert.assertTrue("Pedido Criado com sucesso", createdOrder.getId().contains("ORD"));
			Assert.assertFalse("Ocorreu um erro ao gerar o pedido", !createdOrder.getId().contains("ORD"));

		} catch (UnexpectedException e) {
			System.out.println(e.getMessage());
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}
	}

	public void consultarPedido(API api, String orderId) {
		try {
			
			Order order = api.order().get(orderId);
			Assert.assertTrue("Pedido consultado com sucesso", order.getId().contains("ORD"));
			Assert.assertFalse("Pedido não cadastrado na base de dados", !order.getId().contains("ORD"));
			System.out.println(order.toString());
			
		} catch (UnexpectedException e) {
			System.out.println(e.getMessage());
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}
	}

}
