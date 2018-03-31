import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { InventoryService } from '../inventory.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  products: Product[];

  constructor(
    private inventoryService: InventoryService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit() {
    this.getAllProducts();
  }

  getAllProducts(): void {
    const productType = this.route.snapshot.paramMap.get('productType');
    console.log("productType : " productType);
    this.inventoryService.getProductsByType(productType).subscribe(products => this.products = products);
  }

}
