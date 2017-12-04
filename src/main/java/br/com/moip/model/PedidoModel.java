package br.com.moip.model;

public class PedidoModel {

	private String ownId;
	private String orderId;
	private String customerOwnId;
	private String produto;
	private int quantidade;
	private String detalhe;
	private int valorProduto;
	private String id;

	public String getOwnId() {
		return ownId;
	}

	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public int getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(int valorProduto) {
		this.valorProduto = valorProduto;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerOwnId() {
		return customerOwnId;
	}

	public void setCustomerOwnId(String customerOwnId) {
		this.customerOwnId = customerOwnId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
