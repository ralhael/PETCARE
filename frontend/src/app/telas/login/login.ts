import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private fb = inject(FormBuilder);
  private router = inject(Router);

  loginForm = this.fb.group({
    cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
    senha: ['', [Validators.required, Validators.minLength(6)]]
  });

  onSubmit() {
    if (this.loginForm.valid) {
      const { cpf } = this.loginForm.value;
      // Simulação: se o CPF termina em '00' redireciona para Admin, caso contrário Cliente
      cpf?.endsWith('00') ? this.router.navigate(['/admin']) : this.router.navigate(['/cliente']);
    }
  }
}
