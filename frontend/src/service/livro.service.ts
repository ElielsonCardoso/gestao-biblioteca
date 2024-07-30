import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Livro } from '../model/livro.model';
import { NovoLivro } from '../model/novo-livro.model';
import { LivroResponse } from '../model/livro-response.model';

@Injectable({
  providedIn: 'root'
})
export class LivroService {
  private apiUrlFind = 'http://localhost:8080/api/livros/findByParam';
  private apiUrlBase = 'http://localhost:8080/api/livros';
  private apiUrlRecomendados = 'http://localhost:8080/api/livros/recomendacao';

  constructor(private http: HttpClient) { }

  getLivros(): Observable<Livro[]> {
    return this.http.get<LivroResponse>(this.apiUrlFind)
      .pipe(
        map(response => response.content)
      );
  }

  getLivrosDisponiveis(): Observable<Livro[]> {
    let params = new HttpParams();
    params = params.append('emprestado', 'false');
    return this.http.get<LivroResponse>(this.apiUrlFind, { params })
      .pipe(
        map(response => response.content)
      );
  }

  getLivrosRecomendados(usuarioId: number): Observable<Livro[]> {
    return this.http.get<LivroResponse>(`${this.apiUrlRecomendados}/${usuarioId}`)
      .pipe(
        map(response => response.content)
      );
  }

  addLivro(livro: NovoLivro): Observable<any> {
    return this.http.post<any>(this.apiUrlBase, livro);
  }

  deleteLivro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrlBase}/${id}`);
  }

  updateLivro(livro: Livro): Observable<Livro> {
    return this.http.put<Livro>(`${this.apiUrlBase}`, livro);
  }
}
