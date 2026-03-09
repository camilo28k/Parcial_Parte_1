import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  constructor(private snack: MatSnackBar) {}

  success(message: string, action: string = 'Cerrar', duration: number = 3000) {
    this.snack.open(message, action, { duration });
  }

  error(message: string, action: string = 'Cerrar', duration: number = 3000) {
    this.snack.open(message, action, { duration });
  }
}
