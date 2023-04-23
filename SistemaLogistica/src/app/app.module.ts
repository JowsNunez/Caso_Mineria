import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MensajeriaModule } from './mensajeria/mensajeria.module';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { GoogleMapsModule } from '@angular/google-maps';
import { SemaforoPipe } from './semaforo.pipe';

@NgModule({
  declarations: [
    AppComponent,
    SemaforoPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MensajeriaModule,GoogleMapsModule,
    HttpClientModule,
    HttpClientJsonpModule,FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
