import { Component, OnInit } from '@angular/core';
import{ Need } from '../../model/Need';
import {CupboardService} from '../../services/cupboard.service';
import { ActivatedRoute } from '@angular/router';
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
    private cupboardService: CupboardService,
    private route: ActivatedRoute,
    private location: Location
  ){}

  ngOnInit(): void{
    this.getNeed();
  }

  getNeed(): void{
      const id = parseInt(this.route.snapshot.paramMap.get('id')!,10)
      this.cupboardService.getNeed(id).subscribe(need => this.need = need) 
  }
  
  goBack(): void {
    this.location.back();
  }

}
