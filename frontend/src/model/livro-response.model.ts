import { Livro } from './livro.model';

export interface LivroResponse {
  content: Livro[];
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
