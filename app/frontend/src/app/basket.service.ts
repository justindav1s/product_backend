import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { Basket } from './basket';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class BasketService {

  private basketUrl = 'http://inventory-amazin-dev.apps.ocp.datr.eu/basket/';
  constructor() { }

  createBasket(): Observable<Basket> {
    const url = `${this.basketUrl}/create`;
    return this.http.get<Basket>(url)
    .pipe(
      tap(products => this.log(`fetched basket`)),
      catchError(this.handleError<Basket>(`createBasket`))
    );
  }
}
