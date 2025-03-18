import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Need } from '../model/Need';
import { Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class CupboardService {
  private base_url = "http://localhost:8080"
  private url = this.base_url + "/needs"
  
  private httpOptions = {
    headers: new HttpHeaders({'Conent-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {}

  getNeeds():Observable<Need[]>{
    return this.http.get<Need[]>(this.url,this.httpOptions)
  }

  getNeed(id:number):Observable<Need>{
    return this.http.get<Need>(`${this.url}/${id}`, this.httpOptions)
  }

  searchNeeds(name:string):Observable<Need[]>{
    return this.http.get<Need[]>(`${this.url}/?name=${name}`, this.httpOptions)
  }

  createNeed(need: Need):Observable<Need>{
    return this.http.post<Need>(this.url,need,this.httpOptions)
  }

  updateNeed(need: Need):Observable<Need>{
    return this.http.put<Need>(this.url,need,this.httpOptions)
  }

  deleteNeed(id:number):Observable<Need>{
    return this.http.delete<Need>(`${this.url}/${id}`,this.httpOptions)
  }

  /**
   * Add need to basket given the user and their Need
   * @author Vlad
   */
  addNeedToBasket(username: string, need: Need):Observable<User> {
    return this.http.post<User>(`${this.base_url}/users/${username}/basket`, need);
  }
  
}
