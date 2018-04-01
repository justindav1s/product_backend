import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import { Product } from './product';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class InventoryService {

  private inventoryUrl = 'http://inventory-amazin-dev.apps.ocp.datr.eu/products/';

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    const url = `${this.inventoryUrl}/all`;
    return this.http.get<Product[]>(url)
    .pipe(
      tap(products => this.log(`fetched all products`)),
      catchError(this.handleError<Product[]>(`getAllProducts all`))
    );
  }

  getProductsByType(type: string): Observable<Product[]> {
    const url = `${this.inventoryUrl}/type/${type}`;
    return this.http.get<Product[]>(url).pipe(
      tap(_ => this.log(`fetched products type=${type}`)),
      catchError(this.handleError<Product[]>(`getProductsByType type=${type}`))
    );
  }

  getProduct(id: number): Observable<Product> {
    const url = `${this.inventoryUrl}/${id}`;
    return this.http.get<Product>(url).pipe(
      tap(_ => this.log(`fetched product id=${id}`)),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    console.log('InventoryService: ' + message);
  }
}
