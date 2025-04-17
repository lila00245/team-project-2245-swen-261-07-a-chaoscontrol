import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CupboardService } from '../../services/cupboard.service';
import { UsersService } from '../../services/users.service';
import { Need } from '../../model/Need';
import { User } from '../../model/User';

/**
 * Basket component
 */
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
  quantities: { [id: number]: number } = {};

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

  /**
   * Gets the current user, accesses their basket,
   * and renders each Need and the corresponding
   * total cost calculation.
   * 
   * No explicit events are returned. Errors include
   * User update failures and Unexpected errors.
   */
  loadBasket(): void {
    if (this.name) {
      this.userService.getUser(this.name).subscribe({
        next: (user: User) => {
          this.basket = user.basket;
          for (let i = this.basket.length - 1; i >= 0; i--) {
            const item = this.basket[i];
            this.cupboardService.getNeed(item.id).subscribe({
              next: (result) => {
                this.basket = user.basket
                this.totalCostCalculation()
              },
              error: (err) => {
                if (err.status === 404) {
                  user.basket.splice(i, 1);
                  this.userService.updateUser(user).subscribe({
                    next: (updated: User) => {
                      this.basket = updated.basket;
                      this.totalCostCalculation();
                    },
                    error: (e) => {
                      console.error('Error updating user after removing missing Need:', e);
                    }
                  });
                } else {
                  console.error('Unexpected error:', err);
                  this.basket = user.basket;
                  this.totalCostCalculation();
                }
              }
            });
          }
        },
        error: err => console.error('Failed to load user:', err)
      });
    }
  }

  /**
   * Sums up all columns from basket
   */
  totalCostCalculation(): void {
    let sum = 0;
    for (const need of this.basket) {
      const qty = this.quantities[need.id] || 1;
      sum += need.cost * qty;
    }
    this.totalCost = Math.round(sum * 100) / 100;
  }

  /**
   * Takes in selected Need and current user,
   * filters out item, and passes in updated
   * user to the UserDAO.
   */
  removeNeed(need: Need):void {
    // case where needs or the user don't exist
    if (!need || !this.name){
      if (!need) { console.error("There are no needs in your basket to remove."); }
      if (!this.name) { console.error("The User is not valid."); }
    }
    if (need && this.name) {
      this.userService.getUser(this.name).subscribe({
        next: (user: User) => {
          console.log("Removing need from list, ", need);
          if (user.basket.length === 0) { console.error("Basket has 0 items."); return; } // case where there are 0 items in basket

          for (let i = 0; i < user.basket.length; i++) {
            if (user.basket[i].id === need.id) {
              user.basket.splice(i, 1); // find first occurrence of the Need
              break;
            }
          }

          // run an update to the UserDAO
          this.userService.updateUser(user).subscribe({
            next: (updated: User) => {
              this.basket = updated.basket;// re-set Needs basket
              this.totalCostCalculation(); // re-run cost calculation with updated basket
              alert("Removed Need " + need.id + " from " + this.name + " basket.");
            },
            error: (e) => { console.error("Error removing Need from User, ", e); }
          });
        },
        error: (e) => { console.error("Error removing Need."); }
      });
    }
  }

  /**
   * Takes in current user and empties the basket,
   * passing in updated user to the UserDAO.
   */
  checkout():void {
    // checks if user exists, if not gives an error
    if (!this.name) {
      console.error("The User is not valid."); 
    } 
    else {
      this.userService.getUser(this.name).subscribe({
        next: (user: User) => {
          // case where there are 0 items in basket
          if (user.basket.length === 0) { 
            console.error("Basket has 0 items."); 
            return; 
          } 
          console.log("checking out basket, ", this.basket);
          user.basket = []; // empty the basket
          user.totalFunding+= this.totalCost
          // run an update to the UserDAO
          this.userService.updateUser(user).subscribe({
            next: (updated: User) => {
              this.basket = updated.basket;// re-set Needs basket
              this.totalCostCalculation(); // re-run cost calculation with updated basket
              alert(this.name + " has checked out their basket.");
            },
            error: (e) => { console.error("Error removing Need from User, ", e); }
          });
        },
        error: (e) => { console.error("Error removing Need."); }
      });
    }
  }
  onQuantityChange(needId: number, value: any): void {
    const quantity = Math.max(1, Number(value));
    this.quantities[needId] = quantity;
    this.totalCostCalculation();
  }

  /**
   * Navigates to the Needs endpoint
   */
  goToCupboard():void{
    this.router.navigate(['/needs']);
  }
}