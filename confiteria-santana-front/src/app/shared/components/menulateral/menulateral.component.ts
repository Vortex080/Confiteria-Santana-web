import { Component, inject, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { rxResource } from '@angular/core/rxjs-interop';
import { Category } from '../../interface/category';
import { CategoryService } from '../../service/Category.service';

@Component({
  selector: 'app-menulateral',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menulateral.component.html',
  styleUrls: ['./menulateral.component.css']
})
export class MenulateralComponent {

  constructor(private categoryService: CategoryService) { }

  collapsed = false;
  sidebarVisible = false;

  categories = rxResource({ loader: () => this.categoryService.getallCategory() });

  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
  }

  isSidebarVisible(): boolean {
    return this.sidebarVisible || window.innerWidth >= 768;
  }

  scrollToCategory(categoria: string) {
    const el = document.getElementById(categoria);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth' });
      if (window.innerWidth < 768) {
        this.sidebarVisible = false;
      }
    }
  }
}
