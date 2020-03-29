import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { AppData } from '../model/app-data';
import { AppCode } from '../model/app-code'; 

const CONTEXT_ROOT = environment.contextRoot;
const APPCODE_URL = environment.appCode_url; 
const APPDATAID_URL = environment.appDataById_url; 

@Injectable()
export class AppDataApiService {

  constructor(
    private http: Http
  ) { }

  public getAppCodes(): Observable<AppCode[]> {

    return this.http
      .get(CONTEXT_ROOT + APPCODE_URL) 
      .map( response => {
        const appCodes = response.json(); 
        return appCodes.map((row) => new AppCode(row)); 
      })
      .catch(this.handleError); 
  } 

  public getAppDataByCode(appcode: number): Observable<AppData[]> {

    return this.http
      .get(CONTEXT_ROOT + APPDATAID_URL + appcode ) 
      .map(response => {
        const appdata = response.json(); 
        return appdata.map((row) => new AppData(row)); 
      })
      .catch(this.handleError); 
  }

  private handleError (error: Response | any) {
    console.error('AppDataApiService::handleError', error);
    return Observable.throw(error);
  }

} 
