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
  totalCost:number = 0;
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
        next: (user: User) => {
          this.basket = user.basket;    // set user's basket to variable
          this.totalCostCalculation();  // initialize the basket's sum
        },
        error: (e) => { console.error("Error loading user."); }
      });
    }
  }

  /**
   * Sums up all columns from basket
   * 
   * @author Vlad
   */
  totalCostCalculation():void {
    if (this.name) {
      let sum = 0;
      for (const need of this.basket) {
        sum += 100 * need.cost;
      }
      this.totalCost = sum / 100;
    }
  }

  /**
   * Takes in selected Need and current user, filters out item, and passes in updated user to the UserDAO
   * 
   * @author Vlad
   */
  removeNeed(need: Need):void {
    if (need && this.name) {
      this.userService.getUser(this.name).subscribe({
        next: (user: User) => {
          console.log("Removing need from list, ", need);
          user.basket = user.basket.filter(item => item.id !== need.id); // filter out item to be removed by id

          // run an update to the UserDAO
          this.userService.updateUser(user).subscribe({
            next: (updated: User) => {
              this.basket = updated.basket;// re-set Needs basket
              this.totalCostCalculation(); // re-run cost calculation with updated basket
            },
            error: (e) => { console.error("Error removing Need from User, ", e); }
          });
        },
        error: (e) => { console.error("Error removing Need."); }
      });
    }
  }

}
