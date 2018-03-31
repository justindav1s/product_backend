import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { InventoryService } from '../inventory.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  product: Product;

  constructor(
    private inventoryService: InventoryService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit() {
    this.getProduct();
  }

  getProduct(): void {
    const productId = this.route.snapshot.paramMap.get('productId');
    console.log("productId : " productId);
    this.inventoryService.getProduct(productId).subscribe(product => this.product = product);
  }
}
