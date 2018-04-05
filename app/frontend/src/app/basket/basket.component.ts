import { Component, OnInit, OnChanges, Input } from '@angular/core';

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
  @Input() basket : Basket;
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

  ngOnChanges(changes: SimpleChanges) {
    for (let propName in changes) {
      let chng = changes[propName];
      let cur  = JSON.stringify(chng.currentValue);
      let prev = JSON.stringify(chng.previousValue);
      console.log(`${propName}: currentValue = ${cur}, previousValue = ${prev}`);
      if (propName == "basket")  {
        console.log("lets load the basket");  
      }
    }
  }

}
