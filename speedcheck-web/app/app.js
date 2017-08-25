import angular from 'angular';
import 'angular-aria';
import 'angular-animate';
import 'angular-material';
import 'angular-material/angular-material.min.css';
import 'nvd3/build/nv.d3.min.css';
import 'material-design-icons/iconfont/material-icons.css'
import './speedcheck/speedcheck.module';
import 'sockjs-client';
import 'stompjs';
"use strict"

angular.module('app', ['ngMaterial', 'speedcheck']);
