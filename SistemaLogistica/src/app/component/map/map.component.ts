import { HttpClient } from '@angular/common/http';

import { Component, OnInit } from '@angular/core';
import { UntypedFormArray } from '@angular/forms';
import { MapInfoWindow, MapMarker } from '@angular/google-maps';
import { Observable, catchError, map, of } from 'rxjs';
import { CongestionDto } from 'src/app/dto/congestion-dto';
import { SemaforoDto } from 'src/app/dto/semaforo-dto';
import { Estado } from 'src/app/modelo/semaforo';
import { CongestionService } from 'src/app/service/congestion.service';
import { SemaforoService } from 'src/app/service/semaforo.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {


  estado: Estado = Estado.NONE
  tiempo: number = 0
  currentId = "";
  current: SemaforoDto | undefined
  center: google.maps.LatLngLiteral = { lat: 30.967011756830306, lng: -110.32392624978195 }
  optionsMap: google.maps.MapOptions = {
    maxZoom: 20, minZoom: 8,
    restriction: {
      latLngBounds: {
        north: this.center.lat + 0.005,
        south: this.center.lat - 0.005,
        east: this.center.lng + 0.007,
        west: this.center.lng - 0.007
      }

    }, mapTypeControl: false,
    streetViewControl: false,
    zoomControl: true,
    fullscreenControl: false
  }
  apiLoaded: Observable<boolean>;


  constructor(httpClient: HttpClient, private semaforoService: SemaforoService, private congestionService: CongestionService) {
    this.apiLoaded = httpClient.jsonp('https://maps.googleapis.com/maps/api/js?key=' + environment.MAPS_KEY, 'callback')
      .pipe(
        map(() => true),
        catchError(() => of(false)),
      );
  }


  ngOnInit(): void {
    this.congestionService.recibir()
  }


  getSemaforos(): SemaforoDto[] {
    return this.semaforoService.semaforos
  }

  getCurrent(marker: SemaforoDto) {

    this.current = marker

  }

  getCongestion(): CongestionDto[] {
    return this.congestionService.congestiones;
  }

  onActualizar() {

    if (this.currentId) {

      this.semaforoService.enviarSemaforo(this.currentId, this.estado, this.tiempo);

    }
  }
  onCancelar() {
    alert("Override Method");
  }

  obtenerEstado(value: Estado) {

    if (value == 'VERDE') {
      this.estado = Estado.VERDE
    }
    if (value == 'AMARILLO') {
      this.estado = Estado.AMARILLO
    }
    if (value == 'ROJO') {
      this.estado = Estado.ROJO
    }

  }
  obtenerTiempo(value: number) {

    this.tiempo = value

  }

  obtenerIdSemaforo(value: string) {

    this.currentId = value;
  }
  public openInfoWindow(marker: MapMarker, infoWindow: MapInfoWindow) {
    infoWindow.open(marker);
  }

  public resolver(id: number) {
  
      this.congestionService.resolver(id)
  }

  
  public get service() : CongestionService {
    return this.congestionService
  }
  
}
