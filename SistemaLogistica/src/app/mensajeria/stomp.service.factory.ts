import { RxStompService } from './stomp.service';
import { myRxStompConfig } from './stomp.config';

export function rxStompServiceFactory() {

  const rxStomp = new RxStompService();
  rxStomp.configure(myRxStompConfig("ws://127.0.0.1:15674/ws","guest","guest"));
  rxStomp.activate();
  return rxStomp;
}

