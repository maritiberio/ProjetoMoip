package br.com.moip.framework;

import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import br.com.moip.API;
import br.com.moip.exception.UnexpectedException;
import br.com.moip.exception.ValidationException;
import br.com.moip.model.ClienteModel;
import br.com.moip.model.PagamentoModel;
import br.com.moip.request.ApiDateRequest;
import br.com.moip.request.CreditCardRequest;
import br.com.moip.request.CustomerRequest;
import br.com.moip.request.FundingInstrumentRequest;
import br.com.moip.request.HolderRequest;
import br.com.moip.request.PhoneRequest;
import br.com.moip.request.ShippingAddressRequest;
import br.com.moip.request.TaxDocumentRequest;
import br.com.moip.resource.Customer;
import br.com.moip.resource.FundingInstrument;

public class ClienteController {

	static WebDriver driver;

	public ClienteController(WebDriver driver) {
		this.driver = driver;
	}

	public void criarCliente(API api, ClienteModel clienteModel) {
		try {
			Customer customer = api.customer()
					.create(new CustomerRequest().ownId("CUS-" + System.currentTimeMillis())
							.fullname(clienteModel.getFullname()).email(clienteModel.getEmail())
							.birthdate(new ApiDateRequest().date(new Date()))
							.taxDocument(TaxDocumentRequest.cpf(clienteModel.getCpf()))
							.phone(new PhoneRequest().setAreaCode(clienteModel.getAreaCode())
									.setNumber(clienteModel.getNumber()))
							.shippingAddressRequest(new ShippingAddressRequest().street(clienteModel.getStreet())
									.streetNumber(clienteModel.getStreetNumber())
									.complement(clienteModel.getComplement()).city(clienteModel.getCity())
									.state(clienteModel.getState()).district(clienteModel.getDistrict())
									.country(clienteModel.getCountry()).zipCode(clienteModel.getZipCode())));

			clienteModel.setId(customer.getId());
			Assert.assertTrue("Cliente Criado com sucesso", customer.getId().contains("CUS"));
			System.out.println("Cliente " + clienteModel.getFullname() + ", CPF " + clienteModel.getCpf()
					+ " cadastrado com sucesso. ID: " + customer.getId());
			Assert.assertFalse("Ocorreu um erro ao gerar cliente", !customer.getId().contains("CUS"));

		} catch (UnexpectedException e) {
			System.out.println(e.getMessage());
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}

	}

	public void consultarCliente(API api, String customerId) {
		try {

			Customer customer = api.customer().get(customerId);
			Assert.assertTrue("Cliente consultado com sucesso", customer.getId().contains("CUS"));
			System.out.println("Cliente encontrado: " + customer.toString());
			Assert.assertFalse("Cliente não cadastrado na base de dados", !customer.getId().contains("CUS"));

		} catch (UnexpectedException e) {
			System.out.println(e.getMessage());
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}
	}

	public void adicionarCartao(API api, ClienteModel c) {
		try {
			FundingInstrument creditCard = api.customer()
					.addCreditCard(new CustomerRequest()
							.fundingInstrument(new FundingInstrumentRequest().creditCard(new CreditCardRequest()
									.number("5555666677778884").cvc(123).expirationMonth("05").expirationYear("18")
									.holder(new HolderRequest().fullname(c.getFullname()).birthdate("1988-10-10")
											.phone(new PhoneRequest().setAreaCode("11").setNumber("55667788"))
											.taxDocument(TaxDocumentRequest.cpf(c.getCpf())))))
							.id(c.getId()));

			Assert.assertTrue("Cliente consultado com sucesso", creditCard.getCreditCard().getId().contains("CRC"));
			Assert.assertFalse("Cliente não cadastrado na base de dados",
					!creditCard.getCreditCard().getId().contains("CRC"));
			System.out.println("Cartão de credito adicionado com sucesso. ID: " + creditCard.getCreditCard().getId()
					+ " HashCode: " + creditCard.getCreditCard().hashCode());
		} catch (UnexpectedException e) {
			System.out.println(e.getMessage());
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}

	}

}
