export interface Usuario {
  id: number;
  nome: string;
}

export interface Livro {
  id: number;
  titulo: string;
}

export interface NovoEmprestimo {
  usuario: Usuario;
  livro: Livro;
}
