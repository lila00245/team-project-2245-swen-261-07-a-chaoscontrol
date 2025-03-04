import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Need } from '../../model/Need';
import { CupboardService } from '../../services/cupboard.service';

@Component({
  standalone:false,
  selector: 'app-need-search',
  templateUrl: './need-search.component.html',
  styleUrls: [ './need-search.component.css' ]
})
export class NeedSearchComponent implements OnInit {
  needs$!: Observable<Need[]>;
  private searchTerms = new Subject<string>();

  constructor(private cupboardService: CupboardService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.needs$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => {
        if(term!=""){
          return this.cupboardService.searchNeeds(term)
        } else {
          return this.cupboardService.searchNeeds("+")
        }
      }),
    );
  }
}