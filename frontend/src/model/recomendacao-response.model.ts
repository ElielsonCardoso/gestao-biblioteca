import { Recomendacao } from './recomendacao.model';

export interface RecomendacaoResponse {
  content: Recomendacao[];
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
