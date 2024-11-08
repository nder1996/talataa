import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { AuthService } from "src/service/auth.service";
import { LocalStorageService } from "src/service/local-storage.service";

@Injectable({
    providedIn: 'root'
  })
  export class AuthGuard implements CanActivate {
      constructor(
        private router: Router,
        private authService: AuthService,
        private localStorage: LocalStorageService,
      ) {}
    
      canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot,
        
      ): Observable<boolean> | Promise<boolean> | boolean {
        return this.checkAuth();
      }
    
      canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
      ): Observable<boolean> | Promise<boolean> | boolean {
        return this.canActivate(route, state);
      }
    
      private checkAuth(): boolean {
        const token = this.localStorage.get('currentUserAdmin')
        if (token) {
          return true;
        }
        this.router.navigate(['/login']);
        return false;
      }


}