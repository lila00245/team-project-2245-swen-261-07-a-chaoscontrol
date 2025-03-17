import { Component, OnInit } from '@angular/core';
import{ Need } from '../../model/Need';
import {CupboardService} from '../../services/cupboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-need',
  standalone: false,
  templateUrl: './need.component.html',
  styleUrl: './need.component.css'
})
export class NeedComponent {

  need: Need | undefined;
  constructor(
    private router: Router,
    private cupboardService: CupboardService,
    private route: ActivatedRoute,
    private location: Location
  ){}

  ngOnInit(): void{
    if(!localStorage.getItem('user')){
      this.router.navigate([`/`])
    }
    this.getNeed();
  }

  getNeed(): void{
      const id = parseInt(this.route.snapshot.paramMap.get('id')!,10)
      this.cupboardService.getNeed(id).subscribe(need => this.need = need) 
  }

  /**
   * Add need to basket from UI
   * 
   * @author Vlad
   */
  addNeedToBasket(): void {
    console.log('Adding to basket', this.need);
    const username = localStorage.getItem('user');

    // Error checking
    if (!username) { console.error("User must be logged in."); }
    if (!this.need) { console.error("Need must exist."); }

    if (username && this.need) { // TODO: && user.status == helper
      this.cupboardService.addNeedToBasket(username, this.need).subscribe({
        next: (updated) => { console.log("Added to basket, ", updated); }, // TODO: update UI to show the new basket addition
        error: (e) => { console.error("Error adding to basket."); }
      });
    }
  }
  
  goBack(): void {
    this.location.back();
  }

}
