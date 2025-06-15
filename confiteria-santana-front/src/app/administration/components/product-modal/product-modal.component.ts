import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ImageCropperComponent, ImageCroppedEvent } from 'ngx-image-cropper';
import { Product } from '../../../shared/interface/product';
import { rxResource } from '@angular/core/rxjs-interop';
import { CategoryService } from '../../../shared/service/Category.service';
import { AlergensService } from '../../../shared/service/Alergens.service';
import { Alergen } from '../../../shared/interface/alergen';
import { Photo } from '../../../shared/interface/photo';
import { ProductosService } from '../../../shared/service/productos.service';

@Component({
  selector: 'app-product-modal',
  standalone: true,
  imports: [CommonModule, FormsModule, ImageCropperComponent],
  templateUrl: './product-modal.component.html',
})
export class ProductModalComponent {
  @Input() productToEdit: Product | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() productSaved = new EventEmitter<Product>();

  name = '';
  description = '';
  price!: number;
  unit = '';
  categoryId: number | null = null;
  croppedImage: string | null = null;
  ngOnChanges(changes: SimpleChanges) {
    if (changes['productToEdit'] && this.productToEdit) {
      const prod = this.productToEdit;
      this.name = prod.name;
      this.description = prod.description;
      this.price = prod.price;
      this.unit = prod.unit;
      this.categoryId = prod.category?.id ?? null;
      this.croppedImage = prod.photos?.[0]?.url || null;

      this.alergenIds = prod.alergens?.map(a => a.id) || [];
      this.selectedAlergenMap = Object.fromEntries(this.alergenIds.map(id => [id, true]));
    }

    // Si se cambia de edición a creación
    if (changes['productToEdit'] && !this.productToEdit) {
      this.name = '';
      this.description = '';
      this.price = 0;
      this.unit = '';
      this.categoryId = null;
      this.croppedImage = null;
      this.selectedAlergenMap = {};
    }
  }

  alergenIds: number[] = [];
  selectedAlergenMap: Record<number, boolean> = {};

  imageChangedEvent: any = '';
  visible = false;

  categories = rxResource({ loader: () => this.categoryService.getallCategory() });
  alergens = rxResource({ loader: () => this.alergenService.getallAlergen() });

  constructor(
    private categoryService: CategoryService,
    private alergenService: AlergensService,
    private productService: ProductosService
  ) { }

  abrir() {
    this.visible = true;

    if (this.productToEdit) {
      this.name = this.productToEdit.name;
      this.description = this.productToEdit.description;
      this.price = this.productToEdit.price;
      this.unit = this.productToEdit.unit;
      this.categoryId = this.productToEdit.category?.id ?? null;
      this.croppedImage = this.productToEdit.photos?.[0]?.url || null;

      this.alergenIds = this.productToEdit.alergens?.map(a => a.id) || [];
      this.selectedAlergenMap = Object.fromEntries(this.alergenIds.map(id => [id, true]));
    } else {
      this.name = '';
      this.description = '';
      this.price = 0;
      this.unit = '';
      this.categoryId = null;
      this.croppedImage = null;
      this.selectedAlergenMap = {};
      this.alergenIds = [];
    }

    this.imageChangedEvent = '';
  }

  cerrar() {
    this.close.emit();
    this.visible = false;
  }

  fileChangeEvent(event: any): void {
    const file = event.target.files?.[0];
    if (!file) return;

    const validTypes = ['image/jpeg', 'image/png', 'image/jpg'];
    if (!validTypes.includes(file.type)) {
      alert('Solo se permiten imágenes JPG o PNG');
      return;
    }
    this.imageChangedEvent = event;
  }

  imageCropped(event: ImageCroppedEvent) {
    if (event.base64) {
      this.croppedImage = event.base64;
    } else if (event.blob) {
      const reader = new FileReader();
      reader.onloadend = () => (this.croppedImage = reader.result as string);
      reader.readAsDataURL(event.blob);
    }
  }

  syncAlergenIds() {
    this.alergenIds = Object.keys(this.selectedAlergenMap)
      .filter(id => this.selectedAlergenMap[+id])
      .map(id => +id);
  }

  toggleAlergen(ag: Alergen) {
    this.selectedAlergenMap[ag.id] = !this.selectedAlergenMap[ag.id];
    this.syncAlergenIds();
  }

  removeAlergen(id: number) {
    delete this.selectedAlergenMap[id];
    this.syncAlergenIds();
  }

  get selectedAlergens() {
    return this.alergens.value()?.filter(ag => this.selectedAlergenMap[ag.id]) || [];
  }

  isSelected(id: number) {
    return !!this.selectedAlergenMap[id];
  }

  submit() {
    this.syncAlergenIds();

    if (!this.name || !this.description || !this.price || !this.unit || !this.categoryId || !this.croppedImage) {
      alert('Por favor, completa todos los campos requeridos.');
      return;
    }

    const foto: Photo = {
      id: 0,
      url: this.croppedImage!,
      altText: `${this.name} - foto del producto`
    };

    const categoriaSeleccionada = this.categories.value()?.find(c => c.id === Number(this.categoryId));
    if (!categoriaSeleccionada) {
      console.error('Categoría no encontrada', categoriaSeleccionada);
      return;
    }

    const producto: Product = {
      id: this.productToEdit?.id ?? 0,
      name: this.name,
      description: this.description,
      price: this.price,
      unit: this.unit,
      category: categoriaSeleccionada,
      alergens: this.alergenIds.map(id => ({ id } as Alergen)),
      photos: [foto],
    };

    const accion = this.productToEdit
      ? this.productService.updateProduct(producto)
      : this.productService.crearProduct(producto);

    accion.subscribe({
      next: () => {
        this.cerrar();
        this.productSaved.emit(producto);
      },
      error: (err) => alert('Error al guardar: ' + err.message)
    });
  }
}
