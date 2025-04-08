import { Component } from '@angular/core';
import { CupboardService } from '../../services/cupboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-create',
  standalone: false,
  templateUrl: './create.component.html',
  styleUrl: './create.component.css'
})
export class CreateComponent {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private cupboardService: CupboardService
  ) {}

  createNeed(name: string, foodGroup: string, price: string){
    this.cupboardService.createNeed(name, foodGroup, price).subscribe();
    this.router.navigate(["/needs"]);
  }
}
