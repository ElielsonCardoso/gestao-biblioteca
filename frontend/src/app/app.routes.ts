import { Routes } from '@angular/router';
import { MenuComponent } from './menu/menu.component';
import { LivroListaComponent } from './livro-lista/livro-lista.component';
import { CadastroLivroComponent } from './cadastro-livro/cadastro-livro.component';
import { UsuarioListaComponent } from './usuario-lista/usuario-lista.component';
import { CadastroUsuarioComponent } from './cadastro-usuario/cadastro-usuario.component';
import { EditarUsuarioDialogComponent } from './editar-usuario/editar-usuario.component';
import { EditarLivroDialogComponent } from './editar-livro/editar-livro.component';
import { EmprestimoListaComponent } from './emprestimo-lista/emprestimo-lista.component';
import { CadastroEmprestimoComponent } from './cadastro-emprestimo/cadastro-emprestimo.component';
import { RecomendacaoComponent } from './recomendacao/recomendacao.component';

export const routes: Routes = [
  { path: '', component: MenuComponent },
  { path: 'livros', component: LivroListaComponent },
  { path: 'cadastro-livro', component: CadastroLivroComponent },
  { path: 'editar-livro', component: EditarLivroDialogComponent },
  { path: 'usuarios', component: UsuarioListaComponent },
  { path: 'cadastro-usuario', component: CadastroUsuarioComponent },
  { path: 'editar-usuario', component: EditarUsuarioDialogComponent },
  { path: 'emprestimos', component: EmprestimoListaComponent },
  { path: 'cadastro-emprestimo', component: CadastroEmprestimoComponent },
  { path: 'recomendacoes', component: RecomendacaoComponent },
  { path: '**', redirectTo: '' }
];
