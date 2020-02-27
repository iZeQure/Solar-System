// Assign Planets.
int sun = A0;
int mercury = A1;
int venus = 2;
int earth = 3;
int mars = 4;
int jupiter = 5;
int saturn = 6;
int uranus = 7;
int neptune = 8;
int pluto = 9;

void setup() {
  // Assign Planet Pin Modes.
  pinMode(sun, OUTPUT);         // SUN
  pinMode(mercury, OUTPUT);     // Mercury
  pinMode(venus, OUTPUT);       // Venus
  pinMode(earth, OUTPUT);       // Earth
  pinMode(mars, OUTPUT);        // Mars
  pinMode(jupiter, OUTPUT);     // Jupiter
  pinMode(saturn, OUTPUT);      // Saturn
  pinMode(uranus, OUTPUT);      // Uranus
  pinMode(neptune, OUTPUT);     // Neptune
  pinMode(pluto, OUTPUT);       // Pluto

  Serial.begin(4800);           // Open serial port. Sets data rate to 9600 bps.

  while (!Serial) {
    ;
  }
  Serial.println("\n ## Solar System Controller ## \n");
}

void loop() {
  if (Serial.available() > 0) {
    delay(50); // Waits for the whole message.

    char ch = Serial.read();
    changePlanetState(ch);
  }
}

void changePlanetState(char pin) {
  switch(pin) {
    case '0':
      checkPlanetPinState(sun);
      break;
    case '1':
      checkPlanetPinState(mercury);
      break;
    case '2':
      checkPlanetPinState(venus);
      break;
    case '3':
      checkPlanetPinState(earth);
      break;
    case '4':
      checkPlanetPinState(mars);
      break;
    case '5':
      checkPlanetPinState(jupiter);
      break;
    case '6':
      checkPlanetPinState(saturn);
      break;
    case '7':
      checkPlanetPinState(uranus);
      break;
    case '8':
      checkPlanetPinState(neptune);
      break;
    case '9':
      checkPlanetPinState(pluto);
      break;
  }
}

void checkPlanetPinState(int pin) {
  if (digitalRead(pin) == HIGH) {    
    digitalWrite(pin, LOW);
    
    Serial.print("Turned off planet : ");
    Serial.println(pin);
  }
  else {
    digitalWrite(pin, HIGH);
    
    Serial.print("Turned on planet : ");
    Serial.println(pin);
  }
}
