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
  message:string = "Login with Username and Password!"

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private userService: UsersService,
) {}

  login(name: string, password: string): void {
    console.log("logging in with: ", {name, password} )
    this.userService.getUser(name).subscribe(user => {
      if (user) {
        this.router.navigate(['/needs']);
      } else {
        this.message = "Incorrect username, please enter again.";
      }
    });
  }
}
