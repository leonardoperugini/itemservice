import http from "k6/http";
import { check, sleep } from "k6";

export let options = {
  vus: 20, // utenti virtuali
  duration: "60s",
};

export default function () {
  // GET all items
  let res = http.get("http://127.0.0.1:8080/api/items");
  check(res, { "GET all items status 200": (r) => r.status === 200 });
  sleep(0.5);

  // POST create item
  let payload = JSON.stringify({
    name: `Item ${__VU}-${__ITER}`,
    description: "Test",
  });
  let params = { headers: { "Content-Type": "application/json" } };
  res = http.post("http://127.0.0.1:8080/api/items", payload, params);
  check(res, {
    "POST create item status 201/200": (r) =>
      r.status === 201 || r.status === 200,
  });
  let itemId = null;
  try {
    itemId = res.json("id");
  } catch (e) {}
  sleep(0.5);

  // PUT update item (solo se id disponibile)
  if (itemId) {
    let updatePayload = JSON.stringify({
      name: `Item updated ${__VU}-${__ITER}`,
      description: "Updated",
    });
    res = http.put(
      `http://127.0.0.1:8080/api/items/${itemId}`,
      updatePayload,
      params
    );
    check(res, { "PUT update item status 200": (r) => r.status === 200 });
    sleep(0.5);

    // DELETE item
    res = http.del(`http://127.0.0.1:8080/api/items/${itemId}`);
    check(res, {
      "DELETE item status 204/200": (r) => r.status === 204 || r.status === 200,
    });
    sleep(0.5);
  }
}
