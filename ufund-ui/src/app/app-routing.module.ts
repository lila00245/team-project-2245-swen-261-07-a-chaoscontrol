import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BasketComponent } from './components/basket/basket.component';
import { CupboardComponent } from './components/cupboard/cupboard.component';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {path:'basket', component: BasketComponent},
  {path:'cupboard', component: CupboardComponent},
  {path:'login', component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
