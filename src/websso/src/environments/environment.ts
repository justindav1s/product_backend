// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import { KeycloakConfig } from 'keycloak-angular';

// let keycloakConfig: KeycloakConfig = {
//   url: 'http://127.0.0.1:8080/auth',
//   realm: 'amazin',
//   clientId: 'webapp',
//   "credentials": {
//     "secret": "cc48f8ad-80d3-4019-8d36-7dfb6f5c86e7"
//   }
// };


let keycloakConfigOcp: KeycloakConfig = {
  url: 'https://sso-sso.apps.ocp.datr.eu/auth',
  realm: 'amazin',
  clientId: 'webapp'
};

export const environment = {
  production: false,
  inventory_backend : "http://api-gateway-amazin-dev.apps.ocp.datr.eu",
  basket_backend : "http://api-gateway-amazin-dev.apps.ocp.datr.eu",
  user_backend : "http://api-gateway-amazin-dev.apps.ocp.datr.eu",
  keycloak: keycloakConfig
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
