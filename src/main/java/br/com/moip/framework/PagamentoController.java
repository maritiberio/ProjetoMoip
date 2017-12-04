package br.com.moip.framework;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import br.com.moip.API;
import br.com.moip.exception.UnexpectedException;
import br.com.moip.exception.ValidationException;
import br.com.moip.model.ClienteModel;
import br.com.moip.model.PagamentoModel;
import br.com.moip.model.PedidoModel;
import br.com.moip.request.ApiDateRequest;
import br.com.moip.request.BoletoRequest;
import br.com.moip.request.CreditCardRequest;
import br.com.moip.request.FundingInstrumentRequest;
import br.com.moip.request.HolderRequest;
import br.com.moip.request.InstructionLinesRequest;
import br.com.moip.request.PaymentRequest;
import br.com.moip.request.PhoneRequest;
import br.com.moip.request.TaxDocumentRequest;
import br.com.moip.resource.Payment;

public class PagamentoController {

	static WebDriver driver;

	public PagamentoController(WebDriver driver) {
		this.driver = driver;
	}

	public void pagarComCartaoCredito(API api, PedidoModel p, ClienteModel c) {
		try {
			Payment createdPayment = api.payment().create(new PaymentRequest().orderId(p.getId()).installmentCount(1)
					.fundingInstrument(new FundingInstrumentRequest().creditCard(new CreditCardRequest().hash("cc_hash")
							.holder(new HolderRequest().fullname(c.getFullname()).birthdate("1988-10-10")
									.phone(new PhoneRequest().setAreaCode(c.getAreaCode()).setNumber(c.getNumber()))
									.taxDocument(TaxDocumentRequest.cpf(c.getCpf()))))));

			System.out.println(createdPayment.getStatus());

		} catch (UnexpectedException e) {
			System.out.println(e.getMessage());
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}
	}

	public void pagarComBoleto(API api, PedidoModel p) {
		try {
			Payment createdPayment = api.payment().create(new PaymentRequest().orderId(p.getId()).installmentCount(1)
					.fundingInstrument(new FundingInstrumentRequest().boleto(new BoletoRequest()
							.expirationDate(new ApiDateRequest()
									.date(new GregorianCalendar(2020, Calendar.NOVEMBER, 10).getTime()))
							.logoUri("http://logo.com").instructionLines(new InstructionLinesRequest()
									.first("Primeira linha").second("Segunda linha").third("Terceira linha")))));

			System.out.println("Pagamento do pedido: " + p.getId() + " realizado com sucesso");
			System.out.println("Id pagamento via boleto: " + createdPayment.getId() + " com status: "
					+ createdPayment.getStatus());
			System.out.println("Link do Boleto " + createdPayment.getLinks().payBoletoLink());
			System.out.println("Link para impressão do boleto " + createdPayment.getLinks().payBoletoPrintLink());
			System.out.println(createdPayment.getId());

			Assert.assertTrue("Pagamento via boleto realizado com sucesso", createdPayment.getId().contains("PAY"));
			Assert.assertFalse("Pagamento via boleto não finalizado", !createdPayment.getId().contains("PAY"));
		} catch (UnexpectedException e) {
			System.out.println(e.getMessage());
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}
	}

}
