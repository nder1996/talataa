import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  private spinnerStateSubject = new BehaviorSubject<boolean>(false);
  private loginSuccessSubject = new BehaviorSubject<boolean>(false);
 // loginSuccess$ = this.loginSuccessSubject.asObservable();
  private loginSuccess = new BehaviorSubject<boolean>(false);

  private loadingSubject = new BehaviorSubject<boolean>(false);
  
  loading$ = this.loadingSubject.asObservable();
  private activeRequests = 0;


  
  get getObservableSession(){
    return this.loginSuccess.asObservable();
  }

  set saveObservableSession(session:boolean){
    this.loginSuccess.next(session);
  }





  startLoading() {
    this.activeRequests++;
    this.loadingSubject.next(true);
  }

  stopLoading() {
    this.activeRequests--;
    if (this.activeRequests === 0) {
      this.loadingSubject.next(false);
    }
  }




  constructor() { }

  activarSpinner(activate: boolean): void {
    this.spinnerStateSubject.next(activate);
  }


  desactivarSpinner(disable: boolean): void {
    this.spinnerStateSubject.next(disable);
  }


  getSpinnerState() {
    return this.spinnerStateSubject.asObservable();
  }



}
