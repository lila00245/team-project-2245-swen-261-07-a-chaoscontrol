import { Component } from '@angular/core';
import { Need } from '../../model/Need';
import { CupboardService } from '../../services/cupboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NeedSearchComponent } from '../need-search/need-search.component';
import { LoginComponent } from '../login/login.component';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-cupboard',
  standalone: false,
  templateUrl: './cupboard.component.html',
  styleUrl: './cupboard.component.css'
})

export class CupboardComponent {
  needs: Need[] = [];
  name?:string;
  userAdmin?: Boolean
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cupboardService: CupboardService,
    private userService: UsersService
  ){}

  ngOnInit(): void{
    if(!localStorage.getItem('user')){
      this.router.navigate([`/`])
    }
    this.userAdmin = localStorage.getItem('role') === "admin"
    console.log(localStorage.getItem('user'))
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

  // searchNeeds(name: string):void{
  //   this.router.navigate([`/needs`])
  //       .then(() => {
  //         window.location.reload();
  //       });
  // }

  // add(need: Need):void{
  //   this.cupboardService.createNeed(need).subscribe(need => this.needs.push(need))
  // }

  logOut(): void{
    localStorage.removeItem('user')
    localStorage.removeItem('role')
    this.router.navigate([`/`])
  }
}
