# Projeto Moip

O objetivo deste teste automatizado é validar se as funcionalidades básicas do sistema da Moip estão de acordo com as especificações técnicas e funcionais. Para isso, foram desenvolvidos casos de teste para validar e verificar as funções de criar cliente, pedido e pagamento.

Para o desenvolvimento, foi utilizado o Selenium WebDriver a partir do Eclipse IDE e recursos como o repositório do Maven e o JUnit.

As funções desenvolvidas estão em classes Controller, dentro de src/main/java/br/com/moip/framework/.

A classe ClienteController.java, é composta por três funções:

criarCliente(API api, ClienteModel clienteModel) > A função cria um cliente, gerando um ID para o mesmo.
consultarCliente(API api, String customerId) > A função realiza a consulta do cliente através do ID.
adicionarCartao(API api, ClienteModel c) > A função adiciona um cartão para um cliente já cadastrado.
A classe PagamentoController.java, é composta por duas funções:

pagarComCartaoCredito(API api, PedidoModel p, ClienteModel c) > A função realiza o pagamento de um pedido criado, através do cartão de crédito
pagarComBoleto(API api, PedidoModel p) > A função realiza o pagamento de um pedido criado, através do cartão de crédito
e por último, a classe PedidoController.java, que é composta por duas funções:

criarPedido(API api, PedidoModel p, ClienteModel c)
consultarPedido(API api, String orderId)
As entidades estão em classes Model, dentro de src/main/java/br/com/moip/model/.

Os atributos que compõe a classe ClienteModel.java são:

private String fullname; private String email; private String cpf; private String areaCode; private String number; private String street; private String streetNumber; private String complement; private String city; private String state; private String district; private String country; private String zipCode; private String id;

Os atributos que compõe a classe PagamentoModel.java são:

private String hash;

Os atributos que compõe a classe PagamentoModel.java são:

private String produto; private int quantidade; private String detalhe; private int valorProduto;

Escopo

O escopo, que é composto por três cenários, estão no caminho /src/test/java/test/ :

CN001 > Criar cliente e adicionar um cartão via API
CN002 > Criar pedido e pagar com boleto via API.
CN003 > Cliente novo cria pedido e realiza pagamendo com boleto via API.
Para os cenários que deve criar um cliente novo, foi desenvolvido uma função para gerar e validar o CPF do mesmo.

Segue o passo a passo para executar os cenários
[Passo a passo](ProjetoMoip/Passo a passo.docx)
