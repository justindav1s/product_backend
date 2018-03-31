import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { InventoryService } from '../inventory.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  products: Product[];

  constructor(private inventoryService: InventoryService) { }

  ngOnInit() {
    this.getAllProducts();
  }

  getAllProducts(): void {
    this.inventoryService.getAllProducts().subscribe(products => this.products = products);
  }

}
