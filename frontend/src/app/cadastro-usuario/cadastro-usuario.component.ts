import { Component } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { NovoUsuario } from '../../model/novo-usuario.model';

@Component({
  selector: 'app-cadastro-usuario',
  templateUrl: './cadastro-usuario.component.html',
  styleUrls: ['./cadastro-usuario.component.css']
})
export class CadastroUsuarioComponent {
  usuario: NovoUsuario = {
    nome: '',
    email: '',
    telefone: ''
  };

  successMessage: string | null = null;

  constructor(private usuarioService: UsuarioService) { }

  adicionarUsuario() {
    if (this.camposPreenchidos()){
      this.usuarioService.addUsuario(this.usuario).subscribe(response => {
        this.successMessage = 'Usuário adicionado com sucesso!';
        this.usuario = {
          nome: '',
          email: '',
          telefone: ''
        };
        setTimeout(() => this.successMessage = null, 3000);
        console.log('Usuário adicionado com sucesso', response);
      }, error => {
        console.error('Erro ao adicionar Usuário', error);
      });
    }else{
      alert('Necessário preencher todos os campos!');
    }
  }

  camposPreenchidos(): boolean {
    const usuario = this.usuario;
    return ((usuario.nome!="") && (usuario.email!="") && (usuario.telefone!=""));
  }
}
