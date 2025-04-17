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

/**
 * Gets the user and sets both local storage and the currentUser
 * status before navigating to the Needs dashboard.
 * @param name name of the user
 * @param password password of the associated user
 */
getUser(name: string, password: string): void {
    this.userService.getUser(name)
    .pipe(catchError((ex: any, caught: Observable<User>) => {
      this.message.next("Failed to login: "+ ex.status)
      return of(undefined);
    }))
    .subscribe(user => {
      if (user && user.password === password){
        localStorage.setItem('user',user.name)
        localStorage.setItem('role',user.role)

        this.userService.setCurrentUser(user)
        this.router.navigate(['/needs']).then(() => {
          window.location.reload();
        });
      } else {
        this.message.next("Incorrect username or password, please enter again.");
      }
    });
  }

}
