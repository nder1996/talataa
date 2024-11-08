import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteTopProductosComponent } from './reporte-top-productos.component';

describe('ReporteTopProductosComponent', () => {
  let component: ReporteTopProductosComponent;
  let fixture: ComponentFixture<ReporteTopProductosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReporteTopProductosComponent]
    });
    fixture = TestBed.createComponent(ReporteTopProductosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
