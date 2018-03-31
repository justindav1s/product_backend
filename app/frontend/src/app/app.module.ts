import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';

import { FormsModule }    from '@angular/forms';
import { HttpClientModule }    from '@angular/common/http';
import { AppRoutingModule }     from './app-routing.module';
import { InventoryService }          from './inventory.service';
import { InventoryComponent } from './inventory/inventory.component';
import { CategoriesComponent } from './categories/categories.component';
import { CategoriesService } from './categories.service';
import { TitleCasePipe } from './title-case.pipe';
import { DetailsComponent } from './details/details.component';

@NgModule({
  declarations: [
    AppComponent,
    InventoryComponent,
    CategoriesComponent,
    TitleCasePipe,
    DetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [InventoryService, CategoriesService,],
  bootstrap: [AppComponent]
})
export class AppModule { }
