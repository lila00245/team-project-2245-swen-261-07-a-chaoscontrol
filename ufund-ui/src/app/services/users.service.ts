import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  currentUser?: User
  loggedInStatus = new BehaviorSubject<boolean>(false);
  url = "http://localhost:8080/users"

  constructor(private http: HttpClient) { }

  getUser(name: string):Observable<User>{
    return this.http.get<User>(`${this.url}/${name}`,this.httpOptions)
  }

  getUsers():Observable<User[]>{
    return this.http.get<User[]>(this.url,this.httpOptions)
  }

  createUser(user: User):Observable<User>{
    return this.http.post<User>(this.url,user,this.httpOptions)
  }

  updateUser(user: User):Observable<User>{
    return this.http.put<User>(this.url,user,this.httpOptions)
  }

  deleteUser(name: string):Observable<User>{
    return this.http.delete<User>(`${this.url}/${name}`,this.httpOptions)    
  }

  setCurrentUser(user:User): void{
    this.currentUser = user;
    this.loggedInStatus.next(true);
  }
  
  getCurrentUser():User | undefined{
    return this.currentUser;
  }
}
