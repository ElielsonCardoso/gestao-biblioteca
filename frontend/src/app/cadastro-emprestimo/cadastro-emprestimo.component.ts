import { Component } from '@angular/core';
import { EmprestimoService } from '../../service/emprestimo.service';
import { NovoEmprestimo } from '../../model/novo-emprestimo.model';
import { UsuarioService } from '../../service/usuario.service';
import { LivroService } from '../../service/livro.service';
import { Usuario } from '../../model/usuario.model';
import { Livro } from '../../model/livro.model';

@Component({
  selector: 'app-cadastro-emprestimo',
  templateUrl: './cadastro-emprestimo.component.html',
  styleUrls: ['./cadastro-emprestimo.component.css']
})
export class CadastroEmprestimoComponent {
  emprestimo: NovoEmprestimo = {
    usuario: { id: 0, nome: '' },
    livro: {id: 0, titulo: ''}
  };

  usuarios: Usuario[] = [];
  usuariosFiltrados: Usuario[] = [];
  livros: Livro[] = [];
  livrosFiltrados: Livro[] = [];
  usuarioFiltro: string = '';
  livroFiltro: string = '';

  successMessage: string | null = null;

  constructor(
    private emprestimoService: EmprestimoService,
    private usuarioService: UsuarioService,
    private livroService: LivroService
  ) { }

  ngOnInit() {
    this.carregarLivros();
    this.carregarUsuarios();
  }

  carregarLivros() {
    this.livroService.getLivrosDisponiveis().subscribe(livros => {
      this.livros = livros;
      this.livrosFiltrados = livros;
    });
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

  filtrarLivros() {
    const filtro = this.livroFiltro.toLowerCase();
    this.livrosFiltrados = this.livros.filter(livro =>
      livro.titulo.toLowerCase().includes(filtro)
    );
  }

  adicionarEmprestimo() {
    if (this.camposPreenchidos()){
      this.emprestimoService.addEmprestimo(this.emprestimo).subscribe(response => {
        this.successMessage = 'Empréstimo realizado com sucesso!';
        this.emprestimo = {
          usuario: { id: 0, nome: '' },
          livro: {id: 0, titulo: ''}
        };
        setTimeout(() => this.successMessage = null, 3000);
        console.log('Empréstimo adicionado com sucesso', response);
      }, error => {
        console.error('Erro ao adicionar Empréstimo', error);
      });
    }else{
      alert('Necessário preencher todos os campos!');
    }
  }

  camposPreenchidos(): boolean {
    const emprestimo = this.emprestimo;
    return ((emprestimo.livro.id > 0) && (emprestimo.usuario.id > 0));
  }
}
