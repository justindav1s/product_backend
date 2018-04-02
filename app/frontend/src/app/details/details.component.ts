import { Component, OnInit } from '@angular/core';
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

  product: Product;
  basket: Basket;

  constructor(
    private inventoryService: InventoryService,
    private basketService: BasketService
  ) {}

  ngOnInit() {
    if (this.basket == null) {
      this.createBasket();
    }
  }

  createBasket(): void {
    console.log("creating basket");
    this.basketService.createBasket().subscribe(basket => this.basket = basket);
  }
