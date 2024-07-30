import { Component } from '@angular/core';
import { EmprestimoService } from '../../service/emprestimo.service';
import { UsuarioService } from '../../service/usuario.service';
import { LivroService } from '../../service/livro.service';
import { Usuario } from '../../model/usuario.model';
import { Livro } from '../../model/livro.model';
import { Recomendacao } from '../../model/recomendacao.model';
import { NovoEmprestimo } from '../../model/novo-emprestimo.model';

@Component({
  selector: 'app-recomendacao',
  templateUrl: './recomendacao.component.html',
  styleUrls: ['./recomendacao.component.css']
})
export class RecomendacaoComponent {
  emprestimo: NovoEmprestimo = {
    usuario: { id: 0, nome: '' },
    livro: {id: 0, titulo: ''}
  };

  usuarios: Usuario[] = [];
  usuariosFiltrados: Usuario[] = [];
  livros: Livro[] = [];
  usuarioFiltro: string = '';
  usuarioSelecionadoId: number | null = null;
  recomendacao: Recomendacao[] = [];

  displayedColumns: string[] = ['id', 'titulo', 'autor', 'isbn', 'dataPublicacao', 'descricaoCategoria', 'acoes'];
  dataSource: Livro[] = [];

  successMessage: string | null = null;

  constructor(
    private emprestimoService: EmprestimoService,
    private usuarioService: UsuarioService,
    private livroService: LivroService
  ) { }

  ngOnInit() {
    this.carregarUsuarios();
  }

  carregarUsuarios() {
    this.usuarioService.getUsuarios().subscribe(usuarios => {
      this.usuarios = usuarios;
      this.usuariosFiltrados = usuarios;
    });
  }

  filtrarUsuarios() {
    const filtro = this.usuarioFiltro.toLowerCase();
    this.usuariosFiltrados = this.usuarios.filter(usuario =>
      usuario.nome.toLowerCase().includes(filtro)
    );
  }

  carregarLivrosRecomendados() {
    if (this.usuarioSelecionadoId !== null) {
      this.livroService.getLivrosRecomendados(this.usuarioSelecionadoId).subscribe(livros => {
        this.livros = livros;
        this.dataSource = livros;
      });
    }
  }

  carregarRecomendacao() {
    if (this.usuarioSelecionadoId !== null) {
      this.emprestimoService.getRecomendacao(this.usuarioSelecionadoId).subscribe(recomendacao => {
        this.recomendacao = recomendacao;
      });
    }
  }

  camposPreenchidos(): boolean {
    return this.usuarioSelecionadoId !== null;
  }

  pesquisarRecomendacoes() {
    if (this.camposPreenchidos()) {
      this.carregarLivrosRecomendados();
      this.carregarRecomendacao();
    } else {
      this.recomendacao = [];
      this.livros = [];
      this.dataSource = [];
    }
  }


  adicionarEmprestimo(livroId: number) {
    if (this.camposPreenchidos()){
      this.emprestimo.livro.id = livroId;
      if (this.usuarioSelecionadoId != null){
        this.emprestimo.usuario.id = this.usuarioSelecionadoId;
      }

      this.emprestimoService.addEmprestimo(this.emprestimo).subscribe(response => {
        this.successMessage = 'Empréstimo realizado com sucesso!';
        this.emprestimo = {
          usuario: { id: 0, nome: '' },
          livro: {id: 0, titulo: ''}
        };
        setTimeout(() => this.successMessage = null, 3000);
        console.log('Empréstimo adicionado com sucesso', response);
        this.carregarLivrosRecomendados();
        this.carregarRecomendacao();
      }, error => {
        console.error('Erro ao adicionar Empréstimo', error);
      });
    }else{
      alert('Necessário preencher todos os campos!');
    }
  }
}
