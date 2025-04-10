import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import { UsersService } from './services/users.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent {
    user = new BehaviorSubject<boolean>(false);
    userLoggedIn: boolean = false;
    userAdmin: boolean = false;
    constructor(
      private router: Router,
      private route: ActivatedRoute,
      private usersService: UsersService
    ){}

    ngOnInit(): void{
      this.usersService.loggedInStatus.asObservable().subscribe((status) => {
        console.log(this.userLoggedIn);
        this.userLoggedIn = localStorage.getItem('user') != null;
        this.userAdmin = localStorage.getItem('role') === "admin";
      })
    }
    
  title = 'ufund-ui';

  logOut(): void{
    localStorage.removeItem('user')
    localStorage.removeItem('role')
    this.userLoggedIn = false;
    this.router.navigate([`/`])
    location.reload()
  }
}
