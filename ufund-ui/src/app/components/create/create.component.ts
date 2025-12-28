import { Component } from '@angular/core';
import { CupboardService } from '../../services/cupboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { catchError, of } from 'rxjs';


@Component({
  selector: 'app-create',
  standalone: false,
  templateUrl: './create.component.html',
  styleUrl: './create.component.css'
})
export class CreateComponent {
  message = "Create Need"

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private cupboardService: CupboardService
  ) {}

  ngOnInit(): void{
    if(localStorage.getItem('role')!='admin'){
      this.router.navigate([`/needs`])
    }
  }

  /**
   * Creates a Need object
   * @param name the name of the Need
   * @param foodGroup the food group to put the Need into
   * @param price the corresponding price of each unit of the Need
   */
  createNeed(name: string, foodGroup: string, price: string){
    
    this.cupboardService.createNeed(name, foodGroup, price)
    .pipe(catchError((ex,ob)=>{
      this.message = "Need Already in Cupboard"
      return of();
    } )
    ).subscribe( status => this.router.navigate(["/needs"]));
    
  }
}
