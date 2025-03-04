import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BasketComponent } from './components/basket/basket.component';
import { CupboardComponent } from './components/cupboard/cupboard.component';
import { LoginComponent } from './components/login/login.component';
import { NeedComponent } from './components/need/need.component'
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NeedSearchComponent } from './components/need-search/need-search.component';

const routes: Routes = [
  {path:'', component: DashboardComponent},
  {path:'basket', component: BasketComponent},
  {path:'needs', component: CupboardComponent},
  {path:'login', component: LoginComponent},
  {path:'needs/:id', component: NeedComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
