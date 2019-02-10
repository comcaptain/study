import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Hero } from './hero'
import { HEROES } from './mock-heroes'
import { Observable, of } from 'rxjs'
import { map } from 'rxjs/operators'
import { MessageService } from './message.service'

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  private heroesURL:string = 'api/heroes'

  constructor(
    private messageService: MessageService,
    private http: HttpClient) { }

  getHeroes(): Observable<Hero[]> {
  	this.log("HeroService: fetching heroes");
  	return this.http.get<any>(this.heroesURL).pipe(map(v => v.data));
  }

  private log(message: string) {
    this.messageService.add(message)
  }

  getHero(id: number): Observable<Hero> {
  	this.log(`Hero Service: fetching hero id = ${id}`);
  	return of(HEROES.find(hero => hero.id === id));
  }
}
