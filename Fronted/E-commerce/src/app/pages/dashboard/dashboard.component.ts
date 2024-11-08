import { Component, ElementRef } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthService } from 'src/service/auth.service';

declare var bootstrap: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
 // Definimos el tipo para el tema
 private theme: 'dark' | 'light' = 'light';

 constructor(private elementRef: ElementRef,private auth:AuthService) {}

 ngOnInit(): void {
   // Inicialización del tema
   if (this.isLight()) {
     this.toggleRootClass();
   }

   const sidebarToggle = this.elementRef.nativeElement.querySelector('#sidebar-toggle');
   if (sidebarToggle) {
     sidebarToggle.addEventListener('click', () => {
       this.toggleSidebar();
     });
   }
 }

 private toggleSidebar(): void {
   const sidebar = this.elementRef.nativeElement.querySelector('#sidebar');
   if (sidebar) {
     sidebar.classList.toggle('collapsed');
   }
 }

 handleThemeToggle(): void {
   this.toggleLocalStorage();
   this.toggleRootClass();
 }

 private toggleRootClass(): void {
   const current = document.documentElement.getAttribute('data-bs-theme') as 'dark' | 'light' || 'light';
   const inverted: 'dark' | 'light' = current === 'dark' ? 'light' : 'dark';
   document.documentElement.setAttribute('data-bs-theme', inverted);
 }

 private toggleLocalStorage(): void {
   if (this.isLight()) {
     localStorage.removeItem('light');
   } else {
     localStorage.setItem('light', 'set');
   }
 }

 private isLight(): boolean {
   return !!localStorage.getItem('light');
 }

 // Es buena práctica limpiar los event listeners
 ngOnDestroy(): void {
   const sidebarToggle = this.elementRef.nativeElement.querySelector('#sidebar-toggle');
   if (sidebarToggle) {
     sidebarToggle.replaceWith(sidebarToggle.cloneNode(true));
   }
 }

 logout(){
  this.auth.logoutAdmin();
 }

 /* */
 

}
