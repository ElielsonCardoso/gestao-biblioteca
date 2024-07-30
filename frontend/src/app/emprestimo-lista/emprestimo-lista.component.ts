import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { EmprestimoService } from '../../service/emprestimo.service';
import { Emprestimo } from '../../model/emprestimo.model';

@Component({
  selector: 'app-emprestimo-lista',
  templateUrl: './emprestimo-lista.component.html',
  styleUrls: ['./emprestimo-lista.component.css']
})
export class EmprestimoListaComponent implements OnInit {
  displayedColumns: string[] = ['id', 'nomeUsuario', 'titulo', 'dataEmprestimo', 'dataDevolucao', 'status', 'acoes'];
  dataSource = new MatTableDataSource<Emprestimo>();

  @ViewChild(MatSort) sort!: MatSort;

  successMessage: string | null = null;

  constructor(private emprestimoService: EmprestimoService, public dialog: MatDialog) {}

  carregarEmprestimos(){
    this.emprestimoService.getEmprestimos().subscribe(emprestimos => {
      this.dataSource.data = emprestimos;
      this.dataSource.sort = this.sort;
    });
  }

  ngOnInit(): void {
    this.carregarEmprestimos();
  }

  devolverEmprestimo(id: number) {
    if (confirm('Confirma a devolução deste empréstimo?')) {
      this.emprestimoService.devolver(id).subscribe(() => {
        this.successMessage = 'Devolução realizada com sucesso!';
        setTimeout(() => this.successMessage = null, 3000);
        this.carregarEmprestimos();
      });
    }
  }

}
