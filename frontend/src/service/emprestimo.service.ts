import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Emprestimo } from '../model/emprestimo.model';
import { NovoEmprestimo } from '../model/novo-emprestimo.model';
import { EmprestimoResponse } from '../model/emprestimo-response-model';
import { Recomendacao } from '../model/recomendacao.model';
import { RecomendacaoResponse } from '../model/recomendacao-response.model';

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {
  private apiUrlFind = 'http://localhost:8080/api/emprestimos/findByParam';
  private apiUrlBase = 'http://localhost:8080/api/emprestimos';
  private apiUrlRecomendacao = 'http://localhost:8080/api/emprestimos/recomendacao';
  private apiUrlDevolver = 'http://localhost:8080/api/emprestimos/devolver';

  constructor(private http: HttpClient) { }

  getEmprestimos(): Observable<Emprestimo[]> {
    return this.http.get<EmprestimoResponse>(this.apiUrlFind)
      .pipe(
        map(response => response.content)
      );
  }

  getRecomendacao(usuarioId: number): Observable<Recomendacao[]> {
    return this.http.get<RecomendacaoResponse>(`${this.apiUrlRecomendacao}/${usuarioId}`)
      .pipe(
        map(response => response.content)
      );
  }

  addEmprestimo(emprestimo: NovoEmprestimo): Observable<any> {
    return this.http.post<any>(this.apiUrlBase, emprestimo);
  }

  devolver(id: number): Observable<Emprestimo> {
    return this.http.put<Emprestimo>(`${this.apiUrlDevolver}/${id}`, null);
  }

}
