import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    private basketService: BasketService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit() {
    this.getAllProducts();
    this.getProduct();
      this.createBasket();
  }

  getAllProducts(): void {
    const productType = this.route.snapshot.paramMap.get('productType');
    console.log("productType : " productType);
    this.inventoryService.getProductsByType(productType).subscribe(products => this.products = products);
  }

  getProduct(): void {
    const productId = this.route.snapshot.paramMap.get('productId');
    console.log("productId : " productId);
    this.inventoryService.getProduct(productId).subscribe(product => this.product = product);
  }

  createBasket(): void {
    console.log("creating basket");
    this.basketService.createBasket().subscribe(basket => this.basket = basket);
  }
