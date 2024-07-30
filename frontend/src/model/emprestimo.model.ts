export interface Emprestimo {
  id: number;
  usuarioId: number;
  nomeUsuario: string;
  livroId: number;
  titulo: string;
  dataEmprestimo: string;
  dataPublicacao: string;
  dataDevolucao: string;
  status: string;
}
