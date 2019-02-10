import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Hero } from './hero'
import { HEROES } from './mock-heroes'
import { Observable, of } from 'rxjs'
import { map, catchError, tap } from 'rxjs/operators'
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
  	return this.http.get<any>(this.heroesURL)
      .pipe(
        map(v => v.data),
        tap(v => this.log(`Fetched ${v.length} heroes`)),
        catchError(this.handleError("getHeroes", [])));
  }

  private log(message: string) {
    this.messageService.add(message)
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`)
      return of(result);
    }
  }

  getHero(id: number): Observable<Hero> {
    const url = `${this.heroesURL}/${id}`;
    return this.http.get<any>(url).pipe(
      map(_ => _.data),
      tap(hero => this.log(`Fetched hero ${hero.id}`)),
      catchError(this.handleError<Hero>("getHero"))
    );
  }
}
