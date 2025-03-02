import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Need } from '../model/Need';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CupboardService {
  private url = "http://localhost:8080/needs"
  
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

  searchNeeds(name: string):Observable<Need[]>{
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


}
