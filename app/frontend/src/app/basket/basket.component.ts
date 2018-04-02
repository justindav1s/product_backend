import { Component, OnInit } from '@angular/core';

import { BasketService } from '../basket.service';
@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit {

  constructor(
    private basketService: BasketService
  ) { }

  ngOnInit() {
    this.createBasket();
  }

  createBasket(): void {
    console.log("creating basket");
    this.basketService.createBasket().subscribe( (basket : Basket) => {
      console.log("AppComponent : createBasket : " +basket.id);
      this.basket = basket;
    });
  }
}
