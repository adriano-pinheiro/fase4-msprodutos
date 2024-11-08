# Tech Challenge - Fase 4 

### Projeto: Sistema de Gerenciamento de Pedidos

### Microsserviço de Produtos - Porta 8082

<br>

### Funções Principais:

1. Cadastrar Produtos
2. Listar Produtos
3. Atualizar informações dos Produtos
4. Excluir Produtos

### Ferramentas e Tecnologias:

1. Linguagens de Programação: Java.
2. Banco de dados: MySQL
3. Ferramentas de API: Swagger, Postman, etc.
5. Controle de Versão: Git
6. Docker

### API Endpoints:

1. POST `/api/v1/produtos` - para adicionar um novo produto
2. GET `/api/v1/produtos` - para listar todos os produtos
3. GET `/api/v1/produtos/{produtoId}` - para ver detalhes de um produto
4. PUT `/api/v1/produtos/{produtoId}` - para atualizar um produto
5. DELETE `/api/v1/produtos/{produtoId}` - para excluir um produto

### Arquivo para importação da collection no Postman com os payloads das requisições:

1. externalfiles/fase4.postman_collection.json

### Instrução para a subida do container docker:

1. docker-compose -p msprodutos-mysql up -d
