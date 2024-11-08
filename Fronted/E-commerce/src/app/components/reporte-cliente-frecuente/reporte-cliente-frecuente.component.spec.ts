import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteClienteFrecuenteComponent } from './reporte-cliente-frecuente.component';

describe('ReporteClienteFrecuenteComponent', () => {
  let component: ReporteClienteFrecuenteComponent;
  let fixture: ComponentFixture<ReporteClienteFrecuenteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReporteClienteFrecuenteComponent]
    });
    fixture = TestBed.createComponent(ReporteClienteFrecuenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
