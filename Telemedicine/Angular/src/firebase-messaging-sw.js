importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-messaging.js');
firebase.initializeApp({
  apiKey: "AIzaSyCclHFb655qbfeAcWCSvzY3_QwwWPS1o8Y",
  authDomain: "protean-clinic.firebaseapp.com",
  projectId: "protean-clinic",
  storageBucket: "protean-clinic.appspot.com",
  messagingSenderId: "861263268271",
  appId: "1:861263268271:web:86133e87b2719ee635075a",
  measurementId: "G-BC309270GY"
});
const messaging = firebase.messaging();
