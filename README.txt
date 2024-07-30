# Aplicação para Gestão de Biblioteca

## Visão Geral
Desenvolvimento de uma aplicação para gestão de biblioteca, criada conforme proposta de atividade Oxy.

## Arquitetura
Banco de dados: relacional, contendo a estrutura solicitada na atividade. Ferramenta utilizada: DBeaver. O banco está em nuvem por meio do https://railway.app.
Back-end: Desenvolvido em Java (11), realiza a persistência de dados e fornecimento de API's Rest. Ferramente utilizada: Intellij.
Front-end: Desenvolvido em Angular, responsável pela interface do usuário. Ferramenta utilizada: VS Code.

## Ambiente
Java 11: Foi utilizada a JDK 11.
Node.js e npm: Necessários para o Angular.
Angular CLI: Para facilitar o desenvolvimento Angular.
Banco de Dados Relacional: PostgreSQL. 
Maven: Para gerenciamento de dependências do projeto Java.
Git: Para clonar o repositório (https://github.com/ElielsonCardoso/gestao-biblioteca).

beckend: ...\gestao-biblioteca\src
frontend: ...\gestao-biblioteca\frontend

## Endpoints da API
CATEGORIA
Base URL: "/api/categorias"

Descrição: Retorna uma lista com id e nome de todas as categorias.
Método: GET
Endpoint: /api/categorias

-----

EMPRESTIMO
Base URL: "/api/emprestimos"

Descrição: Criar empréstimo.
Método: POST
Endpoint: /api/emprestimos

Listar Todos os Empréstimos
Método: GET
Endpoint: /api/emprestimos
Param:
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 10).

Pesquisa por ID
Método: GET
Endpoint: /api/emprestimos/{id}

Categoria(s) mais emprestada(s) pelo usuário
Método: GET
Endpoint: /api/emprestimos/recomendacao/{usuarioId}
Param:
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 20).

Buscar Empréstimos por Parâmetros
Método: GET
Endpoint: /api/emprestimos/findByParam
Param:
	EmprestimoParam: {
		Long usuarioId;
		String nomeUsuario;
		Long livroId;
		String titulo;
		Date dataEmprestimo;
		Date dataDevolucao;
		String status;
	}
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 20).
	
Devolver Empréstimo
Método: PUT
Endpoint: /api/emprestimos/devolver/{id}

Deletar Empréstimo
Método: DELETE
Endpoint: /api/emprestimos/{id}

-----

LIVRO
Base URL: "/api/livros"

Criar Livro
Método: POST
Endpoint: /api/livros

Listar Todos os Livros
Método: GET
Endpoint: /api/livros
Param:
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 10).
	
Pesquisa por ID
Método: GET
Endpoint: /api/livros/{id}

Buscar Livros por Parâmetros
Método: GET
Endpoint: /api/livros/findByParam
Param:
	LivroParam: {
		String titulo;
		String autor;
		String isbn;
		Date dataPublicacao;
		String descricaoCategoria;
		Boolean emprestado;
	}
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 20).
	
Busca de Livros recomendados por Usuário (com base na categoria mais emprestada)
Método: GET
Endpoint: /api/livros/recomendacao/{usuarioid}	
Param:
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 20).
	
Atualizar Livro
Método: PUT
Endpoint: /api/livros

Deletar Livro
Método: DELETE
Endpoint: /api/livros/{id}

-----

USUARIO
Base URL: "/api/usuarios"

Criar Usuário
Método: POST
Endpoint: /api/usuarios

Listar Todos os Usuários
Método: GET
Endpoint: /api/usuarios
Param:
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 10).
	
Pesquisa por ID
Método: GET
Endpoint: /api/usuarios/{id}

Buscar Usuários por Parâmetros
Método: GET
Endpoint: /api/usuarios/findByParam
Param:
	UsuarioParam: {
		String nome;
		String email;
		Date dataCadastro;
		String telefone;
	}
	page (opcional): Número da página (padrão: 0).
	size (opcional): Tamanho da página (padrão: 20).
	
Atualizar Usuário
Método: PUT
Endpoint: /api/usuarios

Deletar Usuário
Método: DELETE
Endpoint: /api/usuarios/{id}

## Recomendação de livros
Ao utilizar o endpoint (GET) /api/emprestimos/recomendacao/{usuarioId}
Serão retornadas quais as categorias mais emprestadas pelo usuário
	{
		Long categoriaId;
		Long qtdeEmprestada;
		String descricaoCategoria;	
	}

A listagem de livros recomendados ocorre no endpoint (GET) /api/livros/recomendacao/{usuarioid}
Serão retornados os livros das categorias mais emprestadas, mas que ainda não foram emprestados pelo usuário
	{
		Long id;
		String titulo;
		String autor;
		String isbn;
		LocalDate dataPublicacao;
		String descricaoCategoria;
		Boolean emprestado;	
	}