import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { AppComponent } from './app.component';
import { LivroListaComponent } from './livro-lista/livro-lista.component';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes'
import { MenuModule } from './menu/menu.module';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { MatOptionModule } from '@angular/material/core';
import { CadastroLivroComponent } from './cadastro-livro/cadastro-livro.component';
import { MatCardModule } from '@angular/material/card';
import { UsuarioListaComponent } from './usuario-lista/usuario-lista.component';
import { CadastroUsuarioComponent } from './cadastro-usuario/cadastro-usuario.component';
import { EditarUsuarioDialogComponent } from './editar-usuario/editar-usuario.component';
import { MatDialogModule } from '@angular/material/dialog';
import { SuccessMessageComponent } from './success-message/success-message.component';
import { EditarLivroDialogComponent } from './editar-livro/editar-livro.component';
import { EmprestimoListaComponent } from './emprestimo-lista/emprestimo-lista.component';
import { CadastroEmprestimoComponent } from './cadastro-emprestimo/cadastro-emprestimo.component';
import { RecomendacaoComponent } from './recomendacao/recomendacao.component';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  declarations: [
    AppComponent,
    LivroListaComponent,
    CadastroLivroComponent,
    UsuarioListaComponent,
    CadastroUsuarioComponent,
    EditarUsuarioDialogComponent,
    SuccessMessageComponent,
    EditarLivroDialogComponent,
    EmprestimoListaComponent,
    CadastroEmprestimoComponent,
    RecomendacaoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    MatTableModule,
    MatToolbarModule,
    MatButtonModule,
    MenuModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    FormsModule,
    MatOptionModule,
    MatCardModule,
    MatDialogModule,
    MatListModule,
    MatPaginatorModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
