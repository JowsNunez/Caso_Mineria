import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MensajeriaModule } from './mensajeria/mensajeria.module';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { GoogleMapsModule } from '@angular/google-maps';
import { SemaforoPipe } from './semaforo.pipe';
import { MapComponent } from './component/map/map.component';
import { ControlSemaforoComponent } from './component/control-semaforo/control-semaforo.component';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { MenuComponent } from './component/menu/menu.component';
import { LoginComponent } from './component/login/login.component';
import { HomeComponent } from './component/home/home.component';
import { AlertInfoComponent } from './component/alert-info/alert-info.component';

@NgModule({
  declarations: [
    AppComponent,
    SemaforoPipe,
    MapComponent,
    ControlSemaforoComponent,
    MenuComponent,
    LoginComponent,
    HomeComponent,
    AlertInfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MensajeriaModule,GoogleMapsModule,
    HttpClientModule,
    HttpClientJsonpModule,FormsModule,
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
