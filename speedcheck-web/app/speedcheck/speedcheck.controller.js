import d3 from "d3";
import speedcheckTemplate from './speedcheck.html';
"use strict"

const DOWNLOAD = 'DOWNLOAD', UPLOAD = 'UPLOAD';

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

    constructor($http, $filter, speedcheckService, $mdSidenav) {
        this.$http = $http;
        this.$filter = $filter;
        this.speedcheckService = speedcheckService;
        this.options = options;
        this.$mdSidenav = $mdSidenav;
    }

    $onInit() {
        this.getData();
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
                x: item.timestamp,
                y: item.speed
            };
        });
    }

}

const definition = {
    template: speedcheckTemplate,
    controller: Ctrl,
    controllerAs: 'ctrl'
}

Ctrl.$inject = ['$http', '$filter', 'speedcheckService', '$mdSidenav'];
export default definition;