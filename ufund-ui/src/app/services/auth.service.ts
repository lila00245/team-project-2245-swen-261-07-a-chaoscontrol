import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private url = "http://localhost:8080/auth";

  private httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

  constructor(private http: HttpClient) { }

  login(name: string, password: string): Observable<any> {
    console.log("Sending login request:", { name, password });

    return this.http.post<User>(`${this.url}/login`, { name, password }, this.httpOptions);
  }

  register(name: string, password: string): Observable<any> {
    console.log("Sending register request:", { name, password });

    return this.http.post<User>(`${this.url}/register`, { name, password }, this.httpOptions );
  }
  }
