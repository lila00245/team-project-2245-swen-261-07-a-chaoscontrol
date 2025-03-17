import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { Location } from '@angular/common'
import { User } from '../../model/User';
import { UsersService } from '../../services/users.service';
import { BehaviorSubject, catchError, Observable, of } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user?:User
  message = new BehaviorSubject<String>("Enter Username and Password!");

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private userService: UsersService,
) {}

  login(name: string, password: string): void {
    console.log("logging in with: ", {name, password});

    this.userService.getUser(name
      .pipe(catchError((ex:any, caught: Observable<User>) => {
        this.message.next("Failed to login: " + ex.status)
        return of(undefined);
    }))
    .subscribe(user => {
      if (user) {
        if (user.password === password) {  // checking password
          this.router.navigate(['/needs']);
        } else {
          this.message.next = "Incorrect password, please try again.";
        }
      } else {
        this.message.next = "Incorrect username, please enter again.";
      }
    });
  }
}
