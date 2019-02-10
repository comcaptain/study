import { Injectable } from '@angular/core';
import { LogMessage } from './log-message'

@Injectable({
  providedIn: 'root'
})
export class MessageService {

	messages: LogMessage[] = [];

	add(message: string) {
		this.messages.push({
			time: new Date(),
			content: message
		});
	}

	clear() {
		this.messages = [];
	}
}
