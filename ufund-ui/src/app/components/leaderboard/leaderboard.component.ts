import { Component } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { CupboardService } from '../../services/cupboard.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../model/User';

@Component({
  selector: 'app-leaderboard',
  standalone: false,
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})


export class LeaderboardComponent {
  users: User[] = [];
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cupboardService: CupboardService,
    private userService: UsersService
  ){}

  /**
   * Loads current user and runs helper functions
   */
  ngOnInit(): void{
    if(!localStorage.getItem('user')){
      this.router.navigate([`/`])
    }
    this.getUsers();
  }

  /**
   * Get users from the UserService, sort
   * them by their total funding, and
   * return a small selection of Users.
   */
  getUsers(): void{
    this.userService.getUsers().subscribe(users=>{
      this.users=users.filter(user => user.role != 'admin')
      this.users.sort((a,b)=>b.totalFunding - a.totalFunding)
      this.users = this.users.slice(0,10)
    });
  }
  

}
