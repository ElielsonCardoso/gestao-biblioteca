import { Usuario } from './usuario.model';

export interface UsuarioResponse {
  content: Usuario[];
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
