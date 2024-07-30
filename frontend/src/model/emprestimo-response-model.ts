import { Emprestimo } from './emprestimo.model';

export interface EmprestimoResponse {
  content: Emprestimo[];
  pageable: any;
  last: boolean;
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
  sort: any;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}
