import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroEmprestimoComponent } from './cadastro-emprestimo.component';

describe('CadastroEmprestimoComponent', () => {
  let component: CadastroEmprestimoComponent;
  let fixture: ComponentFixture<CadastroEmprestimoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CadastroEmprestimoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadastroEmprestimoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
