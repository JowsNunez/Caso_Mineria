import { RxStompService } from './stomp.service';
import { myRxStompConfig } from './stomp.config';
import { environment } from 'src/environments/environment';

export function rxStompServiceFactory() {

  const rxStomp = new RxStompService();
  rxStomp.configure(myRxStompConfig(environment.BROKER_HOST,environment.BROKER_USER,environment.BROKER_PASS));
  rxStomp.activate();
  return rxStomp;
}

