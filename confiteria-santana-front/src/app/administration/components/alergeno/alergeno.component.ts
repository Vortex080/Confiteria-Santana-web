import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { rxResource } from '@angular/core/rxjs-interop';
import { ConfirmDialogComponent } from "../../../shared/components/confirm-dialog/confirm-dialog.component";
import { AlergensService } from '../../../shared/service/Alergens.service';
import { ImageCropperComponent, ImageCroppedEvent } from 'ngx-image-cropper';

@Component({
  selector: 'app-alergeno',
  standalone: true,
  imports: [FormsModule, CommonModule, ConfirmDialogComponent, ImageCropperComponent],
  templateUrl: './alergeno.component.html',
  styleUrl: './alergeno.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AlergenoComponent {
  constructor(private alergenoService: AlergensService) { }

  visibleEliminar = false;
  filtro: string = '';
  modalVisible: boolean = false;
  alergenoEditando: any = null;

  alergenos = rxResource({
    loader: () => this.alergenoService.getallAlergen(),
  });

  get alergenosFiltrados() {
    return this.alergenos.value()?.filter(a =>
      a.name.toLowerCase().includes(this.filtro.toLowerCase())
    );
  }

  abrirModalNuevoAlergeno() {
    this.alergenoEditando = { name: '', photo: '' };
    this.croppedImage = null;
    this.imageChangedEvent = null;
    this.modalVisible = true;
  }

  abrirModalEditarAlergeno(alergeno: any) {
    this.alergenoEditando = { ...alergeno };
    this.croppedImage = alergeno.photo || null;
    this.imageChangedEvent = null;
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
  }

  guardarAlergeno() {
    if (!this.alergenoEditando.name) return;

    console.log('Guardando alérgeno:', this.alergenoEditando);

    if (this.alergenoEditando.id) {
      this.alergenoService.updateAlergen(this.alergenoEditando).subscribe(() => {
        this.alergenos.reload();

        this.cerrarModal();
      });
    } else {
      this.alergenoService.crearAlergen(this.alergenoEditando).subscribe(() => {
        this.alergenos.reload();
        this.cerrarModal();
      });
    }
  }

  alergenoAEliminar: any = null;

  confirmarEliminacion(alergeno: any) {
    this.alergenoAEliminar = alergeno;
    this.visibleEliminar = true;
  }

  cancelarEliminacion() {
    this.alergenoAEliminar = null;
    this.visibleEliminar = false;
  }

  eliminarConfirmado() {
    if (this.alergenoAEliminar) {
      this.alergenoService.deleteAlergen(this.alergenoAEliminar.id).subscribe({
        next: () => {
          this.alergenos.reload();
          this.alergenoAEliminar = null;
        },
        error: err => console.error('Error al eliminar el alérgeno:', err)
      });
    }
    this.visibleEliminar = false;
  }

  imageChangedEvent: any = null;
  croppedImage: string | null = null;

  fileChangeEvent(event: any): void {
    const file = event.target.files?.[0];
    if (!file) return;

    const validTypes = ['image/jpeg', 'image/png', 'image/jpg'];
    if (!validTypes.includes(file.type)) {
      alert('Solo se permiten imágenes JPG o PNG');
      return;
    }

    this.imageChangedEvent = event;

    const reader = new FileReader();
    reader.onload = () => {
      const base64 = reader.result as string;
      this.croppedImage = base64;
      this.alergenoEditando.photo = base64; // Guarda directamente por si no se recorta
    };
    reader.readAsDataURL(file);
  }


  imageCropped(event: ImageCroppedEvent) {
    if (event.base64) {
      this.croppedImage = event.base64;
      this.alergenoEditando.photo = event.base64;
    }
  }
}
