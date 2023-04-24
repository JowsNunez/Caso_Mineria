import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlSemaforoComponent } from './control-semaforo.component';

describe('ControlSemaforoComponent', () => {
  let component: ControlSemaforoComponent;
  let fixture: ComponentFixture<ControlSemaforoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ControlSemaforoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ControlSemaforoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
