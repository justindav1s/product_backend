import { Component } from '@angular/core';

import { Product } from './product'
import { Basket } from './basket';

import { BasketService } from './basket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  products: Product[];
  product: Product;
  basket: Basket;

  title = 'Amazin - Shopping Reimagined';

  constructor(
    private basketService: BasketService
  ) {}

  onNewProductList(products : Product[])  {
    console.log("AppComponent : onNewProductList : " + products);
    this.products = products;
  }

  onNewSelectedProduct(product: Product)  {
      console.log("AppComponent : onNewSelectedProduct : " +product.id+":"+product.name+":"+product.price);
      this.product = product;
  }

}
