import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 10, // utenti virtuali
  duration: '30s',
};

export default function () {
  const res = http.get('http://localhost:8080/items'); // Modifica endpoint se necessario
  check(res, { 'status was 200': (r) => r.status === 200 });
  sleep(1);
}
