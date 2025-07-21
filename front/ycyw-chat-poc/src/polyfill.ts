(window as any).global = window;

/***************************************************************************************************
 * BROWSER POLYFILLS
 */

/** IE10 and IE11 requires the following for NgClass support on SVG elements */
// import 'classlist.js';  // Run `npm install --save classlist.js`.

/**
 * Web Animations `@angular/platform-browser/animations`
 * Only required if AnimationBuilder is used within the application and using IE/Edge or Safari.
 * Standard animation support in Angular does not require any polyfills (as of Angular 8.0).
 */
// import 'web-animations-js';  // Run `npm install --save web-animations-js`.

/**
 * By default, zone.js will patch all possible macroTask and DomEvents
 * user can disable parts of macroTask/DomEvents patching
 * by setting following flags
 */

 // (window as any).__Zone_disable_IE_check = true;
// (window as any).__Zone_disable_ZoneAwarePromise = true;
// (window as any).__zone_symbol__UNPATCHED_EVENTS = ['scroll', 'mousemove'];
/***************************************************************************************************
 * Zone JS is required by default for Angular itself.
 */
import 'zone.js';  // Included with Angular CLI.
