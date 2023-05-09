import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubjectMessageService {

    private subject = new Subject<any>();

    sendMessage(message: any) {
        this.subject.next({ text: message });
    }

    clearMessage() {
        this.subject.next();
    }

    onMessage(): Observable<any> {
        return this.subject.asObservable();
    }
    
    onMessageToModal(): Observable<any> {
        return this.subject.asObservable();
    }
}
