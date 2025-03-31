import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {BehaviorSubject} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent {
    user = new BehaviorSubject<boolean>(false);
    constructor(
      private router: Router,
      private route: ActivatedRoute,
    ){}

    
  title = 'ufund-ui';

  logOut(): void{
    localStorage.removeItem('user')
    localStorage.removeItem('role')
    this.router.navigate([`/`])
  }
}
