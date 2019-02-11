import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'templates-test';
  private i: number = 0;
  public change() {
  	this.title = this.i++ + '';
  }
}
