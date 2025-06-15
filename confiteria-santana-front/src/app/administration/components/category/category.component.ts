
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../../../shared/service/Category.service';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { rxResource } from '@angular/core/rxjs-interop';
import { ConfirmDialogComponent } from "../../../shared/components/confirm-dialog/confirm-dialog.component";
@Component({
  selector: 'app-category',
  imports: [FormsModule, CommonModule, ConfirmDialogComponent],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CategoryComponent {


  constructor(private categoriaService: CategoryService) { }
  visibleEliminar = false;
  filtro: string = '';
  modalVisible: boolean = false;
  modalEdicionAbierto: boolean = false;

  categoriaEditando: any = null;

  categorias = rxResource({
    loader: () => this.categoriaService.getallCategory(),
  })

  get categoriasFiltradas() {
    return this.categorias.value()?.filter(c =>
      c.name.toLowerCase().includes(this.filtro.toLowerCase())
    );
  }

  abrirModalNuevaCategoria() {
    this.categoriaEditando = { name: '', description: '' };
    this.modalVisible = true;
  }

  abrirModalEditarCategoria(categoria: any) {
    this.categoriaEditando = { ...categoria }; // Clonar para no modificar en vivo
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
  }

  guardarCategoria() {
    if (!this.categoriaEditando.name || !this.categoriaEditando.description) return;

    if (this.categoriaEditando.id) {
      // Editar existente
      this.categoriaService.updateCategory(this.categoriaEditando).subscribe(() => {
        this.categorias.reload(); // recarga desde el servicio
        this.cerrarModal();
      });
    } else {
      // Crear nueva
      this.categoriaService.crearCategory(this.categoriaEditando).subscribe(() => {
        this.categorias.reload();
        this.cerrarModal();
      });
    }
  }

  categoriaAEliminar: any = null;

  confirmarEliminacion(categoria: any) {
    this.categoriaAEliminar = categoria;
    this.visibleEliminar = true;
  }

  cancelarEliminacion() {
    this.categoriaAEliminar = null;
    this.visibleEliminar = false;
  }

  eliminarConfirmado() {
    if (this.categoriaAEliminar) {
      this.categoriaService.deleteCategory(this.categoriaAEliminar.id).subscribe({
        next: () => {
          this.categorias.reload();
          this.categoriaAEliminar = null;
        },
        error: err => console.error('Error al eliminar la categoria:', err)
      });
      this.categoriaAEliminar = null;
    }
    this.visibleEliminar = false;
  }

}

