import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from "../navbar/navbar.component";

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

}
