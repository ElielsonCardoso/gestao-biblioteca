import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LivroService } from '../../service/livro.service';
import { Livro } from '../../model/livro.model';

@Component({
  selector: 'app-editar-livro',
  templateUrl: './editar-livro.component.html',
  styleUrls: ['./editar-livro.component.css']
})
export class EditarLivroDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<EditarLivroDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { livro: Livro },
    private livroService: LivroService
  ) {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.camposPreenchidos()){
      this.livroService.updateLivro(this.data.livro).subscribe(
        updatedLivro => {
          this.dialogRef.close(updatedLivro);
        },
        error => {
          console.error('Erro ao atualizar o livro', error);
        }
      );
    }else{
      alert('Necessário preencher todos os campos!');
    }
  }

  camposPreenchidos(): boolean {
    const livro = this.data.livro;
    return ((livro.autor!="") && (livro.isbn!="") && (livro.titulo!=""));
  }
}
