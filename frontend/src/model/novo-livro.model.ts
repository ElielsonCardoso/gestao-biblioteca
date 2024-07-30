export interface Categoria {
  id: number;
  descricao: string;
}

export interface NovoLivro {
  titulo: string;
  autor: string;
  isbn: string;
  dataPublicacao: string;
  categoria: Categoria;
}
