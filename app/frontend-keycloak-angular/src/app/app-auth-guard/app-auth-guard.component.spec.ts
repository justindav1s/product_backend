import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppAuthGuardComponent } from './app-auth-guard.component';

describe('AppAuthGuardComponent', () => {
  let component: AppAuthGuardComponent;
  let fixture: ComponentFixture<AppAuthGuardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppAuthGuardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppAuthGuardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
