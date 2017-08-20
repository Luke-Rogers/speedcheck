import angular from 'angular';
import 'd3';
import 'nvd3';
import 'angular-nvd3';
import speedcheckCtrl from './speedcheck.controller';
import speedcheckService from './speedcheck.service';
import resultsTableCtrl from './table/results-table.controller';
import navCtrl from './nav/nav.controller';
import 'angular-material-data-table/dist/md-data-table.min.css';
import 'angular-material-data-table';
"use strict"

export default angular.module('speedcheck', ['nvd3', 'ngMaterial', 'md.data.table'])
                      .component('speedcheck', speedcheckCtrl)
                      .service('speedcheckService', speedcheckService)
                      .component('rrtable', resultsTableCtrl)
                      .component('navigation', navCtrl)
                      .config(function config($mdThemingProvider) {
                          $mdThemingProvider.theme('default')
                              .primaryPalette('blue')
                              .accentPalette('blue');
                      });
