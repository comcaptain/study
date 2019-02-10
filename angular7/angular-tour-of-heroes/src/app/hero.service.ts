import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Hero } from './hero'
import { Observable, of } from 'rxjs'
import { map, catchError, tap } from 'rxjs/operators'
import { MessageService } from './message.service'

const HTTP_OPTIONS = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

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

  updateHero(hero: Hero): Observable<any> {
    return this.http.put(this.heroesURL, hero, HTTP_OPTIONS).pipe(
      tap(_ => this.log(`Updated hero ${hero.id}`)),
      catchError(this.handleError<Hero>("getHero"))
    )
  }

  addHero(hero: Hero): Observable<Hero> {
    return this.http.post<any>(this.heroesURL, hero, HTTP_OPTIONS).pipe(
      map(v => v.data),
      tap(hero => this.log(`Added hero: id=${hero.id}`)),
      catchError(this.handleError<Hero>('addHero'))
    );
  }

  deleteHero(hero: Hero | number): Observable<Hero> {
    const id = typeof hero === 'number' ? hero : hero.id;
    const url = `${this.heroesURL}/${id}`
    return this.http.delete<any>(url, HTTP_OPTIONS).pipe(
      map(v => v.data),
      tap(hero => this.log(`Deleted hero: id=${hero.id}`)),
      catchError(this.handleError<Hero>('addHero'))
    );
  }

  searchHeroes(term: string): Observable<Hero[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.http.get<any>(`${this.heroesURL}/?name=${term}`).pipe(
      map(v => v.data),
      tap(_ => this.log(`Found ${_.length} heroes matching "${term}"`)),
      catchError(this.handleError<Hero[]>("searchHeroes", []))
    );
  }
}
