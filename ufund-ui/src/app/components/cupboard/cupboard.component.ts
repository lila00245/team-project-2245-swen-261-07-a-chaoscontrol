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
  constructor(
    private cupboardService: CupboardService,
  ){}

  ngOnInit(): void{
    this.getNeeds();
  }

  getNeeds(): void{
    this.cupboardService.getNeeds().subscribe(needs => this.needs = needs)
  }

  add(need: Need):void{
    this.cupboardService.createNeed(need).subscribe(need => this.needs.push(need))
  }

  // searchNeeds(): void{
  //   const name = this.route.snapshot.paramMap.get('name')
  //   this.cupboardService.searchNeeds(name).subscribe(needs=> this.needs = needs)
  // }


}
