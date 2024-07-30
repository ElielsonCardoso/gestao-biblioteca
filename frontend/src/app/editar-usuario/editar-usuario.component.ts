import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario.model';

@Component({
  selector: 'app-editar-usuario',
  templateUrl: './editar-usuario.component.html',
  styleUrls: ['./editar-usuario.component.css']
})
export class EditarUsuarioDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<EditarUsuarioDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { usuario: Usuario },
    private usuarioService: UsuarioService
  ) {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.camposPreenchidos()){
      this.usuarioService.updateUsuario(this.data.usuario).subscribe(
        updatedUsuario => {
          this.dialogRef.close(updatedUsuario);
        },
        error => {
          console.error('Erro ao atualizar o usuário', error);
        }
      );
    }else{
      alert('Necessário preencher todos os campos!');
    }
  }

  camposPreenchidos(): boolean {
    const usuario = this.data.usuario;
    return ((usuario.nome!="") && (usuario.email!="") && (usuario.telefone!=""));
  }
}
