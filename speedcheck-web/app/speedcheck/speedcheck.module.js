import angular from 'angular';
import 'd3';
import 'nvd3';
import 'angular-nvd3';
import 'angular-material-data-table/dist/md-data-table.min.css';
import 'angular-material-data-table';
import speedcheckCtrl from './speedcheck.controller';
import speedcheckService from './speedcheck.service';
import resultsTableCtrl from './table/results-table.controller';
import navCtrl from './nav/nav.controller';
import config from './speedcheck.config';
"use strict"

export default angular.module('speedcheck', ['nvd3', 'ngMaterial', 'md.data.table'])
                      .component('speedcheck', speedcheckCtrl)
                      .service('speedcheckService', speedcheckService)
                      .component('rrtable', resultsTableCtrl)
                      .component('navigation', navCtrl)
                      .config(config);
