import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MenuComponent } from './menu.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [MenuComponent],
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatTableModule,
    MatSort,
    HttpClientModule
  ],
  exports: [MenuComponent]
})
export class MenuModule { }
