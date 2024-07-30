import { Component } from '@angular/core';
import { LivroService } from '../../service/livro.service';
import { NovoLivro } from '../../model/novo-livro.model';
import { CategoriaService, Categoria } from '../../service/categoria.service';

@Component({
  selector: 'app-cadastro-livro',
  templateUrl: './cadastro-livro.component.html',
  styleUrls: ['./cadastro-livro.component.css']
})
export class CadastroLivroComponent {
  livro: NovoLivro = {
    titulo: '',
    autor: '',
    isbn: '',
    dataPublicacao: '',
    categoria: { id: 0, descricao: '' }
  };

  categorias: Categoria[] = [];

  successMessage: string | null = null;

  constructor(
    private livroService: LivroService,
    private categoriaService: CategoriaService
  ) { }

  ngOnInit() {
    this.carregarCategorias();
  }

  carregarCategorias() {
    this.categoriaService.getCategorias().subscribe(categorias => {
      this.categorias = categorias;
    });
  }

  adicionarLivro() {
    if (this.camposPreenchidos()){
      this.livroService.addLivro(this.livro).subscribe(response => {
        this.successMessage = 'Livro adicionado com sucesso!';
        this.livro = {
          titulo: '',
          autor: '',
          isbn: '',
          dataPublicacao: '',
          categoria: { id: 0, descricao: '' }
        };
        setTimeout(() => this.successMessage = null, 3000);
        console.log('Livro adicionado com sucesso', response);
      }, error => {
        console.error('Erro ao adicionar livro', error);
      });
    }else{
      alert('Necessário preencher todos os campos!');
    }
  }

  camposPreenchidos(): boolean {
    const livro = this.livro;
    return ((livro.titulo!="") && (livro.autor!="") && (livro.isbn!="") && (livro.dataPublicacao!="" && (livro.categoria.id > 0)));
  }
}
