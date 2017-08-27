import angular from 'angular';
import d3 from "d3";
import speedcheckTemplate from './speedcheck.html';
import notificationTemplate from './notification.html';
import Stomp from "stompjs";
import SockJS from "sockjs-client";
"use strict"

const DOWNLOAD = 'DOWNLOAD', UPLOAD = 'UPLOAD';
const TOAST_DELAY = 5000;

let options = {
    chart: {
        type: 'lineWithFocusChart',
        color: ["#2F99CA","#CA2F2F"],
        height: 400,
        margin : {
            top: 20,
            right: 50,
            bottom: 60,
            left: 50
        },
        duration: 50,
        xAxis: {
            axisLabel: 'Date (dd/mm/yy)',
            tickFormat: function(d){
                return d3.time.format('%d/%m/%y %H:%M')(new Date(d));
            }
        },
        x2Axis: {
            tickFormat: function(d){
                return d3.time.format('%d/%m/%y %H:%M')(new Date(d));
            }
        },
        yAxis: {
            axisLabel: 'Mb/s',
            tickFormat: function(d){
                return d3.format(',.2f')(d);
            },
            rotateYLabel: false
        },
        y2Axis: {
            tickFormat: function(d){
                return d3.format(',.2f')(d);
            }
        }

    }
};

class Ctrl {

    constructor($http, $filter, speedcheckService, $mdSidenav, $mdToast) {
        this.$http = $http;
        this.$filter = $filter;
        this.speedcheckService = speedcheckService;
        this.options = options;
        this.$mdSidenav = $mdSidenav;
        this.$mdToast = $mdToast;
    }

    $onInit() {
        this.getData();
        this.connectSocket();
    }

    getData() {
        this.speedcheckService.getResults().then(data => {
            this.results = data;
            this.dataGenerator(data.results);
        });
    }

    dataGenerator(data) {
        this.data = [
            this.generateGroup(data, DOWNLOAD),
            this.generateGroup(data, UPLOAD)
        ];
    }

    generateGroup(data, type) {
        return {
            key: type,
            values : this.generateValues(data, type)
        }
    }

    generateValues(data, type) {
        return data.filter(r => {
            return r.type === type
        }).map(item => {
            return {
                x: new Date(item.timestamp),
                y: item.speed
            };
        });
    }

    showNotification() {
        this.$mdToast.show({
            template: notificationTemplate,
            controller: function Ctrl() {},
            controllerAs: 'ctrl',
            bindToController: true,
            position: "top right",
            hideDelay: TOAST_DELAY,
            locals: {
                notificationData : this.notificationData
            }
        });
    }

    connectSocket() {
        let socket = new SockJS('/speedcheck-websocket');
        let stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            stompClient.subscribe('/topic/speedtest', payload => {
                this.notificationData = angular.fromJson(payload.body);
                this.showNotification();
            });
        });

    }
}

const definition = {
    template: speedcheckTemplate,
    controller: Ctrl,
    controllerAs: 'ctrl'
}

Ctrl.$inject = ['$http', '$filter', 'speedcheckService', '$mdSidenav', '$mdToast'];
export default definition;