import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudDetallesComponent } from './crud-detalles.component';

describe('CrudDetallesComponent', () => {
  let component: CrudDetallesComponent;
  let fixture: ComponentFixture<CrudDetallesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrudDetallesComponent]
    });
    fixture = TestBed.createComponent(CrudDetallesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
