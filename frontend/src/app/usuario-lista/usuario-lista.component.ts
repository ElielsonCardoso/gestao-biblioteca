import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario.model';
import { EditarUsuarioDialogComponent } from '../editar-usuario/editar-usuario.component';

@Component({
  selector: 'app-usuario-lista',
  templateUrl: './usuario-lista.component.html',
  styleUrls: ['./usuario-lista.component.css']
})
export class UsuarioListaComponent implements OnInit {
  displayedColumns: string[] = ['id', 'nome', 'email', 'dataCadastro', 'telefone', 'qtdeLivrosPendentes', 'acoes'];
  dataSource = new MatTableDataSource<Usuario>();

  @ViewChild(MatSort) sort!: MatSort;

  constructor(private usuarioService: UsuarioService, public dialog: MatDialog) {}

  carregarUsuarios() {
    this.usuarioService.getUsuarios().subscribe(usuarios => {
      this.dataSource.data = usuarios;
      this.dataSource.sort = this.sort;
    });
  }

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  excluirUsuario(id: number) {
    if (confirm('Tem certeza que deseja excluir este usuário?')) {
      this.usuarioService.deleteUsuario(id).subscribe(() => {
        this.carregarUsuarios();
      });
    }
  }

  editarUsuario(usuario: Usuario) {
    const dialogRef = this.dialog.open(EditarUsuarioDialogComponent, {
      width: '400px',
      data: { usuario }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.carregarUsuarios();
      }
    });
  }

}
