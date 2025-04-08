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
  name: string | null = localStorage.getItem('user');
  basket: Need[] = [];
  totalCost: number = 0;

  // Local map of quantities (key = Need ID)
  quantities: { [id: number]: number } = {};

  constructor(
    private router: Router,
    private cupboardService: CupboardService,
    private userService: UsersService
  ) {}

  ngOnInit(): void {
    if (!this.name) {
      this.router.navigate(['/']);
      return;
    }

    this.loadBasket();
  }

  loadBasket(): void {
    if (this.name) {
      this.userService.getUser(this.name).subscribe({
        next: (user: User) => {
          this.basket = user.basket;

          // Default quantity to 1 for each item
          this.basket.forEach(need => {
            this.quantities[need.id] = 1;
          });

          this.totalCostCalculation();
        },
        error: err => console.error('Failed to load user:', err)
      });
    }
  }

  onQuantityChange(needId: number, value: any): void {
    const quantity = Math.max(1, Number(value));
    this.quantities[needId] = quantity;
    this.totalCostCalculation();
  }

  totalCostCalculation(): void {
    let sum = 0;
    for (const need of this.basket) {
      const qty = this.quantities[need.id] || 1;
      sum += need.cost * qty;
    }
    this.totalCost = Math.round(sum * 100) / 100;
  }

  removeNeed(need: Need): void {
    if (!need || !this.name) {
      if (!need) console.error("There are no needs in your basket to remove.");
      if (!this.name) console.error("The User is not valid.");
      return;
    }

    this.userService.getUser(this.name).subscribe({
      next: (user: User) => {
        if (user.basket.length === 0) {
          console.error("Basket has 0 items.");
          return;
        }

        for (let i = 0; i < user.basket.length; i++) {
          if (user.basket[i].id === need.id) {
            user.basket.splice(i, 1);
            break;
          }
        }

        this.userService.updateUser(user).subscribe({
          next: (updated: User) => {
            this.basket = updated.basket;
            // Reset quantities to 1 for all items
            this.quantities = {};
            this.basket.forEach(n => (this.quantities[n.id] = 1));
            this.totalCostCalculation();
            alert("Removed Need " + need.id + " from " + this.name + " basket.");
          },
          error: (e) => {
            console.error("Error removing Need from User, ", e);
          }
        });
      },
      error: (e) => {
        console.error("Error removing Need.");
      }
    });
  }

  checkout(): void {
    if (!this.name) {
      console.error("The User is not valid.");
      return;
    }

    this.userService.getUser(this.name).subscribe({
      next: (user: User) => {
        if (user.basket.length === 0) {
          console.error("Basket has 0 items.");
          return;
        }

        console.log("Checking out basket:", this.basket);
        user.basket = [];

        this.userService.updateUser(user).subscribe({
          next: (updated: User) => {
            this.basket = updated.basket;
            this.quantities = {};
            this.totalCostCalculation();
            alert(this.name + " has checked out their basket.");
          },
          error: (e) => {
            console.error("Error checking out basket.", e);
          }
        });
      },
      error: (e) => {
        console.error("Error checking out.");
      }
    });
  }

  goToCupboard(): void {
    this.router.navigate(['/needs']);
  }
}
