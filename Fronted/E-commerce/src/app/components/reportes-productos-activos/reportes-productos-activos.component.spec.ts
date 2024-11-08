import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportesProductosActivosComponent } from './reportes-productos-activos.component';

describe('ReportesProductosActivosComponent', () => {
  let component: ReportesProductosActivosComponent;
  let fixture: ComponentFixture<ReportesProductosActivosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportesProductosActivosComponent]
    });
    fixture = TestBed.createComponent(ReportesProductosActivosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
