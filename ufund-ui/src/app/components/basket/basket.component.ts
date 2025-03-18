import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CupboardService } from '../../services/cupboard.service';
import { UsersService } from '../../services/users.service';
import { Need } from '../../model/Need';
import { User } from '../../model/User';

@Component({
  selector: 'app-basket',
  standalone: false,
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {
  name:string|null = localStorage.getItem('user');
  basket:Need[] = [];
  constructor(
      private router: Router,
      private cupboardService: CupboardService,
      private userService: UsersService
    ){}
    
  ngOnInit(): void{
    if(!localStorage.getItem('user')){
      this.router.navigate([`/`])
    }
    this.loadBasket();
  }

  loadBasket():void {
    if (this.name) {
      this.userService.getUser(this.name).subscribe({
        next: (user: User) => { this.basket = user.basket; },
        error: (e) => { console.error("Error loading user."); }
      });
    }
  }
}
