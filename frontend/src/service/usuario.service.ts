import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Usuario } from '../model/usuario.model';
import { NovoUsuario } from '../model/novo-usuario.model';
import { UsuarioResponse } from '../model/usuario-response.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrlFind = 'http://localhost:8080/api/usuarios/findByParam';
  private apiUrlBase = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) { }

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<UsuarioResponse>(this.apiUrlFind)
      .pipe(
        map(response => response.content)
      );
  }

  addUsuario(usuario: NovoUsuario): Observable<any> {
    return this.http.post<any>(this.apiUrlBase, usuario);
  }

  deleteUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrlBase}/${id}`);
  }

  updateUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrlBase}`, usuario);
  }
}
