import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { InventoryComponent }   from './inventory/inventory.component';
import { DetailsComponent }   from './details/details.component';

const routes: Routes = [
  { path: '', redirectTo: '/show', pathMatch: 'full' },
  { path: 'show', component: InventoryComponent },
  { path: 'show/:productType', component: InventoryComponent },
  { path: 'show/details/:productId', component: DetailsComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, { enableTracing: true }), ],
  exports: [ RouterModule ],
  declarations: []
})
export class AppRoutingModule { }
