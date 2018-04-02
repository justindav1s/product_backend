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
