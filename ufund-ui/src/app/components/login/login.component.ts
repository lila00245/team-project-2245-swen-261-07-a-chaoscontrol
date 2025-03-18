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
  message = new BehaviorSubject<String>("Enter Username!");

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private userService: UsersService,
    
) {}

getUser(name: string, password: string): void {
  // Changed by Vladislav Usatii on 03 04 25: Refactored
  // to wait for async result before routing to /needs
    this.userService.getUser(name)
    .pipe(catchError((ex: any, caught: Observable<User>) => {
      this.message.next("Failed to login: "+ ex.status)
      return of(undefined);
    }))
    .subscribe(user => {
      if (user && user.password === password){
        localStorage.setItem('user',user.name)
        localStorage.setItem('role',user.role)
        console.log(localStorage.getItem('role'))
        this.userService.setCurrentUser(user)
        this.router.navigate(['/needs']).then(() => {
          window.location.reload();
        });
      } else {
        this.message.next("Incorrect username, please enter again.");
      }
    });
  }

}
