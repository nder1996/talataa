import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudInventariosComponent } from './crud-inventarios.component';

describe('CrudInventariosComponent', () => {
  let component: CrudInventariosComponent;
  let fixture: ComponentFixture<CrudInventariosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrudInventariosComponent]
    });
    fixture = TestBed.createComponent(CrudInventariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
