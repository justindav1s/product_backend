import { Component, OnInit, Input } from '@angular/core';

import { BasketService } from '../basket.service';
import { Product } from '../product';
import { Basket } from '../basket';
import { User } from '../user';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit {
  @Input() user: User;
  basket : Basket;
  product : Product;
  products: Product[] = new Array();

  constructor(
    private basketService: BasketService
  ) { }

  ngOnInit() {
    console.log("BasketComponent ngOnInit user : "  + this.user);
    if (this.user) {
      this.createBasket(this.user);
    }
  }


}
