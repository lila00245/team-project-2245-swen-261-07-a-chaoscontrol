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

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private authService: AuthService,
  ) {}

  register(name:string, password:string) {
    const user = { name, password };

    console.log("regitsering with:", {name, password});

    this.authService.register(user.name, user.password).subscribe(response => {
      console.log('Registration successful', response);
      this.router.navigate(['/login']);
    }, error => {
      console.error('Registration failed', error);
    }

      // next: (user) => {
      //   this.router.navigate(['/login'])  // redirect to login after creating account
      // }, error: (err) => {
      //   console.error(err);
      // }}
    );
  }
}
