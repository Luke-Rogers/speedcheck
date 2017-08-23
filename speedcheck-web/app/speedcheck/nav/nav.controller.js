import navTemplate from './nav.html';
"use strict"

const TEST_OPTIONS = [
    {"type": "BOTH", "name": "Download & Upload"},
    {"type": "DOWNLOAD", "name": "Download"},
    {"type": "UPLOAD", "name": "Upload"}
];

const FILTER_OPTIONS = [
    {"type": "DOWNLOAD", "name": "Download"},
    {"type": "UPLOAD", "name": "Upload"}
];

class Ctrl {

    constructor(speedcheckService, $mdSidenav) {
        this.speedcheckService = speedcheckService;
        this.$mdSidenav = $mdSidenav;
        this.filterTypeOptions = FILTER_OPTIONS;
        this.testTypeOptions = TEST_OPTIONS;
        this.showSummaryTooltip = false;
    }

    toggleSidebar() {
        this.$mdSidenav('left').toggle();
    }

    runTest() {
        this.testing = true;
        this.speedcheckService.runTest(this.testType).then(() => {
            this.refreshData();
            this.testing = false;
        }, () => {
            this.testing = false;
        });
    }

    filter() {
        this.speedcheckService.setFilters(this.filters);
        this.refreshData();
    }
}

Ctrl.$inject = ['speedcheckService', '$mdSidenav'];
export default {
    template: navTemplate,
    controller: Ctrl,
    controllerAs: 'ctrl',
    bindings: {
        refreshData: "&",
        results: "<"
    }
}