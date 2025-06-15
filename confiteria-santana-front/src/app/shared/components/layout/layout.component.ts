import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from "../navbar/navbar.component";
import { LocalStorageService } from '../../service/LocalStorage.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-layout',
  imports: [
    FooterComponent,
    CommonModule,
    RouterModule,
    NavbarComponent
  ],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  user: any = null;
  token: string | null = null;
  sanitizedPhoto: SafeHtml | null = null;

  constructor(private localStorageService: LocalStorageService, private sanitizer: DomSanitizer) {
    this.token = this.localStorageService.getToken();
    this.user = this.localStorageService.getUser();
    if (this.user) {
      this.sanitizedPhoto = this.sanitizer.bypassSecurityTrustUrl(this.user.photo);
    }
  }





}
