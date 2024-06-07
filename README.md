# Safe Driving Awareness

The goal of the "Safe Driving Awareness" project is to raise
awareness about the dangers and legal consequences of driving at illegal speeds.

The idea of this project is to calculate the routes based on different
speed limits, allowing the drivers to see the minimal time gained by speeding.

It is also showing the penalties received based on the selected speed, so the drivers can ask
themselves if it is worth it.

The project is based on Romania's speed limits and penalties.
## Screenshots:

<details>
<summary> 1. Route calculated with no speed increase: </summary>
<img width="800" alt="image" src="https://github.com/cristianm98/safe-driving-awareness-app/assets/48805198/69ce1546-ccd4-4e04-88c3-0d2efa4d7ce1">
</details>

<details>
  <summary>
    2. Route calculated with speed increased by 15 km/h:
  </summary>
  <img width="800" alt="image" src="https://github.com/cristianm98/safe-driving-awareness-app/assets/48805198/b2a9ee70-e641-4181-96d3-ce82f0ba134e">
</details>

<details>
  <summary>
    3. Route calculated with speed increased by 40 km/h:
  </summary>
  <img width="800" alt="image" src="https://github.com/cristianm98/safe-driving-awareness-app/assets/48805198/54f3a1e1-98da-40d9-b9cc-e37cd912819a">
</details>

<details>
  <summary>
    4. For each selected speed increase  option,  the new speed values are calculated for each road category:
  </summary>
  <img width="800" alt="image" src="https://github.com/cristianm98/safe-driving-awareness-app/assets/48805198/fdc32fe5-0c02-4752-8e83-e0e7eca22948">
</details>

<details>
  <summary>
    5. For each selected speed increase option, the penalties are shown:
  </summary>
  <img width="800" alt="image" src="https://github.com/cristianm98/safe-driving-awareness-app/assets/48805198/596d6ccd-0dc7-4835-abb6-6098aca626aa">
</details>

## Technologies used
- Java 21
- Spring Boot 3
- Maven
- Thymeleaf
- Docker
- Lombok

Routes are displayed using _Leaflet Routing Machine_ JavaScript library which is configured to use _Nominatim_ API
in order for address searching. \
The routes are calculated using _GraphHopper_.

Links:
- GraphHopper: https://www.graphhopper.com/
- Nominatim API: https://nominatim.org/release-docs/develop/api/Overview/
- Leaflet Routing Machine: https://www.liedman.net/leaflet-routing-machine/

## Running the project on local
1. Download latest osm file: https://download.geofabrik.de/europe/romania.html
2. Inside _application.yaml_: set the property _graphHopper.osmFilePath_ to the path where the file from step 1 is located
3. Run the app; it will be available on http://localhost:8080