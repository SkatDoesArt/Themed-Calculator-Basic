const CACHE_NAME = 'calculator-v1';
const ASSETS = [
  './',
  './index.html',
  './composeApp.js',
  './composeApp.wasm',
  './styles.css',
  './manifest.json'
];

self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => cache.addAll(ASSETS))
  );
});

self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => {
      return response || fetch(event.request);
    })
  );
});