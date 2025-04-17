import { Component, OnInit } from '@angular/core';
import{ Need } from '../../model/Need';
import {CupboardService} from '../../services/cupboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-need',
  standalone: false,
  templateUrl: './need.component.html',
  styleUrl: './need.component.css'
})
export class NeedComponent {

  need: Need | undefined;
  userAdmin?: Boolean
  edit: Boolean = true;

  constructor(
    private router: Router,
    private cupboardService: CupboardService,
    private route: ActivatedRoute,
    private location: Location,
    private usersService: UsersService
  ){}

  /**
   * Get current user and check role
   */
  ngOnInit(): void{
    if(!localStorage.getItem('user')){
      this.router.navigate([`/`])
    }

    this.userAdmin = localStorage.getItem('role') == "admin"
    console.log(localStorage.getItem('role'))
    console.log("User admin status: " + this.userAdmin)
    this.getNeed();
  }

  /**
   * Get the listed need by id
   */
  getNeed(): void{
      const id = parseInt(this.route.snapshot.paramMap.get('id')!,10)
      this.cupboardService.getNeed(id).subscribe(need => this.need = need) 
  }

  /**
   * Sets edit mode
   */
  editMode():void{
    if(this.edit){
      this.edit = false;
    } else {
      this.edit = true;
    }
  }

  /**
   * Changes a Need's attributes
   * @param name Need's new name
   * @param foodGroup the food group associated with the updated Need
   * @param price the new price associated with the updated Need
   */
  editNeed(name:string, foodGroup:string, price:string):void{
    if(this.need){
      this.cupboardService.updateNeed(this.need?.id, name, foodGroup, price).subscribe()
      this.router.navigate(["needs"])
    }
    }

  /**
   * Add need to basket from UI
   */
  addNeedToBasket(): void {
    console.log('Adding to basket', this.need);
    const username = localStorage.getItem('user');

    // Error checking
    if (!username) { console.error("User must be logged in."); }
    if (!this.need) { console.error("Need must exist."); }

    if (username && this.need) { // TODO: && user.status == helper
      let newNeed = this.need;
      this.cupboardService.addNeedToBasket(username, this.need).subscribe({
        next: (updated) => { alert("Successfully added " + newNeed.name + " to basket."); },
        error: (e) => { console.error("Error adding to basket,", e); }
      });
    }
  }
  
  /**
   * Return option
   */
  goBack(): void {
    this.location.back();
  }

  /**
   * Check if user logged in is a manager
   * If they are not, return
   * If they are, remove the need from the cupboard and go back
   */
  remove(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10)
    this.cupboardService.deleteNeed(id).subscribe({
      next: (v) => {
        console.log("Need " + id + " deleted")
        this.router.navigate(['/needs'])
      },
      error: (v) => console.log("Could not delete need " + id)
    })
    //this.goBack();
  }

}
