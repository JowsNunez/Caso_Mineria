import { Component, Input, OnInit } from '@angular/core';
import { Type } from 'src/app/modelo/congestion';
import { CongestionService } from 'src/app/service/congestion.service';

@Component({
  selector: 'app-alert-info',
  templateUrl: './alert-info.component.html',
  styleUrls: ['./alert-info.component.css']
})
export class AlertInfoComponent implements OnInit {

  @Input('id')
  public id: number | undefined

  @Input('description')
  public description: string | undefined
  @Input('type')
  public type: Type | undefined

  @Input() resolver: CongestionService | undefined;
  doResolve(): void {
    console.log(this.id)
    if (this.resolver && this.id) {
    console.log(this.id)
    console.log(this.resolver)

      this.resolver.resolver(this.id);
    }
  }

  constructor() { }

  




  ngOnInit(): void {
  }

 

}
