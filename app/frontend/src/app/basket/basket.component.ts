import { Component, OnInit, OnChanges, Input, SimpleChanges } from '@angular/core';

import { BasketService } from '../services/basket.service';
import { Product } from '../model/product';
import { Basket } from '../model/basket';
import { User } from '../model/user';

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
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log("Something has changed !!");
    for (let propName in changes) {
      let chng = changes[propName];
      let cur  = JSON.stringify(chng.currentValue);
      let prev = JSON.stringify(chng.previousValue);
      console.log(`${propName}: currentValue = ${cur}, previousValue = ${prev}`);
      if (propName == "basket" && this.basket)  {
        console.log("lets load the basket");
        console.log("BasketComponent : basket BEFORE : " + JSON.stringify(this.basket));
        this.basketService.getBasket(this.basket).subscribe( (basket: Basket) => {
          console.log("BasketComponent : basket AFTER : " + JSON.stringify(basket));
          this.basket = basket
          this.basketService.setBasket(basket);
        });
      }
    }
  }

  onClickRemoveProduct(event: Event)  {
    console.log("BasketComponent onClickRemoveProduct");
    let itemAtIndex: number = event.target.attributes.getNamedItem('data-index').value;
    console.log("Delete item at index : " +itemAtIndex);
    this.basketService.removeProductFromBasket(itemAtIndex).subscribe( (basket: Basket) => {
      console.log("BasketComponent : removeProductFromBasket AFTER : " + JSON.stringify(basket));
      this.basket = basket
      this.basketService.setBasket(basket);
    });
  }

}
