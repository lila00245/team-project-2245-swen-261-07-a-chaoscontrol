import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { User } from '../../model/User';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location
  ){}

  goToLogin():void{
    this.router.navigate(['/login']);
  }

  goToRegister():void{
    this.router.navigate(['/register']);
  }
}
