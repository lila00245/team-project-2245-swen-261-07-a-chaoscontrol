import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CupboardComponent } from './components/cupboard/cupboard.component';
import { BasketComponent } from './components/basket/basket.component';
import { HttpClientModule } from '@angular/common/http';
import { NeedComponent } from './components/need/need.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NeedSearchComponent } from './components/need-search/need-search.component';
import { CreateComponent } from './components/create/create.component';
import { LeaderboardComponent } from './components/leaderboard/leaderboard.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    CupboardComponent,
    BasketComponent,
    NeedComponent,
    DashboardComponent,
    NeedSearchComponent,
    CreateComponent,
    LeaderboardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
