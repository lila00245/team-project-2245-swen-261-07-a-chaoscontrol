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
  
  goBack(): void {
    this.location.back();
  }

  remove(): void {
    // Check if user logged in is a manager
    // If they are not, return
    // If they are, remove the need from the cupboard and go back. 
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10)
    this.cupboardService.deleteNeed(id)
    //this.goBack();
  }

}
