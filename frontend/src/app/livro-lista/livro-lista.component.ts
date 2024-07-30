import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { LivroService } from '../../service/livro.service';
import { Livro } from '../../model/livro.model';
import { EditarLivroDialogComponent } from '../editar-livro/editar-livro.component';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-livro-lista',
  templateUrl: './livro-lista.component.html',
  styleUrls: ['./livro-lista.component.css']
})
export class LivroListaComponent implements OnInit {
  displayedColumns: string[] = ['id', 'titulo', 'autor', 'isbn', 'dataPublicacao', 'descricaoCategoria', 'emprestado', 'acoes'];
  dataSource = new MatTableDataSource<Livro>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private livroService: LivroService, public dialog: MatDialog) {}

  carregarLivros(){
    this.livroService.getLivros().subscribe(livros => {
      this.dataSource.data = livros;
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }

  ngOnInit(): void {
    this.carregarLivros();
  }

  excluirLivro(id: number) {
    if (confirm('Tem certeza que deseja excluir este livro?')) {
      this.livroService.deleteLivro(id).subscribe(() => {
        this.carregarLivros();
      });
    }
  }

  editarLivro(livro: Livro) {
    const dialogRef = this.dialog.open(EditarLivroDialogComponent, {
      width: '400px',
      data: { livro }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.carregarLivros();
      }
    });
  }

}
