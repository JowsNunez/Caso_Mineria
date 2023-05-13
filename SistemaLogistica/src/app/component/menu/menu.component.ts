import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {


  isMenuOpen = false
  deslisar: string = "";
  deslisar2: string = "";

  constructor() { }

  ngOnInit(): void {

  }

  openMenu() {
    if (!this.isMenuOpen) {
      this.isMenuOpen = true;
      this.deslisar = "opened"
    }
  }
  closeMenu() {
    if (this.isMenuOpen) {
      this.deslisar = "closed"
      setTimeout(() => {
        this.isMenuOpen = false
      }, 500)
    }

  }


}
