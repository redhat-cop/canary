import { Injectable } from '@angular/core';
import { AppDataApiService } from './app-data-api.service'; 
import { Observable } from 'rxjs/Observable';  
import { AppCode } from '../model/app-code'; 
import { AppData } from '../model/app-data'; 

@Injectable()
export class AppDataService {

  constructor(
    private api: AppDataApiService
  ) { }

  getAppCodes():Observable<AppCode[]>{
    return this.api.getAppCodes(); 
  }

  getAppDataByCode(appcode: number): Observable<AppData[]>{
    return this.api.getAppDataByCode(appcode); 
  }
  
}
