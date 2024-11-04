import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudOrdenesComponent } from './crud-ordenes.component';

describe('CrudOrdenesComponent', () => {
  let component: CrudOrdenesComponent;
  let fixture: ComponentFixture<CrudOrdenesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrudOrdenesComponent]
    });
    fixture = TestBed.createComponent(CrudOrdenesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
