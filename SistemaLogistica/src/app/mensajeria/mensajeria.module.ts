import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RxStompService } from './stomp.service';
import { rxStompServiceFactory } from './stomp.service.factory';
import { RabbitDespachadorMensaje } from './rabbit.mensajeria';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ], providers: [
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory
    },
    {
      provide: "Rabbit",
    useClass:RabbitDespachadorMensaje}
  ]
})
export class MensajeriaModule { }
