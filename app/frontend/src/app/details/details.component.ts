import { Component, OnInit, Input } from '@angular/core';
import { Location } from '@angular/common';

import { Product } from '../product';
import { Basket } from '../basket';

import { InventoryService } from '../inventory.service';
import { BasketService } from '../basket.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  @Input() product: Product;
  basket: Basket;

  constructor(
    private inventoryService: InventoryService,
    private basketService: BasketService
  ) {}

  ngOnInit() {
  }

  onAddToBasket(product: Product) : void  {
    console.log("Add to basket : "  + product.id + ":" + product.name );
    this.basketService.addProductToBasket(product).subscribe( (basket : Basket) => {
      console.log("AppComponent : createBasket : " +basket.id);
      this.basket = basket;
    });
  }

}
