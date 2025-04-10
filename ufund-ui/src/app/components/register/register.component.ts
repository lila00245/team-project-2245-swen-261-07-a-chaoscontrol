import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { Location } from '@angular/common'
import { User } from '../../model/User';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent {
  message = "Create a New Account"
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private authService: AuthService,
  ) {}

  register(name:string, password:string) {
    let value = document.getElementsByName('option')
    let role = ""
    if((value[0] as HTMLInputElement).checked){
      role = "admin"
    } else {
      role = "user"
    }

    const user = { name, password, role };
    
    console.log("regitsering with:", {name, password, role});

    this.authService.register(user.name, user.password, user.role).subscribe(response => {
      console.log('Registration successful', response);
      this.router.navigate(['/login']);
    }, error => {
      this.message = "Username In Use"
      console.error('Registration failed', error);
    }
    );
  }
}
