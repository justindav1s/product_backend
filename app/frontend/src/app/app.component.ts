import { Component } from '@angular/core';

import { Product } from './product'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  products: Product[];
  title = 'Amazin - Shopping Reimagined';

  onNewProductList(products : Product[])  {
    console.log("AppComponent : onNewProductList : " + products);
    this.products = products;
  }
}
