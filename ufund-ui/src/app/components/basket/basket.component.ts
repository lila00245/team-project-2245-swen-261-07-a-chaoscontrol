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
   * Takes in current user and empties the basket, passing in updated user to the UserDAO
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


  goToCupboard():void{
    this.router.navigate(['/needs']);
  }

}
