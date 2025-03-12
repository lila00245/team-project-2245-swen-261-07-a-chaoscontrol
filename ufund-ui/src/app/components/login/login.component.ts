import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { Location } from '@angular/common'
import { User } from '../../model/User';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user?:User
  message:string = "Enter Username!"

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private userService: UsersService,
) {}

  getUser(name: string): void {
    this.userService.getUser(name).subscribe(user => {
      if (user) {
        this.router.navigate(['/needs']).then(() => {
          window.location.reload();
        });
      } else {
        this.message = "Incorrect username, please enter again.";
      }
    });
  }
}
