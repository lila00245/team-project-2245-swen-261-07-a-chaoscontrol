import { Component } from '@angular/core';
import { Need } from '../../model/Need';
import { CupboardService } from '../../services/cupboard.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cupboard',
  standalone: false,
  templateUrl: './cupboard.component.html',
  styleUrl: './cupboard.component.css'
})
export class CupboardComponent {
  needs: Need[] = [];
  name?:string;
  constructor(
    private route: ActivatedRoute,
    private cupboardService: CupboardService,
  ){}

  ngOnInit(): void{
    this.route.queryParams.subscribe(params => {
    this.name = params['name'];})
    this.getNeeds();
    
  }

  getNeeds(): void{ 
    if(this.name!= null){
      console.log(this.name)
      this.cupboardService.searchNeeds(this.name).subscribe(needs => this.needs = needs)
      console.log(this.needs)
    }else{
      this.cupboardService.getNeeds().subscribe(needs => this.needs = needs)
    }
  }

  add(need: Need):void{
    this.cupboardService.createNeed(need).subscribe(need => this.needs.push(need))
  }
}
